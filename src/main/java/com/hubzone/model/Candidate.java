package com.hubzone.model;

/*
 * This class is for  Candidate table in the database
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
@Table(name = "Candidate")
@NamedQueries({
	@NamedQuery(name = "Candidate.findById", query = "SELECT r FROM Candidate r WHERE r.candidateID = :candidateID")
})

public class Candidate 
{
	@Id
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 30)
	@Column(name = "candidateID", nullable = false, length = 30)
	private String candidateID;

	/*
	 @Basic(optional = false)
	 @NotNull
	 @Size(min = 1, max = 30)
	 @Column(name = "password", nullable = true, length = 30)
	 private String password;
	 */

	@Size(min = 1, max = 30)
	@Column(name = "candidateFirstName", nullable = false, length = 30)
	private String candidateFirstName;

	@Size(min = 1, max = 30)
	@Column(name = "candidateLastName", nullable = false, length = 30)
	private String candidateLastName;

	@Size(min = 1, max = 30)
	@Column(name = "candidatePrimaryPhone", nullable = true, length = 30)
	private String candidatePrimaryPhone;

	@Size(max = 30)
	@Column(name = "candidateAltPhone", nullable = true, length = 30)
	private String candidateAltPhone;

	@Size(min = 5, max = 100)
	@Column(name = "address1", nullable = true, length = 100)
	private String address1;

	@Column(name = "address2", nullable = true, length = 100)
	private String address2;

	@Size(min = 1, max = 50)
	@Column(name = "candidateCity", nullable = false, length = 50)
	private String candidateCity;

	@Size(min = 1, max = 50)
	@Column(name = "candidateState", nullable = false, length = 50)
	private String candidateState;

	@Size(min = 1, max = 10)
	@Column(name = "candidateZip", nullable = false, length = 10)
	private String candidateZip;

	@Column(name = "jobCategory", nullable = false, length = 100)
	private String jobCategory; //JobCategory (IT, Human Resources, Finance, Sales, Training, Clerical/Administrative, Manual Laborer,

	@Column(name = "highestEducationLevel", nullable = false, length = 30)
	private String highestEducationLevel = ""; // No Degree, High School/GED, Associates Degree, Bachelors Degree, MastersDegree or higher

	@Column(name = "salaryLevel", nullable = false, length = 30)
	private String salaryLevel; //Negotiable, 0-30K, 30K-50K, 50K-75K, 75K-100K, 100K-125K, 125K-150K, 150K+
	
