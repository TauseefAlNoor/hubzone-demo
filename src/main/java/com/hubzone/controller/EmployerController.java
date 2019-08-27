package com.hubzone.controller;

/*
 * This class will handle all method related to employer flow
 * 
 * */

import java.io.File;

//password encode 
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.hubzone.dao.CandidateService;
import com.hubzone.dao.EmployerService;
import com.hubzone.dao.JobCategoriesService;
import com.hubzone.dao.JobService;
import com.hubzone.dao.StatesService;
import com.hubzone.dao.UsersService;
import com.hubzone.dao.impl.EmployerServiceImpl;

import com.hubzone.model.Candidate;
import com.hubzone.model.Employer;
import com.hubzone.model.Jobs;
import com.hubzone.model.JobsApplied;
import com.hubzone.model.States;
import com.hubzone.model.Users;
import com.hubzone.utility.DocParser;
import com.hubzone.utility.ResumeSearch;
import com.geoservice.GeoDistanceService;

import com.hubzone.utility.Random;
import com.hubzone.utility.Email;
import com.hubzone.utility.ResumeSearchResult;
import com.hubzone.utility.SendMailTLS;
import com.hubzone.utility.PaginationInput;

import com.hubzone.dao.AdminService;
import com.hubzone.dao.JobAppliedService;

@Controller
@RequestMapping(value = "employer", produces = "application/json")
@SessionAttributes({ "searchForm" })
public class EmployerController {
	Logger log = Logger.getLogger(EmployerServiceImpl.class);

	@Autowired
	private JobCategoriesService jobCategoriesService;
	@Autowired
	private EmployerService employerService;
	
	@Autowired
	StatesService statesService;
	@Autowired
	JobAppliedService jobAppliedService;


	@Autowired
	private UsersService usersService;
	@Autowired
	private JobService jobService;
	@Autowired
	private CandidateService candidateService;
	@Autowired
	AdminService adminService;

	public @Value("${server.address}")
	String serverHostAddress;
	@Autowired
	SendMailTLS sendMailTLS;
	
	//password encode
	@Autowired
	private StandardPasswordEncoder passwordEncoder;


	@ModelAttribute("states")
	public List<States> getAllState() {
		return jobService.countJobByStates();
	}

	@ModelAttribute("searchForm")
	public ResumeSearch populateForm() {
		return new ResumeSearch(); // populates form for the first time if its
									// null
	}
	
	
/*
 * This method will handle number of job applied by job seekers
 * 
 * */
	@RequestMapping(value = "/jobAppliedNumber/{jobID}", method = RequestMethod.GET)
	public String viewApplicant(@PathVariable("jobID") long jobID,Candidate candidate,Principal principal,
			Model model,Employer employer) {
		List<JobsApplied> job=jobAppliedService.candList(jobID);
		model.addAttribute("candList",job);
		
		return "viewApplicant";
	}

	// start

	/*
	 * This method will handle restore password for the user
	 * 
	 * */
	@RequestMapping(value = "/restore-password", method = RequestMethod.POST)
	public String resetPasswordPage(@RequestParam("email") String email,
			@RequestParam("session") String session,
			@RequestParam("password") String password, Model model) {
		Users user = null;
		try {

			user = usersService.getUserByEmail(email);
			if (user.getVerificationCode().equals(session)) {
				String encodedPassword = passwordEncoder.encode(user.getPassword());
				user.setPassword(encodedPassword);
				// user.setEnabled(1);
				user.setVerificationCode("");
				usersService.updateUser(user);
				model.addAttribute("message", "Password changed");
				if (user.getRole().equals("ROLE_EMP")) {
					model.addAttribute("param", "emp");
				} else if (user.getRole().equals("ROLE_CAN")) {
					model.addAttribute("param", "cand");
				}
			} else {
				model.addAttribute("error", "Invalid Session ");
			}

		} catch (Exception e) {
			if (user == null) {
				model.addAttribute("error", "Invalid email");
			} else {
				model.addAttribute("error", "System Error");
			}
		}
		return "restorepassword";
	}

