package com.example.drawer.interfaces;

import com.example.drawer.models.NoteModel;

import java.util.ArrayList;

public interface OnItemClickListener {
    void  setOnItemClickListener(int position, ArrayList <NoteModel> list);
}