//	@Column(name = "salaryLevelText", nullable = false, length = 30)
//	private String salaryLevelText; //Negotiable, 0-30K, 30K-50K, 50K-75K, 75K-100K, 100K-125K, 125K-150K, 150K+

	@Column(name = "residentStatus", nullable = false, length = 30)
	private String residentStatus; //List Menu – US Citizen, Green Card/EAD, H1B or Other Work Visa

	//new function
	@Size(max=2147483647)
	@Column(name="candidateDescription",nullable=true, length=2147483647)
	private String candidateDescription;

	@Size(max=100)
	@Column(name="desiredPosition",nullable=true, length=100)
	private String desiredPosition;

	@Size(max=100)
	@Column(name="candidateSkill",nullable=true, length=100)
	private String candidateSkill;
	
	@Size(max=100)
	@Column(name="recentEmployer",nullable=true,length=100)
	private String recentEmployer;
	
	@Size(max=100)
	@Column(name="recentJobTitle",nullable=true,length=100)
	private String recentJobTitle;
		
	@Size(max=2147483647)
	@Column(name="recentJobDescription",nullable=true,length=100)
	private String recentJobDescription;
	
	@Size(max=100)
	@Column(name="jobStatus",nullable=true,length=100)
	private String jobStatus;
	
	@Size(max=100)
	@Column(name="lastSchoolAttended",nullable=true,length=100)
	private String lastSchoolAttended;
	
	//new post date
	@Basic(optional = true)
	@Column(name = "lastUpdateDate")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso=ISO.DATE)
	private Date lastUpdateDate;

	@Basic(optional = true)
	@Column(name = "date_of_birth")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso=ISO.DATE)
	private Date dob;

	@Basic(optional = false)
	@Lob
	@Size(max = 2147483647)
	@Column(name = "message_text", nullable = true, length = 2147483647)
	private String ResumeText;

	//@Null
	@Size(max = 100)
	@Column(name = "resumeFile", nullable = true, length = 100)
	private String resumeFileLocation;

	@Size(max = 50)
	@Column(name = "resumeFileDesc", nullable = true, length = 50)
	private String resumeFileDesc;
	
	@Size(max = 100)
	@Column(name = "resumeContenttype", nullable = true, length = 100)
	private String resumeContenttype;
	
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "candidate")
	private List<JobsApplied> jobsApplied; //1 to Many relationship to Table:Jobs, Field:Job ID 

	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "candidate")
	private Collection<JobsApplied> jobsAppliedCollection;

	public Candidate(){}

	public String getResumeFileDesc() {
		return resumeFileDesc;
	}

	public String getResumeContenttype() {
		return resumeContenttype;
	}

	public void setResumeContenttype(String resumeContenttype) {
		this.resumeContenttype = resumeContenttype;
	}

	public void setResumeFileDesc(String resumeFileDesc) {
		this.resumeFileDesc = resumeFileDesc;
	}

	public Candidate(String candidateID){
		this.candidateID=candidateID;
	}

	public Collection<JobsApplied> getJobsAppliedCollection() {
		return jobsAppliedCollection;
	}

	public void setJobsAppliedCollection(
			Collection<JobsApplied> jobsAppliedCollection) {
		this.jobsAppliedCollection = jobsAppliedCollection;
	}

	public String getCandidateID() {
		return candidateID;
	}

	public void setCandidateID(String candidateID) {
		this.candidateID = candidateID;
	}

	public String getCandidateFirstName() {
		return candidateFirstName;
	}

	public void setCandidateFirstName(String candidateFirstName) {
		this.candidateFirstName = candidateFirstName;
	}

	public String getCandidateLastName() {
		return candidateLastName;
	}

	public void setCandidateLastName(String candidateLastName) {
		this.candidateLastName = candidateLastName;
	}

	public String getCandidatePrimaryPhone() {
		return candidatePrimaryPhone;
	}

	public void setCandidatePrimaryPhone(String candidatePrimaryPhone) {
		this.candidatePrimaryPhone = candidatePrimaryPhone;
	}

	public String getCandidateAltPhone() {
		return candidateAltPhone;
	}

	public void setCandidateAltPhone(String candidateAltPhone) {
		this.candidateAltPhone = candidateAltPhone;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCandidateCity() {
		return candidateCity;
	}

	public void setCandidateCity(String candidateCity) {
		this.candidateCity = candidateCity;
	}

	public String getCandidateState() {
		return candidateState;
	}

	public void setCandidateState(String candidateState) {
		this.candidateState = candidateState;
	}

	public String getCandidateZip() {
		return candidateZip;
	}

	public void setCandidateZip(String candidateZip) {
		this.candidateZip = candidateZip;
	}

	public String getJobCategory() {
		return jobCategory;
	}

	public void setJobCategory(String jobCategory) {
		this.jobCategory = jobCategory;
	}

	public String getHighestEducationLevel() {
		return highestEducationLevel;
	}

	public void setHighestEducationLevel(String highestEducationLevel) {
		this.highestEducationLevel = highestEducationLevel;
	}

	public String getSalaryLevel() {
		return salaryLevel;
	}

	public void setSalaryLevel(String salaryLevel) {
		this.salaryLevel = salaryLevel;
	}

	public String getResidentStatus() {
		return residentStatus;
	}

	public void setResidentStatus(String residentStatus) {
		this.residentStatus = residentStatus;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public List<JobsApplied> getJobsApplied() {
		return jobsApplied;
	}

	public void setJobsApplied(List<JobsApplied> jobsApplied) {
		this.jobsApplied = jobsApplied;
	}

	public String getResumeText() {
		return ResumeText;
	}

	public void setResumeText(String resumeText) {
		ResumeText = resumeText;
	}

	public String getResumeFileLocation() {
		return resumeFileLocation;
	}

	public void setResumeFileLocation(String resumeFileLocation) {
		this.resumeFileLocation = resumeFileLocation;
	}
	public String getCandidateDescription() {
		return candidateDescription;
	}
	public void setCandidateDescription(String candidateDescription) {
		this.candidateDescription = candidateDescription;
	}
	public String getDesiredPosition() {
		return desiredPosition;
	}
	public void setDesiredPosition(String desiredPosition) {
		this.desiredPosition = desiredPosition;
	}
	public String getCandidateSkill() {
		return candidateSkill;
	}
	public void setCandidateSkill(String candidateSkill) {
		this.candidateSkill = candidateSkill;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getRecentEmployer() {
		return recentEmployer;
	}

	public void setRecentEmployer(String recentEmployer) {
		this.recentEmployer = recentEmployer;
	}

	public String getRecentJobTitle() {
		return recentJobTitle;
	}

	public void setRecentJobTitle(String recentJobTitle) {
		this.recentJobTitle = recentJobTitle;
	}

	public String getRecentJobDescription() {
		return recentJobDescription;
	}

	public void setRecentJobDescription(String recentJobDescription) {
		this.recentJobDescription = recentJobDescription;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getLastSchoolAttended() {
		return lastSchoolAttended;
	}

	public void setLastSchoolAttended(String lastSchoolAttended) {
		this.lastSchoolAttended = lastSchoolAttended;
	}

//	public String getSalaryLevelText() {
//		return salaryLevelText;
//	}
//
//	public void setSalaryLevelText(String salaryLevelText) {
//		this.salaryLevelText = salaryLevelText;
//	} 

}
