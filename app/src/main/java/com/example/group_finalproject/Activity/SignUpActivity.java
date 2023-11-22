package com.example.group_finalproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.group_finalproject.API.APIRequestPostData;
import com.example.group_finalproject.API.PostRetroServer;
import com.example.group_finalproject.Model.Register.Register;
import com.example.group_finalproject.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    EditText etUsername, etEmail, etPhone, etPassword, etConfirmPassword;

    String userName, userEmail, userPhone, userPassword, confirmPassword;

    APIRequestPostData apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_up);

        etUsername = findViewById(R.id.et_username);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);

        TextView signIn = (TextView) findViewById(R.id.textView6);
        signIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                Intent myIntent = new Intent(view.getContext(), MainActivity.class);
//                startActivityForResult(myIntent, 0);
                startActivity(new Intent(SignUpActivity.this, MainActivity.class));

            }

        });

        Button signUpBtn = (Button) findViewById(R.id.btn_add_report);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                userName = etUsername.getText().toString();
                userEmail = etEmail.getText().toString();
                userPhone = etPhone.getText().toString();
                userPassword = etPassword.getText().toString();
                confirmPassword = etConfirmPassword.getText().toString();
                if(userName.trim().equals("")){
                    etUsername.setError("Please enter your name");
                }else if(userEmail.trim().equals("")){
                    etEmail.setError("Please enter your email");
                }else if(userPhone.trim().equals("")){
                    etPhone.setError("Please enter your phone");
                }else if(userPassword.trim().equals("")){
                    etPassword.setError("Please enter your password");
                }else if(confirmPassword.trim().equals("")){
                    etConfirmPassword.setError("Please confirm your password");
                }else if(!confirmPassword.trim().equals(userPassword)){
                    etConfirmPassword.setError("Your password confirmation does not match");
                }else{
                    register(userName, userEmail, userPhone, userPassword, confirmPassword);
                }

            }

            private void register(String userName, String userEmail, String userPhone, String userPassword, String confirmPassword) {

                apiInterface = PostRetroServer.postConnectRetrofit().create(APIRequestPostData.class);
                Call<Register> registerCall = apiInterface.registerResponse(userName, userEmail, userPhone, userPassword, confirmPassword);
                registerCall.enqueue(new Callback<Register>() {
                    @Override
                    public void onResponse(Call<Register> call, Response<Register> response) {
//                        if(response.body() != null && response.isSuccessful() && response.body().getCode()==1){
                        if(response.body() != null && response.code() == 200){
                            Toast.makeText(SignUpActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(SignUpActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Register> call, Throwable t) {
                        Toast.makeText(SignUpActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }

        });
    }
}
