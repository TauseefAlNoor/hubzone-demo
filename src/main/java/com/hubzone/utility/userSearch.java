package com.hubzone.utility;

/*
 * This class is for  handling user search info
 * 
 * */


import com.hubzone.model.Users;

public class userSearch {
	
	private Users user;
	private String userName;
	private String email;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
