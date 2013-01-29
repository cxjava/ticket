/**
 * 
 */
package com.cxjava.ticket.orc;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author cx
 * @date Jan 26, 2013 10:43:29 PM
 */
public class T {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public static void main(String[] args) throws MalformedURLException, IOException {
		// TODO Auto-generated method stub

		for(int i=0;i<10;i++){
			File output= new File("f:\\Downloads\\12306\\"+i+".png");
			BinaryTest.binaryImage(new URL(
					"http://dynamic.12306.cn/otsweb/passCodeAction.do?rand=sjrand")
					.openStream(),"png",output);
		}
	}

}
