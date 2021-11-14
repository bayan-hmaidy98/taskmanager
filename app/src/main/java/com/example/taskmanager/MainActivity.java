package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addTask = (Button) findViewById(R.id.AddTasks);
        Button allTasks = (Button) findViewById(R.id.AllTasks);

        addTask.setOnClickListener(v -> {
            Intent addTask1 = new Intent(MainActivity.this, AddTask.class);
            startActivity(addTask1);
        });

        allTasks.setOnClickListener(v -> {
            Intent allTasks1 = new Intent(MainActivity.this, AllTasks.class);
            startActivity(allTasks1);
        });


    }
}