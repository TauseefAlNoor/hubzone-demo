package com.hubzone.dao;

/*
 * This class is for interface for job categories service
 * 
 * */

import java.util.List;

import com.hubzone.model.JobCategories;

public interface JobCategoriesService {
public void saveJobCategorie(JobCategories jobCat);
public List<JobCategories> getJobCategories();
}
