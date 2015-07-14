package ru.javarush.taskbook.service;

import net.sf.ehcache.CacheManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.javarush.taskbook.model.User;
import ru.javarush.taskbook.util.DbPopulator;

import java.util.Arrays;

import static ru.javarush.taskbook.TestUtil.*;

/**
 * User: blacky
 * Date: 23.11.14
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml",
        "classpath:spring/spring-data-mongo.xml"
})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)

public class UserServiceImplTest {

    @Autowired
    private DbPopulator dbPopulator;

    @Autowired
    private UserService service;

    @Autowired
    private CacheManager cacheManager;

    protected User checkedUser;

    @Rule
    public Timeout globalTimeout = new Timeout(1000);

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testCache(){
        Assert.assertNotNull(cacheManager.getCache("getAllUsers"));

        //calling getByName method first time.
        System.out.println(service.getByName("ONE"));
        //calling getByName method second time. This time, method will not execute.
        System.out.println(service.getByName("ONE"));

        //calling getAll method first time.
        for (int i = 0; i < service.getAll().size(); i++)
        {
            System.out.println(service.getAll().get(i));
        }

        //calling getAll method second time. This time, method will not execute.
        for (int i = 0; i < service.getAll().size(); i++)
        {
            System.out.println(service.getAll().get(i));
        }
    }


    @Test
    public void testGetAll() throws Exception {

        System.out.println("service: " + service);
        assertToStringEquals(Arrays.asList(USER00, USER01, USER02), service.getAll());
    }

    @Test
    public void testGetByName() throws Exception {
        checkedUser = service.getByName("ONE");
        assertToStringEquals(USER00, checkedUser); // (expected, actual)
    }

    @Test
    public void testSave() throws Exception {


        service.save(NEW_USER);
        //assertToStringEquals(NEW_USER, service.get(NEW_USER_ID));
        User testUser = service.getByName(NEW_USER.getUsername());

                assertToStringEquals(NEW_USER,testUser );
    }

    @Test
    public void testUpdate()
    {
        checkedUser = service.getByName("ONE");
        checkedUser.setUsername(UPDATED_NAME);
        checkedUser.setEmail(UPDATED_EMAIL);
        service.update(checkedUser);
        assertToStringEquals(checkedUser, service.getByName(checkedUser.getUsername()));
    }

    @Test
    public void testDelete()
    {
        service.delete(USER00.getUsername());
        assertToStringEquals(Arrays.asList(USER01,USER02), service.getAll());
    }

    @Test(expected = Exception.class)
    public void testDuplicateUsernameSave() throws Exception
    {
        NEW_USER.setUsername(USER00.getUsername());
        service.save(NEW_USER);
    }

    @Test(expected = Exception.class)
    public void testDuplicateMailSave() throws Exception
    {
        NEW_USER.setEmail(USER00.getEmail());
        service.save(NEW_USER);
    }

    @Test(expected = Exception.class)
    public void testDeleteNotFound()
    {
        service.delete("NAME");
    }

}
