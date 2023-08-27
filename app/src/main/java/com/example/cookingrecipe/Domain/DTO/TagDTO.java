package com.example.cookingrecipe.Domain.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TagDTO {

    public static class Request{
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;

        public String getTag() { return name; };
        public int getId() { return id; };
    }

    public static class List {
        @SerializedName("content")
        @Expose
        private java.util.List<TagDTO.Request> items = null;

        public java.util.List<TagDTO.Request> getItems() { return items; }
    }
}
