package com.example.drawer.adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drawer.R;
import com.example.drawer.interfaces.OnItemClickListener;
import com.example.drawer.models.NoteModel;
import com.example.drawer.ui.home.HomeFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Timer;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    public ArrayList<NoteModel> list = new ArrayList<>();

    private OnItemClickListener listener;

    public void addNotes(NoteModel model, OnItemClickListener listener) {
        this.listener = listener;
        list.add(model);
        notifyDataSetChanged();
    }

    public void editModel(NoteModel model, int position) {
        list.get(position).setTitle(model.getTitle());
        notifyItemChanged(position);
    }


    @NonNull
    @NotNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view;
        if (HomeFragment.isList) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashboard_note, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_note, parent, false);
        }
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

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;
        LinearLayout linearLayout;

        public NoteViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.item_txt_title);
            linearLayout = itemView.findViewById(R.id.layoutNote);
        }

        @SuppressLint("ResourceAsColor")
        public void bind(NoteModel noteModel) {
            itemView.setOnClickListener(v -> {
                listener.onItemClick(getAdapterPosition(), noteModel);
            });
            txtTitle.setText(noteModel.getTitle());
            if (noteModel.getBackground() != null) {
                switch (noteModel.getBackground()) {
                    case "black":
                        txtTitle.setTextColor(Color.parseColor("#B1B1B1"));
                        itemView.setBackgroundResource(R.drawable.btn_black);
                        break;
                    case "yellow":
                        txtTitle.setTextColor(Color.parseColor("#A1865E"));
                        itemView.setBackgroundResource(R.drawable.btn_yellow);
                        break;
                    case "red":
                        txtTitle.setTextColor(Color.parseColor("#EAA72E"));
                        itemView.setBackgroundResource(R.drawable.btn_red);
                        break;
                }
            }

        }
    }

    public void filterList(ArrayList<NoteModel> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }

}
