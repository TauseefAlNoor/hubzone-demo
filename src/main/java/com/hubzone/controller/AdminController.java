package com.hubzone.controller;

//password encode 
import org.springframework.security.crypto.password.StandardPasswordEncoder;

/**
 * 
 * This class which will handle all admin panel section  
 * 
 * 
 * @author Tauseef
 *
 */

import java.io.File;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;

import com.hubzone.model.Admin;
import com.hubzone.model.Candidate;
import com.hubzone.model.States;
import com.hubzone.model.Users;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hubzone.dao.EmployerService;
import com.hubzone.dao.AdminService;
import com.hubzone.dao.JobCategoriesService;
import com.hubzone.dao.JobService;
import com.hubzone.dao.StatesService;
import com.hubzone.dao.UsersService;
import com.hubzone.dao.CandidateService;

import com.hubzone.model.Users;
import com.hubzone.model.Employer;
import com.hubzone.utility.Email;
import com.hubzone.utility.EmployerSearch;
import com.hubzone.utility.FileUtility;
import com.hubzone.utility.Random;
import com.hubzone.utility.SendMail;
import com.hubzone.utility.ContactSupportForm.ContactSupportForm;



@Controller
@RequestMapping(value="admin", produces="application/json")

public class AdminController {
Logger log=Logger.getLogger(CandidateController.class);	

@Autowired SendMail sendMailTLS;
@Autowired
UsersService usersService;
@Autowired
EmployerService employerService;
@Autowired
AdminService adminService;
@Autowired
CandidateService candidateService;
@Autowired
JobCategoriesService jobCategoriesService;
@Autowired
StatesService statesService;
@Autowired
JobService jobService;

@Autowired
private StandardPasswordEncoder passwordEncoder;

/*
 * This method will return all US state name
 * 
 * */
@ModelAttribute("states")
public List<States> getAllState(){
	return jobService.countJobByStates();
}


/*
 * This method will return employer search page to the admin 
 * 
 * */
@RequestMapping(value="/user-search",method=RequestMethod.GET)
public String searchPage(Model model, Principal principal) {
	return "userSearchPage";
}

/*
 * This method will return employer list to the admin 
 * 
 * */
@RequestMapping(value="/candidate-search",method=RequestMethod.GET)
public String candidateSearchPage(Model model, Principal principal) {
	List<Users> user1= this.usersService.getCandidateEmailList();
	model.addAttribute("usersize",user1.size());
	return "candidateSearchPage";
}

/*
 * This method will return edit employer page to the user
 * And admin can edit employer page
 * */

@RequestMapping(value="/job-count",method=RequestMethod.GET)
public String jobCount(Model model, Principal principal) {
	return "editJobCount";
}

/*
 * This method will return admin mass email page to the admin
 * 
 * */
@RequestMapping(value="/admin-email",method=RequestMethod.GET)
public String adminEmail(Model model, Principal principal) {
	return "adminEmail";
}

/*
 * From this page admin can view the user search result
 * */
@RequestMapping(value="/user-search",method=RequestMethod.POST)
public String employerSearch(@RequestParam("employerID") String  employerSearch, Model model){
	List<Employer> emp = adminService.serachEmployer(employerSearch);
	model.addAttribute("employerList" ,emp);
	
	//Users emp1 = adminService.findUserByEmail(employerEmailSearch);
	//List<Employer> emp2 = adminService.serachEmployer(emp1);
	//model.addAttribute("employerList" ,emp1);
	
	return "userSearchPage";
}

/*
 * This method will return the employer search result by list
 * It will take email for search employer 
 * */

//new
@RequestMapping(value="/user-search-email",method=RequestMethod.POST)
public String employerSearchEmail(Model model,@RequestParam("email") String  employerEmailSearch){
	try{
	Users user=adminService.searchCandidateByEmail(employerEmailSearch);
	String userId=user.getUserName();
	System.out.println("Email Search Employee"+userId);
	List<Employer> emp = adminService.serachEmployer(userId);
	System.out.println("Employer Size"+emp.size());
	for(Employer java : emp){
	System.out.println("Email Search After"+java);
	}
	model.addAttribute("employerList" ,emp);
	} catch (Exception e) {
		e.printStackTrace();
		model.addAttribute("searchError", "true");
	}
	
	//Users emp1 = adminService.findUserByEmail(employerEmailSearch);
	//List<Employer> emp2 = adminService.serachEmployer(emp1);
	//model.addAttribute("employerList" ,emp1);
	
	return "userSearchPage";
}

/*
 * This method will return candidate list search result 
 * Search by username
 * */


@RequestMapping(value="/candidate-search",method=RequestMethod.POST)
public String candidateSearch(@RequestParam("candidateID") String  candidateSearch, Model model){
	List<Candidate> cand = adminService.serachCandList(candidateSearch);
	model.addAttribute("candidateList" ,cand);
	//List<Users> user1= this.usersService.getCandidateEmailList();
	//model.addAttribute("usersize",user1.size());
	return "candidateSearchPage";
}

/*
 * This method will return candidate search result by list 
 * Search by email
 * */

//new search by email
@RequestMapping(value="/candidate-search-email",method=RequestMethod.POST)
public String candidateSearchByEmail(@RequestParam("email") String  candidateSearch, Model model){
	try{
	Users user=adminService.searchCandidateByEmail(candidateSearch);
	System.out.println("Candidate Before Search"+user);
	List<Candidate> cand = adminService.serachCandList(user.getUserName());
	
	for(Candidate java: cand){
	System.out.println("Candidate After Search"+java);
	}
	model.addAttribute("candidateList" ,cand);
	
	} catch (Exception e) {
		e.printStackTrace();
		model.addAttribute("searchError", "true");
	}
	return "candidateSearchPage";
}

/*
 * This method will return admin profile page
 * */
@RequestMapping(value = "/admin-profile", method=RequestMethod.GET)
public String adminProfile(Principal principal, Model model) {
	log.info("Login user "+principal.getName());
	//Candidate candidate = (Candidate) this.candidateService.getById(principal.getName());
	Admin admin = (Admin) this.adminService.getById(principal.getName());
	model.addAttribute("user",usersService.getUserByName(principal.getName()));
	log.info("admin.firstName "+admin.getAdminID());
	model.addAttribute("admin", admin);
	return "viewAdminPage";
}

/*
 * This method will show edit option for admin profile to admin
 * 
 * */
@RequestMapping(value="/edit-admin-profile", method=RequestMethod.GET)
public String editCompanyProfilePage(Principal principal, Model model) {
//	model.addAttribute("jobCategories", jobService.countJobByCategory());
	
	model.addAttribute("user",usersService.getUserByName(principal.getName()));
	model.addAttribute("admin",adminService.getById(principal.getName()));
//	model.addAttribute("jobCategories", jobCategoriesService.getJobCategories());
	return "editAdminProfile";
}

/*
 * This method will show edit option for admin profile to admin
 * Admin can edit his profile
 * */
@RequestMapping(value = "/updateAdmin", method = RequestMethod.POST)
@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public String update(@ModelAttribute Admin admin,
		@ModelAttribute Users user, Model model) {
	try {
		log.debug("registration candidate11");

		admin.setAdminID(user.getUserName());
		//encode password
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		
//		candidate.setLastUpdateDate(new Date());
		user.setRole("ROLE_ADM");
		user.setEnabled(1);

		adminService.updateAdmin(admin);
		usersService.updateUser(user);
		model.addAttribute("messagge", "Registration Sucessfull"); // {$messagge}
		log.debug("registration done");
	} catch (Exception e) {
		log.debug("registration candidate fail");
		e.printStackTrace();
		// model.addAttribute("messagge", "Registration Fail");
		// //{$messagge}
	}

	model.addAttribute("editadmin", "true");
	
//	return "redirect:/candidate/candidate-profile";
	return "editAdminProfile";

}

/*
 * This method will show edit option for employer profile to admin
 * Admin can update employer info 
 * */
@RequestMapping(value="/edit-job-count/{employerID}", method=RequestMethod.GET)
public String editJobLimit(@PathVariable("employerID") final String employerID,Principal principal, Model model) {
	model.addAttribute("jobCategories", jobService.countJobByCategory());
	model.addAttribute("user",usersService.getUserByName(employerID));
	model.addAttribute("employer",employerService.getById(employerID));
	model.addAttribute("jobCategories", jobCategoriesService.getJobCategories());
//	model.addAttribute("stateName",statesService.getStateList());
	return "editJobCount";
}

/*
 * This method will show edit option for candidate profile to admin
 * Admin can edit candidate profile
 * */

@RequestMapping(value="/edit-candidate/{candidateID}", method=RequestMethod.GET)
public String editCandidate(@PathVariable("candidateID") final String candidateID,Principal principal, Model model) {
	model.addAttribute("jobCategories", jobService.countJobByCategory());
	model.addAttribute("user",usersService.getUserByName(candidateID));
	model.addAttribute("candidate",candidateService.getById(candidateID));
	model.addAttribute("jobCategories", jobCategoriesService.getJobCategories());
//	model.addAttribute("stateName",statesService.getStateList());
	return "editCandidateByAdmin";
}


/*
 * This method will show edit option for employer profile to admin
 * Admin can edit employer profile
 * */
@RequestMapping(value = "/update-job-count/{employerID}", method = RequestMethod.POST)
@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public String updateJobLimit(@ModelAttribute Employer employer,
		@ModelAttribute Users user, Model model, Principal principal, @PathVariable("employerID") final String employerID){
	try {

		//Employer empl = null;
		employer.setJobCountLimit(employer.getJobCountLimit());

		//Employer empl = employerService.getById(employerID);
		//empl.setJobCountLimit(employer.getJobCountLimit());

		//employer.setEmployerID(employerID);
		employer.setEmployerID(employerID);
		//password encode
		//password encode
		//String encodedPassword = passwordEncoder.encode(user.getPassword());
		//user.setPassword(encodedPassword);

		user.setRole("ROLE_EMP");
		user.setEnabled(1);
//		Users usr=usersService.getById(username);
//		usr.setUserName(username);
//		usr.setPassword(password);
		
		
		usersService.updateUser(user);
		employerService.updateEmployer(employer);
		//usersService.updateUser(user);
		model.addAttribute("messagge", "Edit Profile Sucessfull"); // {$messagge}
		log.debug("registration done");
	} catch (Exception e) {
		log.debug("Edit Job fail");
		e.printStackTrace();
	}

	model.addAttribute("editjobcount", "true");

	return "editJobCount";

}


/*
 * This method will show edit option for candidate profile to admin
 * Admin can edit candidate profile
 * Upload pdf/doc file
 * */
@RequestMapping(value = "/update-candidate/{candidateID}", method = RequestMethod.POST)
@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public String updateCandidate(@ModelAttribute Candidate candidate,
		@ModelAttribute Users user, Model model,@PathVariable("candidateID") final String candidateID,UploadItem uploadItem,HttpServletRequest request) {
	try {

		candidate.setCandidateID(candidateID);
		candidate.setLastUpdateDate(new Date());
		//password encode
		//String encodedPassword = passwordEncoder.encode(user.getPassword());
		//user.setPassword(encodedPassword);

		user.setRole("ROLE_CAN");
		user.setEnabled(1);
		//new
		FileItem item = uploadItem.getFileData().getFileItem();

		if (!item.isFormField()) {
			if (item.getSize() > 0) {
				System.out.println("size::" + item.getSize());
				if (item.getSize() > 314342) {
					model.addAttribute("message", "File size is big.");
					return "editCandidateByAdmin";
				}
				String fileName = candidateID + "_resume."
						+ FileUtility.getFileExtention(item.getName());

				String root = request.getSession().getServletContext()
						.getRealPath("/");
				File path = new File(root + "/WEB-INF/cv");
				if (!path.exists()) {
					boolean status = path.mkdirs();
				}
				
				if(!FileUtility.getFileExtention(item.getName()).equals("doc")&& !FileUtility.getFileExtention(item.getName()).equals("docx")){
					model.addAttribute("message", "Please upload in doc format");
					return "editCandidateProfilePage";
				}

				File uploadedFile = new File(path + "/" + fileName);
				System.out.println(uploadedFile.getAbsolutePath());
				item.write(uploadedFile);

				candidate
						.setResumeFileDesc(uploadItem.getFileDescription());
				candidate.setResumeFileLocation(fileName);
				candidate.setResumeContenttype(item.getContentType());
			} else {

				Candidate cand = this.candidateService.getById(candidateID);
				log.debug("no file uploaded " + cand.getResumeContenttype());
				
				candidate.setResumeFileDesc(uploadItem.getFileDescription());

				candidate.setResumeFileLocation(cand
						.getResumeFileLocation());
				candidate.setResumeContenttype(cand.getResumeContenttype());
			}
		//new
		usersService.updateUser(user);
		candidateService.updateCandidate(candidate);
//		model.addAttribute("messagge", "Edit Job Sucessfull"); // {$messagge}
		log.debug("registration done");
		}
		System.err.println("-------------------------------------------");
		System.err.println("File upload: " + item.getName());
		System.err.println("File Content upload: " + item.getContentType());
		System.err.println("File size: " + item.getSize());
		System.err.println("User email: " + user.getEmail());

		// model.addAttribute("messagge", "Registration Sucessfull"); //
		// {$messagge}
		log.debug("edit CandidateProfile done");
		model.addAttribute("message", "Candidate Update Successful.");
	} catch (Exception e) {
//		log.debug("Edit Job fail");
		model.addAttribute("message", "Candidate Update Fail.");
		e.printStackTrace();
	}

//	model.addAttribute("editjobcount", "true");
	model.addAttribute("jobCategories",
			jobCategoriesService.getJobCategories());

	return "editCandidateByAdmin";

}
//@RequestMapping(value = "/", method = RequestMethod.POST)
//public String adminPage() {
//	return "redirect:/login?param=adm";
//}

/*
 * This method will show delete option for candidate profile to admin
 * Admin can delete candidate from this method
 * */

@RequestMapping(value="delete-candidate")
public String candidateDelete(Model model, Principal principal, @ModelAttribute Users user,@RequestParam("candidateID") String candidateID) {
	adminService.deleteCandidate(candidateID);
	return "candidateSearchPage";
}

/*
 * This method will show delete option for employer profile to admin
 * Admin can delete employer from this method
 * */

@RequestMapping(value="delete-employer")
public String employerDelete(Model model, Principal principal, @ModelAttribute Users user,@RequestParam("employerID") String employerID) {
	adminService.delete(employerID);
	return "userSearchPage";
}

/*
 * This method will show mass email page option to admin
 * Admin can send mass email from this method
 * */

@RequestMapping(value="send-mass-email", method = RequestMethod.POST)
public String sendMassEmail(Model model, Principal principal,@ModelAttribute ContactSupportForm csForm) {
	try{
		System.out.println("Email Subject is : "+csForm.getSubject());
		System.out.println("Email Message is : "+csForm.getMessage());
		String random = Random.getRandomValue();
		Email emailObj = new Email();
		
		List <String> datList=new ArrayList<String>();
		List<Users> user= this.usersService.getCandidateEmailList();
		
		for(int i=0;i<user.size();i++){
			
			System.out.println("email is"+i+"number : "+user.get(i).getEmail());
		}
		datList.add(new String("tauseef.cse100@gmail.com"));
		datList.add(new String("tauseef_cse100@yahoo.com"));
		datList.add(new String("tauseef.cse100@gmail.com"));
		datList.add(new String("tauseef_cse100@yahoo.com"));
		datList.add(new String("tauseef.cse100@gmail.com"));
		
		emailObj.setSubject(csForm.getSubject());
		emailObj.setBody(csForm.getMessage());
//		for(int i=1;i<=usersService.getCandidateEmailList().size();i++){
		for(int i=0;i<user.size();i++){
			System.out.println("email is"+i+"number : "+user.get(i).getEmail());
		}
		//emailObj.setRecipt(usersService.getCandidateEmailList().toArray(new String[usersService.getCandidateEmailList().size()]));
		for(int i=0;i< user.size();i++){
		emailObj.setTo(user.get(i).getEmail());
		sendMailTLS.sendMail(emailObj);
		log.debug("activation mail sent");
		
		}
		model.addAttribute("messageblock", "You have sent email successfully!");
		
	}catch (Exception e) {
		model.addAttribute("messageblock1", "send failure");
	}
	
	/*InternetAddress[] addressTo = new InternetAddress[usersService.getCandidateEmailList().size()]; 
	for (int i = 0; i < usersService.getCandidateEmailList().size(); i++) 
		{ 
		try {
			addressTo[i] = new InternetAddress(usersService.getCandidateEmailList().toString());
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}*/

	return "adminEmail";
}

@RequestMapping(value="pass-encode", method = RequestMethod.POST)
public String passwordEncode(Model model, Principal principal) {
	try{
		List<Users> user= this.usersService.getPasswordListEmp();
		
		//emailObj.setRecipt(usersService.getCandidateEmailList().toArray(new String[usersService.getCandidateEmailList().size()]));
		for(int i=0;i< user.size();i++){
			String encodedPassword = passwordEncoder.encode(user.get(i).getPassword());
			user.get(i).setPassword(encodedPassword);

		}
		log.debug("activation mail sent");


	}catch(Exception e){
		
	}
	return "";
}
}
