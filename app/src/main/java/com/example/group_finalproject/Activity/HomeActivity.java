package com.example.group_finalproject.Activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.se.omapi.Session;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group_finalproject.API.APIRequestPostData;
import com.example.group_finalproject.API.PostRetroServer;
import com.example.group_finalproject.Adapter.AdapterPostData;
import com.example.group_finalproject.Model.PostDataModel;
import com.example.group_finalproject.Model.PostResponseModel;
import com.example.group_finalproject.R;
import com.example.group_finalproject.Session.SessionManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    SessionManager sessionManagerHome;
    TextView tvUserName;
    String loggedUserName;
    ImageView btnProfile;

    private RecyclerView postData;
    private RecyclerView.Adapter adPostData;
    private RecyclerView.LayoutManager lmPostData;
    private List<PostDataModel> listPostData = new ArrayList<>();
    private SwipeRefreshLayout srlPostData;
    private ProgressBar pbPostData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home2);

        btnProfile = findViewById(R.id.btn_profile);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
            }
        });

        sessionManagerHome = new SessionManager(HomeActivity.this);

        tvUserName = findViewById(R.id.tv_home_username);
        loggedUserName = sessionManagerHome.getUserDetail().get(sessionManagerHome.USERNAME);
        tvUserName.setText(loggedUserName);

        Button addReportBtn = (Button) findViewById(R.id.btn_add_report);
        addReportBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                Intent myIntent = new Intent(view.getContext(), AddReportActivity.class);
//                startActivityForResult(myIntent, 0);
                startActivity(new Intent(HomeActivity.this, AddReportActivity.class));
            }

        });

        srlPostData = findViewById(R.id.srl_post_data);
        pbPostData = findViewById(R.id.pb_post_data);

        postData = findViewById(R.id.list_post);
        lmPostData = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        postData.setLayoutManager(lmPostData);

        Spinner selectReport = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.select_report, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectReport.setAdapter(adapter);
        selectReport.setOnItemSelectedListener(this);

        //retrievePostData();

        srlPostData.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlPostData.setRefreshing(true);
                retrievePostData();
                srlPostData.setRefreshing(false);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        retrievePostData();
    }

    public void retrievePostData(){
        pbPostData.setVisibility(View.VISIBLE);

        try {
            APIRequestPostData arpdPostData = PostRetroServer.postConnectRetrofit().create(APIRequestPostData.class);
            Call<PostResponseModel> showPostData = arpdPostData.arpdRetrievePostData();

            showPostData.enqueue(new Callback<PostResponseModel>() {
                @Override
                public void onResponse(Call<PostResponseModel> call, Response<PostResponseModel> response) {
                    int code = response.body().getCode();
                    String message = response.body().getMessage();

//                Toast.makeText(HomeActivity.this, "Code: "+code+" | Message: "+message, Toast.LENGTH_SHORT).show();
                    listPostData = response.body().getPostData();
                    Log.d("drax", "result" + listPostData);

                    adPostData = new AdapterPostData(HomeActivity.this, listPostData);
                    postData.setAdapter(adPostData);
                    adPostData.notifyDataSetChanged();

                    pbPostData.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<PostResponseModel> call, Throwable t) {
                    Log.e("API Request Failed", t.getMessage());
                    Toast.makeText(HomeActivity.this, "Failed to connect to the server : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    pbPostData.setVisibility(View.INVISIBLE);
                }
            });

            } catch (HttpException err2) {
            Log.e("RetrofitError", err2.getMessage());
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedReport = parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(), selectedReport, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
