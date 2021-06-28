package com.example.drawer.models;

import android.graphics.Bitmap;

public class SlideshowModel {
    Bitmap bitmap;

    public SlideshowModel(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
