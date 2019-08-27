package com.hubzone.utility;

/*
 * This class is for  searching pdf files 
 * 
 *  
 * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class DocParser {
	static Logger log = Logger.getLogger(DocParser.class);

	public List<String> getCVSearchResult(String serarchStr, String cvLocation)
			throws Exception {

		List<String> cvFileNameList = FileUtility.getFileList(cvLocation);
		List<String> mactchUserList = new ArrayList<String>();
		for (String fileName : cvFileNameList) {
			System.out.println(fileName);
			String wordText="";
			String fileLocation = cvLocation
					+ System.getProperty("file.separator") + fileName;
			
			
			if (fileName.split("_resume.")[1].toLowerCase().equals("pdf")) {
			   wordText = getPdfFileContent(fileLocation);
				continue;
			}
			if (fileName.split("_resume.")[1].toLowerCase().equals("doc")) {
				wordText = getDocFileContent(fileLocation);
			}
			log.info("STring before search : "+serarchStr);
			String newString1;
			if(serarchStr.contains("AND")){
				newString1=serarchStr.replace("AND","bb");
			}else if(serarchStr.contains("and")){
				newString1=serarchStr.replace("and","bb");
			}
			else if(serarchStr.contains("OR")){
				newString1=serarchStr.replace("OR","bb");
			}else if(serarchStr.contains("or")){
				newString1=serarchStr.replace("or","bb");
			}else{
				newString1=serarchStr;
			}
			String newString2=newString1;
			log.info("String after removing"+newString1);
			// log.debug("File Name :" + fileName + " Content : "+wordText );
			String[] keyWordList = newString2.split(" ");
			log.info("New string after : "+keyWordList);
			boolean macthFound = false;
			for (String keyWord : keyWordList) {
				log.debug("FileName" + fileName + " keyWord1 " + keyWord);
				String [] keywords=keyWord.split(" ");
				
				for(String key:keywords){
					if (wordText.toLowerCase().contains(key.trim().toLowerCase())) {
						macthFound = true;
                        break;   
					}
				}
				
				
			}
			if (macthFound) {
				log.debug("Found Candidate : "+fileName.split("_resume.")[0]);
				mactchUserList.add(fileName.split("_resume.")[0]);
			}

		}
		System.out.println("Total user in quick search"+mactchUserList.size());

		return mactchUserList;

	}

	public String getDocFileContent(String fileLocation) throws Exception {
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(
				fileLocation));
		WordExtractor extractor = new WordExtractor(fs);
		String wordText = extractor.getText();
		return wordText;
	}

	public String getPdfFileContent(String fileLocation) throws Exception {
		FileInputStream fi = new FileInputStream(new File(fileLocation));

		PDFParser parser = new PDFParser(fi);
		parser.parse();
		COSDocument cd = parser.getDocument();
		PDFTextStripper stripper = new PDFTextStripper();
		String text = stripper.getText(new PDDocument(cd));

		return text;
	}

	public static void main(String[] args) throws Exception {
		DocParser p = new DocParser();
		// Directory path here
		String path = "D:\\eclipse workspace\\space1\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\hubzone\\WEB-INF\\cv";
		List<String> mactchUserList = p.getCVSearchResult("Sujan Kishor Nath",
				path);
		for (String userName : mactchUserList) {
			System.out.println(userName);
		}

	}
}
