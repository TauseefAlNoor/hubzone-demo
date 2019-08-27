package com.hubzone.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hubzone.dao.CandidateService;

import com.hubzone.dao.UsersService;
import com.hubzone.model.Candidate;

import com.hubzone.model.Users;

public class DataImport {
	static Logger log = Logger.getLogger(DataImport.class);
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		try {

			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy.MM.dd  'at' HH:mm:ss z");
			String start = formatter.format(new Date());
			log.info("**************************************************");
			log.info("*        Prcess start time        " + start);

			ApplicationContext context = new ClassPathXmlApplicationContext(
					new String[] { "spring/root-context.xml",
							"spring/servlet-context.xml" });
			
		/*
			EmployerService service1 = context.getBean(EmployerService.class);
			log.info("Job list :"+service1.getJobList(new Employer("dtn")).size());
			*/
			
			Users user=new Users("sujan");
			user.setEnabled(1);
			user.setUserName("test1");
			user.setRole("empl");
			user.setPassword("passs");
			
			Candidate can = new Candidate();
			can.setCandidateID("test4");
			
			
			CandidateService service1 = context.getBean(CandidateService.class);
			//service1.save(can);
			
			UsersService service2 = context.getBean(UsersService.class);
		//	service2.saveUser(user);
			
			CandidateController con = context.getBean(CandidateController.class);
//			con.create(can, user, null);
			
			//log.info("Job list :"+service1.getJobList(new Candidate("sujan")).size());
			
			
			
			/*
			JobService service1 = context.getBean(JobService.class);
			log.info("Service :"+service1.countJobByCategory().size());
			JobCategories st= service1.countJobByCategory().get(0);
			log.info("count :" +st.getNumJobs());
			log.info("count :" +st.getJobCategoryName());
			*/
			/*
			StatesService service = context.getBean(StatesService.class);
			FileReader fr = new FileReader("D:\\eclipse workspace\\space1\\hubzone\\src\\main\\resources\\data1.txt");
			BufferedReader br = new BufferedReader(fr);
			String s;
			while ((s = br.readLine()) != null) {
				log.info("-------------------------"+s);
				States st = new States();
				st.setStateName(s.trim());
				service.saveState(st);
			}
			fr.close();
			
			
			*/
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy.MM.dd  'at' HH:mm:ss z");
			String end = formatter.format(new Date());

			log.info("*        Prcess end time        " + end);
			log.info("**************************************************");
		}
	}
}