	//
/*
 * 
 * Temporary page for the employer
 * */
	@RequestMapping(value = "/employer-temp-page", method = RequestMethod.GET)
	public String viewCompanyPage() {
		return "employerTempPage";
	}

/*
 * This method will handle registrartion of the employer
 * 
 * 
 * */
	@RequestMapping(value = "/saveEmployer", method = RequestMethod.POST)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String create(@ModelAttribute Employer employer,
			@ModelAttribute Users user, Model model) {
		try {
			log.debug("registration employer");
			log.debug("company  profile " + employer.getCompanyName());
			log.debug("company  profile " + employer.getPocEmail());
			
			//password encode
			
			String encodedPassword = passwordEncoder.encode(user.getPassword());


			employer.setEmployerID(user.getUserName());
			employer.setJobCountLimit(5);
			user.setRole("ROLE_EMP");
			user.setEnabled(1);
			//password encode
			user.setPassword(encodedPassword);
			employer.setCurrentStatus("Inactive");
			// user.setEnabled(new Integer(0));

			List<Users> candi = adminService.matchUser(user.getUserName());
			int i = candi.size();

			if (i == 1) {
				model.addAttribute("duplicateuser", "true");
				//new 
				model.addAttribute("stateName",statesService.getStateList());
				//job categories
//				model.addAttribute("jobCategories",
//						jobCategoriesService.getJobCategories());
				model.addAttribute("user",user);
				model.addAttribute("employer",employer);

				return "employerRegistrationPage";
			}

			List<Users> candid = adminService.matchUserEmail(user.getEmail());
			int j = candid.size();

			if (j == 1) {
				model.addAttribute("duplicateemail", "true");
				//new 
				model.addAttribute("stateName",statesService.getStateList());
				//job categories
//				model.addAttribute("jobCategories",
//						jobCategoriesService.getJobCategories());
				model.addAttribute("user",user);
				model.addAttribute("employer",employer);

				return "employerRegistrationPage";
			}

			String random = Random.getRandomValue();
			String resetUrl = serverHostAddress+"/login?param=emp";
			user.setVerificationCode(Random.getRandomValue());
			Email emailObj = new Email();
			emailObj.setTo(user.getEmail());
			emailObj.setFrom("support@HUBZoneTalent.com");

			emailObj.setSubject("Activate your account");
			// emailObj.setBody("Thank you for signing up with HUBZone Talent.By signing up for HUBZone Talent, you are taking advantage of a Government program to help stimulate job creation in your particular area.  This email is confirmation that your profile submission has been received."
			// +"Your User Name is:"+employer.getEmployerID()+
			// "Please click following link to activate your account!</br> <a href='"
			// + serverHostAddress
			// + "/employer/activate-account/email/"
			// + user.getEmail()
			// + "/session/"
			// + URLEncoder.encode(random, "UTF-8")
			// + "'> Activate</a>");
//			emailObj.setBody("Thank you for signing up with HUBZone Talent.By signing up for HUBZone Talent, you are taking advantage of a Government program to help stimulate job creation in your particular area.  This email is confirmation that your profile submission has been received. We are expecting employers to be start signing up in July 2014. Until then please feel free to update your information when something changes."
//					+ "<br><br><br>Your User Name is: "
//					+ employer.getEmployerID()
//					+ "<br><br><br>Your password is not included in this email for security purposes.  If you can&apos;t remember your password, please click on</br> <a href='"
//					+ serverHostAddress
//					+ "/forgot-password"
//					+ "'> Forgot Password</a>"
//					+ "<br><br><br>Thank you,<br>HUBZone Talent");
			emailObj.setBody("Dear "+employer.getCompanyName()
					+ "<br><br>Thank you for signing up with HUBZone Talent.  Please complete the attached Purchase Order"+"<br>"+" Agreement and email to <a href='mailto:orders@hubzonetalent.com'>orders@hubzonetalent.com. </a>.  Once the signed contract is returned, please"+"<br>"+" login to your account to pay via our secured payment link (see below).  You will receive access to"+"<br>"+" resume database and job postings within 24 hours of signing agreement and payment.  To login,"+"<br>"+" please use your username and password at the following link:"+"<br>"+"<a href='https://www.hubzonetalent.com/hubzone/login?param=emp'>https://www.hubzonetalent.com/hubzone/login?param=emp</a>"
					+"<br><br>If you have any questions or difficulties accessing your account please, contact us at"+"<br>"+" <a href='mailto:support@hubzonetalent.com'>support@hubzonetalent.com </a>"
					+"<br><br>Sincerely"
					+"<br><br>The HUBZoneTalent Team");
			sendMailTLS.sendMail(emailObj);
			log.debug("activation mail sent");
			
			
			Email emailObjAd = new Email();
			//emailObjAd.setTo(user.getEmail());
			emailObjAd.setTo("Orders@hubzonetalent.com");
			emailObjAd.setFrom("support@HUBZoneTalent.com");
			
			emailObjAd.setSubject("Employer Registered");
			emailObjAd.setBody(employer.getCompanyName()+" has signed up for HUBZone Talent."+employer.getPocFirstName()+" is their POC and her email is "+employer.getPocEmail()+". Their method of payment is by credit card on a "+employer.getSubscriptionLevel()+" basis.");
			sendMailTLS.sendMail(emailObjAd);
			log.debug("activation mail sent to Admin");
			
			
			model.addAttribute("message", ",Please Check your email");

			employerService.save(employer);
			usersService.saveUser(user);
			// model.addAttribute("registration", "true");
			model.addAttribute("stateName",statesService.getStateList());
			
			model.addAttribute("message", "Registration Sucessfull");
			log.debug("registration done");
		} catch (Exception e) {
			log.debug("registration employer fail");
			e.printStackTrace();
			model.addAttribute("registrationerror", "true");
			return "employerRegistrationPage";
		}
		// model.addAttribute("registration", "true");
		model.addAttribute("loginHeader", "Employer Login");
		//return "employerRegisterConfirmation";
		return "employerTempPage";
	}
	
