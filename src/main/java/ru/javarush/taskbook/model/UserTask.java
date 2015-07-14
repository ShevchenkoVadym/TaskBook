package ru.javarush.taskbook.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Александр on 04.03.15.
 */
@Entity
@Table(name = "user_tasks")
public class UserTask implements Serializable{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
   // @Column(name = "user_id")
    private User user;

    //@ManyToOne
    //@JoinColumn(name = "task_id")

    //@ManyToOne
    //@JoinColumn(name="task_id")
    @Column(name = "task_id")
    private String taskId;

    @Column(name = "tryies_count")
    private Integer tryiesCount;

    @Column(name = "date_solve")
    private Date dateSolve;

    @Column(name = "status")
    private boolean status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

   /* public Tasks getTasks() {
        return tasks;
    }

    public void setTask(Tasks tasks) {
        this.tasks = tasks;
    }*/

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Date getDateSolve() {
        return dateSolve;
    }

    public Integer getTryiesCount() {
        return tryiesCount;
    }

    public void setTryiesCount(Integer tryiesCount) {
        this.tryiesCount = tryiesCount;
    }

    public void setDateSolve(Date dateSolve) {
        this.dateSolve = dateSolve;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
