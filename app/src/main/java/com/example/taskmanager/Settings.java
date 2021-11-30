package com.example.taskmanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RadioButton team1 = findViewById(R.id.team1Settings);
        RadioButton team2 = findViewById(R.id.team2Settings);
        RadioButton team3 = findViewById(R.id.team3Settings);




        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        findViewById(R.id.submitName).setOnClickListener(view -> {

            String checkTeam = null;
            if (team1.isChecked()) checkTeam = "Team 1";
            else if (team2.isChecked()) checkTeam = "Team 2";
            else if(team3.isChecked()) checkTeam = "Team 3";

            TextView text = findViewById(R.id.userName);
            String name = text.getText().toString();
            editor.putString("teamName", checkTeam);
            editor.apply();
            editor.putString("username", name);
            editor.apply();


        });
    }
}