package com.example.taskmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addTask = (Button) findViewById(R.id.AddTasks);
        Button allTasks = (Button) findViewById(R.id.AllTasks);
        Button settings = (Button) findViewById(R.id.settings);
        Button task1 = (Button) findViewById(R.id.task1);
        Button task2 = (Button) findViewById(R.id.task2);
        Button task3 = (Button) findViewById(R.id.task3);

        addTask.setOnClickListener(v -> {
            Intent addTask1 = new Intent(MainActivity.this, AddTask.class);
            startActivity(addTask1);
        });

        allTasks.setOnClickListener(v -> {
            Intent allTasks1 = new Intent(MainActivity.this, AllTasks.class);
            startActivity(allTasks1);
        });

        task1.setOnClickListener(v -> {
            Intent goToTaskOne = new Intent(MainActivity.this, TaskDetails.class);
            String task = task1.getText().toString();
            goToTaskOne.putExtra("task",task);
            startActivity(goToTaskOne);
        });

        task2.setOnClickListener(v -> {
            Intent goToTaskTwo = new Intent(MainActivity.this, TaskDetails.class);
            String task = task2.getText().toString();
            goToTaskTwo.putExtra("task",task);
            startActivity(goToTaskTwo);
        });

        task3.setOnClickListener(v -> {
            String task = task3.getText().toString();
            Intent goToTaskThree = new Intent(MainActivity.this, TaskDetails.class);
            goToTaskThree.putExtra("task",task);
            startActivity(goToTaskThree);
        });

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
}