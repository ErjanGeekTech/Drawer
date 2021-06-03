package com.example.drawer.adapters;

import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drawer.R;
import com.example.drawer.interfaces.OnItemClickListener;
import com.example.drawer.models.NoteModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class NoteAdapter extends RecyclerView.Adapter <NoteAdapter.NoteViewHolder> {

    ArrayList<NoteModel> list = new ArrayList<>();
    private Timer timer;
    private ArrayList <NoteModel> notesSource;
    NoteModel model;
    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void addNotes(NoteModel model) {
        list.add(model);
        notesSource = list;
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
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //String title = list.get(position).getTitle();
//                //String desc = list.get(position).getDescription();
//                model = new NoteModel(title, desc);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("model", model);
////                getParentFragmentManager().setFragmentResult("key", bundle);
//            }
//        });

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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.setOnItemClickListener(getAdapterPosition(), list);
                }
            });
            txtTitle.setText(noteModel.getTitle());
            txtDescription.setText(noteModel.getDescription());
            txtDate.setText(noteModel.getDate());

        }
    }

    public void  searchNotes(final String searchKeyWord){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (searchKeyWord.trim().isEmpty()){
                    list = notesSource;
                }else {
                    ArrayList<NoteModel> temp = new ArrayList<>();
                    for (NoteModel note : notesSource){
                        if (note.getTitle().toLowerCase().contains(searchKeyWord.toLowerCase())){
                            temp.add(note);
                        }
                    }
                    list = temp;
                }
            }
        },500);

    }
    public  void  cancelTimer(){
        if (timer != null){
            timer.cancel();
        }
    }
}
