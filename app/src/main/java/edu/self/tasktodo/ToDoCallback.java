package edu.self.tasktodo;

/**
 * Created by Hoang Anh on 01-Jul-18.
 */

public interface ToDoCallback {
    void onAddItem();
    void onItemSelected(Task task);
    void onItemSaved(Task task, boolean isAdd);
    void onItemRemoved(Task task);
    void backPressed();
}
