package com.hubzone.dao.impl;

/*
 * This class is for implementation of Candidate Service methods
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

import com.hubzone.dao.CandidateService;
import com.hubzone.model.Candidate;

import com.hubzone.model.JobsApplied;

@Repository
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class CandidateServiceImpl implements CandidateService {
	Logger log = Logger.getLogger(CandidateServiceImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void save(Candidate candidate) {

		entityManager.persist(candidate);

	}

	// public void update(Candidate candidate) {
	//
	// entityManager.merge(candidate);
	//
	// }

	public void removeEmployer(Candidate candidate) {
		entityManager.remove(candidate);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<JobsApplied> getJobList(Candidate candidate) {

		String sql = "Select j from JobsApplied j LEFT OUTER JOIN fetch j.job jo  LEFT OUTER JOIN fetch j.candidate ca     where ca.candidateID=:candidateID";
		TypedQuery<JobsApplied> query = entityManager.createQuery(sql,
				JobsApplied.class);
		query.setParameter("candidateID", candidate.getCandidateID());
		return query.getResultList();

	}

	@Transactional(readOnly = true)
	public List<Candidate> getCandidateListByNames(List<String> candList) {
		StringBuilder cands = new StringBuilder();
		for (String cand : candList) {
			cands.append(cand + " , ");
		}
		String cands1 = cands.substring(0, cands.length() - 1);
		String queryStr = "SELECT c FROM Candidate c where c.candidateID in :candidateIDList";
		TypedQuery<Candidate> query = entityManager.createQuery(queryStr,
				Candidate.class);
		
		query.setParameter("candidateIDList", candList);
		return query.getResultList();
	}

	@Transactional(readOnly = true)
	public Candidate getById(String id) {

		Candidate candidate = entityManager.find(Candidate.class, id);

		return candidate;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateCandidate(Candidate candidate) {
		entityManager.merge(candidate);
	}
}