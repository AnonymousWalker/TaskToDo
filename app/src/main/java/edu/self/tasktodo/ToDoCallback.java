package edu.self.tasktodo;

/**
 * Created by Hoang Anh on 01-Jul-18.
 */

public interface ToDoCallback {
    void onItemSelected(Task task);
    void backPressed();
}
