package com.example.cookingrecipe.Util;

import android.app.Application;

public class AuthConfig extends Application {
    private String nickName;
    private String accessToken;

    public String getNickName() {
        return nickName;
    }

    public void setNickName( String nickName ) {
        this.nickName = nickName;
    }

    public String getAccessToken(){
        return accessToken;
    }

    public void setAccessToken(String accessToken){
        this.accessToken = accessToken;
    }
}
