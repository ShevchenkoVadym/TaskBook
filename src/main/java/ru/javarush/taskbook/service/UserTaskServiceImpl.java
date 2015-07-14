package ru.javarush.taskbook.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.javarush.taskbook.model.Task;
import ru.javarush.taskbook.model.UserTask;
import ru.javarush.taskbook.repository.TaskRepository;
import ru.javarush.taskbook.repository.UserRepository;
import ru.javarush.taskbook.repository.UserTaskRepository;
import ru.javarush.taskbook.web.rest.exceptions.OperationNotPerformedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Александр on 05.03.15.
 */
@Service
public class UserTaskServiceImpl implements UserTaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserTaskServiceImpl.class);

    @Autowired
    private UserTaskRepository userTaskRepository;

    @Override
    @CacheEvict(value = {"getAllUserTasks"}, allEntries = true)
    public void save(UserTask userTask) {

        if (userTask.getUser() == null)
        {
            throw new OperationNotPerformedException();
        }

        if (userTask.getTaskId() == null)
        {
            throw new OperationNotPerformedException();
        }

        userTaskRepository.saveAndFlush(userTask);
    }

    @Override
    @CacheEvict(value = {"getAllUserTasks"}, allEntries = true)
    public void update(UserTask userTask) {

        userTaskRepository.saveAndFlush(userTask);
    }

    @Override
    @CacheEvict(value = {"getAllUserTasks"}, allEntries = true)
    public void delete(int id) {

        userTaskRepository.delete(id);
    }

    @Override
    @Cacheable("getAllUserTasks")
    public List<UserTask> getAllUserTasks() {
        return userTaskRepository.findAllUserTasks();
    }


    @Override
    public Page<UserTask> getAllSuccessTasksForUser(Pageable pageable, String name) {
        return userTaskRepository.findAllSolvedTasksForUser(pageable, name);
    }

    @Override
    public List<String> getAllTaskIdSuccessTaskForUser(String name) {
        return userTaskRepository.findAllTaskIdSuccessTasksForUser(name);
    }

    @Override
    public UserTask getUserTaskUsernameAndTaskId(String username, String taskId) {
        return userTaskRepository.findUserTaskUsernameAndTaskId(username, taskId);
    }

}
