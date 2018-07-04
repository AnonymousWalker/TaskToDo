package edu.self.tasktodo;

import edu.self.tasktodo.Utils.Task;

/**
 * Created by Hoang Anh on 01-Jul-18.
 */

public interface ToDoCallback {
    void addNewItem();
    void itemSelected(Task task);
    void itemSaved(Task task);
    void itemRemoved(String id);
    void backPress();
}
