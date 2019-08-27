package com.hubzone.controller;
/*
 * This class will handle login option
 * for both employer and job-seeker
 * */
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hubzone.dao.CandidateService;
import com.hubzone.dao.EmployerService;
import com.hubzone.dao.UsersService;
import com.hubzone.model.Candidate;
import com.hubzone.model.Employer;

import java.security.Principal;


@Controller

public class LoginController {
	Logger log = Logger.getLogger(LoginController.class);
	@Autowired
	@Qualifier("authenticationManager")
	AuthenticationManager authenticationManager;
	
	@Autowired
	SecurityContextRepository repository;
	
	@Autowired
	RememberMeServices rememberMeServices;
	
	@Autowired
	UsersService userService;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	EmployerService employerService;
	
	
	@RequestMapping(value="/login", method = RequestMethod.GET)	
	public String login(ModelMap model, @RequestParam("param") final String loginType,Principal principal) {
		log.info("Login TYpe: "+loginType);
		if(loginType.equals("emp")) {
			model.addAttribute("loginHeader", "Employer Login");
			model.addAttribute("loginFooter1", "true");
		} else if(loginType.equals("cand")) {
			model.addAttribute("loginHeader", "Job Seeker Login");
			model.addAttribute("loginFooter2", "true");
		}else if(loginType.equals("adm")){
			model.addAttribute("loginHeader","Admin Login");
			model.addAttribute("loginFooter", "true");
		}

		return "login";
	}

	@RequestMapping(value="/loginRediect",method=RequestMethod.GET)
	public String performLogin() 
	{
		
		try {
			
			Collection<? extends GrantedAuthority>  grantedAuthorityCollection =SecurityContextHolder.getContext().getAuthentication().getAuthorities();
			for(GrantedAuthority granAuth:grantedAuthorityCollection){
				if( granAuth.getAuthority().equals("ROLE_EMP")){
					log.debug("User role  :ROLE_EMP");
//					return "redirect:/employer/company-profile";
					return "redirect:/employer/search-resume";
				}else if( granAuth.getAuthority().equals("ROLE_CAN")){
					log.debug("User role  :ROLE_CAN");
					return "redirect:/candidate/candidate-profile";
				}else if(granAuth.getAuthority().equals("ROLE_ADM")){
					return "redirect:/admin/admin-profile";
			}
				
			}
			log.debug("User role  :ADmin");
			
		} catch (BadCredentialsException ex) {
			return "redirect:/login";
		}
		return "";
	}
	
	@RequestMapping(value="/adminlogin", method=RequestMethod.GET)
	public String adminPage(ModelMap model) {
		
		model.addAttribute("loginHeader","Admin Login");
		return "login";
	}
	
	
	@RequestMapping(value="/candidatelogin", method=RequestMethod.GET)
	public String candidatePage(ModelMap model,Principal principal) {
		
		//System.out.println("Login User Name is : "+principal.getName());
		
		if(principal!=null && userService.checkUser(principal.getName()).equals("ROLE_CAN")){
			Candidate candidate = this.candidateService
					.getById(principal.getName());
			log.info("candiate.firstName " + candidate.getCandidateID());
			model.addAttribute("candidate", candidate);
			if (candidate.getResumeFileLocation() != null) {
				model.addAttribute("candicateResume", candidate);
			}
			return "viewCandidateProfilePage";
		}else if(principal!=null && userService.checkUser(principal.getName()).equals("ROLE_EMP")){
			Employer employer = this.employerService
					.getById(principal.getName());
			log.info("candiate.firstName " + employer.getEmployerID());
			model.addAttribute("employer", employer);
			return "viewCompanyPage";
		}else{
			return "redirect:/login?param=cand";
		}
		//return "login";
	}
	
	
	@RequestMapping(value="/employerlogin", method=RequestMethod.GET)
	public String employerPage(ModelMap model,Principal principal) {
		if(principal!=null && userService.checkUser(principal.getName()).equals("ROLE_CAN")){
			Candidate candidate = this.candidateService
					.getById(principal.getName());
			log.info("candiate.firstName " + candidate.getCandidateID());
			model.addAttribute("candidate", candidate);
			if (candidate.getResumeFileLocation() != null) {
				model.addAttribute("candicateResume", candidate);
			}
			return "viewCandidateProfilePage";
		}else if(principal!=null && userService.checkUser(principal.getName()).equals("ROLE_EMP")){
			Employer employer = this.employerService
					.getById(principal.getName());
			log.info("candiate.firstName " + employer.getEmployerID());
			model.addAttribute("employer", employer);
			return "viewCompanyPage";
		}else{
			return "redirect:/login?param=emp";
		}
		
	}

	
	@RequestMapping(value="/loginfailed", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {
		model.addAttribute("error", "true");
		return "login";
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {		
		return "login"; 
	}
}
