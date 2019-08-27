package com.hubzone.controller;

/**
 * 
 * This class which will handle download resume option
 * 
 * 
 * @author Tauseef
 *
 */

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;

import com.hubzone.model.Candidate;
import com.hubzone.dao.CandidateService;


@Controller
@RequestMapping(value = "/download")
public class DownloadController {
	
	@Autowired
	CandidateService candidateService;
	
	@RequestMapping(value = "/downloadCv",method = RequestMethod.POST)
	 public byte[] download(@RequestParam("user") String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Candidate cand = this.candidateService.getById(id);
		File resumeFile = new File(cand.getResumeFileLocation());
		byte[] bytes = FileCopyUtils.copyToByteArray(resumeFile);
		
		response.setHeader("Content-Disposition", "attachment; filename=\"" + resumeFile.getName() + "\"");
	    response.setContentLength(bytes.length);
	    response.setContentType("pdf/doc");
		
		return bytes;
	}
}
