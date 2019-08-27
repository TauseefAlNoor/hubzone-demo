package com.hubzone.utility;

/*
 * This class is for email handling
 * 
 * */

public class Email {
	public String to;
	public String[] recipt;
	public String[] getRecipt() {
		return recipt;
	}

	public void setRecipt(String[] recipt) {
		this.recipt = recipt;
	}

	public String from;
	public String body;
	public String subject;
	public String fileName;
	public String fileSource;

	public String getFileSource() {
		return fileSource;
	}

	public void setFileSource(String fileSource) {
		this.fileSource = fileSource;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

}
