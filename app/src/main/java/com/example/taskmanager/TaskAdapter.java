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

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder>{

    public TaskAdapter(List<Task> allTasks, OnTaskListener onTaskListener) {
        this.allTasks = allTasks;
        this.monTaskListener = onTaskListener;
    }

    List <Task> allTasks = new ArrayList<>();
    // instantiate the onTaskListener in order to be known by the view holder
    private OnTaskListener monTaskListener;

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task,parent,false);
        TaskViewHolder taskViewHolder = new TaskViewHolder(view, monTaskListener);
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

    }

    @Override
    public int getItemCount() {
        return allTasks.size();
    }
// to detect the click (position), use OnClickListener interface and implement the method inside it
    public static class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public Task task;
        public View itemView;
        OnTaskListener onTaskListener;

        public TaskViewHolder(@NonNull View itemView, OnTaskListener onTaskListener) {
            super(itemView);
            this.itemView = itemView;
            this.onTaskListener = onTaskListener;

            // attach OnClickListener to the entire view holder
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            TaskViewHolder holder = null;
            onTaskListener.onTaskClick(getAdapterPosition());
            Intent intent = new Intent();
            intent.putExtra("task", holder.task.title);
        }
    }
// Send click info to the activity
    public interface OnTaskListener{
        void onTaskClick (int position);
    }
}
