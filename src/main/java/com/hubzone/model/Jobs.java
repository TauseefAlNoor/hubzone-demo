package com.hubzone.model;

/*
 * This class is for  Jobs table in the database
 * 
 * */
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;

@Entity
@Table(name = "Jobs")
public class Jobs {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)	
	@Column(name = "jobID", nullable = false)
	private Long jobID;

	//@NotNull
	@JoinColumn(name = "jobEmployer", referencedColumnName = "employerID", nullable = false)
	@ManyToOne(optional = false)
	private Employer employer;

	//@NotNull
	//@Size(min = 3, max = 100)
	@Column(name = "jobTitle", nullable = false, length = 100)
	private String jobTitle;
	
	//@Size(min = 1, max = 10)
	@Column(name = "jobApplicant", nullable = false)
	private Integer jobApplicant;

	//@NotNull
	//@Size(min = 3, max = 50)
	@Column(name = "jobCity", nullable = false, length = 50)
	private String jobCity;

	//@Size(min = 3, max = 100)
	@Column(name = "jobState", nullable = false, length = 100)
	private String jobState;

	//@Size(min = 1, max = 10)
	@Column(name = "JobZip", nullable = false, length = 10)
	private String jobZip;

	///@Size(min = 1, max = 10)
	@Column(name = "jobRate", nullable = false, length = 10)
	private String jobRate;

	//@Size(min = 1, max = 50)
	@Column(name = "JobKeyWord", nullable = false, length = 10)
	private String jobKeyWord;

	//@Size(min = 1, max = 50)
	@Column(name = "JobCategoryName", nullable = false, length = 50)
	private String jobCategoryName;

	//@Size(min = 1, max = 15)
	@Column(name = "jobDuration", nullable = false, length = 15)
	private String jobDuration;

	@Basic(optional = false)
	//@NotNull
	@Lob
	//@Size(min = 1, max = 2147483647)
	@Column(name = "jobSummary", nullable = false, length = 2147483647)
	private String jobSummary;

	@Basic(optional = true)
	@Column(name = "lastDate")
	@Temporal(TemporalType.TIMESTAMP)
//	@DateTimeFormat(iso=ISO.)
	//@Future  
	private Date lastDate;
	
	/*@Basic(optional = true)
	@Column(name = "lastDate")
	@Temporal(TemporalType.TIMESTAMP)*/
//	@DateTimeFormat(iso=ISO.)
	//@Future  
/*	private Date getLastDate(){
		DateFormat formatter;
		Date date = getCurrentDate();

//		formatter = new SimpleDateFormat("MM-dd-yyyy");
//		date = (Date) formatter.parse(lastDate1);
		
		formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		//date = (Date) formatter.parse(lastDate1);
		date= new Date();
		String strDate = formatter.format(date);
		
		
		//add 30 day 
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(formatter.parse(strDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.add(Calendar.DATE, 30);
		
		Date newDate=c.getTime();
		//String lastDate=formatter.format(newDate);
		return newDate;
	}*/
	
	
	//new current date
	@Basic(optional = true)
	@Column(name = "currentDate")
	@Temporal(TemporalType.TIMESTAMP)
//	@DateTimeFormat(iso=ISO.DATE)
	//@Future  
	private Date currentDate;
	
