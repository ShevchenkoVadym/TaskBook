package ru.javarush.taskbook.service;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.javarush.taskbook.logger.LoggerWrapper;
import ru.javarush.taskbook.model.Task;
import ru.javarush.taskbook.model.enums.TaskLevel;
import ru.javarush.taskbook.repository.TaskRepository;
import ru.javarush.taskbook.auxilaryRunners.InstanceTestClassListener;
import ru.javarush.taskbook.auxilaryRunners.InstanceTestClassRunner;
import java.util.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;


/**
 * Created by Ilya on 10.12.2014.
 */
@ContextConfiguration({
        "classpath:/spring/testContextsMongo/TestAppMongoContext.xml",
        "classpath:spring/testContextsMongo/spring-data-mongo-test.xml"
})
@WebAppConfiguration
@RunWith( InstanceTestClassRunner.class)
public class TaskServiceImplTest implements InstanceTestClassListener {
    private static final LoggerWrapper LOG = LoggerWrapper.get(TaskServiceImplTest.class);
    @Autowired
    private MongoTemplate MT;
    @Autowired
    private TaskRepository TR;
    @Autowired
    private TaskServiceImpl TS;

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
    public void testSave(){
        //TODO this method through TS estimating exceptions if necessary
        Task t = new Task().setAuthor("a");
        assertThat(TR.save(t), is(t));
        assertThat(TR.findTasksByAuthor("a").size(), is(1));
    }

    @Test
    public void testSaveIterable(){
        List<Task> list = new ArrayList<>();
        list.add(new Task().setAuthor("a9"));
        list.add(new Task().setAuthor("a9"));
        list.add(new Task().setAuthor("a9"));
        assertThat(TS.save(list),is(list));
        assertThat(TS.findTasksByAuthor("a9"), is(list));
    }

    @Test
    public void testFindAll(){
        List<Task> tasks = TS.findAll();
        assertThat(tasks, hasSize(8));
        assertThat(tasks.get(7).getAuthor(), is("a8"));
    }

    @Test
    public void testFindAllPageble(){
        Page<Task> page = TS.findAll(new PageRequest(1,2));
        assertThat(page.getTotalElements(),is((long)8));
        assertThat(page.getTotalPages(),is(4));
        assertThat(page.getNumberOfElements(),is(2));
    }

    @Test
    public void testFindAllSortable(){
        List<Task> list = TS.findAll(new Sort(Sort.Direction.DESC,"author"));
        assertThat(list.get(0).getAuthor(),is("a8"));
        assertThat(list.get(7).getAuthor(),is("a1"));
    }

    @Test
    public void testFindOne(){
        Task t = TS.findTasksByAuthor("a4").get(0);
        assertThat(TS.findOne(t.getId()), is(t));
    }

    @Test
    public void testExists(){
        Task t = TS.findTasksByAuthor("a4").get(0);
        assertThat(TS.exists(t.getId()), is(true));
    }

    @Test
    public void testFindAllIterable(){
        List<String> l = new ArrayList<>();
        l.add(TS.findTasksByAuthor("a1").get(0).getId());
        l.add(TS.findTasksByAuthor("a5").get(0).getId());

        Iterable<Task> iterable = TS.findAll(l);

        for(Task t: iterable){
            assertThat(t, anyOf(equalTo(TS.findTasksByAuthor("a5").get(0)), equalTo(TS.findTasksByAuthor("a1").get(0))));
        }
    }

    @Test
    public void testCount(){
        assertThat(TS.count(), is((long)8));
    }

    @Test
    public void testDeleteTask(){
        Task t = TS.findTasksByAuthor("a4").get(0);
        TS.delete(t);
        assertThat(TS.findTasksByAuthor("a4"), is(empty()));
    }

    @Test
    public void testDeleteString(){
        String id = TS.findTasksByAuthor("a4").get(0).getId();
        TS.delete(id);
        assertThat(TS.findTasksByAuthor("a4"), is(empty()));
    }

    @Test
    public void testDeleteIterable(){
        List<Task> l = new ArrayList<>();
        l.add(TS.findTasksByAuthor("a1").get(0));
        l.add(TS.findTasksByAuthor("a5").get(0));
        TS.delete(l);
        assertThat(TS.findTasksByAuthor("a1"),is(empty()));
        assertThat(TS.findTasksByAuthor("a5"), is(empty()));
    }

    @Test
    public void testDeleteAll(){
        TS.deleteAll();
        assertThat(TS.count(),is((long)0));
    }

    @Test
    public void testUpdateTaskSimpleField(){
        String id = TS.findTasksByAuthor("a4").get(0).getId();
        TS.updateTaskSimpleField(id, "author", "frosy");
        assertThat(TS.findOne(id).getAuthor(), is("frosy"));
    }

    @Test
    public void testUpdateTaskCollectionField(){
        Task t = TS.findTasksByAuthor("a4").get(0);
        Set<String> tags = t.getTags();
        tags.add("awesome thing");
        assertThat(TS.updateTaskCollectionField(t.getId(),"tags",tags.toArray(new String[tags.size()])).getTags(), hasItem("awesome thing"));
    }

    @Test
    public void testUpdateTaskVotersAmountInc(){
        String id = TS.findTasksByAuthor("a4").get(0).getId();
        TS.updateTaskVotersAmountInc(id);
        assertThat(TS.findTasksByAuthor("a4").get(0).getVotersAmount(), is(1));
    }

    @Test
    public void testUpdateTaskVotersAmountDec(){
        String id = TS.findTasksByAuthor("a4").get(0).getId();
        TS.updateTaskVotersAmountDec(id);
        assertThat(TS.findTasksByAuthor("a4").get(0).getVotersAmount(), is(-1));
    }

    @Test
    public void testUpdateTaskSuccessfulAttemptsInc(){
        String id = TS.findTasksByAuthor("a4").get(0).getId();
        TS.updateTaskSuccessfulAttemptsInc(id);
        assertThat(TS.findTasksByAuthor("a4").get(0).getSuccessfulAttempts(), is(1));
    }

    @Test
    public void testUpdateTaskSuccessfulAttemptsDec(){
        String id = TS.findTasksByAuthor("a4").get(0).getId();
        TS.updateTaskSuccessfulAttemptsDec(id);
        assertThat(TS.findTasksByAuthor("a4").get(0).getSuccessfulAttempts(), is(-1));
    }

    @Test
    public void testUpdateTaskFailureAttemptsInc(){
        String id = TS.findTasksByAuthor("a4").get(0).getId();
        TS.updateTaskFailureAttemptsInc(id);
        assertThat(TS.findTasksByAuthor("a4").get(0).getFailureAttempts(), is(1));
    }

    @Test
    public void testUpdateTaskFailureAttemptsDec(){
        String id = TS.findTasksByAuthor("a4").get(0).getId();
        TS.updateTaskFailureAttemptsDec(id);
        assertThat(TS.findTasksByAuthor("a4").get(0).getFailureAttempts(), is(-1));
    }

    @Test
    public void testFindTasksByLevel(){
        assertThat(TS.findTasksByLevel(TaskLevel.SENIOR).size(),is(3));
        assertThat(TS.findTasksByLevel(TaskLevel.BEGINNER).size(),is(3));
        assertThat(TS.findTasksByLevel(TaskLevel.JUNIOR).size(),is(1));
        assertThat(TS.findTasksByLevel(TaskLevel.MIDDLE).size(),is(1));
    }

    @Test
    public void testGetAllTags(){
        assertThat(TS.getAllTags(),hasItems("t1","t2","t3","t4","t5","t6","t7","t8","t9","t10","t11","t12"));
    }

}
