package com.example.cookingrecipe.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.cookingrecipe.Activity.LoginActivity;
import com.example.cookingrecipe.Activity.MainActivity;
import com.example.cookingrecipe.Domain.DTO.UserDTO;
import android.Manifest;
import com.example.cookingrecipe.R;
import com.example.cookingrecipe.Retrofit.API.RetrofitAPI;
import com.example.cookingrecipe.Retrofit.RetrofitClient;
import com.example.cookingrecipe.Util.AuthConfig;
import com.example.cookingrecipe.Util.PermissionSupport;
import com.example.cookingrecipe.Util.TokenUtil;
import com.google.firebase.database.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class IntroActivity extends AppCompatActivity {
    private ConstraintLayout startBtn;
    private TextView textView;
    // 클래스를 선언
    private PermissionSupport permission;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        startBtn = findViewById(R.id.startBtn);
        textView = findViewById(R.id.loginBtn);

        verifyStoragePermissions(this);

        TokenUtil.init(getApplicationContext());

        checkRefreshToken(this);

        textView.setOnClickListener(v -> {
            startActivitys(this, LoginActivity.class);
        });

        startBtn.setOnClickListener(v -> {
            startActivitys(this, MainActivity.class);
        });
    }

    // 권한 체크
    private void permissionCheck(){
        // sdk 23버전 이하 버전에서는 permission이 필요하지 않음
        if(Build.VERSION.SDK_INT >= 23){
            System.out.println("permissionCheck");
            // 클래스 객체 생성
            permission =  new PermissionSupport(this, this);

            // 권한 체크한 후에 리턴이 false일 경우 권한 요청을 해준다.
            if(!permission.checkPermission()){
                permission.requestPermission();
            }
        }
    }

    // Request Permission에 대한 결과 값을 받는다.
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        // 리턴이 false일 경우 다시 권한 요청
//        if (!permission.permissionResult(requestCode, permissions, grantResults)){
//            permission.requestPermission();
//        }
//    }
    private void checkRefreshToken(Context thisContext){
        String refreshToken = TokenUtil.getRefreshToken(null);
        if(refreshToken != null){
            UserDTO.RefreshRequest request = new UserDTO.RefreshRequest();
            request.setRefreshToken(refreshToken);

            getAccessToken(request,thisContext);
        }

    }

    public void getAccessToken(UserDTO.RefreshRequest request, Context context){
        RetrofitAPI retrofitAPI = RetrofitClient.getClient().create(RetrofitAPI.class);
        retrofitAPI.getAccessToken(request).enqueue(new Callback<UserDTO.Response>() {

            @Override
            public void onResponse(@NonNull Call<UserDTO.Response> call, @NonNull Response<UserDTO.Response> response) {
                if(response.isSuccessful()){
                    UserDTO.Response user = response.body();
                    TokenUtil.setAccessToken(response.body().getAccessToken());


                    AuthConfig.setUserName(context,user.getNickname(),user.getEmail());

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