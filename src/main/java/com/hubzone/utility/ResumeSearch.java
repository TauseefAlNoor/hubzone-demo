package com.hubzone.utility;

/*
 * This class is for  resume search information
 * 
 * */

import java.util.ArrayList;
import java.util.List;

import com.hubzone.model.Candidate;

public class ResumeSearch {
	private Candidate candidate;
	private String resumeUpdated;
	private String search;
	private String searchType;
	private String location;
	private Integer distance;
	private String sort;
	private Integer statrt=0;
	private Integer end=0;
	private Integer limit=3;
	private List<String> malsalaryLevel = new ArrayList<String>();

	
	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public List<String> getMalsalaryLevel() {
		return malsalaryLevel;
	}

	public void setMalsalaryLevel(List<String> malsalaryLevel) {
		this.malsalaryLevel = malsalaryLevel;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public String getResumeUpdated() {
		return resumeUpdated;
	}

	public void setResumeUpdated(String resumeUpdated) {
		this.resumeUpdated = resumeUpdated;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public Integer getStatrt() {
		return statrt;
	}

	public void setStatrt(Integer statrt) {
		this.statrt = statrt;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

}
