package ru.javarush.taskbook.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInAttempt;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.javarush.taskbook.TestUtil;
import ru.javarush.taskbook.model.Role;
import ru.javarush.taskbook.model.User;
import ru.javarush.taskbook.service.UserService;
import ru.javarush.taskbook.util.DbPopulator;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * User: blacky
 * Date: 23.11.14
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-db.xml",
        "classpath:spring/spring-data-mongo.xml",
        "classpath:spring/spring-security.xml"
})

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class UserRestTest {

    public static final String REST_URL = "/rest/users/users";
    public static final String REST_ME = "/rest/users/me";
    public static final String REST_AUTH = "/rest/users/login";
    public static final String REST_SAVE = "/rest/users/save";
    public static final String REST_UPDATE = "/rest/users/update";
//    private static String SEC_CONTEXT_ATTR = HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

    private MockMvc mockMvcSecurity;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    protected MockMvc mockMvc;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private DbPopulator dbPopulator;

    /*save operation user for testing data start*/
    ObjectMapper objectMapper = new ObjectMapper();
    /*save operation user for testing data end*/

    /*update user operation*/
    JSONObject userUpdate = new JSONObject();


    @Autowired
    @Qualifier(value = "myConnectionFactoryLocator")
    private ConnectionFactoryLocator connectionFactoryLocator;

    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

    @PostConstruct
    void postConstruct() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//                .addFilters(this.springSecurityFilterChain)
    }

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();

        mockMvcSecurity = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilters(springSecurityFilterChain)
                .build();

        userUpdate.put("id", userService.getByName("THREE").getId().toString());
        userUpdate.put("username",userService.getByName("THREE").getUsername());
        userUpdate.put("password",userService.getByName("THREE").getPassword());
        userUpdate.put("email",userService.getByName("THREE").getEmail());
        userUpdate.put("roles", userService.getByName("THREE").getRoles());
        userUpdate.put("country",userService.getByName("THREE").getCountry());
        userUpdate.put("creationDate","" + userService.getByName("THREE").getCreationDate().getTime());
        userUpdate.put("imageUrl",userService.getByName("THREE").getImageUrl());
        userUpdate.put("isEnabled","" + userService.getByName("THREE").isEnabled());
        userUpdate.put("isNonReadOnly","" + userService.getByName("THREE").isNonReadOnly());
        userUpdate.put("tasksSolved","" + userService.getByName("THREE").getTasksSolved());
    }

    @Test
    public void testGet() throws Exception {
        //System.out.println(JsonUtil.writeValue(USER00));
        System.out.println("-------------");
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk());
        //.andExpect(content().contentType(MediaType.APPLICATION_JSON))
        //.andExpect(jsonPath("$name", is(USER00.getName())))
        //.andDo(print());
        //.andExpect(content().string(JsonUtil.writeValue(USER00)));
    }

    @Test
    public void testTestUTF() throws Exception {

    }

    @Test
    public void testAuth() throws Exception {
        String userJSON = objectMapper.writeValueAsString(TestUtil.USER00_MAP);

        mockMvc.perform(post(REST_AUTH)
                        .content(userJSON)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print())
        ;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Assert.assertTrue(authentication.isAuthenticated());
        Assert.assertTrue(SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken);
        Assert.assertEquals(TestUtil.USER00.getUsername(), authentication.getName());
//        ResultMatcher matcher = new ResultMatcher() {
//            public void match(MvcResult mvcResult) throws Exception {
//                HttpSession session = mvcResult.getRequest().getSession();
//                SecurityContext securityContext = (SecurityContext) session.getAttribute(SEC_CONTEXT_ATTR);
//                Assert.assertEquals(securityContext.getAuthentication().getName(), USER00.getUsername());
//            }
//        };
//
//        mockMvc.perform(post("/spring_security_login")
//                            .param("j_username", USER00.getUsername())
//                            .param("j_password", USER00.getPassword()))
//                .andExpect(matcher);
//                //.andExpect(status().is(200));
    }

    @Test
    public void testSocialAuth() throws Exception {

        mockMvc.perform(get(REST_AUTH)
                        .sessionAttr(ProviderSignInAttempt.SESSION_ATTRIBUTE, new ProviderSignInAttempt(
                                TestUtil.GOOGLE_OAUTH2_CONNECTION, connectionFactoryLocator, usersConnectionRepository))
        )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
        ;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Assert.assertTrue(authentication.isAuthenticated());
        Assert.assertTrue(SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken);
        Assert.assertEquals(TestUtil.SOCIAL_USER.getUsername(), authentication.getName());

        User currentUser = userService.getByName(TestUtil.SOCIAL_USER.getUsername());
        Assert.assertNotEquals(null, currentUser);
        Assert.assertEquals(TestUtil.SOCIAL_USER, currentUser);
    }

    @Test
    public void testGetAll() throws Exception
    {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                       /* .andDo(print())*/
                    /*---------First test user-----------*/
                .andExpect(jsonPath("$[0].id", is(TestUtil.USER00.getId())))
                .andExpect(jsonPath("$[0].username", is(TestUtil.USER00.getUsername())))
                .andExpect(jsonPath("$[0].password", is(TestUtil.USER00.getPassword())))
                .andExpect(jsonPath("$[0].isEnabled", is(TestUtil.USER00.isEnabled())))
                .andExpect(jsonPath("$[0].isNonReadOnly", is(TestUtil.USER00.isNonReadOnly())))
                .andExpect(jsonPath("$[0].email", is(TestUtil.USER00.getEmail())))
                //.andExpect(jsonPath("$[0].userRoles.role[0]", is(TestUtil.USER00.getUserRoles().iterator().next().toString())))
                .andExpect(jsonPath("$[0].roles[0]", is(TestUtil.USER00.getRoles().iterator().next().toString())))
                .andExpect(jsonPath("$[0].country", is(TestUtil.USER00.getCountry())))
                .andExpect(jsonPath("$[0].imageUrl", is(TestUtil.USER00.getImageUrl())))
                .andExpect(jsonPath("$[0].rating", is(TestUtil.USER00.getRating())))
                .andExpect(jsonPath("$[0].tasksSolved", is(TestUtil.USER00.getTasksSolved())))
                    /*---------Second test user-----------*/
                .andExpect(jsonPath("$[1].id", is(TestUtil.USER01.getId())))
                .andExpect(jsonPath("$[1].username", is(TestUtil.USER01.getUsername())))
                .andExpect(jsonPath("$[1].password", is(TestUtil.USER01.getPassword())))
                .andExpect(jsonPath("$[1].isEnabled", is(TestUtil.USER01.isEnabled())))
                .andExpect(jsonPath("$[1].isNonReadOnly", is(TestUtil.USER01.isNonReadOnly())))
                .andExpect(jsonPath("$[1].email", is(TestUtil.USER01.getEmail())))
//                .andExpect(jsonPath("$[1].userRoles.role[0]", is(TestUtil.USER01.getUserRoles().iterator().next().toString())))
                .andExpect(jsonPath("$[1].roles[0]", is(TestUtil.USER01.getRoles().iterator().next().toString())))
                .andExpect(jsonPath("$[1].country", is(TestUtil.USER01.getCountry())))
                .andExpect(jsonPath("$[1].imageUrl", is(TestUtil.USER01.getImageUrl())))
                .andExpect(jsonPath("$[1].rating", is(TestUtil.USER01.getRating())))
                .andExpect(jsonPath("$[1].tasksSolved", is(TestUtil.USER01.getTasksSolved())))
                    /*---------Third test user-----------*/
                .andExpect(jsonPath("$[2].id", is(TestUtil.USER02.getId())))
                .andExpect(jsonPath("$[2].username", is(TestUtil.USER02.getUsername())))
                .andExpect(jsonPath("$[2].password", is(TestUtil.USER02.getPassword())))
                .andExpect(jsonPath("$[2].isEnabled", is(TestUtil.USER02.isEnabled())))
                .andExpect(jsonPath("$[2].isNonReadOnly", is(TestUtil.USER02.isNonReadOnly())))
                .andExpect(jsonPath("$[2].email", is(TestUtil.USER02.getEmail())))
                //.andExpect(jsonPath("$[2].userRoles.role[0]", is(TestUtil.USER02.getUserRoles().iterator().next().toString())))
                .andExpect(jsonPath("$[2].roles[0]", is(TestUtil.USER02.getRoles().iterator().next().toString())))
                .andExpect(jsonPath("$[2].country", is(TestUtil.USER02.getCountry())))
                .andExpect(jsonPath("$[2].imageUrl", is(TestUtil.USER02.getImageUrl())))
                .andExpect(jsonPath("$[2].rating", is(TestUtil.USER02.getRating())))
                .andExpect(jsonPath("$[2].tasksSolved", is(TestUtil.USER02.getTasksSolved())));
    }

    @Test
    public void testMe() throws Exception {
        HttpSession session = mockMvcSecurity.perform(post("/j_spring_security_check").secure(true)
                .param("j_username", "ONE")
                .param("j_password", "12345"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(redirectedUrl(null))
                .andReturn()
                .getRequest()
                .getSession();


        mockMvcSecurity.perform(get(REST_ME).secure(true).session((MockHttpSession) session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$username", is("ONE")))
//                .andDo(print())
        ;
    }

    @Test
    public void testSavePositive() throws Exception {
        String userJSON = objectMapper.writeValueAsString(TestUtil.NEW_USER_MAP);

        mockMvc.perform(post(REST_SAVE)
                        .content(userJSON)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
        ;

        User currentUser = userService.getByName(TestUtil.NEW_USER.getUsername());
        Assert.assertNotEquals(null, currentUser);
        Assert.assertEquals(TestUtil.NEW_USER.getEmail(), currentUser.getEmail());
        //Assert.assertEquals(true, TestUtil.NEW_USER_MAP.get("password").equals(currentUser.getPassword()));
    }

    @Test
    public void testSaveCloneUsername() throws Exception {
        String userJSON = objectMapper.writeValueAsString(TestUtil.NEW_USER_MAP);

        mockMvc.perform(post(REST_SAVE)
                        .content(userJSON)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
        ;
        String emailBuf = TestUtil.NEW_USER_MAP.get("email");
        /*NEW_USER_MAP changed*/
        TestUtil.NEW_USER_MAP.put("email", "test@mail.ru");
        String incorrectUserJSON = objectMapper.writeValueAsString(TestUtil.NEW_USER_MAP);
        MvcResult mvcResult = mockMvc.perform(post(REST_SAVE)
                        .content(incorrectUserJSON)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isConflict())
//                .andDo(print())
                .andReturn()
                ;

        String errorMessage = mvcResult.getResponse().getErrorMessage();
        Assert.assertEquals("Login already exists", errorMessage);
        /*NEW_USER_MAP restored*/
        TestUtil.NEW_USER_MAP.put("email", emailBuf);
    }

    @Test
    public void testSaveEmptyPassword() throws Exception {
        String passwordBuf = TestUtil.NEW_USER_MAP.get("password");
        /*TestUtil.NEW_USER_MAP modified*/
        TestUtil.NEW_USER_MAP.put("password", "");
        String incorrectUserJSON = objectMapper.writeValueAsString(TestUtil.NEW_USER_MAP);
        MvcResult mvcResult = mockMvc.perform(post(REST_SAVE)
                        .content(incorrectUserJSON)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isUnprocessableEntity())
//                .andDo(print())
                .andReturn()
                ;

        String errorMessage = mvcResult.getResponse().getErrorMessage();
        Assert.assertEquals("Empty password", errorMessage);
        /*NEW_USER_MAP restored*/
        TestUtil.NEW_USER_MAP.put("password", passwordBuf);
    }

    @Test
    public void testSaveMalFormedEmail() throws Exception {
        String emailBuf = TestUtil.NEW_USER_MAP.get("email");
        /*TestUtil.NEW_USER_MAP modified*/
        TestUtil.NEW_USER_MAP.put("email", "jhvwe8u90hf23");
        String incorrectUserJSON = objectMapper.writeValueAsString(TestUtil.NEW_USER_MAP);
        MvcResult mvcResult = mockMvc.perform(post(REST_SAVE)
                        .content(incorrectUserJSON)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isUnprocessableEntity())
//                .andDo(print())
                .andReturn()
                ;

        String errorMessage = mvcResult.getResponse().getErrorMessage();
        Assert.assertEquals("Malformed email", errorMessage);
        /*NEW_USER_MAP restored*/
        TestUtil.NEW_USER_MAP.put("email", emailBuf);
    }


    @Test
    public void testSaveNull() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(REST_SAVE)
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isUnprocessableEntity())
//                .andDo(print())
                .andReturn();

        String errorMessage = mvcResult.getResponse().getErrorMessage();
        Assert.assertEquals("Missing important data", errorMessage);
    }

    @Test
    public void testSaveCloneEmail() throws Exception {
        String userJSON = objectMapper.writeValueAsString(TestUtil.NEW_USER_MAP);

        mockMvc.perform(post(REST_SAVE)
                        .content(userJSON)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
        ;
        /*NEW_USER_MAP changed*/
        String usernameBuf = TestUtil.NEW_USER_MAP.get("username");
        TestUtil.NEW_USER_MAP.put("username", "fedor");
        String incorrectUserJSON = objectMapper.writeValueAsString(TestUtil.NEW_USER_MAP);
        MvcResult mvcResult = mockMvc.perform(post(REST_SAVE)
                        .content(incorrectUserJSON)
                        .contentType(MediaType.APPLICATION_JSON)
        )
//                .andDo(print())
                .andExpect(status().isConflict())
                .andReturn()
                ;
        String errorMessage = mvcResult.getResponse().getErrorMessage();
        Assert.assertEquals("Email already exists", errorMessage);
        /*NEW_USER_MAP restored*/
        TestUtil.NEW_USER_MAP.put("username", usernameBuf);
    }

    @Test
    public void testUpdateLogin() throws Exception
    {
        System.out.println("------------------");
        System.out.println("testUpdateLogin");
       /* Map<String, String> uMap = new HashMap<>();
        String userTested = "ONE";
        String passwordTested = "12345";
        uMap.put("username", userTested);
        uMap.put("password", passwordTested);
        String userJSON = objectMapper.writeValueAsString(uMap);

        MvcResult mvcResult = mockMvc.perform(post(REST_AUTH)
                .content(userJSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                //.andDo(print())
                .andReturn()
        ;*/
        testAuth();

        userUpdate.put("username", "Vasilii");

        MvcResult res = mockMvc.perform(post(REST_UPDATE)
                .content(userUpdate.toJSONString())
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isConflict())
                .andDo(print())
                .andReturn()
                ;
        String errorMessage = res.getResponse().getErrorMessage();
        Assert.assertEquals("Operation is not performed", errorMessage);


    }



    @Test
    public void testUpdateChangeRole() throws Exception
    {

        System.out.println("-----------------");
        System.out.println("testUpdateChangeRole");
        /*Map<String, String> uMap = new HashMap<>();
        String userTested = "ONE";
        String passwordTested = "12345";
        uMap.put("username", userTested);
        uMap.put("password", passwordTested);
        String userJSON = objectMapper.writeValueAsString(uMap);

        MvcResult mvcResult = mockMvc.perform(post(REST_AUTH)
                .content(userJSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                //.andDo(print())
                .andReturn()
        ;*/
        testAuth();

        User user = new User();
        Set<Role> roles = (Set<Role>)userUpdate.get("roles");
        roles.add(Role.ADMIN);
        userUpdate.put("roles",roles);
        MvcResult res = mockMvc.perform(post(REST_UPDATE)
                .content(userUpdate.toJSONString())
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                ;
        Assert.assertEquals(userUpdate.get("roles"),userService.getByName((String)userUpdate.get("username")).getRoles());

    }

    @Test
    public void testUpdateNoRightsChangeRole() throws Exception
    {

        System.out.println("-----------------");
        System.out.println("testUpdateNoRightChangeRole");
        Map<String, String> uMap = new HashMap<>();
        String userTested = "TWO";
        String passwordTested = "123";
        uMap.put("username", userTested);
        uMap.put("password", passwordTested);
        String userJSON = objectMapper.writeValueAsString(uMap);

        MvcResult mvcResult = mockMvc.perform(post(REST_AUTH)
                .content(userJSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                ;

        Set<Role> roles = (Set<Role>)userUpdate.get("roles");
        roles.add(Role.ADMIN);
        userUpdate.put("roles", roles);
        MvcResult res = mockMvc.perform(post(REST_UPDATE)
                .content(userUpdate.toJSONString())
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isConflict())
                .andDo(print())
                .andReturn();
        String errorMessage = res.getResponse().getErrorMessage();
        Assert.assertEquals("Operation is not performed",errorMessage);
        Assert.assertNotEquals(userUpdate.get("roles"), userService.getByName((String)userUpdate.get("username")).getRoles());
    }

    @Test
    public void testUpdateEmail() throws Exception
    {

        System.out.println("------------------");
        System.out.println("testUpdateEmail");
        /*Map<String, String> uMap = new HashMap<>();
        String userTested = "ONE";
        String passwordTested = "12345";
        uMap.put("username", userTested);
        uMap.put("password", passwordTested);
        String userJSON = objectMapper.writeValueAsString(uMap);

        MvcResult mvcResult = mockMvc.perform(post(REST_AUTH)
                .content(userJSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                //.andDo(print())
                .andReturn()
        ;*/
        testAuth();

        userUpdate.put("email","newEmail@mail.ru");
        mockMvc.perform(post(REST_UPDATE)
                .content(userUpdate.toJSONString())
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print())
        ;
        Assert.assertEquals(userUpdate.get("email"), userService.getByName((String)userUpdate.get("username")).getEmail());
    }

    @Test
    public void testUpdateExistEmail() throws Exception
    {

        System.out.println("------------------");
        System.out.println("testUpdateExistEmail");
        /*Map<String, String> uMap = new HashMap<>();
        String userTested = "ONE";
        String passwordTested = "12345";
        uMap.put("username", userTested);
        uMap.put("password", passwordTested);
        String userJSON = objectMapper.writeValueAsString(uMap);
        MvcResult mvcResult = mockMvc.perform(post(REST_AUTH)
                .content(userJSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
        ;*/
        testAuth();

        userUpdate.put("email","ONE@mail.ru");
        MvcResult res = mockMvc.perform(post(REST_UPDATE)
                .content(userUpdate.toJSONString())
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isConflict())
                .andDo(print())
                .andReturn();

        String error = res.getResponse().getErrorMessage();
        Assert.assertEquals("Email already exists", error);
        Assert.assertNotEquals(userUpdate.get("email"),userService.getByName((String)userUpdate.get("username")).getEmail());

    } @Test
      public void testBanUser() throws Exception
    {
        System.out.println("------------------");
        System.out.println("testBanUser");
        //System.out.println(map.toString());
       /* Map<String, String> uMap = new HashMap<>();
        String userTested = "TWO";
        String passwordTested = "123";
        uMap.put("username", userTested);
        uMap.put("password", passwordTested);
        String userJSON = objectMapper.writeValueAsString(uMap);

        MvcResult mvcResult = mockMvc.perform(post(REST_AUTH)
                .content(userJSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                ;*/
        testAuth();

        userUpdate.put("isEnabled", "false");
        MvcResult res = mockMvc.perform(post(REST_UPDATE)
                .content(userUpdate.toJSONString())
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        Assert.assertEquals(userUpdate.get("isEnabled").toString(), "" + userService.getByName((String) userUpdate.get("username")).isEnabled());
    }


    @Test
    public void testBanUserNoRights() throws Exception
    {
        System.out.println("------------------");
        System.out.println("testBanUserNoRights");
        //System.out.println(map.toString());
        Map<String, String> uMap = new HashMap<>();
        String userTested = "TWO";
        String passwordTested = "123";
        uMap.put("username", userTested);
        uMap.put("password", passwordTested);
        String userJSON = objectMapper.writeValueAsString(uMap);

        MvcResult mvcResult = mockMvc.perform(post(REST_AUTH)
                .content(userJSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                ;

        userUpdate.put("isEnabled", "false");
        MvcResult res = mockMvc.perform(post(REST_UPDATE)
                .content(userUpdate.toJSONString())
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isConflict())
                .andDo(print())
                .andReturn();
        Assert.assertNotEquals(userUpdate.get("isEnabled").toString(), "" + userService.getByName((String) userUpdate.get("username")).isEnabled());
    }

    @Test
    public void testUpdateMalformedEmail() throws Exception
    {
        System.out.println("------------------");
        System.out.println("testUpdateMalformedEmail");
        testAuth();

        userUpdate.put("email", "1fsdf3434t5");
        MvcResult res = mockMvc.perform(post(REST_UPDATE)
                .content(userUpdate.toJSONString())
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isUnprocessableEntity())
                .andDo(print())
                .andReturn();
        //System.out.println("Error message: " + res.getResponse().getErrorMessage());
        Assert.assertNotEquals(userUpdate.get("email"),userService.getByName((String) userUpdate.get("username")).getEmail());


    }

    @Test
    public void testUpdateChangeCountryNotYourself() throws Exception
    {
        System.out.println("------------------");
        System.out.println("testUpdateChangeCountryNotYourself");
        testAuth();

        userUpdate.put("country", "Turkey");
        MvcResult res = mockMvc.perform(post(REST_UPDATE)
                .content(userUpdate.toJSONString())
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isConflict())
                .andDo(print())
                .andReturn();
        Assert.assertNotEquals(userUpdate.get("country"), userService.getByName((String) userUpdate.get("username")).getCountry());
    }

    @Test
    public void testUpdateChangeCountryYourself() throws Exception
    {
        System.out.println("------------------");
        System.out.println("testUpdateChangeCountryYourself");
        //System.out.println(map.toString());
        Map<String, String> uMap = new HashMap<>();
        String userTested = "THREE";
        String passwordTested = "234";
        uMap.put("username", userTested);
        uMap.put("password", passwordTested);
        String userJSON = objectMapper.writeValueAsString(uMap);

        MvcResult mvcResult = mockMvc.perform(post(REST_AUTH)
                .content(userJSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();


        userUpdate.put("country", "Turkey");
        MvcResult res = mockMvc.perform(post(REST_UPDATE)
                .content(userUpdate.toJSONString())
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        Assert.assertEquals(userUpdate.get("country"), userService.getByName((String) userUpdate.get("username")).getCountry());
    }

    @Test
    public void testUpdateChangeImageUrlNotYourself() throws Exception
    {
        System.out.println("------------------");
        System.out.println("testUpdateChangeImageUrlNotYourself");
        testAuth();

        userUpdate.put("imageUrl", "C:\\Users\\alex\\Desktop\\1.jpg");
        MvcResult res = mockMvc.perform(post(REST_UPDATE)
                .content(userUpdate.toJSONString())
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isConflict())
                .andDo(print())
                .andReturn();
        Assert.assertNotEquals(userUpdate.get("imageUrl"), userService.getByName((String) userUpdate.get("username")).getImageUrl());
    }

    @Test
    public void testUpdateChangeImageUrlYourself() throws Exception
    {
        System.out.println("------------------");
        System.out.println("testUpdateChangeImageUrlYourself");
        //System.out.println(map.toString());
        Map<String, String> uMap = new HashMap<>();
        String userTested = "THREE";
        String passwordTested = "234";
        uMap.put("username", userTested);
        uMap.put("password", passwordTested);
        String userJSON = objectMapper.writeValueAsString(uMap);

        MvcResult mvcResult = mockMvc.perform(post(REST_AUTH)
                .content(userJSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();


        userUpdate.put("imageUrl", "C:\\Users\\alex\\\\Desktop\\1.jpg");
        MvcResult res = mockMvc.perform(post(REST_UPDATE)
                .content(userUpdate.toJSONString())
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        Assert.assertEquals(userUpdate.get("imageUrl"), userService.getByName((String) userUpdate.get("username")).getImageUrl());
    }
    

}
