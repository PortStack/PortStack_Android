package com.example.cookingrecipe.Retrofit.API;

import com.example.cookingrecipe.Domain.DTO.RegisterDTO;
import com.example.cookingrecipe.Domain.DTO.UserDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {
    @POST("/auth/login")
    Call<UserDTO.Response> login(@Body UserDTO.Request user);

    @POST("/auth/refreshToken")
    Call<UserDTO.Response> getAccessToken(@Body UserDTO.RefreshRequest refreshToken);


    @POST("/auth/register")
    Call<Boolean> register(@Body RegisterDTO.Request registerRequest);

}
