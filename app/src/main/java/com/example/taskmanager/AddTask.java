package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class AddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button submitTask = (Button) findViewById(R.id.submitTask);
        EditText addTitle = findViewById(R.id.addTaskTitle);
        EditText addBody = findViewById(R.id.addTaskDescription);
        EditText addState = findViewById(R.id.addTaskState);


        submitTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titles = addTitle.getText().toString();
                String states = addState.getText().toString();
                String bodies = addBody.getText().toString();
                Task addTask = new Task(titles, bodies, State.NEW);
                Toast.makeText(getApplicationContext(), "Submitted!", Toast.LENGTH_LONG).show();
                TaskDB myDB = Room.databaseBuilder(getApplicationContext(), TaskDB.class, "tasksDatabase").allowMainThreadQueries().fallbackToDestructiveMigration().build();
                TaskDAO taskDao = myDB.taskDAO();

                taskDao.save(addTask);
                Intent addIntent = new Intent(AddTask.this, MainActivity.class);
                startActivity(addIntent);

            }
        });
    }
}