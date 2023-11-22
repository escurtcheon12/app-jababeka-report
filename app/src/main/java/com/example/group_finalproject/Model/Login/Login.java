package com.example.group_finalproject.Model.Login;

import com.google.gson.annotations.SerializedName;

public class Login{

	@SerializedName("code")
	private int code;

	@SerializedName("data")
	private LoginData loginData;

	@SerializedName("message")
	private String message;

	public void setCode(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public void setData(LoginData loginData){
		this.loginData = loginData;
	}

	public LoginData getLoginData(){
		return loginData;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}