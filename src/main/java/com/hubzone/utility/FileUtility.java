package com.hubzone.utility;

/*
 * This class responsible for file utility handling
 * 
 * */

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtility {
	public static String getFileExtention(String fileName) {
		String format = "";

		int index = fileName.lastIndexOf(".");
		if (index > 0) {
			format = fileName.substring(index + 1);
			format = format.toLowerCase();
		}
		return format;
	}

	public static List<String> getFileList(String location) {
		// Directory path here
	

		File folder = new File(location);
		File[] listOfFiles = folder.listFiles();
		List<String> arrayList = new ArrayList<String>();
		for (int i = 0; i < listOfFiles.length; i++) {

			if (listOfFiles[i].isFile()) {
				arrayList.add(listOfFiles[i].getName());

			}
		}
		return arrayList;
	}
}
