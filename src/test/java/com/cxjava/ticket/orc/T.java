package com.cxjava.ticket.orc;

import java.io.File;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import com.cxjava.ticket.ocr.BinaryTest;
import com.cxjava.ticket.ocr.OCR;

/**
 * @author Maty Chen
 * @date 2013-1-22下午12:56:36
 */
public class T {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		for (int i = 31; i < 100; i++) {
			
		// TODO Auto-generated method stub
		File file = new File("D:\\05_Document\\Downloads\\12306\\"+i+".jpg");
		String randCodeByRob = OCR.read(file);
		File codeFile =new File("D:\\05_Document\\Downloads\\12306\\new\\"+randCodeByRob+".jpg");
		FileUtils.copyFile(file, codeFile);
		}
	}

}
