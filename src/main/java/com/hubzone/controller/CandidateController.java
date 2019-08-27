package com.hubzone.controller;


/**
 * 
 * This class which will handle job seeker related informantion  
 * 
 * 
 * @author Tauseef
 *
 */

//password encode
//import org.springframework.security.crypto.password.StandardPasswordEncoder;

import java.io.File;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hubzone.dao.CandidateService;
import com.hubzone.dao.JobCategoriesService;
import com.hubzone.dao.StatesService;
import com.hubzone.dao.JobService;
import com.hubzone.dao.UsersService;

import com.hubzone.model.Candidate;

import com.hubzone.model.States;
import com.hubzone.model.Users;
import com.hubzone.utility.FileUtility;
import com.hubzone.utility.Random;
import com.hubzone.utility.Email;
import com.hubzone.utility.SendMailTLS;

import com.hubzone.dao.AdminService;



@Controller
@RequestMapping(value = "candidate", produces = "application/json")
public class CandidateController {
	Logger log = Logger.getLogger(CandidateController.class);
	@Autowired
	JobCategoriesService jobCategoriesService;
	@Autowired
	CandidateService candidateService;
	@Autowired
	UsersService usersService;
	@Autowired
	StatesService statesService;
	@Autowired
	JobService jobService;
	@Autowired
	AdminService adminService;
	
	//password encode
	@Autowired
	private StandardPasswordEncoder passwordEncoder;
	
	public @Value("${server.address}") String serverHostAddress;
	@Autowired SendMailTLS sendMailTLS;

	@ModelAttribute("states")
	public List<States> getAllState() {
		return jobService.countJobByStates();
	}
	
/*
 * This method will show job-seeker registration page to the user
 * */
	
	@RequestMapping(value = "/saveCandidate", method = RequestMethod.GET)
	public String showRegister(Model model) {
		Users user = new Users();

		model.addAttribute(user);
		
		model.addAttribute("stateName",statesService.getStateList());
		//job categories
		model.addAttribute("jobCategories",
				jobCategoriesService.getJobCategories());
		
		
		return "candidateRegistrationPage";
	}


	/**
	 * 
	 * This method will handle job-seeker registration  
	 * Upload resume
	 * Password encode
	 * send email 
	 *
	 */

