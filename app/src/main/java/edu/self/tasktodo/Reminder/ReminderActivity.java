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
    private Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_reminder);

        btnRemoveTodoReminder = findViewById(R.id.toDoReminderRemoveBtn);
        txtTodoReminderBody = findViewById(R.id.reminderBodyTxt);

        intent = getIntent();
        txtTodoReminderBody.setText(intent.getStringExtra(AlarmUtil.MESSAGE_INTENT));
        btnRemoveTodoReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: remove the task from SharedPref

                String taskId = intent.getStringExtra(AlarmUtil.ID_INTENT);
                App application = (App) getApplication();
                application.removePref(taskId);
                finish();
            }
        });
    }
}
