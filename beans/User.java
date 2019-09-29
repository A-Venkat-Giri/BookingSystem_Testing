package com.bbs.beans;

public class User {
 private int userId;
 private String userName;
 private String email;
 private String password;
 private long contact;
 
 
 
public int getUserId() {
	return userId;
}
public void setUserId(int userId) {
	this.userId = userId;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}

public long getContact() {
	return contact;
}
public void setContact(long contact) {
	this.contact = contact;
}
@Override
public String toString() {
	return "User [userId=" + userId + ", userName=" + userName + ", email=" + email + ", contact=" + contact + "]";
}


 
 
 
}