	@RequestMapping(value = "/saveCandidate", method = RequestMethod.POST)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String create(@ModelAttribute Candidate candidate,
			@ModelAttribute Users user, Model model,UploadItem uploadItem,HttpServletRequest request) {
		try {
			log.debug("registration candidate11");
			

			candidate.setCandidateID(user.getUserName());
			candidate.setLastUpdateDate(new Date());
			//password encode 

			String encodedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encodedPassword);
			
			//candidate other 
			
			/*candidate.setAddress1(candidate.getAddress1());
			candidate.setAddress2(candidate.getAddress2());
			candidate.setCandidateAltPhone(candidate.getCandidateAltPhone());
			candidate.setCandidateCity(candidate.getCandidateCity());
			candidate.setCandidateDescription(candidate.getCandidateDescription());
			candidate.setCandidateFirstName(candidate.getCandidateFirstName());
			candidate.setCandidateID(candidate.getCandidateID());
			candidate.setCandidateLastName(candidate.getCandidateLastName());
			candidate.setCandidatePrimaryPhone(candidate.getCandidatePrimaryPhone());
			candidate.setCandidateSkill(candidate.getCandidateSkill());
			candidate.setCandidateState(candidate.getCandidateState());
			candidate.setCandidateZip(candidate.getCandidateZip());
			candidate.setDesiredPosition(candidate.getHighestEducationLevel());
			candidate.setHighestEducationLevel(candidate.getHighestEducationLevel());
			candidate.setJobCategory(candidate.getJobCategory());
			candidate.setLastSchoolAttended(candidate.getLastSchoolAttended());
			candidate.setRecentEmployer(candidate.getRecentEmployer());
			candidate.setResidentStatus(candidate.getResidentStatus());
			candidate.setSalaryLevel(candidate.getSalaryLevel());*/
			
			user.setRole("ROLE_CAN");
			user.setEnabled(1);
			user.setVerificationCode("");
		//	user.setEnabled(new Integer(0));
			//file upload
			FileItem item = uploadItem.getFileData().getFileItem();

			Candidate cand = this.candidateService.getById(user.getUserName());
			//log.debug("existing cv  " + cand.getResumeFileLocation());
			if (!item.isFormField()) {
				if (item.getSize() > 0) {
					log.debug("size::" + item.getSize());

					if (item.getSize() > 814342) {

						model.addAttribute("message", "File size is big.");
						return "candidateRegistrationPage";
					}
					
					String fileName = user.getUserName()+"_resume."
							+ FileUtility.getFileExtention(item.getName());
					
					//if(!fileName.substring(fileName.lastIndexOf(".")+1).equals("doc")&& !fileName.substring(fileName.lastIndexOf(".")+1).equals("docx") && !fileName.substring(fileName.lastIndexOf(".")+1).equals("pdf")){
					if(!fileName.substring(fileName.lastIndexOf(".")+1).equals("doc")&& !fileName.substring(fileName.lastIndexOf(".")+1).equals("docx")){
						model.addAttribute("messageAlert", "true");
						//new
						model.addAttribute("stateName",statesService.getStateList());
						//job categories
						model.addAttribute("jobCategories",
								jobCategoriesService.getJobCategories());
						model.addAttribute("user",user);
						model.addAttribute("candidate",candidate);

						return "candidateRegistrationPage";
					}

					String root = request.getSession().getServletContext()
							.getRealPath("/");
					File path = new File(root + "/WEB-INF/cv");
						//log.debug("Existing cv is updated");
					
					
					// copy the original file
					
					File uploadedFile = new File(path + "/" + fileName);	
					log.debug(" cv :"+uploadedFile.getAbsolutePath());
					item.write(uploadedFile);
					
					
					candidate.setResumeFileDesc(uploadItem.getFileDescription());
					candidate.setResumeFileLocation(fileName);
					candidate.setResumeContenttype(item.getContentType());
				}
//			} else {
//					candidate.setResumeFileDesc(uploadItem.getFileDescription());
//
//					candidate.setResumeFileLocation(cand.getResumeFileLocation());
//					candidate.setResumeContenttype(cand.getResumeContenttype());
//				}
			}
			
			//file upload
			List<Users> candi = adminService.matchUser(user.getUserName());
			int i=candi.size();
			
			if(i==1 ){
				model.addAttribute("duplicateuser", "true");
				//new
				model.addAttribute("stateName",statesService.getStateList());
				//job categories
				model.addAttribute("jobCategories",
						jobCategoriesService.getJobCategories());
				model.addAttribute("user",user);
				model.addAttribute("candidate",candidate);

				return "candidateRegistrationPage";
			}
			
			List<Users> candid = adminService.matchUserEmail(user.getEmail());
			int j=candid.size();
			
			if(j==1 ){
				model.addAttribute("duplicateemail", "true");
				//new
				model.addAttribute("stateName",statesService.getStateList());
				//job categories
				model.addAttribute("jobCategories",
						jobCategoriesService.getJobCategories());
				model.addAttribute("user",user);
				model.addAttribute("candidate",candidate);

				return "candidateRegistrationPage";
			}
			
			String random = Random.getRandomValue();
			user.setVerificationCode(Random.getRandomValue());
			Email emailObj = new Email();
			emailObj.setTo(user.getEmail());
			emailObj.setFrom("support@HUBZoneTalent.com");
			
			emailObj.setSubject("Job Seeker Registration");
			/*emailObj.setBody("Welcome to HubzoneTalent.com"+"<br><br><br>"+"Dear "+candidate.getCandidateFirstName()+","+""+"<br><br><br>"+"Thanks for signing up at Hubzonetalent.com(HZT)!.We expected hundreds of Hubzone Employer joins this sites in the coming month to provide a great employment opportunities for you."
					+"If you have any question about how HZT works or have any technical difficulties with your account please contact us at support@hubzonetalent.com"
					+"Sincerely,"
					+"<br>The HUBZoneTalent Team"
				);*/
			emailObj.setBody("Dear "+candidate.getCandidateFirstName()+" "+candidate.getCandidateLastName()+","+""+"<br><br><br>"+"Thanks for signing up at <a style='text-decoration:none' href='https://hubzonetalent.com/hubzone/'>hubzonetalent.com</a>(HZT)! I am confident that you will find a"+"<br>"+" substantial pool of employers and jobs to review.  We anticipate hundreds of new employers to"+"<br>"+" join our site within the next few months.  Employers are very interested in viewing the resumes of"+"<br>"+" our job seekers.  Your employment opportunities are endless.  If you have any question about"+"<br>"+" how HZT works or have any technical difficulties with your account, please contact us"+"<br>"+
					"at <a style='text-decoration:none' href='mailto:support@hubzonetalent.com'>support@hubzonetalent.com</a>"+
					"<br><br>"+
					"Sincerely"
					+"<br><br>"+
					"The HUBZoneTalent Team"
				);
			sendMailTLS.sendMail(emailObj);
			log.debug("activation mail sent");
			model.addAttribute("message", ",Please Check your email");
			
			candidateService.save(candidate);
			usersService.saveUser(user);

			model.addAttribute("message", "Registration Sucessfull"); // {$messagge}
			//state
			model.addAttribute("stateName",statesService.getStateList());
			//job categories
			model.addAttribute("jobCategories",
					jobCategoriesService.getJobCategories());
			
			log.debug("registration done");

		} catch (Exception e) {
			log.debug("registration candidate fail");
			e.printStackTrace();
			// model.addAttribute("messagge", "Registration Fail");
			// //{$messagge}
			model.addAttribute("registrationerror", "true");
			return "candidateRegistrationPage";
		}

//		model.addAttribute("registration", "true");
		
