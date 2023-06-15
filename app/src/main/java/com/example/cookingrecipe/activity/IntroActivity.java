package com.example.cookingrecipe.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookingrecipe.Domain.DTO.UserDTO;
import com.example.cookingrecipe.R;
import com.example.cookingrecipe.Retrofit.API.RetrofitAPI;
import com.example.cookingrecipe.Retrofit.RetrofitClient;
import com.example.cookingrecipe.util.AuthConfig;
import com.example.cookingrecipe.util.TokenUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IntroActivity extends AppCompatActivity {
    private ConstraintLayout startBtn;
    private TextView textView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        startBtn = findViewById(R.id.startBtn);
        textView = findViewById(R.id.loginBtn);

        TokenUtil.init(getApplicationContext());

        checkRefreshToken(this);

        textView.setOnClickListener(v -> {
            startActivitys(this,LoginActivity.class);
        });

        startBtn.setOnClickListener(v -> {
            startActivitys(this,MainActivity.class);
        });
    }

    private void checkRefreshToken(Context thisContext){
        String refreshToken = TokenUtil.getRefreshToken(null);
        if(refreshToken != null){
            UserDTO.RefreshRequest request = new UserDTO.RefreshRequest();
            request.setRefreshToken(refreshToken);

            Log.d("Test",request.getRefreshToken());
            getAccessToken(request,thisContext);
        }

    }

    public void getAccessToken(UserDTO.RefreshRequest request, Context context){
        RetrofitAPI retrofitAPI = RetrofitClient.getClient(null).create(RetrofitAPI.class);
        retrofitAPI.getAccessToken(request).enqueue(new Callback<UserDTO.Response>() {

            @Override
            public void onResponse(@NonNull Call<UserDTO.Response> call, @NonNull Response<UserDTO.Response> response) {
                if(response.isSuccessful()){
                    UserDTO.Response user = response.body();
                    Log.d("Test",user.getAccessToken());
//                    TokenUtil.setAccessToken(response.body().getAccessToken());

                    ( (AuthConfig) getApplication() ).setNickName(user.getNickname());
                    ( (AuthConfig) getApplication() ).setAccessToken(user.getNickname());

                    Log.d("TEST", "성공");
                    Log.d("TEST",( (AuthConfig) getApplication() ).getNickName());
                    Log.d("TEST", TokenUtil.getAccessToken("none"));
                    Log.d("Test",TokenUtil.getRefreshToken("nine"));
                    startActivitys(context,MainActivity.class);
                }
            }

            @Override
            public void onFailure(Call<UserDTO.Response> call, Throwable t) {
                Log.d("Test",TokenUtil.getRefreshToken("실패"));
                t.printStackTrace();
            }

        });
    }

    public void startActivitys(Context thisContext, Class tartgetClass){
        Intent intent = new Intent(thisContext, tartgetClass);
        startActivity(intent);
    }

}