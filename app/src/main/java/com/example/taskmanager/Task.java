package com.example.taskmanager;

public class Task {

    public String title;
    public String body;
    public State state;

    public Task(String title, String body, State state) {
        this.title = title;
        this.body = body;
        this.state = state;
    }
}
