package com.example.cookingrecipe.Domain.Model;

import android.net.Uri;

public class RecipeOrder {
    int sequence;
    String imageUrl;
    String content;

    public RecipeOrder(){}

    public RecipeOrder(int sequence, String orderImageName, String content){
        this.sequence = sequence;
        this.imageUrl = orderImageName;
        this.content = content;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getOrderImageName() {
        return imageUrl;
    }

    public void setOrderImageName(String orderImageName){
        this.imageUrl = orderImageName;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }
}
