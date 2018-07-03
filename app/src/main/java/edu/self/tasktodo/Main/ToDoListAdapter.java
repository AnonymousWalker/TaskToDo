package edu.self.tasktodo.Main;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.self.tasktodo.R;
import edu.self.tasktodo.Task;

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
        TextView tvDescription;
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
            itemHolder.tvDescription = convertView.findViewById(R.id.descriptionView);
            itemHolder.letterCircle = convertView.findViewById(R.id.letterCircle);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) convertView.getTag();
        }
        Task currentTask = this.taskList.get(position);
        itemHolder.tvDescription.setText(currentTask.getTitle());
        char firstLetter = currentTask.getTitle().charAt(0);
        itemHolder.letterCircle.setText(String.valueOf(firstLetter));
        //set random color letter circle
//        Random random = new Random();
//        int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
//        itemHolder.letterCircle.setBackgroundColor(color);
        return convertView;
    }
}
