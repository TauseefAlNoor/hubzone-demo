package com.hubzone.controller;

/*
 * This class will handle resume upload option for the job-seeker
 * */

import java.io.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.apache.commons.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;

import com.hubzone.model.Candidate;
import com.hubzone.model.Users;
import com.hubzone.utility.FileUtility;
import com.hubzone.dao.CandidateService;
import java.security.Principal;

@Controller
@RequestMapping(value = "/upload")
public class UploadController {

	@Autowired
	CandidateService candidateService;

	@RequestMapping(value = "/uploadCv", method = RequestMethod.GET)
	public String getUploadForm(Model model, Principal principal) {
		model.addAttribute(new UploadItem());

		Candidate candidate = candidateService.getById(principal.getName());
		if (candidate.getResumeFileLocation() != null
				|| !candidate.getResumeFileLocation().equals("")) {
			model.addAttribute("candicateResume", candidate);
		}

		return "uploadCv";
	}

	@RequestMapping(value = "/downloadCv", method = RequestMethod.GET)
	public HttpEntity<byte[]> downloadCv(Model model, Principal principal,
			HttpServletRequest request) {
		try {
//			if(request.getCharacterEncoding()==null){
//				request.setCharacterEncoding("utf-8");
//			}
			Candidate candidate = candidateService.getById(principal.getName());
			String root = request.getSession().getServletContext()
					.getRealPath("/");
//			response.setCharacterEncoding("utf-8");
			return getResumeContent(candidate, root);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	@RequestMapping(value = "/downloadCandidateCv", method = RequestMethod.GET)
	public HttpEntity<byte[]> downloadCandidateCv(Model model, @RequestParam("user") String user,
			HttpServletRequest request) {
		try {

//			if(request.getCharacterEncoding()==null){
//				request.setCharacterEncoding("utf-8");
//			}
			Candidate candidate = candidateService.getById(user);
			String root = request.getSession().getServletContext()
					.getRealPath("/");
//			request.setCharacterEncoding("utf-8");
//			response.setCharacterEncoding("utf-8");
			return getResumeContent(candidate, root);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	private HttpEntity<byte[]> getResumeContent(Candidate candidate,
			String rootPath) throws IOException {
		
		File path = new File(rootPath + "/WEB-INF/cv");
		File uploadedFile = new File(path + "/"
				+ candidate.getResumeFileLocation());
		
//		File oldfile= new File(rootPath);
//		File newfile= new File(candidate.getCandidateFirstName()+"_"+candidate.getCandidateLastName()+"_resume."+FileUtility.getFileExtention(rootPath));
//		oldfile.renameTo(newfile);


		FileInputStream fstream = new FileInputStream(uploadedFile);
		// Get the object of DataInputStream
		DataInputStream in = new DataInputStream(fstream);

		byte[] fileData = new byte[(int) uploadedFile.length()];
		in.readFully(fileData);
		in.close();
		HttpHeaders header = new HttpHeaders();
		// header.setContentType(new MediaType("application", "pdf"));
		header.set("contentType", candidate.getResumeContenttype());
//		header.set("Content-Disposition",
//				"attachment; filename=" + candidate.getResumeFileLocation());
		header.set("Content-Disposition",
				"attachment; filename=" + candidate.getCandidateFirstName()+"_"+candidate.getCandidateLastName()+"_resume."+FileUtility.getFileExtention(candidate.getResumeFileLocation()));
		header.setContentLength(fileData.length);
//		HttpServletResponse response=new HttpServletResponse();
//		header.setCharacterSet("utf-8");

		return new HttpEntity<byte[]>(fileData, header);
	}

	@RequestMapping(value = "/downloadCandCv", method = RequestMethod.GET)
	public String downloadCandCv(Model model, @RequestParam("user") String user) {
		return null;

	}

	@RequestMapping(value = "/uploadCv", method = RequestMethod.POST)
	public String create(UploadItem uploadItem, BindingResult result,
			HttpServletRequest request, Principal principal, Model model)
			throws Exception {
		try {
			if (result.hasErrors()) {
				for (ObjectError error : result.getAllErrors()) {
					System.err.println("Error: " + error.getCode() + " - "
							+ error.getDefaultMessage());
				}

				return "uploadCv";
			}

			FileItem item = uploadItem.getFileData().getFileItem();
			Candidate candidate = candidateService.getById(principal
					.getName());

			if (!item.isFormField()) {
				System.out.println("size::" + item.getSize());
				if (item.getSize() > 314342) {
					model.addAttribute("message", "File size is big.");
					return "uploadCv";
				}
				String fileName = candidate.getCandidateFirstName()+"_"+candidate.getCandidateLastName()
						+"_resume."
						+ FileUtility.getFileExtention(item.getName());

				String root = request.getSession().getServletContext()
						.getRealPath("/");
				File path = new File(root + System.getProperty("file.separator" ) +"WEB-INF"+System.getProperty("file.separator" ) +"cv");
				if (!path.exists()) {
					boolean status = path.mkdirs();
				}

				File uploadedFile = new File(path + System.getProperty("file.separator" )  + fileName);
				System.out.println(uploadedFile.getAbsolutePath());
				item.write(uploadedFile);

//				Candidate candidate = candidateService.getById(principal
//						.getName());
				candidate.setResumeFileDesc(uploadItem.getFileDescription());
				candidate.setResumeFileLocation(fileName);
				candidate.setResumeContenttype(item.getContentType());
				candidateService.updateCandidate(candidate);
			}

			// Some type of file processing...
			System.err.println("-------------------------------------------");
			System.err.println("File upload: " + item.getName());
			System.err.println("File Content upload: " + item.getContentType());
			System.err.println("File size: " + item.getSize());
			/*
			 * System.err.println("Test upload: " +
			 * uploadItem.getFileData().getOriginalFilename());
			 */
			System.err.println("-------------------------------------------");

			model.addAttribute("message", "Resume Uploaded Successfully.");
		} catch (Exception e) {
			model.addAttribute("message", "Resume Upload Fail.");

		}

		return "uploadCv";
	}
	
	@RequestMapping(value = "/deleteCv", method = RequestMethod.GET)
	public String deleteCv(Model model, Principal principal,
			HttpServletRequest request) {
		try {
//			if(request.getCharacterEncoding()==null){
//				request.setCharacterEncoding("utf-8");
//			}
			Candidate candidate = candidateService.getById(principal.getName());
			String root = request.getSession().getServletContext()
					.getRealPath("/");
//			response.setCharacterEncoding("utf-8");
			File path = new File(root + "/WEB-INF/cv");
			File uploadedFile = new File(path + "/"
					+ candidate.getResumeFileLocation());
			System.out.println("File location Name: "+uploadedFile);
			uploadedFile.setWritable(true);
			boolean success = uploadedFile.delete();
			  if (!success){
			  System.out.println("Deletion failed.");
//			  System.exit(0);
			  }else{
			  System.out.println("File deleted.");
			  Candidate cand=this.candidateService.getById(principal.getName());
			  cand.setResumeFileLocation(null);
			  cand.setResumeContenttype(null);
			  cand.setResumeFileDesc(null);
			  candidateService.updateCandidate(cand);
			    }
			
			return "viewCandidateProfilePage";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("messageresume", "true");
		}
		return null;

	}

}
