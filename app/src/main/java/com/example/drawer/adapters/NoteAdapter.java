package com.example.drawer.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drawer.R;
import com.example.drawer.models.NoteModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter <NoteAdapter.NoteViewHolder> {

    private ArrayList<NoteModel> list = new ArrayList<>();

    public void addNotes(NoteModel model) {
        list.add(model);
        notifyDataSetChanged();
    }


    @NonNull
    @NotNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_note, parent , false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NoteViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle, txtDescription, txtDate;

        public NoteViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.item_txt_title);
            txtDescription = itemView.findViewById(R.id.item_txt_description);
            txtDate = itemView.findViewById(R.id.item_txt_date);

        }

        public void bind(NoteModel noteModel) {
            txtTitle.setText(noteModel.getTitle());
            txtDescription.setText(noteModel.getDescription());
            txtDate.setText(noteModel.getDate());
        }
    }
}
