package edu.self.tasktodo;

import java.util.Date;

/**
 * Created by Hoang Anh on 01-Jul-18.
 */

public class Task {
    private String title;
    private String id;
    private boolean hasReminder;
    private Date todoDate;
    private String time;

    public Task(String id, String title) {
        this.title = title;
        this.id = id;
        this.todoDate = null;
    }

    public Task(String id, String title, boolean hasReminder, Date date){
        this.id = id;
        this.title = title;
        this.hasReminder = hasReminder;
        this.todoDate = date;
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

    public Date getTodoDate() {
        return todoDate;
    }

    public void setTodoDate(Date todoDate) {
        this.todoDate = todoDate;
    }
}
