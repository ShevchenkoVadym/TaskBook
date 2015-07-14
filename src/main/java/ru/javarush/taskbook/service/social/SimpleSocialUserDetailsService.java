package ru.javarush.taskbook.service.social;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;
import ru.javarush.taskbook.model.User;
import ru.javarush.taskbook.repository.UserRepository;
import ru.javarush.taskbook.service.UserService;

import java.util.Date;

/**
 * Created by Сергей on 26.01.2015.
 */
@Service
public class SimpleSocialUserDetailsService implements SocialUserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleSocialUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    /**
     * Loads the username by using the account ID of the user.
     * @param userId    The account ID of the requested user.
     * @return  The information of the requested user.
     * @throws org.springframework.security.core.userdetails.UsernameNotFoundException    Thrown if no user is found.
     * @throws org.springframework.dao.DataAccessException
     */
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {
        LOGGER.debug("Loading user by user id: {}", userId);


        User user =  userRepository.findByEmail(userId);
        user.setLastVisit(new Date());
        userService.update(user);

        UserDetails userDetails = userRepository.findByEmail(userId);
        LOGGER.debug("Found user details: {}", userDetails);

        return (SocialUserDetails) userDetails;
    }
}