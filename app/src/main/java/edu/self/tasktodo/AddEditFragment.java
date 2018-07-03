package edu.self.tasktodo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.self.tasktodo.Main.MainActivity;

/**
 * Created by Hoang Anh on 01-Jul-18.
 */

public class AddEditFragment extends Fragment implements View.OnClickListener {
    private boolean detect;
    private Task currentTask;
    private EditText txtTitle;
    private ToDoCallback callback;
    private FloatingActionButton btnSave;
    private Button btnCancel, btnRemove;
    private final String ADD_SUCCESSFULLY_ALERT = "Successfully saved";
    private final String INVALID_INPUT_ALERT = "Invalid input";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_edit_todo, null);
        txtTitle = (EditText) rootView.findViewById(R.id.txtTodoText);
        btnSave = rootView.findViewById(R.id.saveBtn);
        btnCancel = rootView.findViewById(R.id.discardBtn);
        btnRemove = rootView.findViewById(R.id.removeBtn);

        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnRemove.setOnClickListener(this);
        updateUI();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        callback = (ToDoCallback) getActivity();
    }

    @Override
    public void onClick(View view) {
        boolean isSuccessed;
        switch (view.getId()) {
            case R.id.saveBtn:  //check before saving
                String newTitle = txtTitle.getText().toString();
                if (newTitle.isEmpty()){
                    Toast.makeText(this.getActivity(), INVALID_INPUT_ALERT, Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isAdd;
                if (currentTask != null) {
                    currentTask.setTitle(newTitle);
                    isAdd = false;
                } else {
                    //add new todo
                    String id = String.valueOf(System.currentTimeMillis());
                    currentTask = new Task(id, newTitle);
                    isAdd = true;
                }
                callback.itemSaved(currentTask, isAdd);
                Toast.makeText(this.getActivity(), ADD_SUCCESSFULLY_ALERT, Toast.LENGTH_SHORT).show();
                callback.backPress();
                break;

            case R.id.discardBtn:
                callback.backPress();
                break;

            case R.id.removeBtn:    //check before removing
                if (currentTask == null){
                    callback.backPress();
                    return;
                }
                callback.itemRemoved(currentTask.getId());
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
        this.detect = true;
    }

    public boolean isDetect() {
        return detect;
    }

    public void setDetect(boolean detect) {
        this.detect = detect;
    }

    public void setTodo(Task task) {
        this.currentTask = task;
    }


    // << Private region >>
    private void updateUI() {
        if (currentTask != null) {
            txtTitle.setText(currentTask.getTitle());
        } else {
            txtTitle.setText("");
        }
    }

}
