package com.hubzone.utility;

/*
 * This class is for  employer search information handling
 * 
 * */
import com.hubzone.model.Employer;

public class EmployerSearch {
	
	private Employer employer;
	private String employerID;
	public Employer getEmployer() {
		return employer;
	}
	public void setEmployer(Employer employer) {
		this.employer = employer;
	}
	public String getEmployerID() {
		return employerID;
	}
	public void setEmployerID(String employerID) {
		this.employerID = employerID;
	}

}
