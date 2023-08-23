package com.example.cookingrecipe.Fragment;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cookingrecipe.Activity.GoogleLoginActivity;
import com.example.cookingrecipe.Activity.LoginActivity;
import com.example.cookingrecipe.Activity.MainActivity;
import com.example.cookingrecipe.Util.AuthConfig;
import com.example.cookingrecipe.Util.TokenUtil;
import com.example.cookingrecipe.databinding.FragmentAccountBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class AccountFragment extends Fragment {

    FragmentAccountBinding binding;
    Button logout_btn;
    Button login_btn;
    TextView name;
    TextView email;
    ImageView profile_image;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    ConstraintLayout login_layout;
    ConstraintLayout profile_layout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        String nickname = AuthConfig.getUserName(getContext());
        String userEmail = AuthConfig.getEmail(getContext());


        profile_layout = binding.profile;
        login_layout = binding.login;

        logout_btn = binding.logoutBtn;
        logout_btn.setOnClickListener(v -> signOut());
        login_btn = binding.loginBtn;
        login_btn.setOnClickListener(v -> startActivity(new Intent(this.getActivity(), LoginActivity.class)));

        name = binding.name;
        email = binding.email;
        profile_image = binding.profileImg;

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(getActivity(), gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());

        Log.e("AccountFragement", nickname);
        if (nickname != "" || userEmail != "") {
            profile_layout.setVisibility(View.VISIBLE);
            login_layout.setVisibility(View.GONE);

            name.setText(nickname);
            email.setText(userEmail);
        } else {
            profile_layout.setVisibility(View.GONE);
            login_layout.setVisibility(View.VISIBLE);
        }


        return view;
    }

    void signOut() {
        gsc.signOut().addOnCompleteListener(task -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setTitle("로그아웃").setMessage("정말 로그아웃 하시겠습니까?");

            builder.setNegativeButton("취소", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id)
                {
                    Toast.makeText(getActivity(), "Cancel Click", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNeutralButton("로그아웃", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id)
                {
                    AuthConfig.clearUserName(getActivity());
                    TokenUtil.clearToken();
                    getActivity().finish();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    Toast.makeText(getApplicationContext(), "로그아웃 되었습니다", Toast.LENGTH_SHORT).show();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        });
    }
}