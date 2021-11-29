package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;

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

        RadioButton team1 = findViewById(R.id.team1);
        RadioButton team2 = findViewById(R.id.team2);
        RadioButton team3 = findViewById(R.id.team3);




        submitTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String checkTeam = null;
                if (team1.isChecked()) checkTeam = "Team 1";
                else if (team2.isChecked()) checkTeam = "Team 2";
                else if(team3.isChecked()) checkTeam = "Team 3";

                String titles = addTitle.getText().toString();
                String states = addState.getText().toString();
                String bodies = addBody.getText().toString();


                Amplify.DataStore.query(
                        Team.class, Team.NAME.contains(checkTeam),
                        items -> {
                            while (items.hasNext()) {
                                Team item = items.next();
                                Task task = Task.builder()
                                        .title(titles)
                                        .body(bodies)
                                        .state("NEW").teamId(item.getId())
                                        .build();

                                Amplify.DataStore.save(
                                        task,
                                        success -> Log.i("relation", "Saved item: " + success.item().getTeamId()),
                                        error -> Log.e("relation", "Could not save item to DataStore", error)
                                );
                                Log.i("Amplify", "Id " + item.getId());
                            }
                        },
                        failure -> Log.e("Amplify", "Could not query DataStore", failure)
                );


//
//                Task addTask = new Task(titles, bodies, State.NEW);
//                Toast.makeText(getApplicationContext(), "Submitted!", Toast.LENGTH_LONG).show();
//                TaskDB myDB = Room.databaseBuilder(getApplicationContext(), TaskDB.class, "tasksDatabase").allowMainThreadQueries().fallbackToDestructiveMigration().build();
//                TaskDAO taskDao = myDB.taskDAO();
//
//                taskDao.save(addTask);
                Intent addIntent = new Intent(AddTask.this, MainActivity.class);
                startActivity(addIntent);

            }
        });
    }
}