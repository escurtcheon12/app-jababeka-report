package com.example.group_finalproject.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostRetroServer {
//    private static final String postBaseUrl = "http://10.0.2.2/php_project/President%20University/Semester%204/Final%20Project%20MAD/jababeka_report/";
    private static final String postBaseUrl = "http://10.0.2.2:4000";
    private static Retrofit postRetro;

    public static Retrofit postConnectRetrofit(){
        if(postRetro == null){
            postRetro = new Retrofit.Builder()
                    .baseUrl(postBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return postRetro;
    }
}
