package com.hubzone.dao.impl;

/*
 * This class is for implementation of Employer Service methods
 * 
 * */

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.geoservice.GeoDistanceService;
import com.hubzone.dao.EmployerService;
import com.hubzone.model.Candidate;
import com.hubzone.model.Employer;
import com.hubzone.model.Jobs;

import com.hubzone.utility.ResumeSearch;
import com.hubzone.utility.ResumeSearchResult;
import com.hubzone.utility.DocParserArray;
import org.springframework.ui.Model;

@Repository
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class EmployerServiceImpl implements EmployerService {
	Logger log = Logger.getLogger(EmployerServiceImpl.class);
	
	

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void save(Employer employer) {
		entityManager.persist(employer);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void removeEmployer(Employer employer) {
		entityManager.remove(employer);
	}

	public List<Jobs> getJobList(Employer employer) {
		String sql = "Select j from Jobs j LEFT OUTER JOIN fetch j.employer e where j.employer.employerID=:employerID";
		TypedQuery<Jobs> query = entityManager.createQuery(sql, Jobs.class);
		query.setParameter("employerID", employer.getEmployerID());
		return query.getResultList();
	}

	@Transactional(readOnly = true)
	public Employer getById(String id) {
		Employer employer = entityManager.find(Employer.class, id);
		return employer;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateEmployer(Employer employer) {
		entityManager.merge(employer);
	}

	public List<Candidate> serachResume(ResumeSearch resumeSearch,HttpServletRequest request) {
		// StringBuilder criteria = new StringBuilder();
		//String []userSearchString;
		//ArrayList<String> userSearchString = new ArrayList<String>();
		String userSearchString="";
		String sql = "";
		String sqlMain = "SELECT c FROM Candidate c WHERE ";
		String sqlCount = "SELECT Count(c) FROM Candidate c WHERE ";

		// job seeker profile, though it is drop down list, it will never void
		/*
		 * if (!resumeSearch.getCandidate().getHighestEducationLevel()
		 * .equals("No Degree")) { sql +=
		 * " UPPER(c.highestEducationLevel) LIKE '%" +
		 * resumeSearch.getCandidate().getHighestEducationLevel() .toUpperCase()
		 * + "%' AND"; }
		 */
		if (resumeSearch.getCandidate().getHighestEducationLevel() != "") {
			// sql +=
			// " UPPER(c.highestEducationLevel) LIKE '%"+resumeSearch.getCandidate().getHighestEducationLevel().toUpperCase()+"%' AND";
			sql += "(";
			if (resumeSearch.getCandidate().getHighestEducationLevel()
					.equals("No Degree")) {
				sql += " c.highestEducationLevel= 'No Degree'  OR"
						+ " c.highestEducationLevel= 'High School/GED' OR "
						+ " c.highestEducationLevel= 'Associate Degree' OR "
						+ " c.highestEducationLevel= 'Bachelors Degree' OR "
						+ " c.highestEducationLevel= 'Masters Degree or Higher' ";
			} else if (resumeSearch.getCandidate().getHighestEducationLevel()
					.equals("High School/GED")) {
				sql += " c.highestEducationLevel= 'High School/GED' OR "
						+ " c.highestEducationLevel= 'Associate Degree' OR "
						+ " c.highestEducationLevel= 'Bachelors Degree' OR "
						+ " c.highestEducationLevel= 'Masters Degree or Higher' ";
				;
			} else if (resumeSearch.getCandidate().getHighestEducationLevel()
					.equals("Associate Degree")) {
				sql += " c.highestEducationLevel= 'Associate Degree' OR "
						+ " c.highestEducationLevel= 'Bachelors Degree' OR "
						+ " c.highestEducationLevel= 'Masters Degree or Higher' ";
			} else if (resumeSearch.getCandidate().getHighestEducationLevel()
					.equals("Bachelors Degree")) {
				sql += " c.highestEducationLevel= 'Bachelors Degree' OR "
						+ " c.highestEducationLevel= 'Masters Degree or Higher' ";
			}
			// if(!resumeSearch.getCandidate().getHighestEducationLevel().equals("Masters Degree or Higher"))
			// {
			else {
				sql += " c.highestEducationLevel= 'Masters Degree or Higher' ";
			}
			sql += ") "; // add the closing bracket
			sql += " AND "; // add the AND
		}
		log.debug(" salaryLevel size "
				+ resumeSearch.getMalsalaryLevel().size());

		if (resumeSearch.getMalsalaryLevel().size() != 0) {
			boolean addSalaryLabel = true;

			if (resumeSearch.getMalsalaryLevel().size() == 1) {
				if (resumeSearch.getMalsalaryLevel().get(0).equals("")) {
					addSalaryLabel = false;
				}
			}

			if (addSalaryLabel) {
				log.debug(" adding salaryLevel to query");
				int sl = 0;
				sql += " ( ";
				for (String salaryLavel : resumeSearch.getMalsalaryLevel()) {
					++sl;
					log.debug(" adding salaryLevel :" + salaryLavel);
					sql += " c.salaryLevel LIKE '%" + salaryLavel + "%'  ";
					if (sl != resumeSearch.getMalsalaryLevel().size())
						sql += " OR ";
				}
				sql += " ) AND ";
			}

		}

		log.debug("Desired Position: "
				+ resumeSearch.getCandidate().getDesiredPosition());
		/*if (!resumeSearch.getCandidate().getDesiredPosition().equals("")) {
			// criteria.append(" UPPER( c.desiredPosition) like :desiredPosition");
			/*
			 * sql += " UPPER(c.desiredPosition) LIKE '%" +
			 * resumeSearch.getCandidate().getDesiredPosition() .toUpperCase() +
			 * "%' AND";
			 */
		/*	String str[] = resumeSearch.getCandidate().getDesiredPosition()
					.split(" ");
			if (str.length > 0) {
				sql += "(";
				for (int i = 0; i < str.length; i++) {
					sql += " UPPER(c.desiredPosition) LIKE '%"
							+ str[i].toUpperCase().trim() + "%' OR";
				}
				sql = sql.substring(0, sql.trim().lastIndexOf("OR")); // remove
																		// the
																		// last
																		// OR
				sql += ")"; // add the closing bracket
				sql += " AND ";// add the AND
			}
		}*/
		if(!resumeSearch.getCandidate().getDesiredPosition().equals("")){
			sql += buildQueryString(parseBooleanString(resumeSearch
					.getCandidate().getDesiredPosition()),
					"c.desiredPosition");
		}
		/*if (!resumeSearch.getCandidate().getCandidateSkill().equals("")) {
			
			sql += buildQueryString(parseBooleanString(resumeSearch
					.getCandidate().getCandidateSkill()),
					"c.candidateSkill");
		}*/
		/*
		 * if (!resumeSearch.getSearch().equals("")) { if
		 * (resumeSearch.getSearchType().equals("allword")) { String str[] =
		 * resumeSearch.getSearch().split(","); for (int i = 0; i < str.length;
		 * i++) { sql += " UPPER(c.candidateSkill) LIKE '%" +
		 * str[i].toUpperCase().trim() + "%' AND "; } sql = sql.substring(0,
		 * sql.trim().lastIndexOf("AND")); // remove // the // last // AND }
		 * else if (resumeSearch.getSearchType().equals("anyword")) { String
		 * str[] = resumeSearch.getSearch().split(" "); sql += "("; for (int i =
		 * 0; i < str.length; i++) { sql += " UPPER(c.candidateSkill) LIKE '%" +
		 * str[i].toUpperCase().trim() + "%' OR "; } sql = sql.substring(0,
		 * sql.trim().lastIndexOf("OR")); // remove // the // last // OR sql +=
		 * ")"; // add the closing bracket } sql += " AND ";// add the AND }
		 */

		/*if (!resumeSearch.getCandidate().getRecentEmployer().equals("")) {
			/*
			 * sql += " UPPER(c.recentEmployer) LIKE '%" +
			 * resumeSearch.getCandidate().getRecentEmployer() .toUpperCase() +
			 * "%' AND";
			 */
			/*String str[] = resumeSearch.getCandidate().getRecentEmployer()
					.split(" ");
			sql += "(";
			for (int i = 0; i < str.length; i++) {
				sql += " UPPER(c.recentEmployer) LIKE '%"
						+ str[i].toUpperCase().trim() + "%' OR ";
			}
			sql = sql.substring(0, sql.trim().lastIndexOf("OR")); // remove the
																	// last OR
			sql += ")"; // add the closing bracket
			sql += " AND ";// add the AND
			*/
			/*sql += buildQueryString(parseBooleanString(resumeSearch
					.getCandidate().getRecentEmployer()),
					"c.recentEmployer");
		}*/

		/*
		 * if (!resumeSearch.getCandidate().getRecentJobTitle().equals("")) {
		 * sql += " UPPER(c.recentJobTitle) LIKE '%" +
		 * resumeSearch.getCandidate().getRecentJobTitle() .toUpperCase() +
		 * "%' AND"; } if
		 * (!resumeSearch.getCandidate().getRecentJobDescription().equals("")) {
		 * sql += " UPPER(c.recentJobDescription) LIKE '%" +
		 * resumeSearch.getCandidate().getRecentJobDescription() .toUpperCase()
		 * + "%' AND"; } if
		 * (!resumeSearch.getCandidate().getLastSchoolAttended().equals("")) {
		 * sql += " UPPER(c.lastSchoolAttended) LIKE '%" +
		 * resumeSearch.getCandidate().getLastSchoolAttended() .toUpperCase() +
		 * "%' AND"; }
		 */
		// if (resumeSearch.getCandidate().getJobStatus() != null
		// && !resumeSearch.getCandidate().getJobStatus().equals("")) {
		if (resumeSearch.getCandidate().getJobStatus() != null
				&& !resumeSearch.getCandidate().getJobStatus().equals("")) {
			sql += "( UPPER(c.jobStatus) LIKE '%"
					+ resumeSearch.getCandidate().getJobStatus().toUpperCase()
					+ "%' ) AND ";
		}
		if(resumeSearch.getCandidate().getRecentEmployer()!=null && !resumeSearch.getCandidate().getRecentEmployer().equals("")){
			//new search from copy and paste
			/*sql += buildQueryString(parseBooleanString(resumeSearch
					.getCandidate().getRecentEmployer()),
					"c.recentEmployer");*/
			/*sql += "( UPPER(c.candidateDescription) LIKE '%"
					+ resumeSearch.getCandidate().getCandidateSkill().toUpperCase()
					+ "%' ) AND ";*/
			
			userSearchString=resumeSearch.getCandidate().getRecentEmployer();
			}
		//userSearchString.add("");
		if(resumeSearch.getCandidate().getCandidateSkill()!=null && !resumeSearch.getCandidate().getCandidateSkill().equals("")){
			//new search from copy and paste 
			sql += buildQueryString(parseBooleanString(resumeSearch
					.getCandidate().getCandidateSkill()),
					"c.candidateSkill");
			/*sql += "( UPPER(c.candidateDescription) LIKE '%"
					+ resumeSearch.getCandidate().getRecentEmployer().toUpperCase()
					+ "%' ) AND ";*/
			
			//userSearchString.add(resumeSearch.getCandidate().getCandidateSkill());
			}
		/*else{
				userSearchString.add(resumeSearch.getCandidate().getRecentEmployer());
			}*/
		if (resumeSearch.getCandidate().getJobCategory() != null
				&& !resumeSearch.getCandidate().getJobCategory().equals("")) {
			String cat[] = resumeSearch.getCandidate().getJobCategory()
					.split(",");
			sql += "(";
			for (int i = 0; i < cat.length; i++) {
				sql += " ( UPPER(c.jobCategory) LIKE '%" + cat[i].trim()
						+ "%' ) OR ";
			}
			sql = sql.substring(0, sql.trim().lastIndexOf("OR")); // remove the
																	// last OR
			sql += ")"; // add the closing bracket
			sql += " AND "; // add the AND
		}
		
/*		if(resumeSearch.getCandidate().getRecentEmployer()!=null && !resumeSearch.getCandidate().getRecentEmployer().equals("")){
			//new search from copy and paste
			sql += buildQueryString(parseBooleanString(resumeSearch
					.getCandidate().getCandidateSkill()),
					"c.candidateDescription");
			
			userSearchString.add(resumeSearch.getCandidate().getRecentEmployer());
			}else{
				userSearchString.add(resumeSearch.getCandidate().getCandidateSkill());
			}
		if(resumeSearch.getCandidate().getCandidateSkill()!=null && !resumeSearch.getCandidate().getCandidateSkill().equals("")){
			//new search from copy and paste 
			sql += buildQueryString(parseBooleanString(resumeSearch
					.getCandidate().getRecentEmployer()),
					"c.candidateDescription");
			
			userSearchString.add(resumeSearch.getCandidate().getCandidateSkill());
			}else{
				userSearchString.add(resumeSearch.getCandidate().getRecentEmployer());
			}*/
		
		/*if(resumeSearch.getCandidate().getRecentEmployer()==null && resumeSearch.getCandidate().getRecentEmployer().equals("")){
			userSearchString.add("aaaaaaaa");
			}
		if(resumeSearch.getCandidate().getCandidateSkill()==null && resumeSearch.getCandidate().getCandidateSkill().equals("")){
			userSearchString.add("aaaaaaaa");
			}*/
		log.debug("Sql result is "+sql);
		
		/*for (String userName : userSearchString) {
			log.debug("Found User :"+userName);
		}*/

		// Resume Posted
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String formatedDate = df.format(new Date());
		log.info("DATE: " + formatedDate);

		Calendar cal = Calendar.getInstance();

		int toAdd;
		try {
			toAdd = (-1) * Integer.parseInt(resumeSearch.getResumeUpdated());
			cal.setTime(df.parse(formatedDate));
			cal.add(Calendar.DATE, toAdd);
			// cal.add(Calendar.DATE, Calendar.DATE+(toAdd/1440));
			//change
			sql += " c.lastUpdateDate <= :today AND c.lastUpdateDate >= :range";
			System.out.println(cal.getTime());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			System.out.println(" Exception: " + e1);
			e1.printStackTrace();
		}

		// if(!criteria.toString().equals("")){
		// log.debug(" criteria: "+criteria);
		// criteria.insert(0," Where ");
		// log.debug(" criteria: "+criteria);
		// }

		sqlMain += sql;
		sqlCount += sql;
		log.debug("sqlMain : " + sqlMain);
		log.debug("sqlCount : " + sqlCount);

		Long totalResult = entityManager.createQuery(sqlCount, Long.class)
				.setParameter("today", new Date())
				.setParameter("range", cal.getTime()).getSingleResult();

		log.debug("Total Result : " + totalResult);
		TypedQuery<Candidate> query = entityManager.createQuery(sqlMain,
				Candidate.class);
		query.setParameter("today", new Date());
		query.setParameter("range", cal.getTime());

		ResumeSearchResult resultData = new ResumeSearchResult();
		resultData.setTotalSearchResult(totalResult);
		/*resultData.setCandidateList(query
				.setMaxResults(resumeSearch.getLimit())
				.setFirstResult(
						resumeSearch.getStatrt() * resumeSearch.getLimit())
				.getResultList());*/
		
		resultData.setCandidateList(query.getResultList());
		
		if((resumeSearch.getCandidate().getRecentEmployer().equals(""))){
			log.info("Required result is here"+resultData.getCandidateList().size());
			return resultData.getCandidateList();
		}else{
		String root = request.getSession().getServletContext()
				.getRealPath("/");
		File path = new File(root + System.getProperty("file.separator")
				+ "WEB-INF" + System.getProperty("file.separator") + "cv");
		
		log.debug("Content path for cv: "+path.getPath() );
		
		DocParserArray p = new DocParserArray();
		List<Candidate> mactchUserList=new ArrayList<Candidate>();
		try {
//			 mactchUserList= p.getCVSearchResult(userSearchString, path.getAbsolutePath(), resultData.getCandidateList());
			 mactchUserList= p.getCVSearchResult(userSearchString, path.getPath(), resultData.getCandidateList());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResumeSearchResult resultNewData = new ResumeSearchResult();
		
		
		resultNewData.setCandidateList(mactchUserList);
		resultNewData.setTotalSearchResult(totalResult);
		log.debug("Final Result Size: "+resultNewData.getCandidateList().size());
		log.debug("Content path for cv: "+path.getPath() );
		log.debug("Content path for cv: "+path.getAbsolutePath());
		/*//distance start
		ArrayList<Candidate> result = new ArrayList<Candidate>();
		int reqDist = (int) (resumeSearch.getDistance() * 1.609344 * 1000);

		if (!resumeSearch.getLocation().equals("")) {
			try {
				for (int i = 0; i < resultNewData.getCandidateList().size(); i++) {
					Integer distance = new GeoDistanceService().distanceMatrix(
							resumeSearch.getLocation(),
							resultNewData.getCandidateList().get(i).getCandidateZip()).getRows()[0]
							.getElements()[0].getDistance().getValue();
					if (distance <= reqDist) {
						log.info(i + ". Distance: " + distance + " Req Dist: "
								+ reqDist);
						result.add(resultNewData.getCandidateList().get(i));
					}

				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
//				model.addAttribute("message", "No Result To show");
			}
		}// else if(result.add(cand.get(i))==null){
			// return "resultResumePage";
		// }
		else {
			for (int i = 0; i < resultNewData.getCandidateList().size(); i++) {
				result.add(resultNewData.getCandidateList().get(i));
			}
//			model.addAttribute("candList", result);
			
			return result;
		}
		//distance end*/
		
		
		return resultNewData.getCandidateList();
		}
	}

	private List<String> parseBooleanString(String data) {
		data = data.concat(" ");
		List<String> wordList = new ArrayList<String>();
		boolean endNewWord = false;
		StringBuilder word = new StringBuilder("");
		boolean phraseWord = false;
		for (int i = 0; i < data.length(); i++) {

			// word.append(data.charAt(i));

			if (phraseWord == false && data.charAt(i) == '"') {
				phraseWord = true;
			} else if (phraseWord == true && data.charAt(i) == '"') {
				phraseWord = false;
			}

			if ((data.charAt(i) == '(' || data.charAt(i) == ')'
					|| data.charAt(i) == ',' || data.charAt(i) == ' ' || data
					.charAt(i) == '"') && phraseWord == false) {
				endNewWord = true;
			}

			if (!endNewWord || data.charAt(i) == '"') {
				word.append(data.charAt(i));
			}

			if (endNewWord) {

				if (word.toString().trim().length() > 0) {
					// log.debug(word.toString().trim());
					wordList.add(word.toString().trim());

				}
				if (data.charAt(i) != ' ' && data.charAt(i) != '"') {
					wordList.add(data.charAt(i) + "");
				}

				word = new StringBuilder("");
				endNewWord = false;
			}

		}
		return wordList;

	}

	private String buildQueryString(List<String> wordList, String field) {
		System.out.println("\n*****************");
		System.out.println("word length" + wordList.size());
		StringBuilder query = new StringBuilder();
		query.append( "(");
	
	
		
		boolean preOperator = true;
		for (String key : wordList) {
			if (key.equals("(") || key.equals(")")
					|| key.toLowerCase().equals("or")
					|| key.toLowerCase().equals("and") || key.equals(",")) {

				if (key.equals(",")) {
					query.append(" or ");
				} else {
					query.append(key + " ");
				}
				preOperator = true;
			} else {

				if (preOperator == false && isOperator(key) == false) {
					query.append(" and ");
				}
				preOperator=false;
				if (key.startsWith("\"")) {

					query.append("UPPER(" + field + ") LIKE '%"
							+ key.replaceAll("\"", "").toUpperCase() + "%' ");
				} else {
					query.append("UPPER(" + field + ") LIKE '%"
							+ key.toUpperCase() + "%' ");
				}

			}

		}
		query.append(")"); // add the closing bracket
		query.append( " AND ");// add the AND
		return query.toString();
	}

	private boolean isOperator(String key) {
		return (key.equals("(") || key.equals(")")
				|| key.toLowerCase().equals("or")
				|| key.toLowerCase().equals("and") || key.equals(","));
	}

	public static void main(String[] args) {
		EmployerServiceImpl ob = new EmployerServiceImpl();
		String data = "";
		data = "( ( \"java prog \" or oracle , html ) and ( php  js) )";
		// printArray(ob.parseBooleanString(data));
		System.out.println(ob.buildQueryString(ob.parseBooleanString(data) ,"c.candidateSkill"));
		
		data = "(  java  or  oracle  ) and php ";
		// printArray(ob.parseBooleanString(data));
		System.out.println( "Ex2 :" +ob.buildQueryString(ob.parseBooleanString(data) ,"c.candidateSkill"));
		
		data = "( ( java prog  or oracle , html ) and ( php  js) )";
		// printArray(ob.parseBooleanString(data));
		System.out.println(ob.buildQueryString(ob.parseBooleanString(data) ,"c.candidateSkill"));
		
		
		data = "java prog  , oracle , html";
		// printArray(ob.parseBooleanString(data));
		System.out.println(ob.buildQueryString(ob.parseBooleanString(data) ,"c.candidateSkill"));
		
		
		data = "java prog";
		// /printArray(ob.parseBooleanString(data));
		System.out.println(ob.buildQueryString(ob.parseBooleanString(data) ,"c.candidateSkill"));
		
		data = "java";
		// /printArray(ob.parseBooleanString(data));
		System.out.println(ob.buildQueryString(ob.parseBooleanString(data) ,"c.candidateSkill"));
		
	}

	private static void printArray(List<String> wordList) {
		System.out.println("\n*****************");
		for (String word : wordList) {
			System.out.println(word.toString().trim());
		}
	}
}