//	@Column(name = "salaryLevel", nullable = false, length = 30)
//	private String salaryLevel; //Negotiable, 0-30K, 30K-50K, 50K-75K, 75K-100K, 100K-125K, 125K-150K, 150K+

	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "job")
    private Collection<JobsApplied> jobsAppliedCollection;
	
	
	public String getJobCategoryName() {
		return jobCategoryName;
	}
	public void setJobCategoryName(String jobCategoryName) {
		this.jobCategoryName = jobCategoryName;
	}
	public Collection<JobsApplied> getJobsAppliedCollection() {
		return jobsAppliedCollection;
	}
	public void setJobsAppliedCollection(
			Collection<JobsApplied> jobsAppliedCollection) {
		this.jobsAppliedCollection = jobsAppliedCollection;
	}
	public Jobs() {
	}
	public Jobs(Long jobId ) {
		this.jobID=jobId;
	}

	public Long getJobID() {
		return jobID;
	}

	public void setJobID(Long jobID) {
		this.jobID = jobID;
	}

	public Employer getEmployer() {
		return employer;
	}

	public void setEmployer(Employer employer) {
		this.employer = employer;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public String getJobKeyWord() {
		return jobKeyWord;
	}

	public void setJobKeyWord(String jobKeyWord) {
		this.jobKeyWord = jobKeyWord;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getJobCity() {
		return jobCity;
	}

	public void setJobCity(String jobCity) {
		this.jobCity = jobCity;
	}

	public String getJobState() {
		return jobState;
	}

	public void setJobState(String jobState) {
		this.jobState = jobState;
	}

	public String getJobZip() {
		return jobZip;
	}

	public void setJobZip(String jobZip) {
		this.jobZip = jobZip;
	}

	public String getJobRate() {
		return jobRate;
	}

	public void setJobRate(String jobRate) {
		this.jobRate = jobRate;
	}

	public String getJobDuration() {
		return jobDuration;
	}

	public void setJobDuration(String jobDuration) {
		this.jobDuration = jobDuration;
	}

	public String getJobSummary() {
		return jobSummary;
	}

	public void setJobSummary(String jobSummary) {
		this.jobSummary = jobSummary;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}
	
	public String getRemainingTime(){
		String date = null;
		return date;
	}
	
	
	public int getDays() {

		DateFormat formatter;
		Date earlyDate = null;
		int day=0;

		formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		//date = (Date) formatter.parse(lastDate1);
		earlyDate= new Date();
		String strDate = formatter.format(earlyDate);
		System.out.println("Start Date is : "+strDate);
		String endDate = formatter.format(getLastDate());
		System.out.println("Start Date is : "+endDate);
		Date newDate= null;
		Date lastDate= null;
		//lastDate = getLastDate();

			try {
				newDate=formatter.parse(strDate);
				lastDate=formatter.parse(endDate);
				
				DateTime d1=new DateTime(newDate);
				DateTime d2=new DateTime(lastDate);
				int delta;
//				delta=(int)(lastDate.getTime()-newDate.getTime());
//				if(delta<=0){
//					delta=0;
//					return delta;
//				}
				//int deltaDays = (int)((lastDate.getTime() - earlyDate.getTime()) / (1000*60*60*24));
				int deltaDays=Days.daysBetween(d1, d2).getDays();
				day= deltaDays;
				if(day<=0){
					return 0;
				}
				return deltaDays;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return day;
		/*
		if (getLastDate() != null) {
			c.setTime(new Date(getLastDate().getTime()));
			int lastdate = c.get(Calendar.DATE);
			c.setTime(new Date());
			int now = c.get(Calendar.DATE);
			return lastdate - now;
		}
		return 0;
		*/
	}
	
	public long getMinutes() {
		//Calendar c = Calendar.getInstance();
//		Date lastDate, earlyDate;
//		lastDate = getLastDate();
//		earlyDate = new Date();
//		long delta;
//		if(earlyDate.getTime()>lastDate.getTime()){
//			delta=0;
//			return delta;
//		}else{
//			long deltaDays = (long)((lastDate.getTime() - earlyDate.getTime()) / (long)((60 * 1000) % 60));
//			return deltaDays;
//		}
		
		//return deltaDays;
		/*
		if (getLastDate() != null) {
			c.setTime(new Date(getLastDate().getTime()));
			int lastdate = c.get(Calendar.DATE);
			c.setTime(new Date());
			int now = c.get(Calendar.DATE);
			return lastdate - now;
		}
		return 0;
		*/
		DateFormat formatter;
		Date earlyDate = null;
		int day=0;

		formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		//date = (Date) formatter.parse(lastDate1);
		earlyDate= new Date();
		String strDate = formatter.format(earlyDate);
		System.out.println("Start Date is : "+strDate);
		String endDate = formatter.format(getLastDate());
		System.out.println("Start Date is : "+endDate);
		Date newDate= null;
		Date lastDate= null;
		//lastDate = getLastDate();

			try {
				newDate=formatter.parse(strDate);
				lastDate=formatter.parse(endDate);
				
				DateTime d1=new DateTime(newDate);
				DateTime d2=new DateTime(lastDate);
				int delta;
//				delta=(int)(lastDate.getTime()-newDate.getTime());
//				if(delta<=0){
//					delta=0;
//					return delta;
//				}
				//int deltaDays = (int)((lastDate.getTime() - earlyDate.getTime()) / (1000*60*60*24));
				int deltaDays=Hours.hoursBetween(d1, d2).getHours() % 24;
				day= deltaDays;
				if(day<=0){
					return 0;
				}
				return deltaDays;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return day;
	}
	
	public long getHours() {
		//Calendar c = Calendar.getInstance();
//		Date lastDate, earlyDate;
//		lastDate = getLastDate();
//		earlyDate = new Date();
//		long delta;
//		if(earlyDate.getTime()>lastDate.getTime()){
//			delta=0;
//			return delta;
//		}else{
//			long deltaDays = (long)((lastDate.getTime() - earlyDate.getTime()) /((60 * 60 * 1000) % 24));
//			return deltaDays;
//		}
		
		//return deltaDays;
		/*
		if (getLastDate() != null) {
			c.setTime(new Date(getLastDate().getTime()));
			int lastdate = c.get(Calendar.DATE);
			c.setTime(new Date());
			int now = c.get(Calendar.DATE);
			return lastdate - now;
		}
		return 0;
		*/
		DateFormat formatter;
		Date earlyDate = null;
		int day=0;

		formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		//date = (Date) formatter.parse(lastDate1);
		earlyDate= new Date();
		String strDate = formatter.format(earlyDate);
		System.out.println("Start Date is : "+strDate);
		String endDate = formatter.format(getLastDate());
		System.out.println("Start Date is : "+endDate);
		Date newDate= null;
		Date lastDate= null;
		//lastDate = getLastDate();

			try {
				newDate=formatter.parse(strDate);
				lastDate=formatter.parse(endDate);
				
				DateTime d1=new DateTime(newDate);
				DateTime d2=new DateTime(lastDate);
				int delta;
//				delta=(int)(lastDate.getTime()-newDate.getTime());
//				if(delta<=0){
//					delta=0;
//					return delta;
//				}
				//int deltaDays = (int)((lastDate.getTime() - earlyDate.getTime()) / (1000*60*60*24));
				int deltaDays=Minutes.minutesBetween(d1, d2).getMinutes() % 60;
				day= deltaDays;
				if(day<=0){
					return 0;
				}
				return deltaDays;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return day;
	}
	
	public Date getRemainingDate(){
		DateFormat formatter;
		Date date = null;

//		formatter = new SimpleDateFormat("MM-dd-yyyy");
//		date = (Date) formatter.parse(lastDate1);
		
		formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		//date = (Date) formatter.parse(lastDate1);
		date= new Date();
		String strDate = formatter.format(date);
		
		
		//add 30 day 
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(formatter.parse(strDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.add(Calendar.DATE, 30);
		
		Date newDate=c.getTime();
		//String lastDate=formatter.format(newDate);
		return newDate;
	}
//	public String getSalaryLevel() {
//		return salaryLevel;
//	}
//	public void setSalaryLevel(String salaryLevel) {
//		this.salaryLevel = salaryLevel;
//	}
	public Date getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
	public Integer getJobApplicant() {
		return jobApplicant;
	}
	public void setJobApplicant(Integer jobApplicant) {
		this.jobApplicant = jobApplicant;
	}

}
