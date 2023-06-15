package com.example.cookingrecipe.activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {
    EditText editLogin;
    EditText editPassword;
    Button loginButton; // 로그인 버튼 추가
    TextView registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        editLogin = findViewById(R.id.editID);
        editPassword = findViewById(R.id.ediPassword);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.signIn);


        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, Signup.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(v -> {
            login(editLogin.getText().toString(),editPassword.getText().toString());

        });
    }

    private void login(String id, String password){
        if(id.equals("")||password.equals("")) {
            Toast.makeText(getApplicationContext(), "아이디 혹은 비밀번호를 입력해주세요", Toast.LENGTH_LONG).show();
        }else{
            UserDTO.Request request = new UserDTO.Request();
            request.setAccount(id);
            request.setPassword(password);

            Intent intent = new Intent(this, MainActivity.class);
            //로그인 요청
            loginRequest(request,intent);
        }
    }
    public void loginRequest(UserDTO.Request request,Intent intent){
        RetrofitAPI retrofitAPI = RetrofitClient.getClient(null).create(RetrofitAPI.class);
        retrofitAPI.login(request).enqueue(new Callback<UserDTO.Response>() {

            @Override
            public void onResponse(@NonNull Call<UserDTO.Response> call, @NonNull Response<UserDTO.Response> response) {
                if(response.isSuccessful()){
                    UserDTO.Response user = response.body();

                    Log.d("TEST", "성공");
                    Log.d("TEST", user.getAccessToken());
                    TokenUtil.setAccessToken(user.getAccessToken());
                    TokenUtil.setRefreshToken(user.getRefreshToken());
                    ( (AuthConfig) getApplication() ).setNickName(user.getNickname());

                    Log.d("TEST", "성공");
                    Log.d("TEST",( (AuthConfig) getApplication() ).getNickName());
                    Log.d("TEST", TokenUtil.getAccessToken("none"));
                    Log.d("Test",TokenUtil.getRefreshToken("nine"));

                    startActivity(intent);
                    LoginActivity.this.finish();
                }
            }

            @Override
            public void onFailure(Call<UserDTO.Response> call, Throwable t) {
                Log.d("Test",TokenUtil.getRefreshToken("실패"));
                t.printStackTrace();
            }

        });
    }
}
