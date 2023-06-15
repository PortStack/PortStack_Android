package com.example.cookingrecipe.Retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private final static String BASE_URL = "http://192.168.35.195:8080";
    private static Retrofit retrofit = null;
    private static String jwtToken = null;

    public static Retrofit getClient(String initialJwtToken) {
        jwtToken = initialJwtToken;

        if (retrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            // Add JwtInterceptor to the OkHttpClient
            httpClient.addInterceptor(new JwtInterceptor(jwtToken));

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }

    public static void setJwtToken(String token) {
        jwtToken = token;
    }
}
