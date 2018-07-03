package edu.self.tasktodo;

/**
 * Created by Hoang Anh on 01-Jul-18.
 */

public class Task {
    private String title;
    private String id;
    private String time;

    public Task(String id, String title) {
        this.title = title;
        this.id = id;
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
}
