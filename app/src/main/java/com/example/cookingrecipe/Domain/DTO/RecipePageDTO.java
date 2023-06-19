package com.example.cookingrecipe.Domain.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipePageDTO {
    @SerializedName("content")
    @Expose
    private List<RecipeDTO.Request> items = null;

    public List<RecipeDTO.Request> getItems() { return items; }

}
