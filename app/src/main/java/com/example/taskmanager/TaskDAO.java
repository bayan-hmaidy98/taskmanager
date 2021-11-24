//package com.example.taskmanager;
//
//import androidx.room.Dao;
//import androidx.room.Delete;
//import androidx.room.Insert;
//import androidx.room.Query;
//
//import java.util.List;
//
//
//@Dao
//public interface TaskDAO {
//    @Query("SELECT * FROM task")
//    List<Task> getAll();
//
//    @Query("SELECT * FROM task WHERE id IN (:userIds)")
//    List<Task> loadAllByIds(int[] userIds);
//
//    @Query("SELECT * FROM task WHERE title_col LIKE :first")
//    Task findByName(String first);
//
//    @Insert
//    void save(Task tasks);
//
//    @Delete
//    void delete(Task user);
//
//}
