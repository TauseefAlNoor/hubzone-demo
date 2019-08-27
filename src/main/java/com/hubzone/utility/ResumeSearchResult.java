package com.hubzone.utility;

/*
 * This class is for  result list of candidate
 * 
 * */

import java.util.ArrayList;
import java.util.List;

import com.hubzone.model.Candidate;

public class ResumeSearchResult {
	private List<Candidate> candidateList = new ArrayList<Candidate>();
	private Long totalSearchResult;

	public List<Candidate> getCandidateList() {
		return candidateList;
	}

	public void setCandidateList(List<Candidate> candidateList) {
		this.candidateList = candidateList;
	}

	public Long getTotalSearchResult() {
		return totalSearchResult;
	}

	public void setTotalSearchResult(Long totalSearchResult) {
		this.totalSearchResult = totalSearchResult;
	}

}
