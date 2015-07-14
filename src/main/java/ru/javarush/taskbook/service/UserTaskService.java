package ru.javarush.taskbook.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.javarush.taskbook.model.Task;
import ru.javarush.taskbook.model.UserTask;

import java.util.List;
import java.util.Map;

/**
 * Created by Александр on 05.03.15.
 */
public interface UserTaskService {

    public void save(UserTask userTask);
    public void update (UserTask userTask);
    public void delete(int id);
    public List<UserTask> getAllUserTasks();
    public List<String> getAllTaskIdSuccessTaskForUser(String name);
    public Page<UserTask> getAllSuccessTasksForUser(Pageable pageable, String name);

    public UserTask getUserTaskUsernameAndTaskId(String username, String taskId);
}
