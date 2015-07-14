package ru.javarush.taskbook;

import org.springframework.http.MediaType;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.google.connect.GoogleAdapter;
import org.springframework.social.google.connect.GoogleServiceProvider;
import ru.javarush.taskbook.model.Role;
import ru.javarush.taskbook.model.SocialMediaService;
import ru.javarush.taskbook.model.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;

/**
 * User: blacky
 * Date: 23.11.2014
 */
public class TestUtil {


    public static final MediaType APPLICATION_JSON = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype());

    public static final int START_ID = 100000;

    final static ResourceBundle appResourceBundle = ResourceBundle.getBundle("application");
    final static ResourceBundle testResourceBundle = ResourceBundle.getBundle("test");

    public static final User USER00 = new User();
    public static final User USER01 = new User();
    public static final User USER02 = new User();
    public static final User NEW_USER = new User();
    public static final User SOCIAL_USER = new User();
    public static final Map<String, String> NEW_USER_MAP = new HashMap<>();
    public static final Map<String, String> USER00_MAP = new HashMap<>();

    public static final String UPDATED_NAME = "newName";
    public static final String UPDATED_EMAIL="newEmail@mail.ru";

    public static final OAuth2Connection GOOGLE_OAUTH2_CONNECTION;


    static {
        USER00.setId(START_ID);
        USER00.setUsername("ONE");
        USER00.setPassword("$2a$10$zoEDFVz0ANfe6nNPBgmBq.ngG3l09wMhRxNLT0WuBGKsSA/rrAOCe"); // pass: 12345
        USER00.setEnabled(true);
        USER00.setNonReadOnly(true);
        USER00.setCreationDate(new Date());
        USER00.setEmail("ONE@mail.ru");
/*
        UserRole userRole = new UserRole();
        userRole.setRole(Role.ADMIN);
        USER00.getUserRoles().add(userRole);
*/
        USER00.getRoles().add(Role.ADMIN);
        USER00.setCountry("Russian Federation");
        USER00.setImageUrl("img/user_photo_male.png");
        USER00.setRating(5.55);
        USER00.setTasksSolved(24);

        USER01.setId(START_ID + 1);
        USER01.setUsername("TWO");
        USER01.setPassword("$2a$10$nki82pz0NkFre1niB0mKN.hKAXDrXwfeF2Y.NHHfVtq0O7M.LjNba"); // pass: 123
        USER01.setEnabled(true);
        USER01.setNonReadOnly(true);
        USER01.setCreationDate(new Date());
        USER01.setEmail("TWO@mail.ru");
/*
        UserRole useRole2 = new UserRole();
        useRole2.setRole(Role.USER);
        USER01.getUserRoles().add(useRole2);
*/
        USER01.getRoles().add(Role.USER);
        USER01.setCountry("Canada");
        USER01.setImageUrl("img/user_photo_male.png");
        USER01.setRating(3.11);
        USER01.setTasksSolved(20);

        USER02.setId(START_ID + 2);
        USER02.setUsername("THREE");
        USER02.setPassword("$2a$10$2rlbkKyv9SErPH8h4zQlA.IxE3R.8n.1ufjY08sxGMB6OZtGM0SUy"); // pass: 234
        USER02.setEnabled(true);
        USER02.setNonReadOnly(true);
        USER02.setCreationDate(new Date());
        USER02.setEmail("THREE@mail.ru");
/*
        UserRole useRole3 = new UserRole();
        useRole3.setRole(Role.MODERATOR);
        USER02.getUserRoles().add(useRole3);
*/
        USER02.getRoles().add(Role.MODERATOR);
        USER02.setCountry("China");
        USER02.setImageUrl("img/user_photo_male.png");
        USER02.setRating(2.22);
        USER02.setTasksSolved(23);

        //NEW_USER.setId(NEW_USER_ID);
        NEW_USER.setUsername("vacula");
        NEW_USER.setPassword("$2a$10$zoEDFVz0ANfe6nNPBgmBq.ngG3l09wMhRxNLT0WuBGKsSA/rrAOCe"); // pass: 12345
        NEW_USER.setEnabled(false);
        NEW_USER.setNonReadOnly(false);
        NEW_USER.setCreationDate(new Date());
        NEW_USER.setEmail("vacula@mail.ru");
/*
        UserRole useRole4 = new UserRole();
        useRole4.setRole(Role.USER);
        NEW_USER.getUserRoles().add(useRole4);
*/
        NEW_USER.getRoles().add(Role.USER);
        NEW_USER.setCountry("Brazil");
        NEW_USER.setImageUrl("img/user_photo_male.png");
        NEW_USER.setRating(2.22);
        NEW_USER.setTasksSolved(23);

        SOCIAL_USER.setUsername("testsergiishapoval");
        SOCIAL_USER.setPassword("$2a$10$zoEDFVz0ANfe6nNPBgmBq.ngG3l09wMhRxNLT0WuBGKsSA/rrAOCe"); // pass: 12345
        SOCIAL_USER.setEnabled(true);
        SOCIAL_USER.setNonReadOnly(true);
        SOCIAL_USER.setCreationDate(new Date());
        SOCIAL_USER.setEmail("test.sergiishapoval@gmail.com");
/*
        UserRole useRole4 = new UserRole();
        useRole4.setRole(Role.USER);
        SOCIAL_USER.getUserRoles().add(useRole4);
*/
        SOCIAL_USER.getRoles().add(Role.USER);
        SOCIAL_USER.setCountry("Russian Federation");
        SOCIAL_USER.setImageUrl("https://lh4.googleusercontent.com/-BbkOUKdbuY8/AAAAAAAAAAI/AAAAAAAAABI/bgvqTRsFY8w/photo.jpg?sz=50");
        SOCIAL_USER.setRating(0);
        SOCIAL_USER.setTasksSolved(0);
        SOCIAL_USER.setSignInProvider(SocialMediaService.GOOGLE);

        NEW_USER_MAP.put("username", NEW_USER.getUsername());
        NEW_USER_MAP.put("email", NEW_USER.getEmail());
        NEW_USER_MAP.put("password", "12345");

        USER00_MAP.put("username", USER00.getUsername());
        USER00_MAP.put("password", "12345");


        /*Connection creation for Social Auth test start*/

        GoogleServiceProvider googleServiceProvider = new GoogleServiceProvider(appResourceBundle.getString("google.app.id"), appResourceBundle.getString("google.app.secret"));
        GoogleAdapter googleAdapter = new GoogleAdapter();
        GOOGLE_OAUTH2_CONNECTION = new OAuth2Connection(
                testResourceBundle.getString("providerId"),
                testResourceBundle.getString("providerUserId"),
                testResourceBundle.getString("accessToken"),
                testResourceBundle.getString("refreshToken"),
                Long.parseLong(testResourceBundle.getString("expireTime")),
                googleServiceProvider,
                googleAdapter);

        /*Connection creation for Social Auth test start*/

    }

    public static void assertToStringEquals(Object o1, Object o2) {
        assertEquals(toString(o1), toString(o2));
    }

    public static String toString(Object o1) {
        return o1 == null ? null : o1.toString();
    }
}
