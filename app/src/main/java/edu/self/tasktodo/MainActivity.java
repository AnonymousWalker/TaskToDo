package edu.self.tasktodo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    FrameLayout main_view_container;
    AddEditFragment addEditFragment;
    ToDoListFragment todoListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoListFragment = new ToDoListFragment();
        addEditFragment = new AddEditFragment();
    }
}
