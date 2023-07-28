package com.example.cookingrecipe.Domain.Model;

import java.util.List;

public class RecipeEntity {
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getNickname() {
        return nickname;
    }

    public String getThemNailUrl() {
        return themNailUrl;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public int getViews() {
        return views;
    }

    public int getLikes() {
        return likes;
    }

    public boolean isLikeState() {
        return likeState;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public List<Step> getCookOrderList() {
        return cookOrderList;
    }

    public RecipeEntity(String id, String title, String nickname, String themNailUrl, String createdDate, String modifiedDate, int views, int likes, boolean likeState, List<Ingredient> ingredientList, List<Step> cookOrderList) {
        this.id = id;
        this.title = title;
        this.nickname = nickname;
        this.themNailUrl = themNailUrl;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.views = views;
        this.likes = likes;
        this.likeState = likeState;
        this.ingredientList = ingredientList;
        this.cookOrderList = cookOrderList;
    }

    String id;
    String title;
    String nickname;
    String themNailUrl;
    String createdDate;
    String modifiedDate;
    int views;
    int likes;
    boolean likeState;
//    List<String> tags;
    List<Ingredient> ingredientList;
    List<Step> cookOrderList;


    public static class RecipeBuilder {
        Long id;
        String title;
        String nickname;
        String themNailUrl;
        String createdDate;
        String modifiedDate;
        int views;
        int likes;
        boolean likeState;
        List<Ingredient> ingredientList;
        List<Step> cookOrderList;

        public RecipeBuilder(Long id) {
            this.id = id;
        }
        public RecipeBuilder setTitle(String title){
            this.title = title;
            return this;
        }

        public RecipeBuilder setNickname(String nickname){
            this.nickname = nickname;
            return this;
        }

        public RecipeBuilder setThemNailUrl(String themNailUrl){
            this.themNailUrl = themNailUrl;
            return this;
        }

        public RecipeBuilder setCreatedDate(String createdDate){
            this.createdDate = createdDate;
            return this;
        }

        public RecipeBuilder setModifiedDate(String modifiedDate){
            this.modifiedDate = modifiedDate;
            return this;
        }

        public RecipeBuilder setViews(int views){
            this.views = views;
            return this;
        }

        public RecipeBuilder setLikes(int likes){
            this.likes = likes;
            return this;
        }

        public RecipeBuilder setLikeState(boolean likeState){
            this.likeState = likeState;
            return this;
        }

        public RecipeBuilder setIngredients(List<Ingredient> ingredientList){
            this.ingredientList = ingredientList;
            return this;
        }

        public RecipeBuilder setCookOrderList(List<Step> cookOrderList){
            this.cookOrderList = cookOrderList;
            return this;
        }

    }
}
