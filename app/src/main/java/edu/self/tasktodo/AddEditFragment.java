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
import android.widget.ListView;

/**
 * Created by Hoang Anh on 01-Jul-18.
 */

public class AddEditFragment extends Fragment implements View.OnClickListener{
    private boolean detect;
    private Task currentTask;
    private EditText txtTitle;
    private ToDoCallback callback;
    private FloatingActionButton btnSave;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_edit_todo, null);
        txtTitle = rootView.findViewById(R.id.txtTodoText);
        txtTitle.setText(this.currentTask.getTitle());
        btnSave = rootView.findViewById(R.id.saveBtn);
        btnSave.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        callback = (ToDoCallback) getActivity();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.saveBtn:
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.detect = true;
    }

    public boolean isDetect() {
        return detect;
    }

    public void setDetect(boolean detect) {
        this.detect = detect;
    }

    public void setTodo(Task task){
        this.currentTask = task;
    }


}
