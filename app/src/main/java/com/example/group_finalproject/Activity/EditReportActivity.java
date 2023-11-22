package com.example.group_finalproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group_finalproject.API.APIRequestPostData;
import com.example.group_finalproject.API.PostRetroServer;
import com.example.group_finalproject.Model.PostResponseModel;
import com.example.group_finalproject.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditReportActivity extends AppCompatActivity {
    private int receivedPostId;
    private String receivedPostAuthor, receivedPostCategory, receivedPostTextReport, receivedPostLocation;
    private String getUpdatedPostAuthor, getUpdatedPostCategory, getUpdatedPostTextReport, getUpdatedPostLocation;
    private EditText etAuthor, etCategory, etTextReport, etLocation;
    private Button btnUpdate;

    TextView selectCategory;
    ArrayList<String> categoryList;
    Dialog categoryDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_edit_post);

        selectCategory = findViewById(R.id.tv_select_category2);

        categoryList = new ArrayList<>();
        categoryList.add("Accident");
        categoryList.add("Criminal");
        categoryList.add("Infrastructure");
        categoryList.add("Environment");

        selectCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryDialog = new Dialog(EditReportActivity.this);

                categoryDialog.setContentView(R.layout.category_selection);

                categoryDialog.getWindow().setLayout(650, 800);

                categoryDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                categoryDialog.show();

                EditText etSearchCategory = categoryDialog.findViewById(R.id.et_search_category);
                ListView lvCategory = categoryDialog.findViewById(R.id.list_view_category);

                ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(EditReportActivity.this,
                        android.R.layout.simple_list_item_1,categoryList);

                lvCategory.setAdapter(categoryAdapter);

                etSearchCategory.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        categoryAdapter.getFilter().filter(s);

                    }
                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                lvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectCategory.setText(categoryAdapter.getItem(position));
                        categoryDialog.dismiss();
                    }
                });
            }
        });

        Intent receiveSelectedPostData = getIntent();
            //Sengaja dikasi defaultValue nya -1. Supaya bisa tahu, kalau kita dapat nilai id-nya -1 berarti ada yang salah.
        receivedPostId = receiveSelectedPostData.getIntExtra("sentPostId", -1);
        receivedPostAuthor = receiveSelectedPostData.getStringExtra("sentPostAuthor");
        receivedPostCategory = receiveSelectedPostData.getStringExtra("sentPostCategory");
        receivedPostTextReport = receiveSelectedPostData.getStringExtra("sentPostTextReport");
        receivedPostLocation = receiveSelectedPostData.getStringExtra("sentPostLocation");

        etAuthor = findViewById(R.id.et_author);
//        etCategory = findViewById(R.id.et_category);
        etTextReport = findViewById(R.id.et_text_report);
        etLocation = findViewById(R.id.et_location);

        btnUpdate = findViewById(R.id.btn_edit_report);

        etAuthor.setText(receivedPostAuthor);
//        etCategory.setText(receivedPostCategory);
        selectCategory.setText(receivedPostCategory);
        etTextReport.setText(receivedPostTextReport);
        etLocation.setText(receivedPostLocation);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUpdatedPostAuthor = etAuthor.getText().toString();
                getUpdatedPostCategory = selectCategory.getText().toString();
                getUpdatedPostTextReport = etTextReport.getText().toString();
                getUpdatedPostLocation = etLocation.getText().toString();
                updatePostData();
            }
        });
    }

    private void updatePostData(){
        APIRequestPostData arpdPostData = PostRetroServer.postConnectRetrofit().create(APIRequestPostData.class);
        Call<PostResponseModel> editPostData = arpdPostData.arpdEditPostData(receivedPostId, getUpdatedPostAuthor, getUpdatedPostCategory, getUpdatedPostTextReport, getUpdatedPostLocation);

        editPostData.enqueue(new Callback<PostResponseModel>() {
            @Override
            public void onResponse(Call<PostResponseModel> call, Response<PostResponseModel> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();

                Toast.makeText(EditReportActivity.this, "Code: "+code+" | Message: "+message, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<PostResponseModel> call, Throwable t) {
                Toast.makeText(EditReportActivity.this, "Failed to connect to the server | "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}