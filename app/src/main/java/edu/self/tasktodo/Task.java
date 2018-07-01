package edu.self.tasktodo;

/**
 * Created by Hoang Anh on 01-Jul-18.
 */

public class Task {
    private String title;
    private int position;
    private String time;

    public Task(String title, int position) {
        this.title = title;
        this.position = position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTitle() { return this.title; }
}
