package com.hubzone.controller;

/*
 * This class will handle job related operation. 
 * Update job
 * Delete job
 * Search job 
 * Show job list
 * 
 * */

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import com.geoservice.GeoDistanceService;
import com.hubzone.dao.JobService;

import com.hubzone.model.JobCategories;
import com.hubzone.model.Jobs;
import com.hubzone.model.JobsApplied;
import com.hubzone.model.States;

import com.hubzone.utility.JobSearch;

@Controller
@RequestMapping(value = "jobs", produces = "application/json")
public class JobController {
	
	Logger log = Logger.getLogger(JobController.class);
	
	@Autowired private JobService jobService;
	//@Autowired private JobAppliedService jobAppliedService;
	
	@ModelAttribute("states")
	public List<States> getAllState(){
		return jobService.countJobByStates();
	}
	
	@RequestMapping(value="/job-temp-page", method=RequestMethod.GET)
	public String viewCompanyPage() {
		return "viewJobTempPage";
	}
	
	@RequestMapping(value="updateJob", method = RequestMethod.POST)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
//	public String update(@ModelAttribute Jobs job, Model model,@RequestParam("lastDate1") final String lastDate1) {
	public String update(@ModelAttribute Jobs job, Model model){
		log.info("ID:" + job.getJobID());
		try {
			Jobs j = this.jobService.getJobById(job.getJobID());
			j.setJobCategoryName(job.getJobCategoryName());
			j.setJobTitle(job.getJobTitle());
			j.setJobKeyWord(job.getJobKeyWord());
			j.setJobCity(job.getJobCity());
			j.setJobState(job.getJobState());
			j.setJobZip(job.getJobZip());
			j.setJobRate(job.getJobRate());
			j.setJobDuration(job.getJobDuration());
			j.setJobSummary(job.getJobSummary());
			j.setLastDate(job.getLastDate());
			
//			DateFormat formatter;
//			Date date = null;
//
//			formatter = new SimpleDateFormat("MM-dd-yyyy");
//			date = (Date) formatter.parse(lastDate1);
//           j.setLastDate(date);
			
			this.jobService.update(j);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/employer/job-list-by-employer";
	}
	
	@RequestMapping(value="deleteJob")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String delete(@RequestParam("id") Long jobId, Model model) {
		this.jobService.delete(jobId);
		
		return "redirect:/employer/job-list-by-employer"; 
	}
	
	@RequestMapping(value="refreshJob")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String refresh(@RequestParam("id") Long jobId, Model model) {
		this.jobService.refresh(jobId);
		
		return "redirect:/employer/job-list-by-employer"; 
	}
	
/*	@RequestMapping(value="/refreshAllJob" ,method = RequestMethod.GET)
	//@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String refreshAllJob(@RequestParam("id") Long jobId, Model model,@ModelAttribute Jobs job) {
		int day;
		 //day= this.jobService.refreshAllJob(jobId);
		 Jobs j = this.jobService.getJobById(jobId);
		 		 if(j.getDays()==0){
		 			j.setCurrentDate(j.getCurrentDate());
					j.setLastDate(j.getLastDate());
					}else{
//			 int newDay;
//			 newDay=30-day;	 
			 j.setCurrentDate(new Date());
			 j.setLastDate(j.getRemainingDate());

		 }
		
		return "redirect:/employer/job-list-by-employer"; 
	}*/
		
	
	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public String jobListByCategory(@RequestParam("cat") final String cat,
			Model model,Jobs jobID) throws UnsupportedEncodingException {
		log.debug("decode :"+cat);
		/*List<JobsApplied> job;
		job=jobAppliedService.candList(jobID);
		model.addAttribute("candidateNumber",job.size());*/
		//model.addAttribute("states", jobService.countJobByStates());
		model.addAttribute("jobCategories", jobService.countJobByCategory());
		model.addAttribute("joblist",
				jobService.getJobListByCategory(new JobCategories(cat)));
		return "jobList";

	}

	private JobSearch jobSearchKey(String search) {
		JobSearch jobSearch = new JobSearch();

		Jobs job = new Jobs();
		job.setJobTitle(search);
		job.setJobCategoryName(search);
		job.setJobState(search);
		job.setJobKeyWord(search);
		jobSearch.setJob(job);
		return jobSearch;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String jobSearch(@RequestParam("search") final String search,
			Model model) {
		
		model.addAttribute("jobCategories", jobService.countJobByCategory());

		model.addAttribute("joblist", jobService.jobsSearch(jobSearchKey(search)));

		return "jobSearch";

	}
	@RequestMapping(value = "/advanceSearch", method = RequestMethod.GET)
	public String jobAdvanceSearch(Model model) {
	
		model.addAttribute("jobCategories", jobService.countJobByCategory());

		return "advanceJobSearch";
	}
	@RequestMapping(value = "/advanceSearch", method = RequestMethod.POST)
	public String jobAdvanceSearch(@ModelAttribute JobSearch jobSearch, Model model) {
		
		List<Jobs> job=jobService.searchJob(jobSearch);
		ArrayList<Jobs> result = new ArrayList<Jobs>();
		int reqDist = (int) (jobSearch.getDistance() * 1.609344 * 1000);
		log.info("Result Size: "+job.size() +" Distance: "+ reqDist);
		if(!jobSearch.getLocation().equals("")) {
			try {
				for(int i = 0; i < job.size(); i++) {
					Integer distance = new GeoDistanceService().distanceMatrix(jobSearch.getLocation(), job.get(i).getJobZip()).getRows()[0].getElements()[0].getDistance().getValue();					
					if(distance <= reqDist) {
						log.info(i+". Distance: " + distance+ " Req Dist: "+ reqDist);
						result.add(job.get(i));
					}
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		} else {			
			for(int i = 0; i < job.size(); i++) {
				result.add(job.get(i));					
			}			
		}		
		model.addAttribute("joblist", result);
		return "advanceJobSearchResult";
	}
	
	

	@RequestMapping(value = "/state/{state}", method = RequestMethod.GET)
	public String jobListByState(@PathVariable("state") final String state,
			Model model) {

		model.addAttribute("jobCategories", jobService.countJobByCategory());
		model.addAttribute("joblist",
				jobService.getJobListByState(new States(state)));
		return "jobList";

	}

	@RequestMapping(value = "/details/{jobId}", method = RequestMethod.GET)
	public String jobDetails(@PathVariable("jobId") final Long jobId,
			Model model) {
		
		model.addAttribute("jobCategories", jobService.countJobByCategory());
		model.addAttribute("job", jobService.getJobDetails(new Jobs(jobId)));
		return "jobDetails";

	}

	@RequestMapping(value = "/jobs/applay/jobId/{jobId}/candId/{candid}", method = RequestMethod.GET)
	public String createJobs(@PathVariable("jobId") final Long jobId,
			Model model) {

		model.addAttribute("jobCategories", jobService.countJobByCategory());
		model.addAttribute("job", jobService.getJobDetails(new Jobs(jobId)));
		return "jobDetails";

	}
	
	@RequestMapping(value = "/employer-registration-temp", method = RequestMethod.GET)
	public String employerTempPage() {
		return "employerTempPage";
	}

	
}
