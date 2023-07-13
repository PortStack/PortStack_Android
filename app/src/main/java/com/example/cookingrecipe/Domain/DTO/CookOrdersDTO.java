package com.example.cookingrecipe.Domain.DTO;

import com.example.cookingrecipe.Domain.Model.Ingredient;
import com.example.cookingrecipe.Domain.Model.RecipeOrder;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CookOrdersDTO {

    @SerializedName("title")
    private String title;

    @SerializedName("writer")
    private String writer;

    @SerializedName("tags")
    private String tags;

    @SerializedName("recipeIngredients")
    private List<Ingredient> recipeIngredients;

    @SerializedName("cookOrders")
    private List<RecipeOrder> cookOrders;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
    public List<Ingredient> getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(List<Ingredient> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public List<RecipeOrder> getCookOrders() {
        return cookOrders;
    }

    public void setCookOrders(List<RecipeOrder> cookOrders) {
        this.cookOrders = cookOrders;
    }
}
