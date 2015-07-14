package ru.javarush.taskbook.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.javarush.taskbook.model.Role;
import ru.javarush.taskbook.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javarush.taskbook.repository.UserRepository;
import ru.javarush.taskbook.service.exceptions.UserAlreadyExistsException;
import ru.javarush.taskbook.util.CurrentUser;
import ru.javarush.taskbook.web.rest.exceptions.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String EMAIL_PATTERN = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@" +
            "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";

    @Autowired
    private UserRepository userRepository;

    public Set<Role> allowedRoles = new HashSet<Role>(){{
        add(Role.ADMIN);
    }};

    @Override
    @CacheEvict(value = {"userByUserName", "getAllUsers"}, allEntries=true)
    public void save(User user) throws UserAlreadyExistsException {

        if (user.getUsername() == null
                || user.getEmail() == null){
            throw new MissingDataException();
        }

        if (user.getPassword() == null || user.getPassword().equals("")){
            throw new EmptyPasswordException();
        }

        if (!user.getEmail().matches(EMAIL_PATTERN)){
            throw new MalformedEmailException();
        }

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new EmailAlreadyExistsException();
        }
        LOGGER.debug("Email: {} does not exist. Continuing registration.", user.getEmail());

        if (userRepository.findByUsername(user.getUsername()) != null){
            throw new LoginAlreadyExistsException();
        }
        LOGGER.debug("Username: {} does not exist. Continuing registration.", user.getUsername());

        try{
            userRepository.saveAndFlush(user);
        }
        catch (Exception e){
//            throw new UserAlreadyExistsException();
            // TODO: throw new FileNotFoundException();
            // TODO: throw new UnexpectedException();
        }
    }

    @Override
    public void directUpdate(User user){
        userRepository.saveAndFlush(user);
    }

    @Override
    @CacheEvict(value = {"userByUserName", "getAllUsers"}, allEntries=true)
    public void update(User user) {

        // Collections.disjoint() возвращает true если у коллекций НЕТ общих элементов, и false если они есть

        User userFromDb = getByName(user.getUsername());
        if (user.getId() == null) {
            if (userFromDb == null) {
                throw new NoSuchUserException(user.getUsername());
            }
            if (!Collections.disjoint(allowedRoles, CurrentUser.get().getRoles())) {
                userFromDb.setRoles(user.getRoles());
                userFromDb.setEnabled(user.isEnabled());
            } else {
                throw new OperationNotPerformedException();
            }
        } else {

           //Проверка на изменение логина
           if (getByName(user.getUsername()) == null) {
               throw new OperationNotPerformedException();
           }
           if (user.getEmail() != null) {
              if (!user.getEmail().equals(getByName(user.getUsername()).getEmail()) && getAllEmails().contains(user.getEmail())) {
                  throw new EmailAlreadyExistsException();
              } else if (!user.getEmail().toLowerCase().matches(EMAIL_PATTERN)) {
                  throw new MalformedEmailException();
              } else {
                  userFromDb.setEmail(user.getEmail());
              }
           }
           if (user.getCountry() != null && !user.getCountry().equals(getByName(user.getUsername()).getCountry())) {
               userFromDb.setCountry(user.getCountry());
           }
           if (user.getImageUrl() != null && !user.getImageUrl().equals(getByName(user.getUsername()).getImageUrl())) {
               userFromDb.setImageUrl(user.getImageUrl());
           }

           if (user.getRating() != 0.0 && user.getRating() != userFromDb.getRating())
           {
               userFromDb.setRating(user.getRating());
           }

           if (user.getPassword() != null && !user.getPassword().equals(getByName(user.getUsername()).getPassword())) {
               userFromDb.setPassword(user.getPassword());
           }
           if (user.getTasksSolved() != 0 && user.getTasksSolved() != userFromDb.getTasksSolved())
           {
               userFromDb.setTasksSolved(user.getTasksSolved());
           }
           if (user.getLastVisit() != null && !user.getLastVisit().equals(userFromDb.getLastVisit()))
           {
               userFromDb.setLastVisit(user.getLastVisit());
           }
        }
        userRepository.saveAndFlush(userFromDb);
    }

    @Override
    @CacheEvict(value = {"userByUserName", "getAllUsers"}, allEntries=true)
    public void delete(String username) { userRepository.delete(userRepository.findByUsername(username)); }

    @Override
    @Cacheable("getAllUsers")
    public List<User> getAll() {
        LOGGER.info("Executing method getAll Users");
        return userRepository.findAll();
    }

    @Override
    public User getByName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.debug("Loading user by username: {}", username);
        User user = userRepository.findByUsername(username);
        LOGGER.debug("Found user: {}", user);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user " +  username);
        }
        LOGGER.debug("Returning user details: {}", user);
        return user;
    }

    @Override
    public List<String> getAllEmails() {
        return userRepository.findAllEmails();
    }

    @Override
    public List<String> getAdminModerEmails() {
        return userRepository.findAdminModerEmails();
    }

    @Override
    public Page<User> findAllUsersOnPage(Pageable pageable, String nameQuery) {
        if(nameQuery.equals("")) {
            return userRepository.findAll(pageable);
        } else {
            return userRepository.findByUsernameContaining(nameQuery, pageable);
        }
    }

    @Override
    public User getById(int id) {
       return  userRepository.findById(id);
    }
}
