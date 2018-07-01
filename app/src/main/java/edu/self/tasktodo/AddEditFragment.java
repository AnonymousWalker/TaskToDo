package edu.self.tasktodo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by Hoang Anh on 01-Jul-18.
 */

public class AddEditFragment extends Fragment {
    private boolean detect;
    private Task currentTask;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return rootView;
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
