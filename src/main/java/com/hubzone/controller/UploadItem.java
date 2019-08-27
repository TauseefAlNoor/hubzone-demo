package com.hubzone.controller;

/*
 * This class will handle basic information for uploading file
 * */
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 */
public class UploadItem {
	private String fileDescription;
	private CommonsMultipartFile fileData;

	public CommonsMultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}

	public String getFileDescription() {
		return fileDescription;
	}

	public void setFileDescription(String fileDescription) {
		this.fileDescription = fileDescription;
	}

}
