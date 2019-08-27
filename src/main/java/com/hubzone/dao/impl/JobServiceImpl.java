package com.hubzone.dao.impl;

/*
 * This class is for implementation of Job Service methods
 * 
 * */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hubzone.dao.JobService;
import com.hubzone.model.JobCategories;
import com.hubzone.model.Jobs;
import com.hubzone.model.States;
import com.hubzone.utility.JobSearch;

@Repository
@Transactional(readOnly = true)
public class JobServiceImpl implements JobService {
	Logger log = Logger.getLogger(JobServiceImpl.class);

	@PersistenceContext
	private EntityManager em;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void save(Jobs job) {
		em.persist(job);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void update(Jobs job) {
		em.merge(job);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(Long id) {
		Jobs job = em.find(Jobs.class, id);
		em.remove(job);		
	}
	
	public void refresh(Long id) {
		Jobs job = em.find(Jobs.class, id);
		//change
		job.setLastDate(new Date());
		em.merge(job);
	}
	
	public int refreshAllJob(Long id){
		String sql ="SELECT j.days FROM Jobs j WHERE j.jobID =:jobID";
		TypedQuery<Jobs> query = em.createQuery(sql, Jobs.class);
		query.setParameter("jobID", id);
		return 1;
	}
	
	public Jobs getJobById(Long id) {
		String sql ="SELECT j FROM Jobs j WHERE j.jobID =:jobID";
		TypedQuery<Jobs> query = em.createQuery(sql, Jobs.class);
		query.setParameter("jobID", id);
		return query.getSingleResult();
	}
	
	public Jobs getJobDateById(Long id){
		String sql= "SELECT j FROM jobs j WHERE ";
		return null;
	}

	public List<Jobs> jobsByEmployerByOrder(String employerId) {
		String sql = "Select j  from Jobs j where j.employer.employerID=:employerID order  by j.lastDate desc";
		TypedQuery<Jobs> query = em.createQuery(sql, Jobs.class);
		query.setParameter("employerID", employerId);
		return query.getResultList();
	}
	
	public List<Jobs> jobsByEmploye(String employerId) {
		String sql = "Select j  from Jobs j where j.employer.employerID=:employerID";
		TypedQuery<Jobs> query = em.createQuery(sql, Jobs.class);
		query.setParameter("employerID", employerId);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<JobCategories> countJobByCategory() {
		String sql = "Select jc.JobCategoryID ,  jc.JobCategoryName as name, count(j.jobID) numJobs"
				+ " From JobCategories As jc left join (SELECT jobid, JobCategoryName FROM Jobs WHERE lastDate >= :date  )  as j on jc.JobCategoryName =j.JobCategoryName GROUP BY jc.JobCategoryName ";
		log.debug("countJobByCategory1 :" + sql);
		Query query = em.createNativeQuery(sql);
		query.setParameter("date", new Date());
		List<JobCategories> categories = new ArrayList<JobCategories>();
		List<Object[]> results = query.getResultList();
		for (Object result : results) {
			JobCategories cat = new JobCategories();
			Object[] obj = (Object[]) result;
			cat.setJobCategoryID(new Long(obj[0].toString()));
			cat.setJobCategoryName(obj[1].toString());
			cat.setNumJobs(new Integer(obj[2].toString()));
			categories.add(cat);
		}

		return categories;

	}
	@SuppressWarnings("unchecked")
	public List<States> countJobByStates() {
		String sql = "Select st.StateID ,  st.StateName , count(j.jobID) numJobs"
				+ " From States As st left join (SELECT jobid, jobState FROM Jobs WHERE lastDate >= :date  )   as j on st.StateName =j.jobState   GROUP BY st.StateName  ";
		log.debug("countJobByCategory1 :" + sql);
		Query query = em.createNativeQuery(sql);
		query.setParameter("date", new Date());
		List<States> States = new ArrayList<States>();
		
		List<Object[]> results = query.getResultList();
		for (Object result : results) {
			States st = new States();
			Object[] obj = (Object[]) result;
			st.setStateID(new Long(obj[0].toString()));
			st.setStateName(obj[1].toString());
			st.setNumJobs(new Integer(obj[2].toString()));
			States.add(st);
		}

		return States;

	}

	public List<Jobs> getJobListByCategory(JobCategories cat) {

		String sql = "Select j from Jobs j LEFT OUTER JOIN fetch j.employer e where j.jobCategoryName=:cat and j.lastDate >= :date";
		TypedQuery<Jobs> query = em.createQuery(sql, Jobs.class);
		query.setParameter("cat", cat.getJobCategoryName());
		query.setParameter("date", new Date());
		return query.getResultList();

	}

	public List<Jobs> getJobListByState(States state) {

		String sql = "Select j from Jobs j LEFT OUTER JOIN fetch j.employer e where j.jobState=:state and j.lastDate >= :date";
		TypedQuery<Jobs> query = em.createQuery(sql, Jobs.class);
		query.setParameter("state", state.getStateName());
		query.setParameter("date", new Date());
		return query.getResultList();

	}

	public Jobs getJobDetails(Jobs job) {
		job = em.find(Jobs.class, job.getJobID());
		job.getEmployer();
		return job;

	}

	@Override
	public List<Jobs> jobsSearch(JobSearch jobSearch) {

		String sql = "Select j from Jobs j LEFT OUTER JOIN fetch j.employer e where "
				+ " (UPPER(j.jobState) like :state or UPPER(j.jobKeyWord) like :jobKeyWord or   UPPER(j.jobTitle) "
				+ "  like :jobTitle or UPPER(j.jobCategoryName)  "
				+ " like :jobCategoryName  ) and j.lastDate >= :date";
		TypedQuery<Jobs> query = em.createQuery(sql, Jobs.class);
		query.setParameter("date", new Date());
		query.setParameter("state", "%"
				+ jobSearch.getJob().getJobState().toUpperCase() + "%");
		query.setParameter("jobTitle", "%"
				+ jobSearch.getJob().getJobTitle().toUpperCase() + "%");
		query.setParameter("jobKeyWord", "%"
				+ jobSearch.getJob().getJobKeyWord().toUpperCase() + "%");
		query.setParameter("jobCategoryName", "%"
				+ jobSearch.getJob().getJobCategoryName().toUpperCase() + "%");
		return query.getResultList();

	}
	
	@Override
	public List<Jobs> jobAdvanceSearch(JobSearch jobSearch) {

		StringBuilder criteria = new StringBuilder();

//		if (!jobSearch.getJob().getJobState().equals("")) {
//			criteria.append(" UPPER(j.jobState) like :state and");
//		}
		if (!jobSearch.getJob().getJobTitle().equals("")) {

			criteria.append(" UPPER(j.jobTitle)   like :jobTitle");
		}
//		if (!jobSearch.getJob().getJobCategoryName().equals("")) {
//
//			criteria.append(" UPPER(j.jobCategoryName)   like :jobCategoryName and ");
//		}
//		if (!jobSearch.getJob().getJobKeyWord().equals("")) {
//
//			criteria.append(" UPPER(j.jobKeyWord)   like :jobKeyWord and");
//		}
//		String sql = "Select j from Jobs j LEFT OUTER JOIN fetch j.employer e where "
//				+ criteria.toString() + "   j.lastDate >= :date";
		if(!criteria.toString().equals("")){
			log.debug(" criteria: "+criteria);
			criteria.insert(0," Where ");
			log.debug(" criteria: "+criteria);
		}
		String sql="Select j from Jobs j "+criteria.toString();
		TypedQuery<Jobs> query = em.createQuery(sql, Jobs.class);
//		query.setParameter("date", new Date());
		
//		if (!jobSearch.getJob().getJobState().equals("")) {
//			query.setParameter("state", "%"+ jobSearch.getJob().getJobState().toUpperCase() + "%");
//		}
		if (!jobSearch.getJob().getJobTitle().equals("")) {

			query.setParameter("jobTitle", "%"+ jobSearch.getJob().getJobTitle().toUpperCase() + "%");
		}
//		if (!jobSearch.getJob().getJobCategoryName().equals("")) {
//
//			query.setParameter("jobCategoryName", "%"+ jobSearch.getJob().getJobCategoryName().toUpperCase() + "%");
//		}
//		if (!jobSearch.getJob().getJobKeyWord().equals("")) {
//
//			query.setParameter("jobKeyWord", "%"+ jobSearch.getJob().getJobKeyWord().toUpperCase() + "%");
//		}
		return query.getResultList();

	}
	
	@Override
	public List<Jobs> searchJob(JobSearch jobSearch) {
		String sql = "SELECT j FROM Jobs j LEFT OUTER JOIN FETCH j.employer e WHERE";
		if(!jobSearch.getJob().getJobTitle().equals("")) {
			sql += " UPPER(j.jobTitle) LIKE '%"+jobSearch.getJob().getJobTitle().toUpperCase()+"%' AND";
		}
//		if(resumeSearch.getCandidate().getJobStatus() != null && !resumeSearch.getCandidate().getJobStatus().equals("")) {
//			sql += " UPPER(c.jobStatus) LIKE '%"+resumeSearch.getCandidate().getJobStatus().toUpperCase()+"%' AND";
//		}
		if(jobSearch.getJob().getJobDuration() != null && !jobSearch.getJob().getJobDuration().equals("")) {
			sql += " UPPER(j.jobDuration) LIKE '%"+jobSearch.getJob().getJobDuration().toUpperCase()+"%' AND";
		}
		if(!jobSearch.getSearch().equals("")) {
			if(jobSearch.getSearchType().equals("allword")) {		
				String str[] = jobSearch.getSearch().split(",");
				for (int i = 0; i < str.length; i++) {
					sql += " UPPER(j.jobKeyWord) LIKE '%"+str[i].toUpperCase().trim()+"%' AND";
				}
			} else if(jobSearch.getSearchType().equals("anyword")) {
				String str[] = jobSearch.getSearch().split(",");				
				sql += "(";
				for (int i = 0; i < str.length; i++) {				
					sql += " UPPER(j.jobKeyWord) LIKE '%"+str[i].toUpperCase().trim()+"%' OR";
				}
				sql = sql.substring(0, sql.trim().lastIndexOf(" ")); //  remove the last OR
				sql += ")"; // add the closing bracket 
				sql += " AND";// add the AND
			}
		}
		sql += " j.lastDate >= :date";
		log.info(" SQL: "+ sql);
		TypedQuery<Jobs> query = em.createQuery(sql, Jobs.class);
		query.setParameter("date", new Date());		
		return query.getResultList();
	}
}