	/*
	 * This method will activate account
	 * */

	@RequestMapping(value = "/activate-account/email/{email}/session/{session}", method = RequestMethod.GET)
	public String restorePasswordPage(@PathVariable("email") String email,
			@PathVariable("session") String session, Model model) {
		Users user = null;
		try {

			user = usersService.getUserByEmail(email);
			if (user.getVerificationCode().equals(session)) {

				user.setEnabled(1);
				user.setVerificationCode("");
				usersService.updateUser(user);
				model.addAttribute("message", "true");
			} else {
				model.addAttribute("error", "Invalid Session ");
			}

		} catch (Exception e) {
			if (user == null) {
				model.addAttribute("error", "Invalid email");
			} else {
				model.addAttribute("error", "System Error");
			}
		}
		return "employerRegisterConfirmation";
	}
	
	/*
	 * This method will show registration confirmation page to the employer
	 * 
	 * */

	@RequestMapping(value = "/employer-sign-up-confimation", method = RequestMethod.GET)
	public String employerRegisterConfirmation() {
		return "employerRegisterConfirmation";
	}

	@RequestMapping(value = "/updateEmployer", method = RequestMethod.POST)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String update(
			 Model model, Principal principal, Employer emp) {
		try {
			//log.debug("update employer " + user.getUserName());
			//log.debug("company  profile " + employer.getCompanyName());
			//log.debug("company  profile " + employer.getPocEmail());
			Users user=this.usersService.getById(principal.getName());
			//Employer emp = this.employerService.getById(principal.getName());
			
			//password encode
			//String encodedPassword = passwordEncoder.encode(user.getPassword());
			//user.setPassword(encodedPassword);
			
			emp.setEmployerID(principal.getName());
			user.setRole("ROLE_EMP");
			user.setEmail(user.getEmail());
			user.setEnabled(1);
			user.setUserName(user.getUserName());
			user.setPassword(user.getPassword());
			user.setVerificationCode("");
			emp.setCurrentStatus(emp.getCurrentStatus());
			emp.setJobCountLimit(emp.getJobCountLimit());
			
			employerService.updateEmployer(emp);
			usersService.updateUser(user);
			model.addAttribute("messagge", "update Sucessfull");
			log.debug("update done");
			
			model.addAttribute("user",user);
			model.addAttribute("employer",emp);
			
		} catch (Exception e) {
			log.debug("update employer fail");
			Users user=this.usersService.getById(principal.getName());
			//Employer emp = this.employerService.getById(principal.getName());
			model.addAttribute("user",user);
			model.addAttribute("employer",emp);
			e.printStackTrace();
			
		}

		model.addAttribute("editemployer", "true");
		// return "redirect:/employer/company-profile";
		Users user=this.usersService.getById(principal.getName());
		//Employer emp = this.employerService.getById(principal.getName());
		model.addAttribute("user",user);
		model.addAttribute("employer",emp);
		return "editCompanyProfilePage";

	}

	/*
	 * This method will handle search candidate page
	 * */
	@RequestMapping(value = "/search-resume", method = RequestMethod.GET)
	public String searchResumePage(Model model,
			@ModelAttribute("searchForm") ResumeSearch resumeSearch,
			HttpSession session, HttpServletRequest request) {
		model.addAttribute("jobCategories",
				jobCategoriesService.getJobCategories());
		resumeSearch = new ResumeSearch();
		session.removeAttribute("searchForm");

		model.addAttribute("searchForm", resumeSearch);
		//new status 
		//Employer employer=null;
		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
		// Employer emp = new Employer(userName);
		Employer emp = this.employerService.getById(userName);
		//int jobCount = emp.getJobCountLimit();
		System.out.println("Employer Status: "+emp.getCurrentStatus());
		if(emp.getCurrentStatus().equals("Inactive")){
			//return"employerTempPage";
			//return "redirect:/employer/employer-temp-page";
			return "employerInactivePage";
		}
		return "searchResumePage";
	}
	
