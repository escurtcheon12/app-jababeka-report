package com.example.group_finalproject.Model.Register;

import com.google.gson.annotations.SerializedName;

public class RegisterData {

	@SerializedName("user_email")
	private String userEmail;

	@SerializedName("user_name")
	private String userName;

	public void setUserEmail(String userEmail){
		this.userEmail = userEmail;
	}

	public String getUserEmail(){
		return userEmail;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}
}