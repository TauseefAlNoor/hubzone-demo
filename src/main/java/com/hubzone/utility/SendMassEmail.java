package com.hubzone.utility;

/*
 * This class is for  sending mass mail by admin
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




@Component

public class SendMassEmail {
	
//	@Autowired
//	private UsersService usersService;
	
	
	String serviceEmail="support@HUBZoneTalent.com";

	public void sendMail(Email mail) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtpout.secureserver.net");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "3535");
		mail.setFrom(serviceEmail);
		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(serviceEmail,"hubzone");
				}
			});
 
		try {
			String recipients[ ]=null;
			InternetAddress[] addressTo = new InternetAddress[recipients.length]; 
			for (int i = 0; i < recipients.length; i++) 
				{
				addressTo[i] = new InternetAddress(recipients[i]); 
				}
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mail.getFrom()));
			message.setRecipients(Message.RecipientType.TO,
					addressTo);
			message.setSubject(mail.getSubject());
			message.setText(mail.getBody());
			message.addHeader("Content-Type","text/html; charset=\"utf-8\"");
			Transport.send(message,message.getAllRecipients());
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	
	}
	public static void main(String args[]){
		SendMail mail =new SendMail();
		Email email= new Email();
		email.setTo("sujanctg2005@gmail.com");
		email.setFrom("support@HUBZoneTalent.com");
		email.setSubject("Change Password");
		email.setBody("To change password click following links");
		mail.sendMail(email);
	}

}
