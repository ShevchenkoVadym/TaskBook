package ru.javarush.taskbook.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.javarush.taskbook.model.Task;
import ru.javarush.taskbook.model.TesterAnswerWrapper;
import ru.javarush.taskbook.model.enums.LifeStage;
import ru.javarush.taskbook.model.enums.TaskLevel;
import ru.javarush.taskbook.model.enums.TestEnvironment;


public interface TaskService{

    /**
     * Test solutions received from user
     *
     * @param task contains solution to test
     * @return answer
     */
    Map<String, String> test(Task task);

    <S extends Task> Map<String, String> save(S entity);

    <S extends Task> TesterAnswerWrapper validateAndSave(S entity);

    <S extends Task> List<S> save(Iterable<S> entites);

    Task ensureUniqueTaskName(Task task);

    List<Task> findAll();

    List<Task> findAll(Sort sort);

    Page<Task> findAll(Pageable pageable);

    Task findOne(String id);

    boolean exists(String id);

    Iterable<Task> findAll(Iterable<String> strings);

    long count();

    void delete(Task entity);

    void delete(String id);

    void delete(Iterable<? extends Task> entities);

    void deleteAll();

    Task updateTaskSimpleField(String id, String what, String newValue);

    Task updateTaskCollectionField(String id, String collectionName, String[] newValue);
    Task updateTaskCollectionField(String id, String collectionName, String[] newValues, Boolean addOrReplace);

    void updateTaskRatingInc(String id);
    void updateTaskRatingDec(String id);

    public void updateTaskLikedBy(String id, String username);
    public void updateTaskDislikedBy(String id, String username);

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

    /************************************************
            RESTful API
    *************************************************/

    Page<Task> findAll(Pageable pageable, List<String> fields, List<String> levels, List<String> tags);

    Page<Task> findAll(Pageable pageable, List<String> fields, List<String> levels, List<String> tags,
                       LifeStage lifeStage, List<String> ids, String username, boolean inverse);

    Task findById(String id,List<String>fields);

    Page<Task> findByAuthor(String author, Pageable pageable, List<String> fields);

    TesterAnswerWrapper testAndWrap(Task task, boolean statistic);

    Map<String, Integer> findTagsByUser(String username, List<String> ids);
}
