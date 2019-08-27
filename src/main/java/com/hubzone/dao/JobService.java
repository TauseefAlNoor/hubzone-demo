package com.hubzone.dao;

/*
 * This class is for interface of Job Services
 * 
 * */

import java.util.List;

import com.hubzone.model.JobCategories;
import com.hubzone.model.Jobs;
import com.hubzone.model.States;
import com.hubzone.utility.JobSearch;

public interface JobService {
	public void save(Jobs job);
	public void update(Jobs job);
	public void delete(Long id);
	public void refresh(Long id);
	public int refreshAllJob(Long id);
	public Jobs getJobById(Long id);
	public Jobs getJobDateById(Long id);
	List<JobCategories> countJobByCategory();
	public List<States> countJobByStates();
	public List<Jobs> getJobListByCategory(JobCategories cat);
	public List<Jobs> getJobListByState( States state);
	public  Jobs getJobDetails(Jobs job);
	public List<Jobs> jobsByEmploye(String employerId);
	public List<Jobs> jobsSearch(JobSearch job);
	List<Jobs> jobAdvanceSearch(JobSearch jobSearch);
	public List<Jobs> searchJob(JobSearch jobSearch);
	public List<Jobs> jobsByEmployerByOrder(String employerId);
}
