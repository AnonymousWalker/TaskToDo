package edu.self.tasktodo;

import java.util.Date;

/**
 * Created by Hoang Anh on 01-Jul-18.
 */

public class Task {
    private String id;
    private String title;
    private boolean hasReminder;
    private long todoTimeMilisec;

    public Task(String id, String title) {
        this.title = title;
        this.id = id;
    }

    public Task(String id, String title, boolean hasReminder, long timeMilisec){
        this.id = id;
        this.title = title;
        this.hasReminder = hasReminder;
        this.todoTimeMilisec = timeMilisec;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isHasReminder() {
        return hasReminder;
    }

    public void setHasReminder(boolean hasReminder) {
        this.hasReminder = hasReminder;
    }

    public long getTodoTimeMilisec() {
        return todoTimeMilisec;
    }

    public void setTodoTimeMilisec(long timeMilisec) {
        this.todoTimeMilisec = timeMilisec;
    }
}
