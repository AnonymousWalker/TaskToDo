package edu.self.tasktodo.Main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import edu.self.tasktodo.R;
import edu.self.tasktodo.Task;
import edu.self.tasktodo.ToDoCallback;
import edu.self.tasktodo.ToDoListAdapter;

/**
 * Created by Hoang Anh on 30-Jun-18.
 */

public class ToDoListFragment extends Fragment implements AdapterView.OnItemClickListener{
    private ListView listView;
    private ArrayList<Task> taskList;
    private ToDoListAdapter adapter;
    private ToDoCallback callback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_todo_list, null);
        listView = rootView.findViewById(R.id.list_item);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //generate test items
        taskList = new ArrayList<Task>();
        taskList.add(new Task("Code", 1));
        taskList.add(new Task("Listening music", 2));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new ToDoListAdapter(this.getActivity(), R.layout.task_cell, this.taskList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        callback = (ToDoCallback) this.getActivity();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Task selectedItem = taskList.get(i);
        callback.onItemSelected(selectedItem);
    }
}
