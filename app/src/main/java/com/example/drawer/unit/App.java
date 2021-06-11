package com.example.drawer.unit;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.drawer.db.AppDatabase;

public class App extends Application {
    public static AppDatabase instance = null;

    @Override
    public void onCreate() {
        super.onCreate();

    }
    public static AppDatabase getInstance(Context context){
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, "task-database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return  instance;
    };
}
