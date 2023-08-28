package com.example.cookingrecipe.Domain.DTO;

import com.google.gson.annotations.SerializedName;

public class RegisterDTO {
    public static class Request {
        @SerializedName("account")
        private String account;

        @SerializedName("password")
        private String password;

        @SerializedName("nickname")
        private String nickname;

        @SerializedName("email")
        private String email;

        public Request(String account, String password, String nickname, String email) {
            this.account = account;
            this.password = password;
            this.nickname = nickname;
            this.email = email;
        }
    }
}
