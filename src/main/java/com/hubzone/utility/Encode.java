package com.hubzone.utility;

/*
 * This class is for  url encoding
 * 
 * */

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class Encode {
	public static String encodeUri(String value) throws UnsupportedEncodingException {
		return URLEncoder.encode(value, "UTF-8");
	}
	public static String decodeUri(String value) throws UnsupportedEncodingException {
		return URLDecoder.decode(value, "UTF-8");
	}
}
