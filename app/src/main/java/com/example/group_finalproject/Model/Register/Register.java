package com.example.group_finalproject.Model.Register;

import com.google.gson.annotations.SerializedName;

public class Register{

	@SerializedName("code")
	private int code;

	@SerializedName("data")
	private RegisterData registerData;

	@SerializedName("message")
	private String message;

	public void setCode(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public void setData(RegisterData registerData){
		this.registerData = registerData;
	}

	public RegisterData getData(){
		return registerData;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}