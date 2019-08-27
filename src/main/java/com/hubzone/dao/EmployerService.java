package com.hubzone.dao;

/*
 * This class is for interface of Employer Services
 * 
 * */

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hubzone.model.Candidate;
import com.hubzone.model.Employer;
import com.hubzone.model.Jobs;
import com.hubzone.utility.ResumeSearch;
import com.hubzone.utility.ResumeSearchResult;

public interface EmployerService {
	public void save(Employer employer);
	public void updateEmployer(Employer employer);
	public void removeEmployer(Employer employer);
	public List<Jobs> getJobList(Employer employer);
	public Employer getById(String id);
	public List<Candidate> serachResume(ResumeSearch resumeSearch,HttpServletRequest request);
}
