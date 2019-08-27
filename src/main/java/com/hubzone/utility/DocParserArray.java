package com.hubzone.utility;

/*
 * This class is for search pdf/doc file with userlist
 * 
 * */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import com.hubzone.model.Candidate;



public class DocParserArray {
	static Logger log = Logger.getLogger(DocParserArray.class);
	public List<Candidate> getCVSearchResult(String serarchStr, String cvLocation, List<Candidate> userNameList)
			throws Exception {
		List<String> cvFileNameList = FileUtility.getFileList(cvLocation);
		List<Candidate> mactchUserList = new ArrayList<Candidate>();
		
		/*for (String userName : serarchStr) {
			log.debug("Found User :"+userName);
		}*/
		
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
			boolean macthFound = false;
			boolean macthStr1 = false;
			boolean macthStr2 = false;

			// log.debug("File Name :" + fileName + " Content : "+wordText );
			/*for (int i=0;i<=serarchStr.size();i++){
			String[] keyWordList = serarchStr.get(i).split(" ");
			
			for (String keyWord : keyWordList) {
				String [] keywords=keyWord.split(" ");
				log.debug("List String : "+keyWord);	
			for(String key:keywords){
				if (wordText.toLowerCase().contains(key.trim().toLowerCase())) {
					macthFound = true;
                    break;   
				}
			}
			}
			}*/
			/*String spaceString=" ";
			if(serarchStr.get(0).isEmpty()){
				serarchStr.set(0, "bbbbb");
			}
			if(serarchStr.get(1).isEmpty()){
				serarchStr.set(1,"aaaaaa");
			}*/
			String newString;
			if(!serarchStr.isEmpty()) {
				
			
				if(serarchStr.contains("AND")){
					newString=serarchStr.replace("AND","bb");
				}else if(serarchStr.contains("and"))
				{
					newString=serarchStr.replace("and","bb");
				}else if(serarchStr.contains("OR")){
					newString=serarchStr.replace("OR","bb");
				}else if(serarchStr.contains("or"))
				{
					newString=serarchStr.replace("or","bb");
				}else{
					newString=serarchStr;
				}
				log.info("String after removing"+newString);
				
//				String[] keyWordList1=serarchStr.get(0).split(" ");
				String[] keyWordList1=newString.split(" ");
				//log.debug("Search String2 : "+serarchStr.get(0));
				if(keyWordList1.length>0){
					for(String keyWord : keyWordList1){
						String [] keywords=keyWord.split(" ");
						if(keywords.length > 0) {
							for(String key:keywords){
								if(wordText.toLowerCase().contains(key.trim().toLowerCase())){
									macthStr1=true;
									break;
								}
							}
						}
					}
				}				
			}
			
			
			/*if(!serarchStr.get(1).isEmpty()) {
				
	
				String newString1;
					if(serarchStr.get(1).contains("AND")){
						newString1=serarchStr.get(1).replace("AND","bb");
					}else if(serarchStr.get(1).contains("and")){
						newString1=serarchStr.get(1).replace("and","bb");
					}
					else if(serarchStr.get(1).contains("OR")){
						newString1=serarchStr.get(1).replace("OR","bb");
					}else if(serarchStr.get(1).contains("or")){
						newString1=serarchStr.get(1).replace("or","bb");
					}else{
						newString1=serarchStr.get(1);
					}
//			String[] keyWordList2=serarchStr.get(1).split(" ");
			String[] keyWordList2=newString1.split(" ");
			//log.debug("Search String1 : "+serarchStr.get(1));
			
			
			if(keyWordList2.length>0){
			for(String keyWord : keyWordList2){
				String [] keywords=keyWord.split(" ");
				for(String key:keywords){
					if(wordText.toLowerCase().contains(key.trim().toLowerCase())){
						macthStr2=true;
						break;
					}
				}
			}
			}
			}
			
			if(macthStr1 && macthStr2){
				macthFound=true;
			}*/
			
			System.out.println("Total user in first search"+userNameList.size());
			
			if (macthStr1) {
				//log.debug("Found Candidate : "+fileName.split("_resume.")[0]);
				//mactchUserList.add(fileName.split("_resume.")[0]);
				String name = fileName.split("_resume.")[0];
				for(Candidate cand : userNameList) {
					//log.debug("Candidate id in Loop "+cand.getCandidateID());
//					if (cand.getCandidateID().equals(name)) {
					if (cand.getCandidateID().equals(name)) {
						//log.debug("Candidate id "+cand.getCandidateID());
						mactchUserList.add(cand);
					}
				
					/*if(userNameList.contains(name)){
						mactchUserList.add(cand);
					}*/
				}				
//				if(Arrays.asList(userNameList.toArray()).contains(name)) {
//					
//				
//				mactchUserList.add(str);
//				}
			}
		}
		/*ArrayList<String>result=new ArrayList<String>();
		for(String temp:mactchUserList){
			if(userNameList.contains(temp)){
				result.add(temp);
			}
		}*/
		System.out.println("Total user in quick search Array"+mactchUserList.size());

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

}
