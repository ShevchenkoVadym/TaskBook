package ru.javarush.taskbook.model;

import java.util.Map;

/**
 * Created by ilyakhromov on 12.02.15.
 */
public class TesterAnswerWrapper {

    private final static long serialVersionUID = 1L;

    private Task task;
    private Map<String,String> map;

    public TesterAnswerWrapper(Task task, Map<String, String> map) {
        this.task = task;
        this.map = map;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TesterAnswerWrapper that = (TesterAnswerWrapper) o;

        if (map != null ? !map.equals(that.map) : that.map != null) return false;
        if (task != null ? !task.equals(that.task) : that.task != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = task != null ? task.hashCode() : 0;
        result = 31 * result + (map != null ? map.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TesterAnswerWrapper{" +
                "task=" + task +
                ", map=" + map +
                '}';
    }
}
