package com.example.cookingrecipe.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookingrecipe.Domain.DTO.RegisterDTO;
import com.example.cookingrecipe.Domain.DTO.UserDTO;
import com.example.cookingrecipe.R;
import com.example.cookingrecipe.Retrofit.API.RetrofitAPI;
import com.example.cookingrecipe.Retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    TextView back;
    EditText name, id, pw, pw2, email;
    Button pwcheck, submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        back = findViewById(R.id.back);
        back.setOnClickListener(v -> onBackPressed());

        name = findViewById(R.id.signName);
        id = findViewById(R.id.signID);
        pw = findViewById(R.id.signPW);
        pw2 = findViewById(R.id.signPW2);
        email = findViewById(R.id.signmail);

        pwcheck = findViewById(R.id.pwcheckbutton);
        pwcheck.setOnClickListener(v -> {
            if (pw.getText().toString().equals(pw2.getText().toString())) {
                pwcheck.setText("일치");
                pwcheck.setTextColor(getResources().getColor(R.color.white));
                Toast.makeText(SignupActivity.this, "비밀번호가 일치합니다.", Toast.LENGTH_LONG).show();
            } else {
                pwcheck.setText("불일치");
                pwcheck.setTextColor(getResources().getColor(R.color.red));
                Toast.makeText(SignupActivity.this, "비밀번호가 다릅니다.", Toast.LENGTH_LONG).show();
            }
        });

        submit = findViewById(R.id.signupbutton);
        submit.setOnClickListener(v -> {
            if (validateInput()) {
                performSignUp();
            }
        });
    }

    private boolean validateInput() {
        String nameValue = name.getText().toString().trim();
        String idValue = id.getText().toString().trim();
        String pwValue = pw.getText().toString();
        String pw2Value = pw2.getText().toString();
        String emailValue = email.getText().toString().trim();

        if (nameValue.isEmpty() || idValue.isEmpty() || pwValue.isEmpty() || pw2Value.isEmpty() || emailValue.isEmpty()) {
            Toast.makeText(this, "모든 필드를 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!pwValue.equals(pw2Value)) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isStrongPassword(pwValue)) {
            Toast.makeText(this, "비밀번호는 알파벳, 숫자, 특수문자를 모두 포함해야 합니다.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // 추가적인 유효성 검사 (이메일 등) 또한 필요한 경우 여기에 추가

        return true;
    }

    private boolean isStrongPassword(String password) {
        // 비밀번호에 알파벳, 숫자, 특수문자가 모두 포함되었는지 검사하는 로직
        boolean containsAlphabet = false;
        boolean containsNumber = false;
        boolean containsSpecialChar = false;
        String specialChars = "!@#$%^&*()_-+=<>?";

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                containsAlphabet = true;
            } else if (Character.isDigit(c)) {
                containsNumber = true;
            } else if (specialChars.contains(String.valueOf(c))) {
                containsSpecialChar = true;
            }
        }

        return containsAlphabet && containsNumber && containsSpecialChar;
    }


    private void performSignUp() {
        String nameValue = name.getText().toString().trim();
        String idValue = id.getText().toString().trim();
        String pwValue = pw.getText().toString();
        String emailValue = email.getText().toString().trim();

        RetrofitAPI retrofitAPI = RetrofitClient.getClient().create(RetrofitAPI.class);
        RegisterDTO.Request registerRequest = new RegisterDTO.Request(idValue, pwValue, nameValue, emailValue);

        Call<Boolean> call = retrofitAPI.register(registerRequest);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SignupActivity.this, "회원 가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

                Toast.makeText(SignupActivity.this, "회원가입에 실패하였습니다", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
