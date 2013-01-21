package com.cxjava.ticket.ocr2;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

/**
 * @author Maty Chen
 * @date 2013-1-21下午5:24:28
 */
public class OCRTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 String path = "D:\\05_Document\\Downloads\\passCodeAction.jpg";      
	        try {   
	        	for (int i = 0; i < 10; i++) {
	    			File file=new File("D:\\05_Document\\Downloads\\passCodeAction"+i+".jpg");
	    			InputStream input=new URL(
							"http://dynamic.12306.cn/otsweb/passCodeAction.do?rand=sjrand")
							.openStream();
	    			FileUtils.copyInputStreamToFile(input, file);
	            String valCode = new OCR().recognizeText(file, "jpg");      
	            System.out.println(valCode);      
	        	}
	        } catch (IOException e) {      
	            e.printStackTrace();      
	        } catch (Exception e) {   
	            e.printStackTrace();   
	        }     
	}

}
