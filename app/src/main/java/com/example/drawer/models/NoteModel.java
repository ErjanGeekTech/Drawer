package com.example.drawer.models;

import java.io.Serializable;

public class NoteModel implements Serializable {
    String title;
    String description;
    String background;

    public NoteModel(String title, String background) {
        this.title = title;
        this.background = background;
    }
    public  NoteModel(){}

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    String date;

    public NoteModel(String title) {
        this.title = title;
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
}
