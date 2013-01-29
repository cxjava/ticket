/**
 * 
 */
package com.cxjava.ticket.orc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cxjava.ticket.ocr.Binary;

public class BinaryTest {
	private static final Logger LOG = LoggerFactory.getLogger(BinaryTest.class);
	// private static final String SAVE_PATH = "f:\\Downloads\\12306\\";
	private static String SAVE_PATH = "D:\\05_Document\\Downloads\\12306\\";

	public static void main(String[] args) throws MalformedURLException, IOException {
		for (int i = 0; i < 20; i++) {
			File output = new File(SAVE_PATH + i + ".png");
			File output2 = new File(SAVE_PATH + i + "a.png");
			InputStream input=new URL("http://dynamic.12306.cn/otsweb/passCodeAction.do?rand=sjrand"+ "&" + Math.random())
			.openStream();
			FileUtils.copyInputStreamToFile(input, output2);
			Binary.binaryImage(output2, "png", output);
		}
	}
}