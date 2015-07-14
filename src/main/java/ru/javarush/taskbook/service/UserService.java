package ru.javarush.taskbook.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.javarush.taskbook.model.User;
import ru.javarush.taskbook.service.exceptions.UserAlreadyExistsException;

import java.rmi.UnexpectedException;
import java.util.List;


public interface UserService extends UserDetailsService {
        public void save(User user) throws UserAlreadyExistsException;
        public void directUpdate(User user);
        public void update(User user);
        public void delete(String username);
        public List<User> getAll();
        public User getByName(String name);
        public User getByEmail(String email);
        public List<String> getAllEmails();
        public List<String> getAdminModerEmails();
        public User getById(int id);

        public Page<User> findAllUsersOnPage(Pageable pageable, String nameQuery);

}