		model.addAttribute("loginHeader", "Job Seeker Login");

//		return "redirect:/login?param=cand";
		return "candidateRegisterConfirmation";
	}
	
	
/*
 * This method will activate job-seeker account 
 * not active
 * 
 * */	
	
	@RequestMapping(value = "/activate-account/email/{email}/session/{session}", method = RequestMethod.GET)
	public String restorePasswordPage(@PathVariable("email") String email,
			@PathVariable("session") String session, Model model) {
		Users user = null;
		try {
			log.info("Account Activate");
			user = usersService.getUserByEmail(email);
			if(user.getVerificationCode().equals(session)){
				
				user.setEnabled(1) ;
				user.setVerificationCode("");
				usersService.updateUser(user);
				model.addAttribute("message", "true");
			}else{
				model.addAttribute("error", "Invalid Session ");
			}
		log.info("");
		} catch (Exception e) {
			if (user == null) {
				model.addAttribute("error", "Invalid email");
			} else {
				model.addAttribute("error", "System Error");
			}
		}
		return "candidateRegisterConfirmation";
	}
	
	//not active 
	
	@RequestMapping(value="/sign-up-confimation", method=RequestMethod.GET)
	public String candidateRegisterConfirmation() {
		return "candidateRegisterConfirmation";
	}
	/*
	 * Temporary page for job-seeker 
	 * 
	 * */
	
	@RequestMapping(value="/view-job-temp", method=RequestMethod.GET)
	public String viewJobTempPage() {
		return "viewJobTempPage";
	}
	
	
	/*
	 *This method will show profile information to the job-seeker 
	 * 
	 * */

	@RequestMapping(value = "/candidate-profile")
	public String candidateProfile(Principal principal, Model model) {
		log.info("Login user " + principal.getName());
		// Candidate candidate = (Candidate)
		// this.candidateService.getById(principal.getName());
		Users user=this.usersService.getById(principal.getName());
		Candidate candidate = this.candidateService
				.getById(principal.getName());
		log.info("candiate.firstName " + candidate.getCandidateID());
		model.addAttribute("candidate", candidate);
		model.addAttribute("user",user);
		if (candidate.getResumeFileLocation() != null) {
			model.addAttribute("candicateResume", candidate);
		}
		return "viewCandidateProfilePage";
	}
	
	/*
	 * This method will show the candidate search result to the employer
	 * */
	
	@RequestMapping(value = "/candidate-profile-search")
	public String candidateProfileSearch(Principal principal, Model model) {
		log.info("Login user " + principal.getName());
		// Candidate candidate = (Candidate)
		// this.candidateService.getById(principal.getName());
		Candidate candidate = this.candidateService
				.getById(principal.getName());
		log.info("candiate.firstName " + candidate.getCandidateID());
		model.addAttribute("candidate", candidate);
		if (candidate.getResumeFileLocation() != null) {
			model.addAttribute("candicateResume", candidate);
		}
		return "viewCandidateProfileSearch";
	}
	
	/*
	 * This method will show profile page to the job-seeker
	 * 
	 * */
	
	@RequestMapping(value = "/candidate-profile/{candidateID}")
	public String candidateProfilePage(Principal principal, Model model, @PathVariable("candidateID") String candidateID) {
		log.info("Login user " + principal.getName());
		// Candidate candidate = (Candidate)
		// this.candidateService.getById(principal.getName());
		Candidate candidate = this.candidateService
				.getById(candidateID);
		Users user=this.usersService.getById(candidateID);
		model.addAttribute("user",user);
		log.info("candiate.firstName " + candidate.getCandidateID());
		model.addAttribute("candidate", candidate);
		if (candidate.getResumeFileLocation() != null) {
			model.addAttribute("candicateResume", candidate);
		}
		return "viewCandidateProfilePage";
	}

