package edu.self.tasktodo.Main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import edu.self.tasktodo.AddEditFragment;
import edu.self.tasktodo.App;
import edu.self.tasktodo.R;
import edu.self.tasktodo.Task;
import edu.self.tasktodo.ToDoCallback;

public class MainActivity extends AppCompatActivity implements ToDoCallback {
    FrameLayout mainViewContainer;
    AddEditFragment addEditFragment;
    ToDoListFragment todoListFragment;
    App application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoListFragment = new ToDoListFragment();
        addEditFragment = new AddEditFragment();

        showFragment(todoListFragment, addEditFragment);

        application = (App) getApplication();
        mainViewContainer = findViewById(R.id.main_container);
    }

    public void showFragment(Fragment fragmentToShow, Fragment fragmentToHide) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (fragmentToShow.isAdded()) {
            transaction.show(fragmentToShow);
        }
        else
            transaction.add(R.id.main_container, fragmentToShow);

        if (fragmentToHide.isAdded())
            transaction.hide(fragmentToHide);

        transaction.commit();
    }

    @Override
    public void onAddItem() {
        addEditFragment.setTodo(null);
        showFragment(addEditFragment, todoListFragment);
    }

    @Override
    public void onItemSelected(Task task) {
        showFragment(addEditFragment, todoListFragment);
        addEditFragment.setTodo(task);
    }

    @Override
    public void onItemSaved(Task task, boolean isAdd) {
        String keyId;

        if (isAdd){
            keyId = Integer.toString(ToDoListFragment.CURRENT_IN_POSITION);
        } else {
            keyId = Integer.toString(task.getId());
        }
        application.savePref(keyId, task.getTitle());

        //trigger update
        todoListFragment.refreshFragmentData();
    }

    @Override
    public void onBackPressed() {
        backPressed();
    }

    public void backPressed()   //allows all fragments have access to the backPress
    {
        if (addEditFragment.isDetect() == true) {
                      addEditFragment.onDestroyView();
            showFragment(todoListFragment, addEditFragment);
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(addEditFragment)
                    .commit();
            addEditFragment.setDetect(false);
        } else {
            super.onBackPressed();
        }
    }


}
