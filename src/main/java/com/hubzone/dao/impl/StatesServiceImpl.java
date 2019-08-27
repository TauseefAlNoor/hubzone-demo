package com.hubzone.dao.impl;

/*
 * This class is for implementation of State Service methods
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

import com.hubzone.dao.StatesService;

import com.hubzone.model.States;

@Repository
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class StatesServiceImpl implements StatesService {
	Logger log = Logger.getLogger(StatesServiceImpl.class);

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveState(States state) {
		em.persist(state);
	}

	@Override
	public List<States> getStateList() {
		String sql = "Select s from States s";
		TypedQuery<States> query = em.createQuery(sql, States.class);

		return query.getResultList();

	}

}
