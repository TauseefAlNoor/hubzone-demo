package com.hubzone.utility;

/*
 * This class is for  generating random number
 * 
 * */

import java.security.SecureRandom;
import java.math.BigInteger;

public class Random {
	
	 private SecureRandom random = new SecureRandom();

	  public String nextSessionId()
	  {
	    return new BigInteger(130, random).toString(32);
	  }
	  
	  public static String getRandomValue(){
			return "asdaasdf@###sadas@#s45sadfjnsdfsdfklhj8934u23423423esfsfzc%%%";
		}

}
