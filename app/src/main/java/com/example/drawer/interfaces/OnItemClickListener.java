package com.example.drawer.interfaces;

import android.content.SharedPreferences;
import android.widget.TextView;

import com.example.drawer.models.NoteModel;

import java.util.ArrayList;

public interface OnItemClickListener {
    void onItemClick(int position , NoteModel model);
}
