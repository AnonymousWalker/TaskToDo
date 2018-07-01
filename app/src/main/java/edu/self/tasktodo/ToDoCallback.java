package edu.self.tasktodo;

/**
 * Created by Hoang Anh on 01-Jul-18.
 */

public interface ToDoCallback {
    void onItemClick(Task task);
    void backPressed();
}
