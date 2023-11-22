package com.example.group_finalproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.example.group_finalproject.R;
import com.example.group_finalproject.Session.SessionManager;

public class SplashScreenActivity extends AppCompatActivity {
    
    SessionManager sessionManager;
//    TextView tvUserName;
//    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        // Hide ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        sessionManager = new SessionManager(SplashScreenActivity.this);
        if(!sessionManager.isLoggedIn()){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    moveToLoginPage();
                }
            }, 3000);
        }else{
            // timer
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
//                    tvUserName = findViewById(R.id.tv_home_username);
//                    userName = sessionManager.getUserDetail().get(SessionManager.USERNAME);
//                    tvUserName.setText(userName);
                    startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));

//                    finish();
                }
            }, 3000);
        }



    }

    private void moveToLoginPage() {
        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }
}