package com.hubzone.utility;

/*
 * This class is for  send mail configuration 
 * 
 * */

import java.io.File;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import org.springframework.stereotype.Component;

//import com.scrapperx.utility.SendMailTLS.EmailThread;

@Component
public class SendMailTLS {
	Session session;
	Properties props = new Properties();

	public SendMailTLS() {
		
		//props.put("mail.smtp.host", "smtpout.secureserver.net");
		props.put("mail.smtp.host", "mail.hubzonetalent.com");
		props.put("mail.smtp.socketFactory.port", "465");
		//465
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "25");
		//props.setProperty("mail.smtp.host", "smtpout.secureserver.net");
		session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(
								"support@HUBZoneTalent.com", "Valiant2015");
					}
				});
	}

	public void sendAttachMentMail(Email mail) {

		try {

			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(mail.getFrom()));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					mail.getTo()));

			// Set Subject: header field
			message.setSubject(mail.getSubject());

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			messageBodyPart.setText(mail.getBody());

			// Create a multipar message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			messageBodyPart = new MimeBodyPart();
			String filename = mail.getFileName();
			DataSource source = new FileDataSource(mail.getFileSource());
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			message.setContent(multipart);

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	public void sendMail(Email mail) {

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mail.getFrom()));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(mail.getTo()));
			message.setSubject(mail.getSubject());
			message.setText(mail.getBody());
			message.addHeader("Content-Type", "text/html; charset=\"utf-8\"");
			
			//Transport.send(message);
			//log.debug("Creating thread for email notification");
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
	 public   static void main1(String c){
		c="d";
	}
	public static void main(String args[]) {
		SendMailTLS mail = new SendMailTLS();
		Email email = new Email();
		email.setTo("tauseef.cse100@gmail.com");
		email.setFrom("support@HUBZoneTalent.com");
		email.setSubject("Change Password");
		email.setBody("To change password click following links");
		//email.setFileSource("Users/tauseefalnoor/Desktop/uni-mail.txt");
		//email.setFileName("cv.pdf");
		//mail.sendAttachMentMail(email);
		mail.sendMail(email);
		System.out.println("Done");
		//File resume = new File("D:\\ThesisReport.pdf");
		//String c="ddddv";
		//System.out.println(c.getClass().getName());
	}
}
