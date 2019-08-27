package com.hubzone.dao.impl;

/*
 * This class is for implementation of job Applied Service methods
 * 
 * */

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hubzone.dao.JobCategoriesService;
import com.hubzone.model.JobCategories;


@Repository
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class JobCategoriesServiceImpl implements JobCategoriesService {
	Logger log = Logger.getLogger(JobCategoriesServiceImpl.class);

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveJobCategorie(JobCategories jobCat) {
		em.persist(jobCat);

	}

	@Override
	public List<JobCategories> getJobCategories() {
		String sql = "Select jc from JobCategories jc";
		TypedQuery<JobCategories> query = em.createQuery(sql,
				JobCategories.class);

		return query.getResultList();

	}

}
