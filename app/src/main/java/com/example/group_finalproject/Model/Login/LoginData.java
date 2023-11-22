package com.example.group_finalproject.Model.Login;

import com.google.gson.annotations.SerializedName;

public class LoginData {

	@SerializedName("user_email")
	private String userEmail;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("user_name")
	private String userName;

	public void setUserEmail(String userEmail){
		this.userEmail = userEmail;
	}

	public String getUserEmail(){
		return userEmail;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}
}