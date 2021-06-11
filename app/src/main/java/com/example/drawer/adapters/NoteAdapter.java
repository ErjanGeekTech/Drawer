package com.example.drawer.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import java.util.List;
import java.util.Timer;
import java.util.logging.Handler;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    public List<NoteModel> list = new ArrayList<>();
    public List<NoteModel> listSourse = new ArrayList<>();

    private OnItemClickListener listener;
    public boolean random = false;



    public void addNotes(NoteModel model, OnItemClickListener listener) {
        this.listener = listener;
        list.add(model);
        listSourse = list;
        notifyDataSetChanged();
    }

    public void editModel(NoteModel model, int position) {
        list.get(position).setTitle(model.getTitle());
        list.get(position).setBackground(model.getBackground());
        list.get(position).setDate(model.getDate());
        notifyItemChanged(position);
    }
    public void addListOfModel(List<NoteModel> listM){
        list.clear();
        this.list.addAll(listM);
        notifyDataSetChanged();

    }


    @NonNull
    @NotNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view;
        if (!HomeFragment.isList) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashboard_note, parent, false);
            random = false;
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_note, parent, false);
            random = true;

        }
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NoteViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    public void delete(int position) {
        list.remove(position);
        notifyItemRemoved(position);
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

        TextView txtTitle, txtDate;

        public NoteViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.item_txt_title);
            txtDate = itemView.findViewById(R.id.item_txt_date);
        }

        @SuppressLint("ResourceAsColor")
        public void bind(NoteModel noteModel) {
            itemView.setOnClickListener(v -> {
                listener.onItemClick(getAdapterPosition(),noteModel);
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemLongClick(getAdapterPosition());
                    return false;
                }
            });
            txtTitle.setText(noteModel.getTitle());
            txtDate.setText(noteModel.getDate());
            if (noteModel.getBackground() != null) {
                switch (noteModel.getBackground()) {
                    case "black":
                        txtTitle.setTextColor(Color.parseColor("#B1B1B1"));
                        itemView.setBackgroundResource(R.drawable.btn_black);
                        break;
                    case "yellow":
                        txtTitle.setTextColor(Color.parseColor("#A1865E"));
                        txtDate.setTextColor(Color.parseColor("#C0B18B"));
                        itemView.setBackgroundResource(R.drawable.btn_yellow);
                        break;
                    case "red":
                        txtTitle.setTextColor(Color.parseColor("#EAA72E"));
                        txtDate.setTextColor(Color.parseColor("#A16801"));
                        itemView.setBackgroundResource(R.drawable.btn_red);
                        break;
                }
            }

        }
    }




    public void filterList(List<NoteModel> filteredList) {
            list = filteredList;
        notifyDataSetChanged();
    }

    public void listEmpty() {
        list = listSourse;
        notifyDataSetChanged();
    }

}
