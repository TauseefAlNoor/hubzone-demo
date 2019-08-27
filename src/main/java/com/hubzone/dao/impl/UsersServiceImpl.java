package com.hubzone.dao.impl;

/*
 * This class is for implementation of User Service methods
 * 
 * */

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hubzone.dao.UsersService;
import com.hubzone.model.Candidate;
import com.hubzone.model.Jobs;
import com.hubzone.model.Users;

@Repository
@Transactional(readOnly=false,  propagation = Propagation.REQUIRED)
public class UsersServiceImpl implements UsersService {
	Logger log = Logger.getLogger(UsersServiceImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	public void saveUser(Users user) {
		log.debug("Saving user"+user.getUserName());
		entityManager.persist(user);

	}

	@Override	
	public void updateUser(Users user) {
		log.debug("update user"+user.getUserName());
		entityManager.merge(user);

	}
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	public void removeUser(Users user) {
		
		log.debug("retriving user");
		Users u=entityManager.find(Users.class, user.getUserName());
		u.setPassword("123");
		log.debug("removing user  :"+u.getPassword());
		entityManager.remove(u);
		log.debug("removed user  :"+user.getUserName());
		
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED)
	public String checkUser(String userID){
		
		String sql="select u from  Users u where u.userName=:userID";
		TypedQuery<Users> query=entityManager.createQuery(sql, Users.class);
		query.setParameter("userID",userID);	
		System.out.println("Result in Role is :"+query.getSingleResult());
		Users user=query.getSingleResult();
		return user.getRole();
		//return null;
		
	}
	
	@Transactional(readOnly = true)
	public Users getById(String id) {
		Users user = entityManager.find(Users.class, id);
		
		return user;
	}

	@Override
	public Users getUserByName(String username) {
		
		return entityManager.find(Users.class, username);
	}

	@Override
	public Users getUserByEmail() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Users getUserByEmail(String email) {
		String sql="select u from  Users u where u.email=:email";		
		TypedQuery<Users> query=entityManager.createQuery(sql, Users.class);
		query.setParameter("email",email);		
		return query.getSingleResult();
	}
	
	public Users getEmailById(String id){
		return entityManager.find(Users.class, id);
	}
	
	
	public  List<Users> getCandidateEmailList(){
		//Users user=null;
		String roleName="ROLE_CAN";
		//change
		String sql= "SELECT u FROM Users u WHERE u.role LIKE '%"+roleName+"%'";
		TypedQuery<Users> query = entityManager.createQuery(sql, Users.class);
		//query.setParameter("role", user.getRole());
		return query.getResultList();
	}
	
	public  List<Users> getPasswordList(){
		//Users user=null;
		String roleName="ROLE_CAN";
		String roleName1="ROLE_EMP";
		//change
		String sql= "SELECT u FROM Users u WHERE u.role LIKE '%"+roleName+"%'";
		TypedQuery<Users> query = entityManager.createQuery(sql, Users.class);
		//query.setParameter("role", user.getRole());
		return query.getResultList();
	}
	
	public  List<Users> getPasswordListEmp(){
		//Users user=null;
		String roleName="ROLE_CAN";
		String roleName1="ROLE_EMP";
		//change
		String sql= "SELECT u FROM Users u WHERE u.role LIKE '%"+roleName1+"%'";
		TypedQuery<Users> query = entityManager.createQuery(sql, Users.class);
		//query.setParameter("role", user.getRole());
		return query.getResultList();
	}
	public static void main(String[] args) {
		UsersService uer=null;
		/*List<Users> userList=new ArrayList<Users>();
		userList= uer.getCandidateEmailList();
		for(int i=1;i<=userList.size();i++){
			System.out.println("Email is : "+userList);
		}*/
		Users role;
		//role=uer.checkUser("moin");
		//System.out.println("Role is : "+role);
		
	}

}
