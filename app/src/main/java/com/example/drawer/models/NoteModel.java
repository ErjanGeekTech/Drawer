package com.example.drawer.models;

import java.io.Serializable;

public class NoteModel implements Serializable {
    String title;
    String description;
    String date;

    public NoteModel(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public NoteModel(String title, String description, String date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }
}
