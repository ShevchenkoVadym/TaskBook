package ru.javarush.taskbook;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.javarush.taskbook.model.Task;
import ru.javarush.taskbook.model.User;
import ru.javarush.taskbook.service.TaskService;
import ru.javarush.taskbook.service.TaskServiceImpl;
import ru.javarush.taskbook.service.UserService;
import ru.javarush.taskbook.service.UserServiceImpl;

/**
 * User: blacky
 * Date: 22.08.2014
 */
public class SpringMain {
    public static void main(String[] args) {
        String[] contexts = {"spring/spring-app.xml",
                "spring/spring-db.xml",
                "spring/spring-data-mongo.xml"};
        ConfigurableApplicationContext bf = new ClassPathXmlApplicationContext(contexts);
        //GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        //ctx.getEnvironment().setActiveProfiles(Profiles.JPA, Profiles.HSQLDB);
        //ctx.load(contexts);
        //ctx.refresh();
        //ctx.close();

        UserService service = bf.getBean(UserService.class);
        TaskService taskService = bf.getBean(TaskService.class);
        MongoTemplate MT = bf.getBean(MongoTemplate.class);

        int i = 0;

        for (User item : service.getAll())
        {
            if (i++ > 5)
                break;
            System.out.println("user: " + item.getUsername());
        }

        i = 0;

        for (Task item : taskService.findAll())
        {
            if (i++ > 5)
                break;
            System.out.println("task: " + item.getCondition());
        }

        //populate mongo db with 100000 tasks
        // for(i = 0; i< 100000; i++){
        //     MT.insert(new Task().setAuthor("ara"+i).setRating(i).setTaskName("task"+i));
        // }

        System.out.println("\n========Beans=in=Spring=context========");
        for (String bean : bf.getBeanDefinitionNames())
            System.out.println(bean);
        System.out.println("=======================================\n");

        bf.close();
    }
}