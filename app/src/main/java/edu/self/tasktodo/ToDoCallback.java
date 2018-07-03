package edu.self.tasktodo;

/**
 * Created by Hoang Anh on 01-Jul-18.
 */

public interface ToDoCallback {
    void addNewItem();
    void itemSelected(Task task);
    void itemSaved(Task task, boolean isAdd);
    void itemRemoved(String id);
    void backPress();
}
