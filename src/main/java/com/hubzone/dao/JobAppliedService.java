package com.hubzone.dao;

/*
 * This class is for interface of Job Apply Services
 * 
 * */

import java.util.List;

import com.hubzone.model.Candidate;
import com.hubzone.model.Jobs;
import com.hubzone.model.JobsApplied;

public interface JobAppliedService {
	public int appliedJobCount(long jobID);
	public List<JobsApplied> candList(long JobID);
	public void save(JobsApplied jobApplied);

}
