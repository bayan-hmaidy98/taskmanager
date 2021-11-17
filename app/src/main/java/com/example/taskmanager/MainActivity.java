package com.example.taskmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnTaskListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addTask = (Button) findViewById(R.id.AddTasks);
        Button allTasks = (Button) findViewById(R.id.AllTasks);
        Button settings = (Button) findViewById(R.id.settings);

        addTask.setOnClickListener(v -> {
            Intent addTask1 = new Intent(MainActivity.this, AddTask.class);
            startActivity(addTask1);
        });

        allTasks.setOnClickListener(v -> {
            Intent allTasks1 = new Intent(MainActivity.this, AllTasks.class);
            startActivity(allTasks1);
        });

        List<Task> tasksList = new ArrayList<>();
        tasksList.add(new Task("Clean The house", "You need to clean the living room, dinning room, and your bedroom.", State.COMPLETE));
        tasksList.add(new Task("Finish the assignments", "You have to get done from your reading, lab, code challenge, and learning journal", State.ASSIGNED));
        tasksList.add(new Task("No tasks", "Since it's the weekend, no tasks for the today, go and enjoy your time and do whatever you want.", State.NEW));
        RecyclerView recyclerView =findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TaskAdapter(tasksList, this));


        settings.setOnClickListener(v -> {
            Intent goToSettings = new Intent(MainActivity.this, Settings.class);
            startActivity(goToSettings);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String instName = sharedPreferences.getString("username","Go to settings and set the username");
        TextView welcome = findViewById(R.id.displayUserName);
        welcome.setText(instName);
    }

    @Override
    public void onTaskClick(int position) {
        Intent intent = new Intent(this, TaskDetails.class);
        startActivity(intent);

    }
}