package com.hubzone.web.controller;


/*import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;*/
import java.util.List;

//import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hubzone.model.Candidate;
import com.hubzone.model.Users;

public class LoginControllerTest {
	
	//private static WebDriver driver;
	static Candidate candidate;
	static Users user;
	final static String baseurl = "https://localhost:8080/hubzone";
	
	
	/*@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		
		user = new Users("moin","moin");
		driver = new HtmlUnitDriver();
		
		driver.get(baseurl+"/login?param=cand");
		
		driver.findElement(By.name("j_username")).sendKeys(user.getUserName());
		driver.findElement(By.name("j_password")).sendKeys(user.getPassword());
		driver.findElement(By.className("btn")).click();
		
		if(driver.findElement(By.cssSelector(".table-style")).getText().contains("Job Seeker Profile")){
			System.out.println("Login Successful");
		}else{
			System.out.println("Login Failed");
		}
		
		Thread.sleep(1000);
		
	}
	
	@Test
	public void testAdminLogin(){
		
		user = new Users("admin","admin");
		
		driver = new HtmlUnitDriver();
		
		driver.get(baseurl+"/adminlogin");
		driver.findElement(By.name("j_username")).sendKeys(user.getUserName());
		driver.findElement(By.name("j_password")).sendKeys(user.getPassword());
		driver.findElement(By.className("btn")).click();
		
		if(driver.findElement(By.cssSelector(".table-style")).getText().contains("Admin Profile")){
			System.out.println("Login Successful");
		}else{
			System.out.println("Login Failed");
		}
		
		driver.quit();
	}
	
	@Test
	public void testEmployerLogin(){
		user = new Users("dtn","passs");
		
		driver = new HtmlUnitDriver();
		
		driver.get(baseurl+"/login?param=emp");
		driver.findElement(By.name("j_username")).sendKeys(user.getUserName());
		driver.findElement(By.name("j_password")).sendKeys(user.getPassword());
		driver.findElement(By.className("btn")).click();
		
		if(driver.findElement(By.cssSelector(".table-style")).getText().contains("Company Profile")){
			System.out.println("Login Successful");
		}else{
			System.out.println("Login Failed");
		}
		
		driver.quit();
		
	}*/

}
