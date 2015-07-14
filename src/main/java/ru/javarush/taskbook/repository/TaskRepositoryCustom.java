package ru.javarush.taskbook.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import ru.javarush.taskbook.model.Task;
import ru.javarush.taskbook.model.enums.LifeStage;
import ru.javarush.taskbook.model.enums.TaskLevel;
import ru.javarush.taskbook.model.enums.TestEnvironment;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Ilya on 02.12.2014.
 */
@NoRepositoryBean
public interface TaskRepositoryCustom {

    Task updateTaskSimpleField(String id, String what, String newValue);

    Task updateTaskCollectionField(String id, String collectionName, String[] newValue);
    Task updateTaskCollectionField(String id, String collectionName, String[] newValues, Boolean addOrReplace);

    void updateTaskRatingInc(String id);
    void updateTaskRatingDec(String id);

    void updateTaskLikedBy(String id, String username);
    void updateTaskDislikedBy(String id, String username);

    Task updateTaskVotersAmountInc(String id);
    Task updateTaskVotersAmountDec(String id);

    Task updateTaskSuccessfulAttemptsInc(String id);
    Task updateTaskSuccessfulAttemptsDec(String id);

    Task updateTaskFailureAttemptsInc(String id);
    Task updateTaskFailureAttemptsDec(String id);

    List<Task> findTasksByAuthor(String author);

    List<Task> findTasksByLevel(TaskLevel level);

    List<String> getAllTags();

    List<TaskLevel> getAllLevels();

    List<TestEnvironment> getAllTestEnvironment();

    boolean isTaskExists(String id);

    Task ensureUniqueTaskName(Task task);

    /************************************************************
     * RESTful API
     *************************************************************/
    Page<Task> findAll(Pageable pageable, List<String> fields, List<String> levels, List<String> tags);

    Page<Task> findAll(Pageable pageable, List<String> fields, List<String> levels, List<String> tags,
                       LifeStage lifeStage, List<String> ids, String username, boolean inverse);

    Task findById(String id,List<String>fields);

    Page<Task> findByAuthor(String author, Pageable pageable, List<String> fields);

    Map<String, Integer> findTagsByUser(String username, List<String> ids);
}
