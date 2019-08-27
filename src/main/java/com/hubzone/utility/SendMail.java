package com.hubzone.utility;

/*
 * This class is for  sending mail configuration
 * 
 * */

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

import com.hubzone.utility.SendMailTLS.EmailThread;


@Component

public class SendMail {
	
	
	String serviceEmail="support@HUBZoneTalent.com";
	public void sendMail(Email mail) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "mail.hubzonetalent.com");
		props.put("mail.smtp.socketFactory.port", "465");
		//465
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "3535");
		mail.setFrom(serviceEmail);
		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(serviceEmail,"Valiant2015");
				}
			});
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mail.getFrom()));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(mail.getTo()));
			message.setSubject(mail.getSubject());
			message.setText(mail.getBody());
			message.addHeader("Content-Type","text/html; charset=\"utf-8\"");
			//Transport.send(message);
			
			Thread th = new Thread(new EmailThread(message));
			th.start();
			//log.debug("********** email notification sent*********");
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	
	}
	
	class EmailThread implements Runnable {
		Message message;		

		public EmailThread(Message message) {
			this.message = message;			
		}

		@Override
		public void run() {
			try {
				//log.debug("****************Sending email************************");
				Transport.send(message);
				//log.debug("****************Email Sent************************");
			} catch (MessagingException e) {

				e.printStackTrace();
			}
		}

	}
	
	public static void main(String args[]){
		SendMail mail =new SendMail();
		Email email= new Email();
		email.setTo("tauseef.cse100@gmail.com");
		email.setFrom("support@HUBZoneTalent.com");
		email.setSubject("Change Password");
		email.setBody("To change password click following links");
		mail.sendMail(email);
	}

}
