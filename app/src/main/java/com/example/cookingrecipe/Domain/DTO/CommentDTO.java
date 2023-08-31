package com.example.cookingrecipe.Domain.DTO;

import com.google.gson.annotations.SerializedName;

public class CommentDTO {
    @SerializedName("id")
    private int id;

    @SerializedName("nickName")
    private String nickname;

    @SerializedName("comment")
    private String comment;

    @SerializedName("createdDate")
    private  String createdDate;

    public CommentDTO(String nickname, String content) {
        this.nickname = nickname;
        this.comment = content;
    }

    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getComment() {
        return comment;
    }

    public  String getCreatedDate() {
        return createdDate;
    }

}
