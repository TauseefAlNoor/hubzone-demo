package com.hubzone.controller;

/*This class will show some jsp page for general user
 * 
 * 
 * 
 * */



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.hubzone.dao.JobCategoriesService;
import com.hubzone.dao.JobService;
import com.hubzone.dao.StatesService;




@Controller
public class HomeController {
	@Autowired
	JobService jobService;
	@Autowired
	StatesService statesService;
	@Autowired
	JobCategoriesService jobCategoriesService;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home() {
		
		return "welcome";
	}
	@RequestMapping(value="/jobCategory", method=RequestMethod.GET)
	public String jobCategory(Model model){
		model.addAttribute("catSep", ((int)jobService.countJobByCategory().size()/2));
		model.addAttribute("catEnd",jobService.countJobByCategory().size());
		
		model.addAttribute("states", jobService.countJobByStates());
		model.addAttribute("jobCategories", jobService.countJobByCategory());
		return "jobCategories";
	}
	
	@RequestMapping(value="/secure", method=RequestMethod.GET)
	public void secure() {}
	
	@RequestMapping(value="/links", method=RequestMethod.GET)
	public void links() {}
	
	@RequestMapping(value="/user-registration", method=RequestMethod.GET)
	public String employerRegistrationPage(Model model) {
		model.addAttribute("states", statesService.getStateList());
		return "employerRegistrationPage";
	}
	
//	@RequestMapping(value="/adminlogin", method=RequestMethod.GET)
//	public String adminPage() {
//		return "redirect:/admin/admin-profile";
//	}
	
	@RequestMapping(value="/candidate-registration", method=RequestMethod.GET)
	public String candidateRegistrationPage(Model model) {
		model.addAttribute("states", statesService.getStateList());
		model.addAttribute("jobCategories", jobCategoriesService.getJobCategories());
		return "candidateRegistrationPage";
	}
	


	
	@RequestMapping(value="/view-company-profile", method=RequestMethod.GET)
	public String viewCompanyPage() {
		return "viewCompanyPage";
	}
	
	@RequestMapping(value="/about-us", method=RequestMethod.GET)
	public String aboutUsPage() {
		return "aboutUsPage";
	}
	
	@RequestMapping(value="/contact-us", method=RequestMethod.GET)
	public String contactUsPage() {
		return "contactUsPage";
	}
	
	@RequestMapping(value="/frequently-asked-questions", method=RequestMethod.GET)
	public String frequentlyAskedQuestions() {
		return "frequentlyAskedQuestions";
	}
	
	@RequestMapping(value="/privacy-statement", method=RequestMethod.GET)
	public String privacyStatementPage() {
		return "privacyStatementPage";
	}
	
	@RequestMapping(value="/terms-and-conditions", method=RequestMethod.GET)
	public String termsAndConditionPage() {
		return "termsAndConditionPage";
	}
	
	@RequestMapping(value="/search-job", method=RequestMethod.GET)
	public String searchJobPage() {
		return "searchJobPage";
	}
	
	@RequestMapping(value="/view-candidate-profile", method=RequestMethod.GET)
	public String viewCandidateProfilePagePage() {
		return "viewCandidateProfilePage";
	}
	
	
	
//	@RequestMapping(value="/edit-candidate-profile", method=RequestMethod.GET)
//	public String editCandidateProfilePage() {
//		return "editCandidateProfilePage";
//	}
	
}

