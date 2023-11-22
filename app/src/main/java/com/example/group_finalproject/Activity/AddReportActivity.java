package com.example.group_finalproject.Activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.group_finalproject.API.APIRequestPostData;
import com.example.group_finalproject.API.PostRetroServer;
import com.example.group_finalproject.Model.PostResponseModel;
import com.example.group_finalproject.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddReportActivity extends AppCompatActivity {


    private EditText etCategory, etTextReport, etLocation, etAuthor;
    private Button btnAddReport;
    private String postCategory, postTextReport, postLocation, postAuthor;
    String categoryPosition = "";

    TextView selectCategory;
    ArrayList<String> categoryList;
    Dialog categoryDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_report);

        selectCategory = findViewById(R.id.tv_select_category);

        categoryList = new ArrayList<>();
        categoryList.add("Accident");
        categoryList.add("Criminal");
        categoryList.add("Infrastructure");
        categoryList.add("Environment");

        selectCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryDialog = new Dialog(AddReportActivity.this);

                categoryDialog.setContentView(R.layout.category_selection);

                categoryDialog.getWindow().setLayout(700, 800);

                categoryDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                categoryDialog.show();

                EditText etSearchCategory = categoryDialog.findViewById(R.id.et_search_category);
                ListView lvCategory = categoryDialog.findViewById(R.id.list_view_category);

                ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(AddReportActivity.this,
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
                        categoryPosition = selectCategory.getText().toString();
                        selectCategory.setError(null);
                        selectCategory.setTextColor(Color.BLACK);
                        categoryDialog.dismiss();
                    }
                });
            }
        });

//        etCategory = findViewById(R.id.et_category);
        etTextReport = findViewById(R.id.et_text_report);
        etLocation = findViewById(R.id.et_location);
        etAuthor = findViewById(R.id.et_author);

        btnAddReport = findViewById(R.id.btn_add_report);

        btnAddReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postCategory = selectCategory.getText().toString();
                postTextReport = etTextReport.getText().toString();
                postLocation = etLocation.getText().toString();
                postAuthor = etAuthor.getText().toString();

//
//                if(postCategory.trim().equals("")){
//
//                    etCategory.setError("Must input the category");
//
//                }
//                else if(postCategory.trim().equals("Select Category")){
//                    selectCategory.setError("Must select the category");
//
//                }
                if(categoryPosition.trim().equals("")){
                    selectCategory.setError("");
                    selectCategory.setTextColor(Color.RED);
                    selectCategory.setText("Must select the category");
                }
                else if(postTextReport.trim().equals("")){

                    etTextReport.setError("Must input the text report");
//                    selectCategory.setText(categoryAdapter.getItem(position));

                }else if(postLocation.trim().equals("")){

                    etLocation.setError("Must input the location");

                }else if(postAuthor.trim().equals("")){

                    etAuthor.setError("Must input the author");

                }else{
                    createPostData();
                }
            }
        });
    }

    private void createPostData(){
        APIRequestPostData arpdPostData = PostRetroServer.postConnectRetrofit().create(APIRequestPostData.class);
        Call<PostResponseModel> savePostData = arpdPostData.arpdCreatePostData(postAuthor, postCategory, postTextReport, postLocation);

        savePostData.enqueue(new Callback<PostResponseModel>() {
            @Override
            public void onResponse(Call<PostResponseModel> call, Response<PostResponseModel> response) {
                int code = response.body().getCode();
                String message = response.body().getMessage();

                Toast.makeText(AddReportActivity.this, "Code: "+code+" | Message: "+message, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<PostResponseModel> call, Throwable t) {
                Toast.makeText(AddReportActivity.this, "Failed to connect to the server | "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}