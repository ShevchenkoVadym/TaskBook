package ru.javarush.taskbook.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import ru.javarush.taskbook.model.*;
import ru.javarush.taskbook.repository.UserRepository;
import ru.javarush.taskbook.service.*;
import ru.javarush.taskbook.service.exceptions.UserAlreadyExistsException;
import ru.javarush.taskbook.util.CurrentUser;
import ru.javarush.taskbook.web.rest.UserSortingOption.UserSort;
import ru.javarush.taskbook.web.rest.exceptions.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by Александр on 16.02.15.
 */

@RestController
@RequestMapping(value = "rest/api/v1/users")
public class UserRESTful {

    private static final Logger LOG = LoggerFactory.getLogger(UserRESTful.class);
    private final ProviderSignInUtils providerSignInUtils = new ProviderSignInUtils();

    private static final String USER_DEFAULT_PAGE = "0";
    private static final String USER_DEFAULT_PER_PAGE = "10";
    private static final String USER_DEFAULT_SORT = "-rating";


    @Autowired
    UserService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailService mailService;

    @Autowired
    TaskService taskService;

    @Autowired
    private UserTaskService userTaskService;

    Random random = new Random();

    @RequestMapping(value = "/current_user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUser()
    {
        HttpHeaders httpHeaders = new HttpHeaders();
        String httpMessage;
        if (CurrentUser.get() != null){
            httpMessage = String.format("%s get successfully", CurrentUser.get().getUsername());
            User user = CurrentUser.get();
            httpHeaders.set("request", httpMessage);
            return new ResponseEntity(user, httpHeaders, HttpStatus.OK);
        } else {
            httpMessage = "No user is logged in";
            httpHeaders.set("request", httpMessage);
            return new ResponseEntity(null, httpHeaders, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllUsers( @RequestParam(value = "page", required = false, defaultValue = USER_DEFAULT_PAGE) int page,
                                       @RequestParam(value = "per_page", required = false, defaultValue = USER_DEFAULT_PER_PAGE) int perPage,
                                       @RequestParam(value = "sort", required = false, defaultValue = USER_DEFAULT_SORT) String sort,
                                       @RequestParam(value = "name_query", required = false, defaultValue = "") String nameQuery)
    {
        Sort sortOption = UserSort.getUserSortForName(sort).getSortingOption();

        PageRequest pageRequest = new PageRequest(page, perPage, sortOption);

        Page<User> userPage = service.findAllUsersOnPage(pageRequest, nameQuery);
        List<User> users = userPage.getContent();
        for (User user : users)
        {
            user.setPassword(null);
            user.setEmail(null);
            user.setSignInProvider(null);
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        String headerMessage = String.format("all users in pages requested %n " +
                                             "current page number = %d, items per page = %d %n" +
                                             "overall pages = %d, sorting =  %s", page, perPage, userPage.getTotalPages(), sort);

        httpHeaders.set("request", headerMessage);

        return userPage.hasContent() ? (new ResponseEntity(userPage, httpHeaders, HttpStatus.OK)) :
                                       (new ResponseEntity("", httpHeaders, HttpStatus.NO_CONTENT));
    }

    @RequestMapping(value = "/{name}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity get(@PathVariable ("name") String name)
    {
        LOG.info("get user {}", name);
        User user = service.getByName(name);
        HttpHeaders httpHeaders = new HttpHeaders();
        String httpMessage;

        if (user != null){
            LOG.info(user.toString());
            if (CurrentUser.get() == null || (CurrentUser.get() != null && !user.getUsername().equals(CurrentUser.get().getUsername()))){
                user.setPassword(null);
                user.setEmail(null);
                user.setSignInProvider(null);
            }
            httpMessage = String.format("Requested user with username '%s'", user.getUsername());
            httpHeaders.set("request", httpMessage);
            return new ResponseEntity(user,httpHeaders,HttpStatus.OK);
        } else {
            httpMessage = String.format("No such user: %s", name);
            httpHeaders.set("request", httpMessage);
            return new ResponseEntity(String.format("Не удалось найти пользователя с именем %s", name), httpHeaders, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity save(@RequestBody User user) throws UserAlreadyExistsException
    {
        addInitialData(user);
        LOG.info("save user: {}", user);

        HttpHeaders httpHeaders = new HttpHeaders();
        String httpMessage;

        try {
            service.save(user);
        } catch (EmptyPasswordException ex) {
            httpMessage = "Password cannot be empty";
            httpHeaders.set("response", httpMessage);
            return  new ResponseEntity("Пароль не может быть пустым",
                    httpHeaders, HttpStatus.EXPECTATION_FAILED);
        } catch (MalformedEmailException ex) {
            httpMessage = "Email has incorrect format";
            httpHeaders.set("response", httpMessage);
            return  new ResponseEntity("Недопустимое значение адреса эл. почты",
                    httpHeaders, HttpStatus.EXPECTATION_FAILED);
        } catch (EmailAlreadyExistsException ex) {
            httpMessage = "Email already exists";
            httpHeaders.set("response", httpMessage);
            return  new ResponseEntity("Пользователь с таким адресом эл. почты уже существует",
                    httpHeaders, HttpStatus.EXPECTATION_FAILED);
        } catch (LoginAlreadyExistsException ex) {
            httpMessage = "Login already exists";
            httpHeaders.set("response", httpMessage);
            return  new ResponseEntity("Пользователь с таким логином уже существует - выберите другое имя",
                    httpHeaders, HttpStatus.EXPECTATION_FAILED);
        } catch (Exception ex) {
            httpMessage = "An unexpected error occurred";
            httpHeaders.set("response", httpMessage);
            return  new ResponseEntity("Произошла неизвестная ошибка",
                    httpHeaders, HttpStatus.EXPECTATION_FAILED);
        }
        String email = user.getEmail();
        String host = "www.javapractice.ru";
        user.generateToken();
        userRepository.saveAndFlush(user);
        String token = user.getTokenConfirmation();
        String textMessage = String.format("Здравствуйте, %s!\n" +
                        "Подтвердите, пожалуйста, свой E-mail " + email + "\n" +
                        "Для подтверждения E-mail перейдите по следующей ссылке: \n\n%s\n\n" +
                        "Пожалуйста, проигнорируйте данное письмо, если оно попало к Вам по ошибке.\n" +
                        "С уважением,\n" + "Команда javapractice.com", user.getUsername(),
                "http://" + host + "/rest/api/v1/users/confirmation/do?token=" + token + "&username=" +
                        user.getUsername() + "&email=" + email);
        try {
            LOG.info("Trying to send a confirmation email to " + email);
            mailService.sendEmailRegConfirm(email, textMessage);
        } catch (MailException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(null, httpHeaders, HttpStatus.BAD_REQUEST);
        }
        httpMessage = String.format("%s successfully added", user.getUsername());
        httpHeaders.set("response", httpMessage);
        return  new ResponseEntity(null,httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{name}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity update(@PathVariable(value = "name") String name, @RequestBody User user)
    {
        LOG.info("update user: {}", user);

        HttpHeaders httpHeaders = new HttpHeaders();
        String httpMessage;

        try {
            service.update(user);
        } catch (NoSuchUserException ex) {

            httpMessage = String.format("user %s does not exist", user.getUsername());
            httpHeaders.set("request", httpMessage);
            return  new ResponseEntity<>(String.format("Пользователь %s не найден", user.getUsername()),
                    httpHeaders, HttpStatus.NOT_FOUND);

        } catch (OperationNotPerformedException ex) {

            httpMessage = "Action is not allowed.";
            httpHeaders.set("request", httpMessage);
            return  new ResponseEntity<>("Доступ запрещен", httpHeaders, HttpStatus.UNAUTHORIZED);

        } catch (EmailAlreadyExistsException ex) {

            httpMessage = String.format("user with email %s already exists", user.getEmail());
            httpHeaders.set("request", httpMessage);
            return  new ResponseEntity<>("Пользователь с таким адресом электронной почты уже существует",
                    httpHeaders, HttpStatus.NOT_ACCEPTABLE);

        } catch (MalformedEmailException ex) {

            httpMessage = String.format("user with email %s already exists", user.getEmail());
            httpHeaders.set("request", httpMessage);
            return  new ResponseEntity<>("Недопустимое значение адреса эл. почты",
                    httpHeaders, HttpStatus.EXPECTATION_FAILED);

        } catch (Exception ex) {

            httpMessage = "An unexpected error occurred";
            httpHeaders.set("request", httpMessage);
            return  new ResponseEntity<>("Произошла неизвестная ошибка", httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return  new ResponseEntity<>(null, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/{name}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@PathVariable(value = "name") String name)
    {
        LOG.info("delete user: {}", name);
        HttpHeaders httpHeaders = new HttpHeaders();
        User user = service.getByName(name);

        if (user != null && user.getUsername().equals(name))
        {
            String httpMessage = String.format("%s successfully deleted", user.getUsername());
            service.delete(name);
            httpHeaders.set("request", httpMessage);
            return new ResponseEntity(user, httpHeaders, HttpStatus.NO_CONTENT);
        }
        else
        {
            String httpMessage = String.format("%s not exist", name);
            httpHeaders.set("request", httpMessage);
            return new ResponseEntity(null, httpHeaders, HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/{name}/tags",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllTagsByUser(@PathVariable("name") String name){
        validateName(name);
        LOG.info("Get All " + name + "'s tags");
        List<String> ids = userTaskService.getAllTaskIdSuccessTaskForUser(name);

        Map<String, Integer> tags = taskService.findTagsByUser(name, ids);

        HttpHeaders httpHeaders = new HttpHeaders();
        String headerMessage = String.format("Get All User's tags");
        httpHeaders.set("request",headerMessage);

        return new ResponseEntity<>(tags, httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/login",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@RequestBody User user)
    {
        HttpHeaders httpHeaders = new HttpHeaders();
        String httpMessage = "Incorrect login/password combination";
        httpHeaders.set("request", httpMessage);
        ResponseEntity error = new ResponseEntity("Неверная комбинация логина и пароля", httpHeaders, HttpStatus.EXPECTATION_FAILED);

        User userToLogin = service.getByName(user.getUsername());
        if (userToLogin == null) {
            return error;
        } else if(userToLogin.isEnabled()) {
            LOG.info("Logging in user: {}", user);
            if(!userToLogin.isConfirmed()) {
                return new ResponseEntity("Пожалуйста подтвердите ваш email", httpHeaders, HttpStatus.EXPECTATION_FAILED);
            }
            try {
                Authentication request = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
                Authentication result = authenticationManager.authenticate(request);
                SecurityContextHolder.getContext().setAuthentication(result);
            } catch (BadCredentialsException ex) {
                return error;
            }

            LOG.info("User: {} has been logged in.", user);

            CurrentUser.get().setLastVisit(new Date());
            service.update(CurrentUser.get());
            httpMessage = String.format("%s passed authentication", user.getUsername());
            httpHeaders.set("request", httpMessage);
            return new ResponseEntity(userToLogin, httpHeaders, HttpStatus.OK);

        } else {
            LOG.info("User is blocked.");
            httpMessage = "User is blocked.";
            httpHeaders.set("request", httpMessage);
            return new ResponseEntity("Аккаунт заблокирован", httpHeaders, HttpStatus.UNAUTHORIZED);
        }

    }

    /*User will be redirected to home page after social login*/
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public void socialLogin(WebRequest request, HttpServletResponse response) throws IOException {
        LOG.debug("Saving user received through social auth");

        /*response.sendRedirect("login/vkontakte");*/

        Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);
        if (connection != null) {
            User userSocial = new User();
            UserProfile socialMediaProfile = connection.fetchUserProfile();
            ConnectionKey providerKey = connection.getKey();
            userSocial.setSignInProvider(SocialMediaService.valueOf(providerKey.getProviderId().toUpperCase()));

            /*TODO handle fake data*/
            User savedUser;
            Authentication authentication;
            String userEmail;
            String userName;
            /*if we already have user with such email - auth him, if not - create new*/
            if (userSocial.getSignInProvider().equals(SocialMediaService.VKONTAKTE) ||
                    (savedUser = userRepository.findByEmail(socialMediaProfile.getEmail())) == null ) {
                addInitialData(userSocial);
                userSocial.setEmail(socialMediaProfile.getEmail());
                userSocial.setImageUrl(connection.getImageUrl());
                userSocial.setFullName(connection.getDisplayName());

                String userFakePassword = connection.createData().getAccessToken();
                if (userFakePassword.length() > 140)
                    userFakePassword = userFakePassword.substring(0,140);
                userSocial.setPassword(userFakePassword);

                /*generate fake email for Vkontakte users, due to Spring Social Problems*/
                if (userSocial.getSignInProvider().equals(SocialMediaService.VKONTAKTE)){
                    userSocial.setEmail(socialMediaProfile.getUsername() + "@yandex.by");
                    while (userRepository.findByEmail(socialMediaProfile.getEmail()) != null){
                        userSocial.setEmail(random.nextInt(9) + userSocial.getEmail());
                    }

                }
                /*Setting unique but user friendly userName*/
                userSocial.setUsername(userSocial.getEmail().split("@")[0]);
                /*replace All not supported by http symbols*/
                userSocial.setUsername(userSocial.getUsername().replaceAll("[^A-Za-z0-9]", ""));
                while (userRepository.findByUsername(userSocial.getUsername()) != null){
                    userSocial.setUsername(userSocial.getUsername() + random.nextInt(9));
                }
                LOG.debug("Register new social user {}", userSocial);
                try {
                    service.save(userSocial);
                } catch (UserAlreadyExistsException e) {
                    throw new UserAlreadyExistsWebException();
                }
                LOG.debug("Logging in user: {}", userSocial);
                authentication = new UsernamePasswordAuthenticationToken(userSocial, null , userSocial.getAuthorities());
                userEmail = userSocial.getEmail();
                userName = userSocial.getUsername();
            } else {
                LOG.debug("Logging in user: {}", savedUser);
                authentication = new UsernamePasswordAuthenticationToken(savedUser, null , savedUser.getAuthorities());
                userEmail = savedUser.getEmail();
                userName = savedUser.getUsername();
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
            LOG.debug("User: {} has been logged in.", userSocial);
            providerSignInUtils.doPostSignUp(userEmail, request);
            LOG.debug("User {} connection data has been persisted.", userEmail);

            response.sendRedirect("/#/users/" + userName);

        } else {
            response.sendError(418);
        }
    }

    @RequestMapping(value = "/recover",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity recoverPassword(HttpServletRequest request,
                                          @RequestParam(value = "email", required = true) String email) {
        HttpHeaders httpHeaders = new HttpHeaders();
        String httpMessage;
        SimpleMailMessage mail = new SimpleMailMessage();

        User userToRecover = service.getByEmail(email);

        if (userToRecover == null) {
            httpMessage = String.format("Started recovery procedure for email %s", email);
            httpHeaders.set("request", httpMessage);
            return new ResponseEntity<>("Пользователь с таким адресом эл. почты не найден.",
                    httpHeaders, HttpStatus.UNAUTHORIZED);
        } else {
            userToRecover.generateToken();
            service.directUpdate(userToRecover);
            String token = userToRecover.getTokenConfirmation();
            String path = request.getServerName();

            String recText = String.format("Здравствуйте!\n" +
                            "Вы отправили запрос на восстановление пароля от аккаунта %s. " +
                            "Для того, чтобы сбросить пароль, перейдите по ссылке: \n\n%s\n\n" +
                            "Пожалуйста, проигнорируйте данное письмо, если оно попало к Вам по ошибке.\n" +
                            "С уважением,\n" + "Команда javapractice.com", userToRecover.getUsername(),
                    "http://" + path + "/rest/api/v1/users/recover/do?token=" + token + "&username=" +
                            userToRecover.getUsername() + "&email=" + email);

            try {
                LOG.info("Trying to send a recovery password email to " + email);
                mailService.sendEmailPasswordRecovery(email, recText);
            } catch (MailException ex) {
                ex.printStackTrace();
                return new ResponseEntity<>(null, httpHeaders, HttpStatus.BAD_REQUEST);
            }

            httpMessage = String.format("User with email %s does not exist", email);
            httpHeaders.set("request", httpMessage);
            return new ResponseEntity<>(null, httpHeaders, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/recover/do",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity resetPassword(@RequestParam(value = "token", required = false) String token_confirmation,
                                        @RequestParam(value = "username", required = false) String username,
                                        @RequestParam(value = "email", required = true) String email){
        User userToRecover;
        HttpHeaders httpHeaders = new HttpHeaders();
        SimpleMailMessage mail = new SimpleMailMessage();

        if(username != null) {
            userToRecover = service.getByName(username);
        } else {
            return new ResponseEntity<>(null, httpHeaders, HttpStatus.BAD_REQUEST);
        }

        LOG.info(userToRecover.getTokenConfirmation());

        if (userToRecover != null && userToRecover.getTokenConfirmation().equals(token_confirmation)) {
            String password = User.generatePassword();
            userToRecover.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
            service.directUpdate(userToRecover);

            mail.setTo(email);
            mail.setFrom("no-reply.taskbook@yandex.ru");
            mail.setSubject("Восстановление пароля");
            mail.setText(String.format("Ваш новый пароль:\n\n%s\n\n" +
                    "С уважением,\n" + "Команда javapractice.com", password));

            try {
                LOG.info("Trying to send a recovery password email to " + email);
                mailSender.send(mail);
                userToRecover.setTokenConfirmation(null);
                service.directUpdate(userToRecover);
            } catch (MailException ex) {
                ex.printStackTrace();
                return new ResponseEntity<>(null, httpHeaders, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("На адрес Вашей эл. почты был выслан новый пароль.", httpHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, httpHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/confirmation/do",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity sentConfirmation(@RequestParam(value = "token", required = false) String token_confirmation,
                                       @RequestParam(value = "username", required = false) String username,
                                       @RequestParam(value = "email", required = true) String email){
        User userToRecover;
        HttpHeaders httpHeaders = new HttpHeaders();
        SimpleMailMessage mail = new SimpleMailMessage();

        if(username != null) {
            userToRecover = service.getByName(username);
        } else {
            return new ResponseEntity<>(null, httpHeaders, HttpStatus.BAD_REQUEST);
        }

        LOG.info(userToRecover.getTokenConfirmation());

        if (userToRecover != null && userToRecover.getTokenConfirmation().equals(token_confirmation)) {
            userToRecover.setConfirmed(true);
            service.directUpdate(userToRecover);

            mail.setTo(email);
            mail.setFrom("no-reply.taskbook@yandex.ru");
            mail.setSubject("Спасибо за регистрацию");
            mail.setText(String.format("Ваш email подтвержден \n" +
                    "С уважением,\n" + "Команда javapractice.com"));
            try {
                LOG.info("Trying to send a confirmation email to " + email);
                mailSender.send(mail);
                userToRecover.setTokenConfirmation(null);
                service.directUpdate(userToRecover);
            } catch (MailException ex) {
                ex.printStackTrace();
                return new ResponseEntity<>(null, httpHeaders, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("Ваш аккаунт подтвержден. Команда JavaPractice", httpHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, httpHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/feedback",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity sendFeedback(@RequestBody Feedback feedback){

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo("jr.taskbook@yandex.ru");
        mail.setCc(feedback.getRequester());
        mail.setFrom(feedback.getRequester());
        mail.setSubject(feedback.getSubject());
        mail.setText(feedback.getMessage());

        HttpHeaders httpHeaders = new HttpHeaders();
        try {
            LOG.info("Trying to send a new feedback from " + feedback.getRequester());
            mailSender.send(mail);
        } catch (MailException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(null, httpHeaders, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(null, httpHeaders, HttpStatus.OK);
    }

    /********************************************************************************************************************************/
    /********************************************************************************************************************************/
    /********************************************************************************************************************************/
    /********************************************************************************************************************************/
    /********************************************************************************************************************************/

    private void validateName(String name){//true - hide source code from user and validate rest fields
        if(name!=null){
            if(!name.matches("[a-zA-Z0-9]+"))throw new IncorrcetRequestedNameException(name);
        }
    }

    private void addInitialData(User user) {
        user.setEnabled(true);
        user.setNonReadOnly(true);
        user.setRoles(new HashSet<Role>() {{
            add(Role.USER);
        }});
        if (user.getCountry() == null)
        {
            user.setCountry("");
        }
        user.setImageUrl("");
        LOG.info("save user: {}", user);
    }

}