	/*
	 * This method will handle doc file search from search page
	 * 
	 * */

	@RequestMapping(value = "/search-doc-resume", method = RequestMethod.GET)
	public String searchDocResumePage(Model model) {
		model.addAttribute("jobCategories",
				jobCategoriesService.getJobCategories());
		return "searchDocResume";
	}

	/*@RequestMapping(value = "/search-doc-resume", method = RequestMethod.POST)
	public String searchResumeDocPage(
			@RequestParam("serachKeyword") String serachKeyword, Model model,
			Principal principal, HttpServletRequest request) {
		try {
			String root = request.getSession().getServletContext()
					.getRealPath("/");
			File path = new File(root + System.getProperty("file.separator")
					+ "WEB-INF" + System.getProperty("file.separator") + "cv");
			DocParser p = new DocParser();
			log.debug("CV path: " + path.getAbsolutePath());

			List<String> mactchUserList = p.getCVSearchResult(serachKeyword,
					path.getAbsolutePath());
			log.debug("Total Found User"+mactchUserList.size());
			for (String userName : mactchUserList) {
				log.debug("Found User :"+userName);
			}
			if (mactchUserList.size() > 0) {
				List<Candidate> cand = candidateService
						.getCandidateListByNames(mactchUserList);
				log.debug("size :" + cand.size());
				model.addAttribute("candList", cand);
			}
			// model.addAttribute("user",
			// usersService.getUserByName(principal.getName()));
			/*
			 * model.addAttribute("jobCategories",
			 * jobCategoriesService.getJobCategories());
			 */

	/*	} catch (Exception e) {
			e.printStackTrace();
		}
		return "resultResumePage";

	}*/
	
	/*@RequestMapping(value = "/search-doc-resume", method = RequestMethod.POST)
	public String searchResumeDocPage(
			@RequestParam("serachKeyword") String serachKeyword, Model model,
			Principal principal, HttpServletRequest request,@RequestParam("start") Integer start,
			@RequestParam("limit") Integer limit) {*/
	
	/*
	 * This method will search doc file and show it to the employer
	 * 
	 * */
	
