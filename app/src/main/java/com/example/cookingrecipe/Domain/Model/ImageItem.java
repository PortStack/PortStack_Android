package com.example.cookingrecipe.Domain.Model;

import android.net.Uri;

public class ImageItem {
    private Uri imageUri;

    public ImageItem(){
    }

    public ImageItem(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}