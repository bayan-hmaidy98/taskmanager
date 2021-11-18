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
        String titles = addTitle.getText().toString();
        EditText addBody = findViewById(R.id.addTaskDescription);
        String bodies = addBody.getText().toString();
        EditText addState = findViewById(R.id.addTaskState);
        String states = addState.getText().toString();
        Task addNewTask = new Task(titles, bodies, State.NEW);
        submitTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Submitted!", Toast.LENGTH_LONG).show();
                TaskDB db = Room.databaseBuilder(getApplicationContext(), TaskDB.class, "database-name").build();
                TaskDAO taskDAO = db.taskDAO();

                taskDAO.save(addNewTask);
                Intent addIntent = new Intent(AddTask.this, MainActivity.class);
                startActivity(addIntent);

            }
        });
    }
}