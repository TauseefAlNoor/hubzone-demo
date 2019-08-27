package com.hubzone.model;

/*
 * This class is for  Employer table in the database
 * 
 * */

import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Employer")
public class Employer {
	
	@Id	
	@Basic(optional = false)	
	@Column(name = "employerID")
	private String employerID;

//	@Size(min = 1, max = 50)
	@Column(name = "subscriptionLevel", nullable = true , length = 50)
	private String subscriptionLevel;
	
	//new
//	@Size(min = 1, max = 10)
	@Column(name = "currentStatus", nullable = true , length = 50)
	private String currentStatus;
	
//	@Size(min = 1, max = 255)
	@Column(name = "CompanyName", nullable = false, length = 255)
	private String companyName;
	
	//@NotNull
//	@Size(min = 1, max = 255)
	@Column(name = "CompanyStreetAddress1", nullable = true, length = 255)
	private String companyStreetAddress1;
	
	@Column(name = "CompanyStreetAddress2", nullable = true, length = 255)
	private String companyStreetAddress2;
	
//	@Size(min = 1, max = 50)
	@Column(name = "CompanyCity", nullable = true, length = 50)
	private String companyCity;

//	@Size(min = 1, max = 50)
	@Column(name = "CompanyState ", nullable = true, length = 50)
	private String companyState;
	
//	@Size(min = 1, max = 50)
	@Column(name = "CompanyZip", nullable = true, length = 50)
	private String companyZip;

//	@Size(min = 1, max = 255)
	@Column(name = "POCFirstName", nullable = true, length = 255)
	private String pocFirstName;
	
//	@Size(min = 1, max = 255)
	@Column(name = "POCLastName", nullable = true, length = 255)
	private String pocLastName;
	
//	@Size(min = 1, max = 255)
	@Column(name = "POCEmail", nullable = true, length = 255)
	private String pocEmail;
	
//	@Size(min = 1, max = 255)
	@Column(name = "POCPrimaryPhone", nullable = true, length = 255)
	private String pocPrimaryPhone;

	@Column(name = "POCSecondaryPhone ", nullable = true, length = 255)
	private String pocSecondaryPhone;
	
	@Column(name = "jobCountLimit", nullable = false, length = 2)
	private Integer jobCountLimit;
		
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "employer")
    private Collection<Jobs> jobsCollection;
    
    
    public Employer(){}
	
	public Employer(String employerID){
		this.employerID=employerID;
	}

	public Collection<Jobs> getJobsCollection() {
		return jobsCollection;
	}
	public void setJobsCollection(Collection<Jobs> jobsCollection) {
		this.jobsCollection = jobsCollection;
	}
	public String getSubscriptionLevel() {
		return subscriptionLevel;
	}
	public void setSubscriptionLevel(String subscriptionLevel) {
		this.subscriptionLevel = subscriptionLevel;
	}
	public String getEmployerID() {
		return employerID;
	}
	public void setEmployerID(String employerID) {
		this.employerID = employerID;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyStreetAddress1() {
		return companyStreetAddress1;
	}
	public void setCompanyStreetAddress1(String companyStreetAddress1) {
		this.companyStreetAddress1 = companyStreetAddress1;
	}
	public String getCompanyStreetAddress2() {
		return companyStreetAddress2;
	}
	public void setCompanyStreetAddress2(String companyStreetAddress2) {
		this.companyStreetAddress2 = companyStreetAddress2;
	}
	public String getCompanyCity() {
		return companyCity;
	}
	public void setCompanyCity(String companyCity) {
		this.companyCity = companyCity;
	}
	public String getCompanyState() {
		return companyState;
	}
	public void setCompanyState(String companyState) {
		this.companyState = companyState;
	}
	public String getCompanyZip() {
		return companyZip;
	}
	public void setCompanyZip(String companyZip) {
		this.companyZip = companyZip;
	}
	public String getPocFirstName() {
		return pocFirstName;
	}
	public void setPocFirstName(String pocFirstName) {
		this.pocFirstName = pocFirstName;
	}
	public String getPocLastName() {
		return pocLastName;
	}
	public void setPocLastName(String pocLastName) {
		this.pocLastName = pocLastName;
	}
	public String getPocEmail() {
		return pocEmail;
	}
	public void setPocEmail(String pocEmail) {
		this.pocEmail = pocEmail;
	}
	public String getPocPrimaryPhone() {
		return pocPrimaryPhone;
	}
	public void setPocPrimaryPhone(String pocPrimaryPhone) {
		this.pocPrimaryPhone = pocPrimaryPhone;
	}
	public String getPocSecondaryPhone() {
		return pocSecondaryPhone;
	}
	public void setPocSecondaryPhone(String pocSecondaryPhone) {
		this.pocSecondaryPhone = pocSecondaryPhone;
	}
	public Integer getJobCountLimit() {
		return jobCountLimit;
	}
	public void setJobCountLimit(Integer jobCountLimit) {
		this.jobCountLimit = jobCountLimit;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
}
