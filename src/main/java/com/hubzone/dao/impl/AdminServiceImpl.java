package com.hubzone.dao.impl;

/*
 * This class is for implementation of Admin Service methods
 * 
 * */

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hubzone.dao.AdminService;
import com.hubzone.model.Admin;
import com.hubzone.model.Candidate;
import com.hubzone.model.Employer;
import com.hubzone.model.Users;
//import com.hubzone.utility.EmployerSearch;


@Repository
@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
public class AdminServiceImpl implements AdminService {

	Logger log = Logger.getLogger(AdminServiceImpl.class);

	@PersistenceContext
	private EntityManager entityManager;
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	public void save(Admin admin) {

		entityManager.persist(admin);

	}
	
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	public void delete(String userName) {

		Employer employer=entityManager.find(Employer.class, userName);
		Users users = entityManager.find(Users.class, userName);
		entityManager.remove(employer);
		entityManager.remove(users);

	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	public void deleteCandidate(String userName) {

		Candidate candidate=entityManager.find(Candidate.class, userName);
		Users users = entityManager.find(Users.class, userName);
		entityManager.remove(candidate);
		entityManager.remove(users);

	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	public Users searchCandidateByEmail(String email) {

//		Candidate candidate=entityManager.find(Candidate.class, userName);
//		Users users = entityManager.find(Users.class, userName);
//		entityManager.remove(candidate);
//		entityManager.remove(users);
		String sql="select u from  Users u where u.email=:email";		
		TypedQuery<Users> query=entityManager.createQuery(sql, Users.class);
		query.setParameter("email",email);		
		return query.getSingleResult();
	}
	
//	@Query("SELECT u FROM  Users u WHERE u.email = :email")
//	public Users findUserByEmail(@Param("email") String email);
//	
	
	@Transactional(readOnly = true)
	public Admin getById(String id) {
		Admin admin = entityManager.find(Admin.class, id);
		
		return admin;
	}
	
	
	@Override
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED)
	public void updateAdmin(Admin admin) {
		entityManager.merge(admin);
	}@Override
	public List<Employer> serachEmployer(String employerSearch) {
		log.info("Emp: "+ employerSearch);
		String sql="SELECT c FROM Employer c";
		if(!employerSearch.equals("")) {
			sql += " WHERE UPPER(c.employerID) LIKE '%"+employerSearch.toUpperCase()+"%'";
		}
		
		TypedQuery<Employer> query = entityManager.createQuery(sql, Employer.class);
		return query.getResultList();
	}
	
	public List<Employer> matchEmployer(String employerSearch) {
		log.info("Emp: "+ employerSearch);
		String sql="SELECT c FROM Employer c";
		if(!employerSearch.equals("")) {
			sql += "WHERE UPPER(c.employerID) = '"+employerSearch.toUpperCase()+"'";
		}
		
		TypedQuery<Employer> query = entityManager.createQuery(sql, Employer.class);
		return query.getResultList();
	}
	
	public List<Candidate> serachCandidate(String candidateSearch) {
		log.info("Emp: "+ candidateSearch);
		String sql="SELECT c FROM Candidate c order  by c.lastUpdateDate desc";
		if(!candidateSearch.equals("")) {
			sql += " WHERE UPPER(c.candidateID) LIKE '%"+candidateSearch.toUpperCase()+"%'";
		}
		
		TypedQuery<Candidate> query = entityManager.createQuery(sql, Candidate.class);
		return query.getResultList();
	}
	
	public List<Candidate> serachCandList(String candidateSearch) {
		//log.info("Emp: "+ employerSearch);
		String sql="SELECT c FROM Candidate c";
		if(!candidateSearch.equals("")) {
			sql += " WHERE UPPER(c.candidateID) LIKE '%"+candidateSearch.toUpperCase()+"%'";
		}
		
		TypedQuery<Candidate> query = entityManager.createQuery(sql, Candidate.class);
		return query.getResultList();
	}
	
	//new 
	public List<Candidate> serachCandListEmail(String candidateSearch) {
		//log.info("Emp: "+ employerSearch);
		String sql="SELECT c FROM Candidate c";
		String query1 = " Select u from Users u LEFT JOIN FETCH  u.email c  LEFT JOIN FETCH  u.servicePackage pk where  u.trailPeriod=:trail  and u.enabled=:enabled and u.serviceActive=:sactive and u.paymentActive=:active and u.nextBillDate<=:today ";

		if(!candidateSearch.equals("")) {
			sql += " WHERE UPPER(c.candidateID) LIKE '%"+candidateSearch.toUpperCase()+"%'";
		}
		
		TypedQuery<Candidate> query = entityManager.createQuery(sql, Candidate.class);
		return query.getResultList();
	}
	
	public List<Candidate> matchCandidate(String candidateSearch) {
		log.info("Emp: "+ candidateSearch);
		String sql="SELECT c FROM Candidate c WHERE";
		if(!candidateSearch.equals("")) {
			sql += " UPPER(c.candidateID) = '"+candidateSearch.toUpperCase()+"'";
		}
		
		TypedQuery<Candidate> query = entityManager.createQuery(sql, Candidate.class);
		return query.getResultList();
	}
	
	public List<Users> matchUser(String userSearch) {
		log.info("Emp: "+ userSearch);
		String sql="SELECT c FROM Users c WHERE";
		if(!userSearch.equals("")) {
			sql += " UPPER(c.userName) = '"+userSearch.toUpperCase()+"'";
		}
		
		TypedQuery<Users> query = entityManager.createQuery(sql, Users.class);
		return query.getResultList();
	}
	
	public List<Users> matchUserEmail(String userSearch) {
		log.info("Emp: "+ userSearch);
		String sql="SELECT c FROM Users c WHERE";
		if(!userSearch.equals("")) {
			sql += " UPPER(c.email) = '"+userSearch.toUpperCase()+"'";
		}
		
		TypedQuery<Users> query = entityManager.createQuery(sql, Users.class);
		return query.getResultList();
	}
	
}
