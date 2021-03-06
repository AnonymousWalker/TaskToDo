package edu.self.tasktodo.Main;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

import edu.self.tasktodo.App;
import edu.self.tasktodo.R;
import edu.self.tasktodo.Utils.Task;
import edu.self.tasktodo.ToDoCallback;

/**
 * Created by Hoang Anh on 30-Jun-18.
 */

public class ToDoListFragment extends Fragment implements AdapterView.OnItemClickListener{
    private ListView listView;
    private ArrayList<Task> taskList;
    private ToDoListAdapter adapter;
    private ToDoCallback callback;
    private App app;
    private FloatingActionButton btnAddToDoTask;
    private Button btnRemoveAll;
    private Map<String, ?> allPref;
    private LinearLayout emptyLvLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_todo_list, null);
        listView = rootView.findViewById(R.id.list_item);
        btnAddToDoTask = rootView.findViewById(R.id.addTodoBtn);
        btnRemoveAll = rootView.findViewById(R.id.removeAllBtn);
        emptyLvLayout = rootView.findViewById(R.id.emptyListView);

        btnAddToDoTask.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                btnAddToDoTask.setBackgroundColor(btnAddToDoTask.getContext().getResources().getColor(R.color.floatingButtonOnPressed, null));
                callback.addNewItem();
            }
        });
        btnRemoveAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.removeAllPref();
                refreshFragmentData();
            }
        });

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        taskList = new ArrayList<Task>();
//        taskList.add(new Task("Test1: Code"));
//        taskList.add(new Task("Test2: Listening music"));

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //GET: list of tasks in local db
        this.getListFromLocalData();

        adapter = new ToDoListAdapter(this.getActivity(), R.layout.task_cell, taskList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        callback = (ToDoCallback) getActivity();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Task selectedItem = taskList.get(i);
        callback.itemSelected(selectedItem);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshFragmentData();
    }

    public void refreshFragmentData(){
        taskList.clear();
        getListFromLocalData(); //refresh taskList
        adapter = new ToDoListAdapter(this.getActivity(), R.layout.task_cell, taskList);
        if (taskList.size() == 0) emptyLvLayout.setVisibility(View.VISIBLE);
        else emptyLvLayout.setVisibility(View.GONE);
        listView.setAdapter(adapter);
    }

    private void getListFromLocalData(){
        app = (App) getActivity().getApplication();
        SharedPreferences preferences = app.getSharedPreferences(App.APP_SHARED_PREFERENCE, App.MODE_PRIVATE);
        allPref = preferences.getAll();
        Gson gson = new Gson();
        for (Map.Entry<String, ?> item : allPref.entrySet()){
//          TODO:  CONVERT JSON TO TASK obj!!!
            //taskList.add(new Task(item.getKey().toString(), item.getValue().toString()));
            Task task = gson.fromJson(item.getValue().toString(), Task.class);
            taskList.add(task);
        }
        Collections.sort(taskList, new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                return t1.getId().compareTo(t2.getId());
            }
        });
    }
}
