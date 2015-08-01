package com.amey.appdirect.dataBeans;

import org.genericdao.PrimaryKey;

@PrimaryKey("email")
public class UserBean{

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	String firstName; 
	String email;
	
	
	String openId;
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	String creator;

		public UserBean() {
			super();
		}

	
	
}
