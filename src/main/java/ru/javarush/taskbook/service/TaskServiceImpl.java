package ru.javarush.taskbook.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import ru.javarush.taskbook.model.Task;
import ru.javarush.taskbook.model.TesterAnswerWrapper;
import ru.javarush.taskbook.model.UserTask;
import ru.javarush.taskbook.model.enums.LifeStage;
import ru.javarush.taskbook.model.enums.TaskLevel;
import ru.javarush.taskbook.model.enums.TestEnvironment;
import ru.javarush.taskbook.repository.TaskRepository;
import ru.javarush.taskbook.tester.TaskTesterUtility;
import ru.javarush.taskbook.util.CurrentUser;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class TaskServiceImpl implements TaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    private MongoTemplate MT;

    @Autowired
    TaskRepository repository;

    @Autowired
    UserTaskService userTaskService;

    @Autowired
    UserService userService;

    @Override
    public Map<String, String> test(Task task) {
        return TaskTesterUtility.performTest(task , false);
    }

    @Override
    public TesterAnswerWrapper testAndWrap(Task task, boolean statistic) {
        Map<String, String> map = TaskTesterUtility.performTest(task, false);

        if (statistic)
        {
           UserTask userTask = null;

           if (map.get("compilation").equals("FAILURE"))
           {
               if (userTaskService.getAllUserTasks() != null)
               {
                   for (UserTask item : userTaskService.getAllUserTasks())
                   {
                       if (item.getUser().getId() == CurrentUser.getId() && item.getTaskId().equals(task.getId()))
                       {
                           userTask = item;
                           break;
                       }
                   }

                   if (userTask == null)
                   {
                       userTask = new UserTask();
                       userTask.setUser(CurrentUser.get());
                       userTask.setTaskId(task.getId());
                       userTask.setTryiesCount(1);
                       userTaskService.save(userTask);
                       task.setFailureAttempts(task.getFailureAttempts() + 1);
                       updateTaskFailureAttemptsInc(task.getId());

                   }
                   else
                   {
                       userTask.setTryiesCount(userTask.getTryiesCount() + 1);
                       userTaskService.update(userTask);
                       task.setFailureAttempts(task.getFailureAttempts() + 1);
                       updateTaskFailureAttemptsInc(task.getId());
                   }
               }
               else
               {
                   userTask = new UserTask();
                   userTask.setUser(CurrentUser.get());
                   userTask.setTaskId(task.getId());
                   userTask.setTryiesCount(1);
                   userTaskService.save(userTask);
                   task.setFailureAttempts(task.getFailureAttempts() + 1);
                   updateTaskFailureAttemptsInc(task.getId());
               }


           }
           else if (map.get("tests").equals("FAILURE"))
           {
               if (userTaskService.getAllUserTasks() != null)
               {
                   for (UserTask item : userTaskService.getAllUserTasks())
                   {
                       if (item.getUser().getId() == CurrentUser.getId() && item.getTaskId().equals(task.getId()))
                       {
                           userTask = item;
                           break;
                       }
                   }

                   if (userTask == null)
                   {
                       userTask = new UserTask();
                       userTask.setUser(CurrentUser.get());
                       userTask.setTaskId(task.getId());
                       userTask.setTryiesCount(1);
                       userTaskService.save(userTask);
                       task.setFailureAttempts(task.getFailureAttempts() + 1);
                       updateTaskFailureAttemptsInc(task.getId());
                   }
                   else
                   {
                       userTask.setTryiesCount(userTask.getTryiesCount() + 1);
                       userTaskService.update(userTask);
                       task.setFailureAttempts(task.getFailureAttempts() + 1);
                       updateTaskFailureAttemptsInc(task.getId());
                   }
               }
               else
               {

                   userTask = new UserTask();
                   userTask.setUser(CurrentUser.get());
                   userTask.setTaskId(task.getId());
                   userTask.setTryiesCount(1);
                   userTaskService.save(userTask);
                   task.setFailureAttempts(task.getFailureAttempts() + 1);
                   updateTaskFailureAttemptsInc(task.getId());
               }
           }
           else
           {
               double[] array = new double[] {0.05, 0.15, 0.25, 0.35};
               if (!userTaskService.getAllUserTasks().isEmpty())
               {
                   for (UserTask item : userTaskService.getAllUserTasks())
                   {
                       if (item.getUser().getId() == CurrentUser.getId() && item.getTaskId().equals(task.getId()))
                       {
                           userTask = item;
                           break;
                       }
                   }

                   if (userTask == null)
                   {

                       userTask = new UserTask();
                       userTask.setUser(CurrentUser.get());
                       userTask.setTaskId(task.getId());
                       userTask.setTryiesCount(1);
                       userTask.setDateSolve(new Date());
                       userTask.setStatus(true);
                       userTaskService.save(userTask);
                       increaseRating(task, array, false);
                       CurrentUser.get().setTasksSolved(CurrentUser.get().getTasksSolved() + 1);
                       userService.update(CurrentUser.get());
                       task.setSuccessfulAttempts(task.getSuccessfulAttempts() + 1);
                       updateTaskSuccessfulAttemptsInc(task.getId());


                   }
                   else
                   {
                       if (!userTask.isStatus())
                       {

                           userTask.setTryiesCount(userTask.getTryiesCount() + 1);
                           userTask.setDateSolve(new Date());
                           userTask.setStatus(true);
                           userTaskService.update(userTask);
                           increaseRating(task, array, true);
                           CurrentUser.get().setTasksSolved(CurrentUser.get().getTasksSolved() + 1);
                           userService.update(CurrentUser.get());
                           task.setSuccessfulAttempts(task.getSuccessfulAttempts() + 1);
                           updateTaskSuccessfulAttemptsInc(task.getId());

                       }
                   }
               }
               else
               {

                   userTask = new UserTask();
                   userTask.setUser(CurrentUser.get());
                   userTask.setTaskId(task.getId());
                   userTask.setTryiesCount(1);
                   userTask.setDateSolve(new Date());
                   userTask.setStatus(true);
                   userTaskService.save(userTask);
                   increaseRating(task, array, false);
                   CurrentUser.get().setTasksSolved(CurrentUser.get().getTasksSolved() + 1);
                   userService.update(CurrentUser.get());
                   task.setSuccessfulAttempts(task.getSuccessfulAttempts() + 1);
                   updateTaskSuccessfulAttemptsInc(task.getId());

               }
           }
        }
        return new TesterAnswerWrapper(task, map);
    }

    /**
     * Save task only when it pass all tests
     * @param task task to save
     * @return server answer
     */
    @Override
    @CacheEvict(value = {"findAllTasks", "findOneTask", "getAllTags"}, allEntries=true)
    public Map<String, String> save(Task task) {
        Map<String, String> map = TaskTesterUtility.performTest(task, true);
        if (!map.containsValue("FAILURE") && !map.containsValue("Error")) {
         repository.save(task);
        }
        return map;
    }

    @Override
    @CacheEvict(value = {"findAllTasks", "findOneTask", "getAllTags"}, allEntries=true)
    public <S extends Task> TesterAnswerWrapper validateAndSave(S task) {

        Map<String, String> map = TaskTesterUtility.performTest(task, true);
        if (!map.containsValue("FAILURE") && !map.containsValue("Error")) {
            increaseRating(task, new double[]{0.15, 0.25, 0.35, 0.45}, false);
            userService.update(CurrentUser.get());

            task = (S) repository.ensureUniqueTaskName(task);

            task = repository.save(task);
        }
        return new TesterAnswerWrapper(task,map);

    }

    @Override
    public Task ensureUniqueTaskName(Task task){
        return repository.ensureUniqueTaskName(task);
    }

    @Override
    public <S extends Task> List<S> save(Iterable<S> entites) {
        return repository.save(entites);
    }

    @Override
    public List<Task> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Task> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public Page<Task> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Cacheable("findOneTask")
    public Task findOne(String id) {
        return repository.findOne(id);
    }

    @Override
    public boolean exists(String id) {
        return repository.exists(id);
    }

    @Override
    public Iterable<Task> findAll(Iterable<String> ids) {
        return repository.findAll(ids);
    }

    @Override
    public long count() {
        return repository.count();
    }

    public void delete(Task task) {
        repository.delete(task);
    }

    @Override
    @CacheEvict(value = {"findAllTasks", "findOneTask", "getAllTags"}, allEntries=true)
    public void delete(String id) {
        repository.delete(id);
    }

    @Override
    public void delete(Iterable<? extends Task> entities) {
        repository.delete(entities);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public Task updateTaskSimpleField(String id, String what, String newValue) {
        return repository.updateTaskSimpleField(id,what,newValue);
    }

    @Override
    public Task updateTaskCollectionField(String id, String collectionName, String[] newValues) {
        return repository.updateTaskCollectionField(id, collectionName, newValues);
    }

    @Override
    public Task updateTaskCollectionField(String id, String collectionName, String[] newValues, Boolean addOrReplace) {
        return repository.updateTaskCollectionField(id,collectionName,newValues,addOrReplace);
    }

    @Override
    public void updateTaskRatingInc(String id) { repository.updateTaskRatingInc(id); }

    @Override
    public void updateTaskRatingDec(String id) { repository.updateTaskRatingDec(id); }

    @Override
    public void updateTaskLikedBy(String id, String username) { repository.updateTaskLikedBy(id, username); }

    @Override
    public void updateTaskDislikedBy(String id, String username) { repository.updateTaskDislikedBy(id, username); }

    @Override
    public Task updateTaskVotersAmountInc(String id) {
        return repository.updateTaskVotersAmountInc(id);
    }

    @Override
    public Task updateTaskVotersAmountDec(String id) {
        return repository.updateTaskVotersAmountDec(id);
    }

    @Override
    public Task updateTaskSuccessfulAttemptsInc(String id) {
        return repository.updateTaskSuccessfulAttemptsInc(id);
    }

    @Override
    public Task updateTaskSuccessfulAttemptsDec(String id) {
        return repository.updateTaskSuccessfulAttemptsDec(id);
    }

    @Override
    public Task updateTaskFailureAttemptsInc(String id) {
        return repository.updateTaskFailureAttemptsInc(id);
    }

    @Override
    public Task updateTaskFailureAttemptsDec(String id) {
        return repository.updateTaskFailureAttemptsDec(id);
    }

    @Override
    @Cacheable("getAddedTasks")
    public List<Task> findTasksByAuthor(String author) {
        return repository.findTasksByAuthor(author);
    }

    @Override
    public List<Task> findTasksByLevel(TaskLevel level) {
        return repository.findTasksByLevel(level);
    }

    // do not cache this method
    @Override
    public List<String> getAllTags() {
        return repository.getAllTags();
    }

    @Override
    @Cacheable("getAllLevels")
    public List<TaskLevel> getAllLevels() {
        return repository.getAllLevels();
    }

    @Override
    public List<TestEnvironment> getAllTestEnvironment() {
        return repository.getAllTestEnvironment();
    }

    @Override
    public boolean isTaskExists(String id) {
        return repository.isTaskExists(id);
    }

    /******************************************************************************************
     * RESTful API
     ******************************************************************************************/
    @Override
    @Cacheable("findAllTasks")
    public Page<Task> findAll(Pageable pageable, List<String> fields, List<String> levels, List<String> tags) {
        return repository.findAll(pageable, fields, levels, tags);
    }

    @Override
    public Page<Task> findAll(Pageable pageable, List<String> fields, List<String> levels, List<String> tags,
                              LifeStage lifeStage, List<String> ids, String username, boolean inverse) {
        return repository.findAll(pageable, fields, levels, tags, lifeStage, ids, username, inverse);
    }

    @Override
    public Task findById(String id, List<String> fields) {
        return repository.findById(id, fields);
    }

    @Override
    public Page<Task> findByAuthor(String author, Pageable pageable, List<String> fields) {
        return repository.findByAuthor(author, pageable, fields);
    }

    @Override
    public Map<String, Integer> findTagsByUser(String username, List<String> ids) {
        return repository.findTagsByUser(username, ids);
    }

    public void increaseRating(Task task, double [] array, boolean ReduceRating)
    {

        switch (task.getLevel())
        {
            case BEGINNER:{

                if (ReduceRating)
                {
                    UserTask userTask = userTaskService.getUserTaskUsernameAndTaskId(CurrentUser.get().getUsername(), task.getId());
                    if (userTask != null)
                    {
                      if (userTask.getTryiesCount() > 3 && userTask.getTryiesCount() < 50)
                      {
                        for (int i = 4; i <= userTask.getTryiesCount(); i++)
                        {
                            if ((array[0] - (array[0] * 0.2)) > 0)
                            {
                                array[0] -= array[0] * 0.2;
                            }
                        }
                        CurrentUser.get().setRating(new BigDecimal(CurrentUser.get().getRating() + array[0]).setScale(2, RoundingMode.UP).doubleValue());

                      }
                      else if (userTask.getTryiesCount() <= 3)
                      {
                          CurrentUser.get().setRating(Double.parseDouble(String.format("%.2f", CurrentUser.get().getRating() + array[0]).replaceAll(",", ".")));
                      }
                    }
                }
                else
                {
                   CurrentUser.get().setRating(Double.parseDouble(String.format("%.2f", CurrentUser.get().getRating() + array[0]).replaceAll(",", ".")));
                }
                break;
            }
            case JUNIOR:{
                if (ReduceRating)
                {
                    UserTask userTask = userTaskService.getUserTaskUsernameAndTaskId(CurrentUser.get().getUsername(), task.getId());
                    if (userTask != null)
                    {
                        if (userTask.getTryiesCount() > 3 && userTask.getTryiesCount() < 50)
                        {
                            for (int i = 4; i <= userTask.getTryiesCount(); i++)
                            {
                                if ((array[1] - (array[1] * 0.2)) > 0)
                                {
                                    array[1] -= array[1] * 0.2;
                                }
                            }
                            CurrentUser.get().setRating(new BigDecimal(CurrentUser.get().getRating() + array[1]).setScale(2, RoundingMode.UP).doubleValue());

                        }
                        else if (userTask.getTryiesCount() <= 3)
                        {
                            CurrentUser.get().setRating(Double.parseDouble(String.format("%.2f", CurrentUser.get().getRating() + array[1]).replaceAll(",", ".")));
                        }
                    }
                }
                else
                {
                    CurrentUser.get().setRating(Double.parseDouble(String.format("%.2f", CurrentUser.get().getRating() + array[1]).replaceAll(",", ".")));
                }
                break;
            }

            case MIDDLE:{
                if (ReduceRating)
                {
                    UserTask userTask = userTaskService.getUserTaskUsernameAndTaskId(CurrentUser.get().getUsername(), task.getId());
                    if (userTask != null)
                    {
                        if (userTask.getTryiesCount() > 3 && userTask.getTryiesCount() < 50)
                        {
                            for (int i = 4; i <= userTask.getTryiesCount(); i++)
                            {
                                if ((array[2] - (array[2] * 0.2)) > 0)
                                {
                                    array[2] -= array[2] * 0.2;
                                }
                            }
                            CurrentUser.get().setRating(new BigDecimal(CurrentUser.get().getRating() + array[2]).setScale(2, RoundingMode.UP).doubleValue());

                        }
                        else if (userTask.getTryiesCount() <= 3)
                        {
                            CurrentUser.get().setRating(Double.parseDouble(String.format("%.2f", CurrentUser.get().getRating() + array[2]).replaceAll(",", ".")));
                        }
                    }
                }
                else
                {
                    CurrentUser.get().setRating(Double.parseDouble(String.format("%.2f", CurrentUser.get().getRating() + array[2]).replaceAll(",", ".")));
                }
                break;
            }

            case SENIOR:{
                if (ReduceRating)
                {
                    UserTask userTask = userTaskService.getUserTaskUsernameAndTaskId(CurrentUser.get().getUsername(), task.getId());
                    if (userTask != null)
                    {
                        if (userTask.getTryiesCount() > 3 && userTask.getTryiesCount() < 50)
                        {
                            for (int i = 4; i <= userTask.getTryiesCount(); i++)
                            {
                                if ((array[3] - (array[3] * 0.2)) > 0)
                                {
                                    array[3] -= array[3] * 0.2;
                                }
                            }
                            CurrentUser.get().setRating(new BigDecimal(CurrentUser.get().getRating() + array[3]).setScale(2, RoundingMode.UP).doubleValue());

                        }
                        else if (userTask.getTryiesCount() <= 3)
                        {
                            CurrentUser.get().setRating(Double.parseDouble(String.format("%.2f", CurrentUser.get().getRating() + array[3]).replaceAll(",", ".")));
                        }
                    }
                }
                else
                {
                    CurrentUser.get().setRating(Double.parseDouble(String.format("%.2f", CurrentUser.get().getRating() + array[3]).replaceAll(",", ".")));
                }
                break;
            }

        }

    }
}
