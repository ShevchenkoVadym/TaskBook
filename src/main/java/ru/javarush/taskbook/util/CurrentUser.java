package ru.javarush.taskbook.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.javarush.taskbook.logger.LoggerWrapper;
import ru.javarush.taskbook.model.User;

/**
 * User: blacky
 * Date: 23.11.2014
 */
public class CurrentUser {
    private static final LoggerWrapper LOG = LoggerWrapper.get(CurrentUser.class);

    /*
    Return current logged user
    */
    /*
    public static Object get() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return null;
        return auth.getPrincipal();
    }
    */


    public static User get() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return null;
        Object user = auth.getPrincipal();
        return (user instanceof User) ? (User) user : null;
    }

    public static int getId() {
        User user = get();
        if (user == null) {
            throw LOG.getIllegalStateException("No authorized user found");
        }
        return user.getId();
    }

    public static User update(User newUser) {
        User current = get();
        current.setEmail(newUser.getEmail().toLowerCase());
        current.setPassword(newUser.getPassword());
        return current;
    }
}
