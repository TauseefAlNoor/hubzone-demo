/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hubzone.model;

/*
 * This class is for  Users table in the database
 * 
 * */

import java.io.Serializable;


import javax.persistence.*;

import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author mohammadmoinuddin
 * 
 */
@Entity()
@Table(name="Users", 
uniqueConstraints=@UniqueConstraint(columnNames={"email"}))
public class Users implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	//@NotNull
	@Size(min = 1, max = 30)
	@Column(name = "user_name", nullable = false, length = 30)
	private String userName;

	@Basic(optional = false)
	//@NotNull
	@Size(min = 1, max = 256)
	@Column(name = "password", nullable = false, length = 256)
	private String password;

	@Basic(optional = false)
	//@NotNull
	@Size(min = 1, max = 20)
	@Column(name = "role", nullable = false, length = 20)
	private String role;

	//@NotNull
	@Column(name = "enabled", nullable = false, length = 2)
	private Integer enabled;

	@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Invalid email")
	// if the field contains email address consider using this annotation to
	// enforce field validation
	@Basic(optional = false)
	//@NotNull
	@Size(min = 1, max = 100)
	@Column(name = "email", nullable = false, length = 100)
	private String email;

	@Size(max = 100)
	@Column(name = "verificationCode", nullable = true, length = 100)
	private String verificationCode;
	
	public Users(String username, String password){
		super();
		this.userName = username;
		this.password = password;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public Users() {
	}

	public Users(String userName) {
		this.userName = userName;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (userName != null ? userName.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Users)) {
			return false;
		}
		Users other = (Users) object;
		if ((this.userName == null && other.userName != null)
				|| (this.userName != null && !this.userName
						.equals(other.userName))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.ride.ws.model.Users[ userName=" + userName + " ]";
	}

}
