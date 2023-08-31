package com.example.cookingrecipe.Retrofit;

import android.util.Log;

import com.example.cookingrecipe.BuildConfig;
import com.example.cookingrecipe.Util.TokenUtil;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private final static String BASE_URL = BuildConfig.SAMPLE_API_KEY;
    private static Retrofit retrofit = null;
    private static String jwtToken = null;

    public static Retrofit getClient() {
        jwtToken = TokenUtil.getAccessToken("none");

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // Add JwtInterceptor to the OkHttpClient
        httpClient.addInterceptor(new JwtInterceptor());

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        return retrofit;
    }

    public static void setJwtToken(String token) {
        jwtToken = token;
    }
}
