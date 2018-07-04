package edu.self.tasktodo.Reminder;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.self.tasktodo.App;
import edu.self.tasktodo.R;
import edu.self.tasktodo.Utils.AlarmUtil;

/**
 * Created by Hoang Anh on 04-Jul-18.
 */

public class ReminderActivity extends AppCompatActivity{
    private Button btnRemoveTodoReminder;
    private TextView txtTodoReminderBody;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.fragment_reminder);

        btnRemoveTodoReminder = findViewById(R.id.toDoReminderRemoveBtn);
        txtTodoReminderBody = findViewById(R.id.toDoReminderTextViewBody);

        btnRemoveTodoReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: remove the task from SharedPref
                Intent intent = getIntent();
                String taskId = intent.getStringExtra(AlarmUtil.ID_INTENT);
                App application = (App) getApplication();
                application.removePref(taskId);
                finish();
            }
        });
    }
}
