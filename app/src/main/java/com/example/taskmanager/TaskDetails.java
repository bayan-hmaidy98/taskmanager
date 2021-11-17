package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TaskDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        Intent intent = getIntent();
        TextView text = findViewById(R.id.taskTitle);
        TextView bodyText = findViewById(R.id.taskBody);
        TextView stateTxt = findViewById(R.id.taskState);
        String taskName = intent.getExtras().getString("taskName");
        String taskBody = intent.getExtras().getString("taskBody");
        String taskState = intent.getExtras().getString("taskState");
        text.setText(taskName);
        bodyText.setText(taskBody);
        stateTxt.setText(taskState);

    }
}