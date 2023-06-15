package com.example.cookingrecipe.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cookingrecipe.R;


public class AddPopUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showOptionsDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("팝업화면")
                .setItems(new String[]{"레시피 등록하기", "게시글 등록하기", "취소"}, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                //startActivity(new Intent(MainActivity.this, AddRecipe.class));
                                break;
                            case 1:
                                // 게시글 등록하기를 선택한 경우의 동작 추가
                                break;
                            case 2:
                                // 취소를 선택한 경우의 동작 추가
                                break;
                        }
                    }
                })
                .setCancelable(true);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
