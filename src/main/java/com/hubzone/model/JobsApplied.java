package com.hubzone.model;

/*
 * This class is for  JobsApplied table in the database
 * 
 * */

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "JobsApplied")
public class JobsApplied {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	//@NotNull
	@Column(name = "jobsApplied", nullable = false)
	private Long jobsApplied;

	@NotNull
	@JoinColumn(name = "candidateID", referencedColumnName = "candidateID", nullable = false)
	@ManyToOne(optional = false)
	private Candidate candidate;

	@NotNull
	@JoinColumn(name = "jobID", referencedColumnName = "jobID", nullable = false)
	@ManyToOne(optional = false)
	private Jobs job;

	@Column(name = "ApplyDate")
	private Date applyDate;

	
	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Long getJobsApplied() {
		return jobsApplied;
	}

	public void setJobsApplied(Long jobsApplied) {
		this.jobsApplied = jobsApplied;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public Jobs getJob() {
		return job;
	}

	public void setJob(Jobs job) {
		this.job = job;
	}

}
