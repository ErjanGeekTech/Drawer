package com.example.drawer.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drawer.R;
import com.example.drawer.models.SlideshowModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SlideshowAdapter extends RecyclerView.Adapter <SlideshowAdapter.SlideshowViewHolder> {

    List <SlideshowModel> list;

    public void addImage(SlideshowModel model){
        list.add(model);
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public SlideshowViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_strogace, parent, false);
        return  new SlideshowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SlideshowViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SlideshowViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public SlideshowViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView3);
        }

        public void bind(SlideshowModel model) {
            image.setImageBitmap(model.getBitmap());
        }
    }
}
