package com.example.cookingrecipe.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.cookingrecipe.Fragment.AccountFragment;
import com.example.cookingrecipe.Fragment.FavoriteFragment;
import com.example.cookingrecipe.Fragment.SearchFragment;
import com.example.cookingrecipe.R;
import com.example.cookingrecipe.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
private BottomNavigationView bottomNavigationView;
    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new HomeFragment());
        bottomNavigationView = binding.bottomNavigation;
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.search) {
                replaceFragment(new SearchFragment());
            } else if (item.getItemId() == R.id.insert) {
                showOptionsDialog();
            } else if (item.getItemId() == R.id.favorite) {
                replaceFragment(new FavoriteFragment());
            } else if (item.getItemId() == R.id.account) {
                replaceFragment(new AccountFragment());
            }
            return true;
        });
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
                                // Handle "게시글 등록하기" action
                                addPost();
                                break;
                            case 2:
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

    private void addPost() {
        // Perform the necessary actions for adding a post
        // For example, start a new activity or show a fragment for post addition
    }



}