package edu.self.tasktodo;

/**
 * Created by Hoang Anh on 01-Jul-18.
 */

public class Task {
    private String title;
    private int id;
    private String time;

    public Task(String title) {
        this.title = title;
    }

    public Task(String title, int id) {
        this.title = title;
        this.id = id;
    }

    public int getId() {
        return this.id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
