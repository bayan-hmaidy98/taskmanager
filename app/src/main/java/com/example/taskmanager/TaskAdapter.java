package com.example.taskmanager;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    public TaskAdapter(List<Task> allTasks) {
        this.allTasks = allTasks;
    }

    List<Task> allTasks = new ArrayList<>();
    // instantiate the onTaskListener in order to be known by the view holder


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task, parent, false);
        TaskViewHolder taskViewHolder = new TaskViewHolder(view);
        return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.task = allTasks.get(position);

        TextView taskTitle = holder.itemView.findViewById(R.id.titleFragment);
        TextView taskBody = holder.itemView.findViewById(R.id.bodyFragment);
        TextView taskState = holder.itemView.findViewById(R.id.stateFragment);

        taskTitle.setText(holder.task.title);
        taskBody.setText(holder.task.body);
        taskState.setText(holder.task.state.toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent taskDetailsIntent = new Intent(v.getContext(), TaskDetails.class); //v.getContext, TaskDetailActivity.class
                taskDetailsIntent.putExtra("taskName", holder.task.title);
                taskDetailsIntent.putExtra("taskBody", holder.task.body);
                taskDetailsIntent.putExtra("taskState", holder.task.state);
                v.getContext().startActivity(taskDetailsIntent);// v.getContext.startActivity(taskDetailsIntent)
            }
        });

    }

    @Override
    public int getItemCount() {
        return allTasks.size();
    }

    // to detect the click (position), use OnClickListener interface and implement the method inside it
    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        public Task task;
        public View itemView;


        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;


        }


    }

}
