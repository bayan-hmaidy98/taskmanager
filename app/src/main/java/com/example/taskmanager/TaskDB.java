package com.example.taskmanager;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Task.class}, version = 1)

    public abstract class TaskDB extends RoomDatabase {
        public abstract TaskDAO taskDAO();

}
