package com.hubzone.utility;

/*
 * This class is for data of job searching from database
 * 
 * */

import com.hubzone.model.Jobs;

public class JobSearch {
	private Jobs job;
	private String location;
	private Integer distance;
	private String Search;
	private String searchType;
	private String sort;
	private Integer statrt;
	private Integer end;
	public Jobs getJob() {
		return job;
	}
	public void setJob(Jobs job) {
		this.job = job;
	}
	public String getSearch() {
		return Search;
	}
	public void setSearch(String search) {
		Search = search;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
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
	
}
