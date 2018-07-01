package edu.self.tasktodo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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

            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) convertView.getTag();
        }
        Task currentTask = this.taskList.get(position);
        itemHolder.tvDescription.setText(currentTask.getTitle());

        return convertView;
    }
}
