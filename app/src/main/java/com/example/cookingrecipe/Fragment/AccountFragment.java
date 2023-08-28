package com.example.cookingrecipe.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.cookingrecipe.Activity.LoginActivity;
import com.example.cookingrecipe.Activity.MainActivity;
import com.example.cookingrecipe.Util.AuthConfig;
import com.example.cookingrecipe.Util.TokenUtil;
import com.example.cookingrecipe.databinding.FragmentAccountBinding;

public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;
    private Button logoutBtn;
    private Button loginBtn;
    private TextView nameTextView;
    private TextView emailTextView;
    private ConstraintLayout profileLayout;
    private ConstraintLayout loginLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        nameTextView = binding.name;
        emailTextView = binding.email;
        profileLayout = binding.profile;
        loginLayout = binding.login;

        logoutBtn = binding.logoutBtn;
        logoutBtn.setOnClickListener(v -> showLogoutDialog());

        loginBtn = binding.loginBtn;
        loginBtn.setOnClickListener(v -> startActivity(new Intent(requireActivity(), LoginActivity.class)));

        updateUI();

        return view;
    }

    private void updateUI() {
        String nickname = AuthConfig.getUserName(requireContext());
        String userEmail = AuthConfig.getEmail(requireContext());

        if (!nickname.isEmpty() || !userEmail.isEmpty()) {
            profileLayout.setVisibility(View.VISIBLE);
            loginLayout.setVisibility(View.GONE);

            nameTextView.setText(nickname);
            emailTextView.setText(userEmail);
        } else {
            profileLayout.setVisibility(View.GONE);
            loginLayout.setVisibility(View.VISIBLE);
        }
    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("로그아웃")
                .setMessage("정말 로그아웃 하시겠습니까?")
                .setNegativeButton("취소", (dialog, id) -> Toast.makeText(requireContext(), "Cancel Click", Toast.LENGTH_SHORT).show())
                .setNeutralButton("로그아웃", (dialog, id) -> performLogout());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void performLogout() {
        AuthConfig.clearUserName(requireContext());
        TokenUtil.clearToken();
        startActivity(new Intent(requireContext(), MainActivity.class));
        Toast.makeText(requireContext(), "로그아웃 되었습니다", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
