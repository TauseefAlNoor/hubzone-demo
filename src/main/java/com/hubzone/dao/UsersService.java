package com.hubzone.dao;

/*
 * This class is for interface of User Service
 * 
 * */
import java.util.List;

import com.hubzone.model.Employer;
import com.hubzone.model.Jobs;
import com.hubzone.model.Users;

public interface UsersService {
	public void saveUser(Users user);

	public void updateUser(Users user);

	public Users getUserByEmail();
	public Users getById(String id);
	public Users getEmailById(String id);

	public void removeUser(Users user);
	public Users getUserByEmail(String email);
	public Users getUserByName(String username);
	//password encode
	public  List<Users> getPasswordList();
	public  List<Users> getPasswordListEmp();
	
	public String checkUser(String userID);


	public List<Users> getCandidateEmailList();
}
