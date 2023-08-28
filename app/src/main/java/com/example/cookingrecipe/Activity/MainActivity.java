package com.example.cookingrecipe.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.cookingrecipe.Fragment.AccountFragment;
import com.example.cookingrecipe.Fragment.FavoriteFragment;
import com.example.cookingrecipe.Fragment.SearchFragment;
import com.example.cookingrecipe.R;
import com.example.cookingrecipe.Util.AuthConfig;
import com.example.cookingrecipe.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
private BottomNavigationView bottomNavigationView;
    private String searchData;
    private int selectedTabIndex = 0;

    String nickname;
    String userEmail;
    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //로그인 정보
        nickname = AuthConfig.getUserName(this);
        userEmail = AuthConfig.getEmail(this);

        replaceFragment(new HomeFragment());
        bottomNavigationView = binding.bottomNavigation;
        bottomNavigationView.setOnItemSelectedListener(item -> {


            if (item.getItemId() == R.id.home) {
                replaceFragment(new HomeFragment());
                setSearchData(null);
            } else if (item.getItemId() == R.id.search) {
                if (searchData != null) {
                    replaceFragment(SearchFragment.newInstance(searchData));
                    searchData = null; // 데이터 사용 후 초기화
                } else {
                    replaceFragment(new SearchFragment());
                }
            } else if (item.getItemId() == R.id.insert) {
                bottomNavigationView.getMenu().getItem(selectedTabIndex).setChecked(true);
                setSearchData(null);
                if(nickname != "" && userEmail != ""){
                    showOptionsDialog();
                } else {
                    Toast.makeText(this, "로그인 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                    return false;
                }

            } else if (item.getItemId() == R.id.favorite) {
                setSearchData(null);
                replaceFragment(new FavoriteFragment());
            } else if (item.getItemId() == R.id.account) {
                setSearchData(null);
                replaceFragment(new AccountFragment());
            }
            return true;
        });
    }

    public void setSearchData(String searchData) {
        this.searchData = searchData;
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    private void showOptionsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("추가하기")
                .setItems(new String[]{"레시피 등록하기", "게시글 등록하기", "취소"}, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                // Handle "레시피 등록하기" action
                                addRecipe();
                                break;
                            case 1:
                                // Handle "취소" action
                                dialog.dismiss();
                                break;
                        }
                    }
                })
                .setCancelable(true);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addRecipe() {
        // Perform the necessary actions for adding a recipe
        // For example, start a new activity or show a fragment for recipe addition
        startActivity(new Intent(MainActivity.this, RecipeAddActivity.class));
    }


}