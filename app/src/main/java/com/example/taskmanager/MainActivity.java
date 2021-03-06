package com.example.taskmanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    List<Task> tasksList = new ArrayList<>();
    TaskAdapter adapter = new TaskAdapter(tasksList);
    Handler handler = new Handler();


    Runnable runnable = new Runnable() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void run() {
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addTask = (Button) findViewById(R.id.AddTasks);
        Button allTasks = (Button) findViewById(R.id.AllTasks);
        Button settings = (Button) findViewById(R.id.settings);
        Button logIn = (Button) findViewById(R.id.logInButton);
        Button logOut = (Button) findViewById(R.id.logOutButton);

        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
            // Add this line, to include the Auth plugin.
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());
            Log.i("Tutorial", "Initialized Amplify");
        } catch (AmplifyException failure) {
            Log.e("Tutorial", "Could not initialize Amplify", failure);
        }
        Amplify.DataStore.observe(Task.class,
                started -> Log.i("Tutorial", "Observation began."),
                change -> Log.i("Tutorial", change.item().toString()),
                failure -> Log.e("Tutorial", "Observation failed.", failure),
                () -> Log.i("Tutorial", "Observation complete.")
        );

        Amplify.Auth.fetchAuthSession(
                result -> Log.i("AmplifyQuickstart","Result: " + result.toString()),
                error -> Log.e("AmplifyQuickstart", error.toString())
        );


        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Amplify.Auth.signInWithWebUI(
                        MainActivity.this,
                        result -> Log.i("AuthQuickStart", result.toString()),
                        error -> Log.e("AuthQuickStart", error.toString())
                );
            }
        });



        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Amplify.Auth.signOut(
                        () -> Log.i("AuthQuickstart", "Signed out successfully"),
                        error -> Log.e("AuthQuickstart", error.toString())
                );
            }
        });



        addTask.setOnClickListener(v -> {
            Intent addTask1 = new Intent(MainActivity.this, AddTask.class);
            startActivity(addTask1);
        });

        allTasks.setOnClickListener(v -> {
            Intent allTasks1 = new Intent(MainActivity.this, AllTasks.class);
            startActivity(allTasks1);
        });

//        List<Task> tasksList = new ArrayList<>();
//        tasksList.add(new Task("Clean The house", "You need to clean the living room, dinning room, and your bedroom.", State.COMPLETE));
//        tasksList.add(new Task("Finish the assignments", "You have to get done from your reading, lab, code challenge, and learning journal", State.ASSIGNED));
//        tasksList.add(new Task("No tasks", "Since it's the weekend, no tasks for the today, go and enjoy your time and do whatever you want.", State.NEW));

//        TaskDB db = Room.databaseBuilder(getApplicationContext(), TaskDB.class, "tasksDatabase").allowMainThreadQueries().fallbackToDestructiveMigration().build();
//        TaskDAO taskDAO = db.taskDAO();
//        List<Task> tasksList = taskDAO.getAll();

        Amplify.DataStore.query(
                Task.class,
                response -> {
                    while (response.hasNext()){
                        Task item = response.next();
                        tasksList.add(item);
                        Log.i("type of response", item.getId());

                    }

                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        settings.setOnClickListener(v -> {
            Intent goToSettings = new Intent(MainActivity.this, Settings.class);
            startActivity(goToSettings);
        });



    }
    private void uploadFile() {
        File exampleFile = new File(getApplicationContext().getFilesDir(), "ExampleKey");

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(exampleFile));
            writer.append("Example file contents");
            writer.close();
        } catch (Exception exception) {
            Log.e("MyAmplifyApp", "Upload failed", exception);
        }

        Amplify.Storage.uploadFile(
                "ExampleKey",
                exampleFile,
                result -> Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey()),
                storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        Button logIn = (Button) findViewById(R.id.logInButton);
        Button logOut = (Button) findViewById(R.id.logOutButton);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String instName = sharedPreferences.getString("username", "Go to settings and set the username");
        TextView welcome = findViewById(R.id.displayUserName);
        welcome.setText(instName);

//        List<Task> tasksList = new ArrayList<>();
        String team = sharedPreferences.getString("teamName", "Team");

        Amplify.DataStore.query(
                Team.class, Team.NAME.contains(team),
                items -> {
                    while (items.hasNext()) {
                        Team item = items.next();
                        Amplify.DataStore.query(
                                Task.class, Task.TEAM_ID.contains(item.getId()),
                                response -> {
                                    while (response.hasNext()){
                                        Task task = response.next();
                                        tasksList.add(task);
                                        Log.i("type of response", task.getId());

                                    }
                                handler.post(runnable);
                                },
                                error -> Log.e("MyAmplifyApp", "Query failure", error)
                        );
                        Log.i("Amplify", "Id " + item.getId());
                    }
                    handler.post(runnable);
                },
                failure -> Log.e("Amplify", "Could not query DataStore", failure)
        );


        Amplify.Auth.fetchAuthSession(
                result -> {
                    if (result.isSignedIn()){
                        logIn.setVisibility(View.INVISIBLE);
                        welcome.setText(Amplify.Auth.getCurrentUser().getUsername());
                    }
                    else logOut.setVisibility(View.INVISIBLE);
                },
                error -> Log.e("AmplifyQuickstart", error.toString())
        );

    }


}