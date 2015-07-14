package ru.javarush.taskbook.repository.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.javarush.taskbook.logger.LoggerWrapper;
import ru.javarush.taskbook.model.Task;
import ru.javarush.taskbook.model.enums.LifeStage;
import ru.javarush.taskbook.model.enums.TaskLevel;
import ru.javarush.taskbook.model.enums.TestEnvironment;
import ru.javarush.taskbook.repository.TaskRepositoryCustom;

import java.util.*;

/**
 * Created by Ilya on 02.12.2014.
 */
public class TaskRepositoryImpl implements TaskRepositoryCustom {
    private static final LoggerWrapper LOG = LoggerWrapper.get(TaskRepositoryImpl.class);
    @Autowired
    private MongoTemplate MT;

    @Override
    public Task updateTaskSimpleField(String id, String what, String newValue) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().set(what,newValue).currentDate("lastModificationDate");//currentTimestamp("lastModificationDate");
        return MT.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), Task.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Task updateTaskCollectionField(String id, String collectionName, String[] newValues) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().addToSet(collectionName).each(newValues).currentDate("lastModificationDate");
        return MT.findAndModify(query,update,new FindAndModifyOptions().returnNew(true),Task.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Task updateTaskCollectionField(String id, String collectionName, String[] newValues, Boolean addOrReplace) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = addOrReplace? new Update().addToSet(collectionName).each(newValues).currentDate("lastModificationDate"):
                                      new Update().set(collectionName, newValues);
        return MT.findAndModify(query,update,new FindAndModifyOptions().returnNew(true),Task.class);
    }

    @Override
    public void updateTaskRatingInc(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().inc("rating", 1);
        MT.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), Task.class);
    }

    @Override
    public void updateTaskRatingDec(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().inc("rating", -1);
        MT.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), Task.class);
    }

    @Override
    public void updateTaskLikedBy(String id, String username) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().push("likedBy", username);
        MT.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), Task.class);
    }

    @Override
    public void updateTaskDislikedBy(String id, String username) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().push("dislikedBy", username);
        MT.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), Task.class);
    }

    @Override
    public Task updateTaskVotersAmountInc(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().inc("votersAmount", 1);
        return MT.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), Task.class);
    }

    @Override
    public Task updateTaskVotersAmountDec(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().inc("votersAmount",-1);
        return MT.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), Task.class);
    }

    @Override
    public Task updateTaskSuccessfulAttemptsInc(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().inc("successfulAttempts",1);
        return MT.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), Task.class);
    }

    @Override
    public Task updateTaskSuccessfulAttemptsDec(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().inc("successfulAttempts",-1);
        return MT.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), Task.class);
    }

    @Override
    public Task updateTaskFailureAttemptsInc(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().inc("failureAttempts",1);
        return MT.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), Task.class);
    }

    @Override
    public Task updateTaskFailureAttemptsDec(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().inc("failureAttempts",-1);
        return MT.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), Task.class);
    }

    @Override
    public List<Task> findTasksByAuthor(String author) {
        Query query = new Query(Criteria.where("author").is(author));
        return MT.find(query, Task.class);
    }

    @Override
    public List<Task> findTasksByLevel(TaskLevel level) {
        Query query = new Query(Criteria.where("level").is(level.name()));
        return MT.find(query, Task.class);
    }

    @Override
    public List<String> getAllTags() {
        return MT.getCollection("task").distinct("tags");
    }

    @Override
    public List<TaskLevel> getAllLevels() {
        return Arrays.asList(TaskLevel.values());
    }

    @Override
    public List<TestEnvironment> getAllTestEnvironment() {
        return Arrays.asList(TestEnvironment.ACTUALLY_USED_VALUES);
    }

    @Override
    public boolean isTaskExists(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        return (MT.count(query,Task.class) > 0);
    }

    @Override
    public Task ensureUniqueTaskName(Task task){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").nin(task.getId()));
        query.fields().include("taskName");
        List<Task> tasks = MT.find(query, Task.class);

        task.ensureUniqueTaskName(tasks);

        return task;
    }

    /********************************************************************************
     */
    @Override
    public Page<Task> findAll(Pageable pageable, List<String> fields, List<String> levels, List<String> tags) {

        Query query = new Query();

        if (levels.size() > 0) { query.addCriteria(Criteria.where("level").in(levels)); }
        if (tags.size() > 0) { query.addCriteria(Criteria.where("tags").in(tags)); }
        long docAvailable = MT.find(query, Task.class).size();
        query = query.with(pageable);
        for(String field : fields){
            query.fields().include(field);
        }
        List<Task> tasks = MT.find(query,Task.class);

        return new PageImpl<>(tasks, pageable, docAvailable);
    }

    @Override
    public Page<Task> findAll(Pageable pageable, List<String> fields, List<String> levels, List<String> tags,
                              LifeStage lifeStage, List<String> ids, String username, boolean inverse) {

        Query query = new Query();

        if (levels.size() > 0) { query.addCriteria(Criteria.where("level").in(levels)); }
        if (tags.size() > 0) { query.addCriteria(Criteria.where("tags").in(tags)); }
        if (lifeStage != null) { query.addCriteria(Criteria.where("lifeStage").is(lifeStage)); }
        if (!username.equals("") && !inverse) {
            query.addCriteria(Criteria.where("id").in(ids));
        } else {
            query.addCriteria(Criteria.where("id").nin(ids));
        }

        long docAvailable = MT.find(query, Task.class).size();
        query = query.with(pageable);
        for(String field : fields){
            query.fields().include(field);
        }
        List<Task> tasks = MT.find(query,Task.class);

        return new PageImpl<>(tasks, pageable, docAvailable);
    }

    @Override
    public Task findById(String id, List<String> fields) {
        Query query = new Query(Criteria.where("id").is(id));
        for(String field : fields){
            query.fields().include(field);
        }
        return MT.findOne(query,Task.class);
    }

    @Override
    public Page<Task> findByAuthor(String author, Pageable pageable, List<String> fields) {
        long docAvailable = MT.find(new Query(Criteria.where("author").is(author)), Task.class).size();

        Query query = new Query(Criteria.where("author").is(author)).with(pageable);
        for(String field : fields){
            query.fields().include(field);
        }
        List<Task> tasks = MT.find(query,Task.class);
        return new PageImpl<>(tasks,pageable,docAvailable);
    }

    @Override
    public Map<String, Integer> findTagsByUser(String username, List<String> ids) {
        Query query = new Query();
        Criteria author = Criteria.where("author").is(username);

        if (ids.size() > 0){
            query.addCriteria(new Criteria().orOperator(Criteria.where("id").in(ids), author));
        } else {
            query.addCriteria(author);
        }

        List<Task> tasks = MT.find(query, Task.class);
        List<String> temp = new ArrayList<>();
        for(Task task : tasks){
            temp.addAll(task.getTags());
        }

        Map<String, Integer> allTags = new HashMap<>();
        for (String t: temp){
            if (allTags.containsKey(t)){
                allTags.put(t, allTags.get(t) + 1);
            }else{
                allTags.put(t, 1);
            }
        }
        return allTags;
    }
}
