package com.example.drawer.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.drawer.models.NoteModel;

@Database(entities = {NoteModel.class}, version = 1, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {

    public  abstract TaskDao getTaskDao();

}
