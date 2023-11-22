package com.example.group_finalproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.group_finalproject.R;
import com.example.group_finalproject.Session.SessionManager;

public class ProfileActivity extends AppCompatActivity {

    SessionManager sessionManager;
    TextView tvUserProfileNameTop, tvUserProfileName, tvUserProfileEmail;
    String userProfileName, userProfileEmail;
    Button btnLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();

        sessionManager = new SessionManager(ProfileActivity.this);

        tvUserProfileNameTop = findViewById(R.id.tv_user_profile_name_top);
        tvUserProfileName = findViewById(R.id.tv_user_profile_name);
        tvUserProfileEmail = findViewById(R.id.tv_user_profile_email);

        userProfileName = sessionManager.getUserDetail().get(sessionManager.USERNAME);
        userProfileEmail = sessionManager.getUserDetail().get(sessionManager.USER_EMAIL);

        tvUserProfileNameTop.setText(userProfileName);
        tvUserProfileName.setText(userProfileName);
        tvUserProfileEmail.setText(userProfileEmail);

        btnLogout = findViewById(R.id.btn_logout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logoutSession();
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                finish();
            }
        });
    }
}