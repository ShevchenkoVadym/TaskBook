package ru.javarush.taskbook.web.rest;

import com.google.gson.Gson;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.javarush.taskbook.auxilaryRunners.InstanceTestClassListener;
import ru.javarush.taskbook.auxilaryRunners.InstanceTestClassRunner;
import ru.javarush.taskbook.model.Task;
import ru.javarush.taskbook.model.enums.TaskLevel;
import ru.javarush.taskbook.service.TaskService;

import javax.annotation.PostConstruct;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by ilyakhromov on 23.01.15.
 */

@ContextConfiguration({
        "classpath:/spring/testContextsMongo/TestAppMongoContext.xml",
        "classpath:spring/testContextsMongo/spring-data-mongo-test.xml"
})

@WebAppConfiguration
@RunWith( InstanceTestClassRunner.class)
public class TaskRestTest implements InstanceTestClassListener{


    public static final String REST_LEVELS = "/rest/tasks/levels";
    public static final String REST_BASE = "/rest/tasks/";
    public static final String REST_SAVE = "/rest/tasks/save";
    public static final String REST_TAGS = "/rest/tasks/tags";
    public static final String REST_GET_ALL_TASKS = "/rest/tasks/tasks";
    public static final String REST_GET_ALL_TASKS_BY_AUTHOR = "/rest/tasks/user/";
    public static final String REST_GET_ALL_TEST_ENV = "/rest/tasks/environment";

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("UTF-8"));

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TaskService TS;
    @Autowired
    private MongoTemplate MT;

    @PostConstruct
    void postConstruct() {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Rule
    public Timeout globalTimeout = new Timeout(1000);

    @Rule
    public ExternalResource resource = new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            MT.dropCollection("task");
            MT.insert(new Task().setAuthor("a1").setLevel(TaskLevel.SENIOR).setTags(new HashSet<>(Arrays.asList("t1", "t2", "t2"))));
            MT.insert(new Task().setAuthor("a2").setLevel(TaskLevel.BEGINNER).setTags(new HashSet<>(Arrays.asList("t2", "t3"))));
            MT.insert(new Task().setAuthor("a3").setLevel(TaskLevel.JUNIOR).setTags(new HashSet<>(Arrays.asList("t4", "t5"))));
            MT.insert(new Task().setAuthor("a4").setLevel(TaskLevel.MIDDLE).setTags(new HashSet<>(Arrays.asList("t6", "t7", "t7"))));
            MT.insert(new Task().setAuthor("a5").setLevel(TaskLevel.SENIOR).setTags(new HashSet<>(Arrays.asList("t7", "t8"))));
            MT.insert(new Task().setAuthor("a6").setLevel(TaskLevel.SENIOR).setTags(new HashSet<>(Arrays.asList("t8", "t9"))));
            MT.insert(new Task().setAuthor("a7").setLevel(TaskLevel.BEGINNER).setTags(new HashSet<>(Arrays.asList("t10", "t12"))));
            MT.insert(new Task().setAuthor("a8").setLevel(TaskLevel.BEGINNER).setTags(new HashSet<>(Arrays.asList("t11", "t12"))));
        }
    };

    @Override
    public void afterClassSetup() {
        MT.getDb().dropDatabase();
    }
    @Override
    public void beforeClassSetup() {}

    @Test
    public void testGetAllLevels() throws Exception{

        mockMvc.perform(get(REST_LEVELS))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(is(new Gson().toJson(TS.getAllLevels()))))
        ;
    }

    @Test
    public void testGetAllLevelsException() throws Exception{

        mockMvc.perform(get(REST_LEVELS+"1"))
                .andExpect(status().is(400))
        ;
    }

    @Test
    public void testGetAllTags() throws Exception{
        mockMvc.perform(get(REST_TAGS).accept(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(content().string(is(new Gson().toJson(TS.getAllTags()))))
        ;
    }

    @Test
    public void testGetAllTagsException() throws Exception{
        mockMvc.perform(get(REST_TAGS+"1").accept(APPLICATION_JSON_UTF8))
                .andExpect(status().is(400))
        ;
    }

    @Test
    public void testGetAll() throws Exception{
        mockMvc.perform(get(REST_GET_ALL_TASKS).accept(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[*].author", hasItems("a1", "a2", "a3")))
        ;
    }

    @Test
    public void testGetAllException() throws Exception{
        mockMvc.perform(get(REST_GET_ALL_TASKS+"1").accept(APPLICATION_JSON_UTF8))
                .andExpect(status().is(400))
        ;
    }

    @Test
    public void testGet() throws Exception{
        String id = TS.findTasksByAuthor("a1").get(0).getId();
        mockMvc.perform(get(REST_BASE+id).accept(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.author",is("a1")))
        ;
    }

    @Test
    public void testGetException1() throws Exception{
        String id = TS.findTasksByAuthor("a1").get(0).getId();
        mockMvc.perform(get(REST_BASE+id+"g"))
                .andExpect(status().is(400))
        ;
    }

    @Test
    public void testGetException2() throws Exception{
        String id = TS.findTasksByAuthor("a1").get(0).getId();
        mockMvc.perform(get(REST_BASE+id+"a"))
                .andExpect(status().is(409))
        ;
    }

    @Test
    public void testDelete() throws Exception{
        String id = TS.findTasksByAuthor("a4").get(0).getId();
        mockMvc.perform(delete(REST_BASE+id).accept(APPLICATION_JSON_UTF8))
               .andExpect(status().isOk())
        ;
    }

    @Test
    public void testDeleteException() throws Exception{
        String id = TS.findTasksByAuthor("a1").get(0).getId();
        mockMvc.perform(delete(REST_BASE+id+"g").accept(APPLICATION_JSON_UTF8))
                .andExpect(status().is(400))
        ;
    }


    @Test
    public void testGetAddedTasks() throws Exception{
        List<String> ids = new ArrayList<>();
        for(Task T: TS.findTasksByAuthor("a2")){
            ids.add(T.getId());
        }
        mockMvc.perform(get(REST_GET_ALL_TASKS_BY_AUTHOR+"a2").accept(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[*].id",hasItems(ids.toArray())));
        ;
    }

    @Test
    public void testGetAddedTasksException() throws Exception{
        mockMvc.perform(get(REST_GET_ALL_TASKS_BY_AUTHOR+"a123").accept(APPLICATION_JSON_UTF8))
                .andExpect(status().is(404))
        ;
    }

    @Test
    public void testGetAllTestEnvironment() throws Exception{
        mockMvc.perform(get(REST_GET_ALL_TEST_ENV))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(is(new Gson().toJson(TS.getAllTestEnvironment()))))
        ;
    }

    @Test
    public void testGetAllTestEnvironmentException() throws Exception{
        mockMvc.perform(get(REST_GET_ALL_TEST_ENV+"1"))
                .andExpect(status().is(400))
        ;
    }

    @Test
    public void testSave() throws Exception{
        mockMvc.perform(post(REST_SAVE, new Task().setAuthor("a7")).accept(APPLICATION_JSON_UTF8))
                //.andExpect(status().isOk())
                //.andExpect(content().contentType(APPLICATION_JSON_UTF8))
        ;
        //TODO I don't know what to expect
        assertThat(TS.findTasksByAuthor("A7"),notNullValue());
    }

}