/*
 * This page will show job-seeker list to the user
 * 
 * */
	@RequestMapping(value = "/candidate-profile-search/{candidateID}")
	public String candidateProfilePageSearch(Principal principal, Model model, @PathVariable("candidateID") String candidateID) {
		log.info("Login user " + principal.getName());
		// Candidate candidate = (Candidate)
		// this.candidateService.getById(principal.getName());
		Candidate candidate = this.candidateService
				.getById(candidateID);
		log.info("candiate.firstName " + candidate.getCandidateID());
		model.addAttribute("candidate", candidate);
		if (candidate.getResumeFileLocation() != null) {
			model.addAttribute("candicateResume", candidate);
		}
		return "viewCandidateProfileSearch";
	}
	
/*
 * This method will show edit option for job-seeker profile
 * */
	

	@RequestMapping(value = "/edit-candiate-profile", method = RequestMethod.GET)
	public String editCompanyProfilePage(Principal principal, Model model) {

		model.addAttribute("user",
				usersService.getUserByName(principal.getName()));
		model.addAttribute("candidate",
				candidateService.getById(principal.getName()));
		model.addAttribute("jobCategories",
				jobCategoriesService.getJobCategories());
		return "editCandidateProfilePage";
	}
	
	/*
	 * This method will show edit option for job-seeker profile
	 * job seeker can update their profile from here
	 * upload resume option
	 * */	

	@RequestMapping(value = "/updateCandidate", method = RequestMethod.POST)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String update(@ModelAttribute Candidate candidate, Model model, UploadItem uploadItem,
			Principal principal, HttpServletRequest request) {
		try {
			log.debug("edit CandidateProfile ");
			Users user=usersService.getById(principal.getName());

			candidate.setCandidateID(principal.getName());
			candidate.setLastUpdateDate(new Date());
			user.setRole("ROLE_CAN");
			user.setEnabled(1);
			user.setVerificationCode("");
			user.setUserName(user.getUserName());
			user.setEmail(user.getEmail());
			user.setPassword(user.getPassword());
			
			//password encode
//			if (!entity.getPassword().equals("")) {
//				user.setPassword(passwordEncoder.encode(entity.getPassword()));
//			}
			//String encodedPassword = passwordEncoder.encode(user.getPassword());
			//user.setPassword(encodedPassword);

			FileItem item = uploadItem.getFileData().getFileItem();

			Candidate cand = this.candidateService.getById(principal
					.getName());
			log.debug("existing cv  " + cand.getResumeFileLocation());
			if (!item.isFormField()) {
				if (item.getSize() > 0) {
					log.debug("size::" + item.getSize());

					if (item.getSize() > 814342) {

						model.addAttribute("message", "File size is big.");
						return "editCandidateProfilePage";
					}
					String fileName = principal.getName()+"_resume."
							+ FileUtility.getFileExtention(item.getName());

					String root = request.getSession().getServletContext()
							.getRealPath("/");
					File path = new File(root + "/WEB-INF/cv");
					if (!path.exists()) {
						boolean status = path.mkdirs();
					}
					/*if(!FileUtility.getFileExtention(item.getName()).equals("doc")|| !FileUtility.getFileExtention(item.getName()).equals("docx") || !FileUtility.getFileExtention(item.getName()).equals("pdf")){
						model.addAttribute("message", "Please upload in doc or pdf format");
						return "editCandidateProfilePage";
					}*/
					if(!fileName.substring(fileName.lastIndexOf(".")+1).equals("doc") && !fileName.substring(fileName.lastIndexOf(".")+1).equals("docx")){
						model.addAttribute("messageName", "Please upload in doc or docx format");
						System.out.println("File Extension name is : "+fileName.substring(fileName.lastIndexOf(".")+1));
						model.addAttribute("candidate"+candidate);
						model.addAttribute("user"+user);
						return "editCandidateProfilePage";
					}

					
                     // delete existing cv 
				
					File existingCV = new File(path + "/"
							+ cand.getResumeFileLocation());					
					existingCV.setWritable(true);
					if( existingCV.delete()){
						log.debug("Existing cv is updated");
					}
					
					// copy the original file
					
					File uploadedFile = new File(path + "/" + fileName);	
					log.debug(" cv :"+uploadedFile.getAbsolutePath());
					item.write(uploadedFile);
					
					
					candidate.setResumeFileDesc(uploadItem.getFileDescription());
					candidate.setResumeFileLocation(fileName);
					candidate.setResumeContenttype(item.getContentType());
				} else {

					

					candidate
							.setResumeFileDesc(uploadItem.getFileDescription());

					candidate.setResumeFileLocation(cand
							.getResumeFileLocation());
					candidate.setResumeContenttype(cand.getResumeContenttype());
				}

				candidateService.updateCandidate(candidate);
				usersService.updateUser(user);
			}

			// Some type of file processing...
			System.err.println("-------------------------------------------");
			System.err.println("File upload: " + item.getName());
			System.err.println("File Content upload: " + item.getContentType());
			System.err.println("File size: " + item.getSize());
			System.err.println("User email: " + user.getEmail());

			// model.addAttribute("messagge", "Registration Sucessfull"); //
			// {$messagge}
			log.debug("edit CandidateProfile done");
			model.addAttribute("message", "Candidate Update Successful.");
			model.addAttribute("candidate"+candidate);
			model.addAttribute("user"+user);
		} catch (Exception e) {
			model.addAttribute("message", "Candidate Update Fail.");
			log.debug("edit CandidateProfile  fail");
			e.printStackTrace();
			// model.addAttribute("messagge", "Registration Fail");
			// //{$messagge}
		}
		Users user=usersService.getById(principal.getName());

		model.addAttribute("jobCategories",
				jobCategoriesService.getJobCategories());
		//model.addAttribute("user", entity);
		
		model.addAttribute("candidate"+candidate);
		model.addAttribute("user"+user);

		return "editCandidateProfilePage";

	}


}
