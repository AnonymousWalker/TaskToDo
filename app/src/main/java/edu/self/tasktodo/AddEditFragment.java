package edu.self.tasktodo;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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
        switch (view.getId()) {
            case R.id.saveBtn:
                String newTitle = txtTitle.getText().toString();
                if (newTitle.isEmpty()) return;
                boolean isAdd;
                if (currentTask != null){
                    currentTask.setTitle(newTitle);
                    isAdd = false;
                } else {
                    currentTask = new Task(newTitle);
                    isAdd = true;
                }
                callback.onItemSaved(currentTask, isAdd);
                callback.backPressed();
                break;
            case R.id.discardBtn:
                callback.backPressed();
                break;
            case R.id.removeBtn:
                callback.backPressed();
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
