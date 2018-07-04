package edu.self.tasktodo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;

import edu.self.tasktodo.Utils.AlarmUtil;
import edu.self.tasktodo.Utils.Task;

/**
 * Created by Hoang Anh on 01-Jul-18.
 */

public class AddEditFragment extends Fragment implements View.OnClickListener {
    private boolean detect;
    private Task currentTask;
    private EditText txtTitle, txtDate, txtTime;
    private SwitchCompat switchReminder;
    private ToDoCallback callback;
    private FloatingActionButton btnSave;
    private Button btnCancel, btnRemove;
    private final String ADD_SUCCESSFULLY_ALERT = "Successfully saved";
    private final String EMPTY_INPUT_ALERT = "Empty title is not allowed";
    private final String INVALID_DATETIME_INPUT = "Invalid DateTime";
    private long timeMilisecond = 0;
    private long dateAsMilisec = 0;
    private long timeClockAsMilisec = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_edit_todo, null);
        txtTitle = (EditText) rootView.findViewById(R.id.txtTodoText);
        txtDate = rootView.findViewById(R.id.todoDateEditText);
        txtTime = rootView.findViewById(R.id.todoTimeEditText);
        switchReminder = rootView.findViewById(R.id.switchAlarm);
        btnSave = rootView.findViewById(R.id.saveBtn);
        btnCancel = rootView.findViewById(R.id.discardBtn);
        btnRemove = rootView.findViewById(R.id.removeBtn);

        txtDate.setOnClickListener(this);
        txtTime.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnRemove.setOnClickListener(this);

        switchReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateReminderPart(switchReminder.isChecked());
            }
        });
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
            case R.id.todoDateEditText: {
                hideKeyboard(txtTitle);
                handleEditDate();
                break;
            }
            case R.id.todoTimeEditText: {
                hideKeyboard(txtTitle);
                handleEditTime();
                break;
            }
            case R.id.saveBtn: {
                boolean isAdd, hasReminder = switchReminder.isChecked();
                long timeAsMilisecond = (hasReminder) ? timeMilisecond : -1;
                String newTitle = txtTitle.getText().toString();

                //validate before saving
                if (!isValidated(hasReminder, newTitle)) return;

                if (currentTask != null) {
                    //Edit existing item
                    isAdd = false;
                    currentTask.setTitle(newTitle);
                    currentTask.setTodoTimeMilisec(timeAsMilisecond);
                    currentTask.setHasReminder(hasReminder);

                } else {
                    //Add New Item
                    isAdd = true;
                    String id = String.valueOf(System.currentTimeMillis());
                    currentTask = new Task(id, newTitle, hasReminder, timeAsMilisecond);
                }

                callback.itemSaved(currentTask); //if this line is put below, the id appears latency in 1 milisec
                //set background alarm
                if (hasReminder) {
                    AlarmUtil.setAlarm(this.getActivity(), currentTask.getId(), currentTask.getTitle(), timeAsMilisecond);
                }
                Toast.makeText(this.getActivity(), ADD_SUCCESSFULLY_ALERT, Toast.LENGTH_SHORT).show();
                callback.backPress();
                break;
            }
            case R.id.discardBtn: {
                callback.backPress();
                break;
            }
            case R.id.removeBtn: {   //check before removing
                if (currentTask == null) {
                    callback.backPress();
                    return;
                }
                callback.itemRemoved(currentTask.getId());
                break;
            }
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
            if (currentTask.isHasReminder()) {
                //update reminder UI
                switchReminder.setChecked(true);
                timeMilisecond = currentTask.getTodoTimeMilisec();
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(timeMilisecond);
                int year = calendar.get(Calendar.YEAR);
                int monthBase0 = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                String monthName = new DateFormatSymbols().getMonths()[monthBase0];
                String shortDate = String.format("%02d %s, %04d", day, monthName, year);
                String shortTime = String.format("%02d:%02d", hour, minute);
                calendar.set(year, monthBase0, day, 0, 0, 0);
                dateAsMilisec = calendar.getTimeInMillis();
                timeClockAsMilisec = 1000 * (hour * 3600 + minute * 60);
                timeMilisecond = dateAsMilisec + timeClockAsMilisec;
                txtDate.setText(shortDate);
                txtTime.setText(shortTime);
            } else {
                switchReminder.setChecked(false);
                txtDate.setText("");
                txtTime.setText("");
            }
        } else {
            switchReminder.setChecked(false);
            txtTitle.setText("");
            txtDate.setText("");
            txtTime.setText("");
        }
    }

    private void updateReminderPart(boolean isChecked) {
        if (isChecked) {
            timeMilisecond = System.currentTimeMillis();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timeMilisecond);
            int year = calendar.get(Calendar.YEAR);
            int monthBase0 = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            String monthName = new DateFormatSymbols().getMonths()[monthBase0];
            String shortDate = String.format("%02d %s, %04d", day,
                    monthName, year);
            String shortTime = String.format("%02d:%02d", hour, minute);
            txtDate.setText(shortDate);
            txtTime.setText(shortTime);
            timeClockAsMilisec = (hour * 3600 + minute * 60) * 1000;
            dateAsMilisec = timeMilisecond - timeClockAsMilisec;
        } else {
            txtDate.setText("");
            txtTime.setText("");
        }
    }

    private boolean isValidated(boolean hasReminder, String newTitle) {
        if (newTitle.isEmpty()) {
            Toast.makeText(this.getActivity(), EMPTY_INPUT_ALERT, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (hasReminder && (txtDate.getText().toString().isEmpty()
                || txtTime.getText().toString().isEmpty())) {
            Toast.makeText(this.getActivity(), INVALID_DATETIME_INPUT, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void hideKeyboard(EditText edTxt) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edTxt.getWindowToken(), 0);
    }

    private void handleEditDate() {
        long milliseconds;
        if (currentTask != null && currentTask.isHasReminder()) {
            milliseconds = currentTask.getTodoTimeMilisec();
        } else {
            milliseconds = System.currentTimeMillis();
        }

        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                //update date
                String monthName = new DateFormatSymbols().getMonths()[month];
                txtDate.setText(String.format("%02d %s, %04d", day, monthName, year));
                switchReminder.setChecked(true);
                calendar.set(year, month, day, 0, 0, 0);
                dateAsMilisec = calendar.getTimeInMillis();         //update date as milisec
                timeMilisecond = dateAsMilisec + timeClockAsMilisec;    //update timeMili with time set
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(this.getActivity(), callback, year, month, day);
        datePickerDialog.show();
    }

    private void handleEditTime() {
        Date date;
        if (currentTask != null && currentTask.getTodoTimeMilisec() > 0) {
            date = new Date(currentTask.getTodoTimeMilisec());
        } else {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog.OnTimeSetListener callback = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int h, int min) {
                txtTime.setText(String.format("%02d:%02d", h, min));
                switchReminder.setChecked(true);
                timeClockAsMilisec = (3600 * h + 60 * min) * 1000;
                timeMilisecond = dateAsMilisec + timeClockAsMilisec;    //update timeMili with time set
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this.getActivity(), callback, hour, minute, true);
        timePickerDialog.show();
    }


}
