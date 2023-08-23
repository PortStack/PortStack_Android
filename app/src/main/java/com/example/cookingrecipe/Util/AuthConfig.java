package com.example.cookingrecipe.Util;

import android.content.Context;
import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AuthConfig extends Application {
    static final String PREF_USER_NAME = "username";
    static final String PREF_USER_EMAIL = "email";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    // 계정 정보 저장
    public static void setUserName(Context ctx, String userName, String email) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.putString(PREF_USER_EMAIL, email);
        editor.commit();
    }

    // 저장된 정보 가져오기
    public static String getUserName(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static String getEmail(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_EMAIL, "");
    }


    // 로그아웃
    public static void clearUserName(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.commit();
    }
//    private String nickName;
//    private String accessToken;
//
//    public String getNickName() {
//        return nickName;
//    }
//
//    public void setNickName( String nickName ) {
//        this.nickName = nickName;
//    }
//
//    public String getAccessToken(){
//        return accessToken;
//    }
//
//    public void setAccessToken(String accessToken){
//        this.accessToken = accessToken;
//    }
}
