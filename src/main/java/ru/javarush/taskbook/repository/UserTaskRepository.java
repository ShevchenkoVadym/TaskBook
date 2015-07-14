package ru.javarush.taskbook.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.javarush.taskbook.model.UserTask;

import java.util.List;

/**
 * Created by Александр on 05.03.15.
 */
public interface UserTaskRepository extends JpaRepository<UserTask, Integer> {


    @Query(value = "SELECT * FROM user_tasks", nativeQuery = true)
    public List<UserTask> findAllUserTasks();

    @Query("SELECT u.taskId from UserTask u where u.status = true and u.user.username = :name")
    public List<String> findAllTaskIdSuccessTasksForUser(@Param("name") String name);

    @Query("select u from UserTask u  where u.status = true and u.user.username = :name")
    public Page<UserTask> findAllSolvedTasksForUser(Pageable pageable, @Param("name") String name);

    @Query("select u from UserTask u where u.user.username = :name and u.taskId = :taskId")
    public UserTask findUserTaskUsernameAndTaskId(@Param("name") String username, @Param("taskId") String taskId);
}

