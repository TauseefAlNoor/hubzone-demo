package com.hubzone.controller;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;


import java.security.Principal;
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
import com.hubzone.dao.JobAppliedService;
import com.hubzone.dao.JobService;

import com.hubzone.model.Candidate;
import com.hubzone.model.Employer;
import com.hubzone.model.JobCategories;
import com.hubzone.model.Jobs;
import com.hubzone.model.States;
import com.hubzone.model.JobsApplied;


import com.hubzone.utility.JobSearch;


@Controller
@RequestMapping(value = "jobApplied", produces = "application/json")
public class JobAppliedController {
	
	Logger log = Logger.getLogger(JobAppliedController.class);
	
	@Autowired 
	JobAppliedService jobAppliedService;
	@Autowired
	JobService jobService;
	


}
