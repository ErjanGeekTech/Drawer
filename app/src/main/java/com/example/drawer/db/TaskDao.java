package com.example.drawer.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.drawer.models.NoteModel;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM notemodel")
   LiveData < List <NoteModel>> getAll();
    @Insert
    void insertAll(NoteModel models);

    @Delete
    void delete(NoteModel model);

    @Update
    void update(NoteModel model);
}
