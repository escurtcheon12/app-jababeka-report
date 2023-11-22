package com.example.group_finalproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group_finalproject.API.APIRequestPostData;
import com.example.group_finalproject.API.PostRetroServer;
import com.example.group_finalproject.Model.Login.Login;
import com.example.group_finalproject.Model.Login.LoginData;
import com.example.group_finalproject.R;
import com.example.group_finalproject.Session.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    String userName, userPassword;
    APIRequestPostData apiInterface;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);

        TextView signUp = (TextView) findViewById(R.id.textView6);
        signUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }

        });

        Button signInBtn = (Button) findViewById(R.id.btn_add_report);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                userName = etUsername.getText().toString();
                userPassword = etPassword.getText().toString();
                if(userName.trim().equals("")){
                    etUsername.setError("Please enter your username");
                }else if(userPassword.trim().equals("")){
                    etPassword.setError("Please enter your password");
                }else{
                    login(userName, userPassword);
                }
            }

            private void login(String userName, String userPassword) {

                apiInterface = PostRetroServer.postConnectRetrofit().create(APIRequestPostData.class);
                Call<Login> loginCall = apiInterface.loginResponse(userName, userPassword);
                loginCall.enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
//                        if(response.body() != null && response.isSuccessful() && response.body().getCode()==1){
                        if(response.body() != null && response.code() == 200){
                            sessionManager = new SessionManager(MainActivity.this);
                            LoginData loginData = response.body().getLoginData();
                            sessionManager.createLoginSession(loginData);

//                            Toast.makeText(MainActivity.this, response.body().getLoginData().getUserName(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            finish();

                        }
                        else {
                            Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
//                        Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                          Toast.makeText(MainActivity.this, "Sorry, your username or password is incorrect", Toast.LENGTH_SHORT).show();

                    }
                });

            }

        });
    }
}