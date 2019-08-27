package com.hubzone.dao;

/*
 * This class is for interface of Candidate Services
 * 
 * */

import java.util.List;

import com.hubzone.model.Candidate;
//import com.hubzone.model.Employer;
import com.hubzone.model.JobsApplied;

public interface CandidateService {
	
	public void save(Candidate candidate) ;
	public List<JobsApplied> getJobList(Candidate candidate);
	Candidate getById(String id);
	public void updateCandidate(Candidate candidate);
	public List<Candidate> getCandidateListByNames(List<String> candList);
}
