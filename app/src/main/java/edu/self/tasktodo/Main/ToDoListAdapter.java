package edu.self.tasktodo.Main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import edu.self.tasktodo.R;
import edu.self.tasktodo.Utils.Task;

/**
 * Created by Hoang Anh on 01-Jul-18.
 */

public class ToDoListAdapter extends ArrayAdapter {
    private Context context;
    private int resource;
    private ArrayList<Task> taskList;

    public ToDoListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Task> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.taskList = objects;
    }

    @Override
    public int getCount() {
        return this.taskList.size();
    }

    private final class ItemHolder{
        TextView titleCellView;
        TextView dateCellView;
        TextView letterCircle;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ItemHolder itemHolder;
        if (convertView == null){
            itemHolder = new ItemHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);
            itemHolder.titleCellView = convertView.findViewById(R.id.titleCellView);
            itemHolder.dateCellView = convertView.findViewById(R.id.dateCellView);
            itemHolder.letterCircle = convertView.findViewById(R.id.letterCircle);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) convertView.getTag();
        }
        Task currentTask = this.taskList.get(position);
        itemHolder.titleCellView.setText(currentTask.getTitle());
        char firstLetter = currentTask.getTitle().charAt(0);
        itemHolder.letterCircle.setText(String.valueOf(firstLetter));
        //set random color letter circle
//        Random random = new Random();
//        int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
//        itemHolder.letterCircle.setBackgroundColor(color);
        if (currentTask.isHasReminder()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(currentTask.getTodoTimeMilisec());
            String shortDateTime = String.format("%02d/%02d/%04d at %02d:%02d",
                    calendar.get(Calendar.DAY_OF_MONTH), (calendar.get(Calendar.MONTH)+1), calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
            itemHolder.dateCellView.setText(shortDateTime);
        } else {
            itemHolder.dateCellView.setText("");
        }
        return convertView;
    }
}
