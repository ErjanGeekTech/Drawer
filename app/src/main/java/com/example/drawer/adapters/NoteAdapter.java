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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drawer.R;
import com.example.drawer.interfaces.OnItemClickListener;
import com.example.drawer.models.NoteModel;
import com.example.drawer.ui.home.HomeFragment;
import com.example.drawer.unit.App;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.logging.Handler;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    public List<NoteModel> list;

    private OnItemClickListener listener;


    public NoteAdapter(boolean isList, OnItemClickListener listener) {
        this.list = new ArrayList<>();
        this.listener = listener;
    }


    public void addListOfModel(List<NoteModel> listM) {
        list.clear();
        list.addAll(listM);
        notifyDataSetChanged();
    }


    @NonNull
    @NotNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view;
        if (!HomeFragment.isList) {
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

    public void delete(int position) {
        list.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

//    @Override
//    public Filter getFilter() {
//        return filter;
//    }

//    Filter filter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            String charString = constraint.toString();
//            if (charString.isEmpty()) {
//                HomeFragment.search = list;
//            } else {
//                List<NoteModel> filteredList = new ArrayList<>();
//                for (NoteModel row : list) {
//
//                    // name match condition. this might differ depending on your requirement
//                    // here we are looking for name or phone number match
//                    if (row.getTitle().toLowerCase().contains(charString.toLowerCase())) {
//                        filteredList.add(row);
//                    }
//                }
//
//                HomeFragment.search = filteredList;
//            }
//
//            FilterResults filterResults = new FilterResults();
//            filterResults.values = HomeFragment.search;
//            return filterResults;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            list = (ArrayList<NoteModel>) results.values;
//            notifyDataSetChanged();
//        }
//    };

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
                listener.onItemClick(getAdapterPosition(), noteModel);
            });
            txtTitle.setText(noteModel.getTitle());
            txtDate.setText(noteModel.getDate());
            if (noteModel.getBackground() != null) {
                switch (noteModel.getBackground()) {
                    case "black":
                        txtTitle.setTextColor(Color.parseColor("#B1B1B1"));
                        txtDate.setTextColor(Color.parseColor("#616161"));
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
        list = HomeFragment.search;
        notifyDataSetChanged();
    }

}
