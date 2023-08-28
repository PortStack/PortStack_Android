package com.example.cookingrecipe.Domain.DTO;

import com.example.cookingrecipe.Domain.Model.Recipe;
import com.example.cookingrecipe.Domain.Model.RecipeOrder;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeDTO {

    public static class Request{

        @SerializedName("id")
        private int id;

        @SerializedName("title")
        private String title;

        @SerializedName("nickname")
        private String nickname;

        @SerializedName("createDate")
        private String createDate;

        @SerializedName("modifiedDate")
        private String modifiedDate;

        @SerializedName("likeState")
        private boolean likeState;

        @SerializedName("themNailUrl")
        private String themNailUrl;

        @SerializedName("views")
        private int views;

        @SerializedName("likes")
        private int likes;

        @SerializedName("ingredientList")
        private List<IngredientDTO> ingredientList;

        @SerializedName("cookOrderList")
        private List<RecipeOrder> cookOrderList;

        @SerializedName("tags")
        private List<TagDTO.Request> tags;

        @SerializedName("comments")
        private List<CommentDTO> comments;

        public int getId() {return id;}

        public String getTitle() {return title;}

        public String getThemNailUrl() { return themNailUrl;}

        public List<CommentDTO> getComments() {
            return comments;
        }

        public List<TagDTO.Request> getTags() { return tags;}

        public String getNickname() {return nickname;}

        public String getCreateDate() {return createDate;}

        public String getModifiedDate() {return modifiedDate;}

        public int getViews() {return views;}

        public int getLikes() {return likes;}

        public List<IngredientDTO> getIngredientList() {return ingredientList;}

        public List<RecipeOrder> getCookOrderList() {return cookOrderList;}

        public void setId(int id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean getLikeState() {
            return likeState;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public void setModifiedDate(String modifiedDate) {
            this.modifiedDate = modifiedDate;
        }

        public void setViews(int views) {
            this.views = views;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        public void setTags(List<TagDTO.Request> tags) { this.tags = tags;}
        public void setIngredientList(List<IngredientDTO> ingredientList) {
            this.ingredientList = ingredientList;
        }

        public void setCookOrderList(List<RecipeOrder> cookOrderList) {
            this.cookOrderList = cookOrderList;
        }


    }

}
