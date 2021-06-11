package com.example.drawer.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity
public class NoteModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    String title;
    String date;
    String background;

    public NoteModel(String title, String background, String date) {
        this.title = title;
        this.background = background;
        this.date = date;
    }


    public  NoteModel(){}

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }



    public NoteModel(String date) {
        this.date= date;
//        this.date = date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }


    public String getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
