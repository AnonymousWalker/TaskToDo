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
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

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
    private long timeAsMilisec;

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
                if (switchReminder.isChecked()) {
                    timeAsMilisec = System.currentTimeMillis();
                }
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
                String newTitle = txtTitle.getText().toString();

                //validate before saving
                if (!isValidated(hasReminder, newTitle)) return;

                if (currentTask != null) {
                    currentTask.setTitle(newTitle);
                    if (hasReminder) {
                        //edit date time
                        currentTask.setTodoDate(new Date(timeAsMilisec));
                    } else {
                        currentTask.setTodoDate(null);
                    }
                    isAdd = false;
                } else {
                    //add new to do
                    String id = String.valueOf(System.currentTimeMillis());
                    Date date = (hasReminder) ? new Date(timeAsMilisec) : null;
                    currentTask = new Task(id, newTitle, hasReminder, date);
                    isAdd = true;
                }

                callback.itemSaved(currentTask, isAdd);
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
        } else {
            txtTitle.setText("");
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
        Date date;
        if (currentTask != null && currentTask.getTodoDate() != null) {
            date = currentTask.getTodoDate();
        } else {
            date = new Date();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                //update date text
                String monthName = new DateFormatSymbols().getMonths()[month - 1];
                txtDate.setText(day + " " + monthName + ", " + year);
                switchReminder.setChecked(true);
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(this.getActivity(), callback, year, month, day);
        datePickerDialog.show();
    }

    private void handleEditTime() {
        Date date;
        if (currentTask != null && currentTask.getTodoDate() != null) {
            date = currentTask.getTodoDate();
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
                DecimalFormat formatter = new DecimalFormat("00.#");
                txtTime.setText(formatter.format(h) + ":" + formatter.format(minute));
                switchReminder.setChecked(true);
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this.getActivity(), callback, hour, minute, true);
        timePickerDialog.show();
    }


}
