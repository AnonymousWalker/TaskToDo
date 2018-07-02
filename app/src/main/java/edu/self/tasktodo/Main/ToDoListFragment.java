package edu.self.tasktodo.Main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Map;

import edu.self.tasktodo.App;
import edu.self.tasktodo.R;
import edu.self.tasktodo.Task;
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
    private Map<String, ?> allPref;
    public static int CURRENT_IN_POSITION;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_todo_list, null);
        listView = rootView.findViewById(R.id.list_item);
        btnAddToDoTask = rootView.findViewById(R.id.addTodoBtn);

        btnAddToDoTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onAddItem();
            }
        });
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //generate test items
        taskList = new ArrayList<Task>();
//        taskList.add(new Task("Test1: Code"));
//        taskList.add(new Task("Test2: Listening music"));

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //GET: list of tasks in local db
        app = (App) getActivity().getApplication();
        SharedPreferences preferences = app.getSharedPreferences(App.APP_SHARED_PREFERENCE, App.MODE_PRIVATE);
        allPref = preferences.getAll();
        for (Map.Entry<String, ?> item : allPref.entrySet()){
            if (item.getValue() instanceof String){
                taskList.add(new Task((item.getValue()).toString()));
            }
        }

        CURRENT_IN_POSITION = taskList.size();
        adapter = new ToDoListAdapter(this.getActivity(), R.layout.task_cell, taskList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        callback = (ToDoCallback) this.getActivity();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Task selectedItem = taskList.get(i);
        selectedItem.setId(i);
        callback.onItemSelected(selectedItem);
    }

    public void refreshFragment(){
//        Fragment frg = getActivity().getSupportFragmentManager().findFragmentByTag("ToDoListFragment");
//        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.detach(frg);
//        ft.attach(frg);
//        ft.commit();
    }
}