	@RequestMapping(value = "/search-doc-resume", method = RequestMethod.POST)
	public String searchResumeDocPage(
			@RequestParam("serachKeyword") String serachKeyword, Model model,
			Principal principal, HttpServletRequest request) {
		try {
//			start.intValue();
//			limit.intValue();
			String root = request.getSession().getServletContext()
					.getRealPath("/");
			File path = new File(root + System.getProperty("file.separator")
					+ "WEB-INF" + System.getProperty("file.separator") + "cv");
			DocParser p = new DocParser();
			log.debug("CV path: " + path.getPath());

//			List<String> mactchUserList = p.getCVSearchResult(serachKeyword,
//					path.getAbsolutePath());
			List<String> mactchUserList = p.getCVSearchResult(serachKeyword,
					path.getPath());
			log.debug("Total Found User"+mactchUserList.size());
			for (String userName : mactchUserList) {
				log.debug("Found User :"+userName);
			}
			if (mactchUserList.size() > 0) {
				List<Candidate> cand = candidateService
						.getCandidateListByNames(mactchUserList);
				log.debug("size :" + cand.size());
				model.addAttribute("candList", cand);
			}
			model.addAttribute("totalResult", mactchUserList.size());
			// pagination control
			/*model.addAttribute("totalRecord",
					mactchUserList.size());
			String recordRange = ((start * limit) + 1) + " - "
					+ ((start * limit) + limit);
			model.addAttribute("recordRange", recordRange);
			model.addAttribute("resultStartPos",  ((start * limit) + 1));
			int backpage = 0;
			int nextpage = 0;
			boolean displayBack = true;
			boolean displayNext = true;
			boolean displayPagingControl = true;
			if (mactchUserList.size() == 0) {
				displayPagingControl = false;
			} else {
				if (start != 0) {
					backpage = start - 1;
				} else {
					displayBack = false;
				}
				if ((start * limit) + limit >= mactchUserList.size()) {
					displayNext = false;
					recordRange = ((start * limit) + 1) + " - "
							+ ((start * limit) + mactchUserList.size());
					model.addAttribute("recordRange", recordRange);
					model.addAttribute("resultStartPos",  ((start * limit) + 1));
					
				} else {
					nextpage = start + 1;
				}

			}

			model.addAttribute("backpage", backpage);
			model.addAttribute("nextpage", nextpage);
			model.addAttribute("displayBack", displayBack);
			model.addAttribute("displayNext", displayNext);
			model.addAttribute("displayPagingControl", displayPagingControl);
			// end pagination control
			
			// model.addAttribute("user",
			// usersService.getUserByName(principal.getName()));
			/*
			 * model.addAttribute("jobCategories",
			 * jobCategoriesService.getJobCategories());
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "resultResumePage";
	}

	/*
	 * This method will shwo resume search result
	 * */
	@RequestMapping(value = "/search-resume-result")
/*	public String searchResume(
			@ModelAttribute("searchForm") ResumeSearch resumeSearch,
			Model model, Principal principal,
			@RequestParam("start") Integer start,
			@RequestParam("limit") Integer limit,HttpServletRequest request) {
		resumeSearch.setStatrt(start.intValue());
		resumeSearch.setLimit(limit.intValue());*/

	public String searchResume(
			@ModelAttribute("searchForm") ResumeSearch resumeSearch,
			Model model, Principal principal,HttpServletRequest request) {
		//resumeSearch.setStatrt(start.intValue());
		//resumeSearch.setLimit(limit.intValue());

	/*	int backpage = 0;
		int nextpage = 0;
		boolean displayBack = true;
		boolean displayNext = true;
		boolean displayPagingControl = true;
		
		//for pagination
		int backpage1 = 0;
		int nextpage1 = 0;
		boolean displayBack1 = true;
		boolean displayNext1 = true;
		boolean displayPagingControl1 = true;*/
		
		// System.out.println("EmployerController.searchResume()" +
		// resumeSearch.getMalsalaryLevel().size());
//		ResumeSearchResult resumeSearchResult = employerService
//				.serachResume(resumeSearch,request);
		List<Candidate> resumeSearchResult = employerService
				.serachResume(resumeSearch,request);
		List<Candidate> cand = resumeSearchResult;
		ArrayList<Candidate> result = new ArrayList<Candidate>();
		
		
		int reqDist = (int) (resumeSearch.getDistance() * 1.609344 * 1000);

		if (!resumeSearch.getLocation().equals("")) {
			try {
				for (int i = 0; i < cand.size(); i++) {
					Integer distance = new GeoDistanceService().distanceMatrix(
							resumeSearch.getLocation(),
							cand.get(i).getCandidateZip()).getRows()[0]
							.getElements()[0].getDistance().getValue();
					if (distance <= reqDist) {
						log.info(i + ". Distance: " + distance + " Req Dist: "
								+ reqDist);
						result.add(cand.get(i));
					}
					
	
					}

			//	}
				
				// pagination control
				/*displayPagingControl = false;
				model.addAttribute("totalRecord1",
						result.size());
				model.addAttribute("totalRecord",
						result.size());
				String recordRange1 = ((start * limit) + 1) + " - "
						+ ((start * limit) + limit);
				model.addAttribute("recordRange1", recordRange1);
				model.addAttribute("resultStartPos1",  ((start * limit) + 1));
				log.info("Result counter :"+result.size());
				/*int backpage = 0;
				int nextpage = 0;
				boolean displayBack = true;
				boolean displayNext = true;
				boolean displayPagingControl = true;*/
				/*if (result.size() == 0) {
					displayPagingControl1 = false;
				} else {
					if (start != 0) {
						backpage1 = start - 1;
					} else {
						displayBack1 = false;
					}
					if ((start * limit) + limit >= result
							.size()) {
						displayNext1 = false;
						recordRange1 = ((start * limit) + 1) + " - "
								+ ((start * limit) + result.size());
						model.addAttribute("recordRange1", recordRange1);
						model.addAttribute("resultStartPos1",  ((start * limit) + 1));
						
					} else {
						nextpage1 = start + 1;
					}
				}
				/*model.addAttribute("backpage1", backpage1);
				model.addAttribute("nextpage1", nextpage1);
				model.addAttribute("displayBack1", displayBack1);
				model.addAttribute("displayNext1", displayNext1);
				model.addAttribute("displayPagingControl1", displayPagingControl1);*/
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("message", "No Result To show");
			}
		}// else if(result.add(cand.get(i))==null){
			// return "resultResumePage";
		// }
		else {
			for (int i = 0; i < cand.size(); i++) {
				result.add(cand.get(i));
			}

		}
		if (result == null) {
			return "resultResumePage";
		}
		
		// pagination control
		//int number=6;
	/*	model.addAttribute("totalRecord",
				result.size());
		String recordRange = ((start * limit) + 1) + " - "
				+ ((start * limit) + limit);
		model.addAttribute("recordRange", recordRange);
		model.addAttribute("resultStartPos",  ((start * limit) + 1));
		/*int backpage = 0;
		int nextpage = 0;
		boolean displayBack = true;
		boolean displayNext = true;
		boolean displayPagingControl = true;*/
		/*if (cand.size() == 0) {
			displayPagingControl = false;
		} else {
			if (start != 0) {
				backpage = start - 1;
			} else {
				displayBack = false;
			}
			if ((start * limit) + limit >= resumeSearchResult
					.size()) {
				displayNext = false;
				recordRange = ((start * limit) + 1) + " - "
						+ ((start * limit) + cand.size());
				model.addAttribute("recordRange", recordRange);
				model.addAttribute("resultStartPos",  ((start * limit) + 1));
				
			} else {
				nextpage = start + 1;
			}
		}
		
			log.info("Back Page is "+backpage);
			log.info("Next page is "+nextpage);
			log.info("Display Back "+displayBack);
			log.info("Display Next"+displayNext);
			log.info("Total record is here :"+result.size());

		model.addAttribute("backpage", backpage);
		model.addAttribute("nextpage", nextpage);
		model.addAttribute("displayBack", displayBack);
		model.addAttribute("displayNext", displayNext);
		model.addAttribute("displayPagingControl", displayPagingControl);*/

		// end pagination control
		

//		 else{
//		return "resultResumePage";
//		 }
//		PaginationInput pagination = new PaginationInput();
//		pagination.setPageNumber(1);
//		pagination.setEntriesPerPage(3);
//		
		
		log.info("Result counter :"+result.size());
		model.addAttribute("candList", result);
		model.addAttribute("totalResult", result.size());
		// model.addAttribute("user",
		// usersService.getUserByName(principal.getName()));
		
		model.addAttribute("jobCategories",
				jobCategoriesService.getJobCategories());

		return "resultResumePage";
	}
	
	/*
	 * This method will show job list to by employer 
	 * */

	@RequestMapping(value = "/job-list-by-employer", method = RequestMethod.GET)
	public String jobListByCompany(Principal principal, Model model) {
		log.info("Inside thre JobByEmployer");
		// if(ascend.equals(asc)){
		model.addAttribute("joblist",
				jobService.jobsByEmploye(principal.getName()));
		// }else{
		// model.addAttribute("joblist",
		// jobService.jobsByEmployerByOrder(principal.getName()));
		// }
		
		//new status
		//Employer employer=null;
		//Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
		// Employer emp = new Employer(userName);
		Employer emp = this.employerService.getById(userName);
		System.out.println("Employer Status: "+emp.getCurrentStatus());
		if(emp.getCurrentStatus().equals("Inactive")){
			//return "employerTempPage";
			//return "redirect:/employer/employer-temp-page";
			return "employerInactivePage";
		}

		return "jobListByEmployer";
	}
/*
 * This method will show job list by order to the job-seeker
 * */
	@RequestMapping(value = "/job-list-by-employer-by-order", method = RequestMethod.GET)
	public String jobListByCompanyByOrder(Principal principal, Model model) {
		log.info("Inside thre JobByEmployer");
		// if(ascend.equals(asc)){
		model.addAttribute("joblist",
				jobService.jobsByEmployerByOrder(principal.getName()));
		// }else{
		// model.addAttribute("joblist",
		// jobService.jobsByEmployerByOrder(principal.getName()));
		// }

		return "jobListByEmployer";
	}

/*
 * This method will show job by date
 * 
 * */	
	@RequestMapping(value="/refreshAllJob" ,method = RequestMethod.GET)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String refreshAllJob(Model model,@ModelAttribute Jobs job,Principal principal) {
		int day;
		 //day= this.jobService.refreshAllJob(jobId);
		List<Jobs> candi = jobService.jobsByEmploye(principal.getName());
		
		for(int i=0;i<candi.size();i++){
		System.out.println("Job number "+i+"is"+candi.get(i).getJobTitle());
			
		 Jobs j = this.jobService.getJobById(candi.get(i).getJobID());
		 		 if(j.getDays()<=0){
		 			try{
		 			j.setCurrentDate(j.getCurrentDate());
					j.setLastDate(j.getLastDate());
					/*j.setJobCategoryName(job.getJobCategoryName());
					j.setJobTitle(job.getJobTitle());
					j.setJobKeyWord(job.getJobKeyWord());
					j.setJobCity(job.getJobCity());
					j.setJobState(job.getJobState());
					j.setJobZip(job.getJobZip());
					j.setJobRate(job.getJobRate());
					j.setJobDuration(job.getJobDuration());
					j.setJobSummary(job.getJobSummary());*/
					
					this.jobService.update(j);
		 			} catch(Exception e) {
		 				e.printStackTrace();
		 			}
					}else{
//			 int newDay;
//			 newDay=30-day;	
			 try{
			 j.setCurrentDate(new Date());
			 j.setLastDate(j.getRemainingDate());
			/* j.setJobCategoryName(job.getJobCategoryName());
			 j.setJobTitle(job.getJobTitle());
			 j.setJobKeyWord(job.getJobKeyWord());
			 j.setJobCity(job.getJobCity());
			 j.setJobState(job.getJobState());
			 j.setJobZip(job.getJobZip());
			 j.setJobRate(job.getJobRate());
			 j.setJobDuration(job.getJobDuration());
			 j.setJobSummary(job.getJobSummary());*/
					 
			 this.jobService.update(j);
			 } catch(Exception e) {
					e.printStackTrace();
				}

		 }
		}
		
		return "redirect:/employer/job-list-by-employer"; 
	}

	/*
	 * This method will show the employer edit option for posted job
	 * employer can edit job from here
	 * */

	@RequestMapping(value = "edit-posted-job", method = RequestMethod.GET)
	public String editPostedJob(@RequestParam("id") Long jobId, Model model) {
		model.addAttribute("jobCategories",
				jobCategoriesService.getJobCategories());
		model.addAttribute("promptJob", jobService.getJobById(jobId));
		return "postedJobEdit";
	}
	
	/*
	 * This method will show posted job edit option
	 * 
	 * */
	
	@RequestMapping(value = "/jobjAppliedNumber", method = RequestMethod.GET)
	public String jobAppliedNumber(@RequestParam("id") Long jobId, Model model) {
		model.addAttribute("jobCategories",
				jobCategoriesService.getJobCategories());
		model.addAttribute("promptJob", jobService.getJobById(jobId));
		return "postedJobEdit";
	}
/*
 * Show profile to the employer
 * 
 * */
	@RequestMapping(value = "/company-profile")
	public String companyProfile(Principal principal, Model model) {
		log.info("Login wuser " + principal.getName());
		log.info("Email size is : "+usersService.getCandidateEmailList().size());
		Employer employer = this.employerService.getById(principal.getName());
		model.addAttribute("user",
				usersService.getUserByName(principal.getName()));
		model.addAttribute("employer", employer);
		return "viewCompanyPage";
	}

	/*
	 * Employer can edit their profile 
	 * 
	 * */
	@RequestMapping(value = "/edit-company-profile", method = RequestMethod.GET)
	public String editCompanyProfilePage(@ModelAttribute Users user,Principal principal, Model model) {

		model.addAttribute("user",
				usersService.getUserByName(principal.getName()));
		model.addAttribute("employer",
				employerService.getById(principal.getName()));
		Employer emp = this.employerService.getById(principal.getName());
		emp.setCurrentStatus(emp.getCurrentStatus());
		emp.setJobCountLimit(emp.getJobCountLimit());
		model.addAttribute("user",user);
		model.addAttribute("employer",emp);
		return "editCompanyProfilePage";
	}

	/*
	 * Show candidate profile 
	 * */
	@RequestMapping(value = "/candidate-profile")
	public String candidateProfilePage(Model model,
			@RequestParam("CandidateID") final String CandId) {
		Candidate candidate = this.candidateService.getById(CandId);
		Users user=this.usersService.getById(CandId);
		model.addAttribute("user",user);
		model.addAttribute("candidate", candidate);
		return "viewCandidateProfilePage";
	}
	
	/*
	 * This method will show candidate profile search page
	 * 
	 * */
	@RequestMapping(value = "/candidate-profile-search")
	public String candidateProfilePageSearch(Model model,
			@RequestParam("CandidateID") final String CandId) {
		Candidate candidate = this.candidateService.getById(CandId);
		Users user=this.usersService.getById(CandId);
		model.addAttribute("user",user);
		model.addAttribute("candidate", candidate);
		return "viewCandidateProfileSearch";
	}
	
	/*
	 * This method will show form for posting job
	 * */

	@RequestMapping(value = "/post-job", method = RequestMethod.GET)
	public String postJobPage(Model model,HttpServletRequest request) {

		model.addAttribute("jobCategories",
				jobCategoriesService.getJobCategories());
		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
		// Employer emp = new Employer(userName);
		Employer emp = this.employerService.getById(userName);
		if(emp.getCurrentStatus().equals("Inactive")){
			//return "employerTempPage";
			return "employerInactivePage";
		}
		return "postJobPage";
	}

	
	/*
	 * This method will post job 
	 * */
	@RequestMapping(value = "/postJob", method = RequestMethod.POST)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
//	public String createJob(@ModelAttribute Jobs job, Model model,
//			@RequestParam("lastDate1") final String lastDate1,
//			HttpServletRequest request) {
		
	public String createJob(@ModelAttribute Jobs job, Model model,
			HttpServletRequest request) {
		//new status 
		//Employer employer=null;
		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
		// Employer emp = new Employer(userName);
		Employer emp = this.employerService.getById(userName);
		int jobCount = emp.getJobCountLimit();

		try {
			// log.debug("registration candidate11");

			DateFormat formatter;
			Date date = null;

//			formatter = new SimpleDateFormat("MM-dd-yyyy");
//			date = (Date) formatter.parse(lastDate1);
			
			formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
			//date = (Date) formatter.parse(lastDate1);
			date= new Date();
			String strDate = formatter.format(date);
			
			
			//add 30 day 
			Calendar c = Calendar.getInstance();
			c.setTime(formatter.parse(strDate));
			c.add(Calendar.DATE, 30);
			
			Date newDate=c.getTime();
			String lastDate=formatter.format(newDate);
			
			log.debug("Start Date is "+strDate);
			log.debug("Last Date is "+lastDate);

			//Principal principal = request.getUserPrincipal();

			if (principal != null) {
//				String userName = principal.getName();
//				// Employer emp = new Employer(userName);
//				Employer emp = this.employerService.getById(userName);
//				int jobCount = emp.getJobCountLimit();
				if (jobCount > 0) {
					log.info("Job Count before: " + jobCount);
					jobCount = jobCount - 1;
					emp.setJobCountLimit(jobCount);
					log.info("Job Count After: " + jobCount);
					// emp.setJobCountLimit(emp.getJobCountLimit() - 1);
					//new
					job.setCurrentDate(formatter.parse(strDate));
					//new
					job.setLastDate(formatter.parse(formatter.format(c.getTime())));
					//job.setLastDate(formatter.parse(lastDate));
					
					//view applicant
					job.setJobApplicant(0);
					job.setEmployer(emp);
					jobService.save(job);
					model.addAttribute("postnewjob", "true");
					model.addAttribute("message", "Job Post Successful"); // {$messagge}
					model.addAttribute("jobCategories",
							jobCategoriesService.getJobCategories());
				} else {
					model.addAttribute("message",
							"Your Job posting limit has been exceded"); // {$messagge}
					model.addAttribute("postnewjobwarn", "true");
				}
				// return "redirect:/employer/jobListByCompany";
				
				//new status 
				if(emp.getCurrentStatus().equals("Inactive")){
					//return "employerTempPage";
					//return "redirect:/employer/employer-temp-page";
					return "employerInactivePage";
				}
				return "postJobPage";
			}

		} catch (Exception e) {
			// log.debug("registration candidate fail");
			e.printStackTrace();
			model.addAttribute("message", "Job Post fail"); // {$messagge}
			model.addAttribute("postnewjoberror", "true");

		}

		// return "redirect:/post-job";
		//new status
		System.out.println("Employer Status: "+emp.getCurrentStatus());
		if(emp.getCurrentStatus().equals("Inactive")){
			//return "employerTempPage";
			System.out.println("Redirect This Page");
			return "employerInactivePage";
		}
		
		return "postJobPage";

	}
	
	@RequestMapping(value="/password-encode", method = RequestMethod.GET)
	public String passwordEncode(Model model) {
		try{
			List<Users> user= this.usersService.getPasswordList();
			
			for(int i=0;i<user.size();i++){
				System.out.println("Password is"+i+"number : "+user.get(i).getPassword());
			}

			
			//emailObj.setRecipt(usersService.getCandidateEmailList().toArray(new String[usersService.getCandidateEmailList().size()]));
			for(int i=0;i< user.size();i++){
				Users usr=this.usersService.getById(user.get(i).getUserName());
				String encodedPassword = passwordEncoder.encode(user.get(i).getPassword());
				usr.setPassword(encodedPassword);
				this.usersService.updateUser(usr);

			}
			log.debug("activation mail sent");


		}catch(Exception e){
			e.printStackTrace();
		}
		return "jobListByEmployer";
	}

}
