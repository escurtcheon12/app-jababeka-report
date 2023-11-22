package com.example.group_finalproject.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group_finalproject.API.APIRequestPostData;
import com.example.group_finalproject.API.PostRetroServer;
import com.example.group_finalproject.Activity.EditReportActivity;
import com.example.group_finalproject.Activity.HomeActivity;
import com.example.group_finalproject.Model.PostDataModel;
import com.example.group_finalproject.Model.PostResponseModel;
import com.example.group_finalproject.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterPostData extends RecyclerView.Adapter<AdapterPostData.HolderPostData> {
    private Context postCtx;
    private List<PostDataModel> listPost;
    private List<PostDataModel> selectedPost;

    private int selectedPostId;

    public AdapterPostData(Context postCtx, List<PostDataModel> listPost) {
        this.postCtx = postCtx;
        this.listPost = listPost;
    }

    @NonNull
    @Override
    public HolderPostData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View postLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        HolderPostData postHolder = new HolderPostData(postLayout);
        return postHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderPostData holder, int position) {
        PostDataModel pdm = listPost.get(position);

        holder.postId.setText(String.valueOf(pdm.getPost_id()));
        holder.postDate.setText(String.valueOf(pdm.getPost_date()));
        holder.postAuthor.setText(pdm.getPost_author());
        holder.postCategory.setText(pdm.getPost_category());
        holder.postTextReport.setText(pdm.getPost_text_report());
        holder.postLocation.setText(pdm.getPost_location());

    }

    @Override
    public int getItemCount() {
        return listPost.size();
    }

    public class HolderPostData extends RecyclerView.ViewHolder {
        TextView postId, postDate, postAuthor, postCategory, postTextReport, postLocation;


        public HolderPostData(@NonNull View itemView) {
            super(itemView);

            postId = itemView.findViewById(R.id.postId);
            postDate = itemView.findViewById(R.id.postDate);
            postAuthor = itemView.findViewById(R.id.postAuthor);
            postCategory = itemView.findViewById(R.id.postCategory);
            postTextReport = itemView.findViewById(R.id.postTextReport);
            postLocation = itemView.findViewById(R.id.postLocation);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder postChooseOption = new AlertDialog.Builder(postCtx);
                    postChooseOption.setMessage("What would you like to do?");
                    postChooseOption.setCancelable(true);

                    selectedPostId = Integer.parseInt(postId.getText().toString());

                    postChooseOption.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deletePostData();
                            dialog.dismiss();

                            Handler postHandler = new Handler();
                            postHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ((HomeActivity) postCtx).retrievePostData();
                                }
                            }, 200);

//                            ((HomeActivity) postCtx).retrievePostData();
                        }
                    });

                    postChooseOption.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getPostData();
                            dialog.dismiss();
                        }
                    });

                    postChooseOption.show();

                    return false;
                }
            });
        }

        private void deletePostData(){

            APIRequestPostData arpdPostData = PostRetroServer.postConnectRetrofit().create(APIRequestPostData.class);
            Call<PostResponseModel> deleteData = arpdPostData.arpdDeletePostData(selectedPostId);

            deleteData.enqueue(new Callback<PostResponseModel>() {
                @Override
                public void onResponse(Call<PostResponseModel> call, Response<PostResponseModel> response) {
                    int code = response.body().getCode();
                    String message = response.body().getMessage();

                    Toast.makeText(postCtx, "Code: "+code+" | Message: "+message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<PostResponseModel> call, Throwable t) {
                    Toast.makeText(postCtx, "Failed to connect to the server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void getPostData(){
            APIRequestPostData arpdPostData = PostRetroServer.postConnectRetrofit().create(APIRequestPostData.class);
            Call<PostResponseModel> getSelectedPostData = arpdPostData.arpdGetPostData(selectedPostId);

            getSelectedPostData.enqueue(new Callback<PostResponseModel>() {
                @Override
                public void onResponse(Call<PostResponseModel> call, Response<PostResponseModel> response) {
                    int code = response.body().getCode();
                    String message = response.body().getMessage();
                    selectedPost = response.body().getPostData();

                    //.get(0) itu berarti get data pertama. Karena memang kita pas select where id =.... itu cuma ada 1 data
                    int varPostId = selectedPost.get(0).getPost_id();
                    String varPostAuthor = selectedPost.get(0).getPost_author();
                    String varPostCategory = selectedPost.get(0).getPost_category();
                    String varPostTextReport = selectedPost.get(0).getPost_text_report();
                    String varPostLocation = selectedPost.get(0).getPost_location();

                    //Toast.makeText(postCtx, "Code: "+code+" | Message: "+message+" | Data: "+varPostId+", "+varPostAuthor+", "+varPostCategory+", "+varPostTextReport+", "+varPostLocation , Toast.LENGTH_SHORT).show();
                    Intent sendSelectedPostData = new Intent(postCtx, EditReportActivity.class);
                    sendSelectedPostData.putExtra("sentPostId", varPostId);
                    sendSelectedPostData.putExtra("sentPostAuthor", varPostAuthor);
                    sendSelectedPostData.putExtra("sentPostCategory", varPostCategory);
                    sendSelectedPostData.putExtra("sentPostTextReport", varPostTextReport);
                    sendSelectedPostData.putExtra("sentPostLocation", varPostLocation);
                    postCtx.startActivity(sendSelectedPostData);
                }

                @Override
                public void onFailure(Call<PostResponseModel> call, Throwable t) {
                    Toast.makeText(postCtx, "Failed to connect to the server : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
