package com.hubzone.model;

/*
 * This class is for  Admin table in the database
 * 
 * */

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name = "Admin")
@NamedQueries({
	@NamedQuery(name = "Admin.findById", query = "SELECT r FROM Admin r WHERE r.adminID = :adminID")
})

public class Admin {
	
	@Id
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 30)
	@Column(name = "adminID", nullable = false, length = 30)
	private String adminID;

	/*
	 @Basic(optional = false)
	 @NotNull
	 @Size(min = 1, max = 30)
	 @Column(name = "password", nullable = true, length = 30)
	 private String password;
	 */

	@Size(min = 1, max = 30)
	@Column(name = "adminFirstName", nullable = false, length = 30)
	private String adminFirstName;

	@Size(min = 1, max = 30)
	@Column(name = "adminLastName", nullable = false, length = 30)
	private String adminLastName;

	public String getAdminID() {
		return adminID;
	}

	public void setAdminID(String adminID) {
		this.adminID = adminID;
	}

	public String getAdminFirstName() {
		return adminFirstName;
	}

	public void setAdminFirstName(String adminFirstName) {
		this.adminFirstName = adminFirstName;
	}

	public String getAdminLastName() {
		return adminLastName;
	}

	public void setAdminLastName(String adminLastName) {
		this.adminLastName = adminLastName;
	}

}
