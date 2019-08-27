package com.hubzone.model;

/*
 * This class is for  Job Categories table in the database
 * 
 * */


import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "JobCategories")
public class JobCategories {
	public JobCategories(){
		
	}
	public JobCategories(String cat){
		this.jobCategoryName=cat;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)	
	@Column(name = "JobCategoryID",  nullable=false)
	private Long jobCategoryID;
	
	@NotNull
	@Size(min = 4, max = 50)
	@Column(name = "JobCategoryName", unique=true, nullable=false, length = 50)
	private String jobCategoryName;

	@javax.persistence.Transient
	@Column(name = "numJobs")
	private Integer numJobs;
	
	
	public Long getJobCategoryID() {
		return jobCategoryID;
	}

	public void setJobCategoryID(Long jobCategoryID) {
		this.jobCategoryID = jobCategoryID;
	}

	public Integer getNumJobs() {
		return numJobs;
	}

	public void setNumJobs(Integer numJobs) {
		this.numJobs = numJobs;
	}

	public String getJobCategoryName() {
		return jobCategoryName;
	}

	public void setJobCategoryName(String jobCategoryName) {
		this.jobCategoryName = jobCategoryName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((jobCategoryID == null) ? 0 : jobCategoryID.hashCode());
		result = prime * result
				+ ((jobCategoryName == null) ? 0 : jobCategoryName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JobCategories other = (JobCategories) obj;
		if (jobCategoryID == null) {
			if (other.jobCategoryID != null)
				return false;
		} else if (!jobCategoryID.equals(other.jobCategoryID))
			return false;
		if (jobCategoryName == null) {
			if (other.jobCategoryName != null)
				return false;
		} else if (!jobCategoryName.equals(other.jobCategoryName))
			return false;
		return true;
	}
	
	
	
	
	
}
