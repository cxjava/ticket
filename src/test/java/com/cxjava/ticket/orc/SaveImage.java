package com.cxjava.ticket.orc;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.cxjava.ticket.ocr.BinaryTest;

/**
 * @author Maty Chen
 * @date 2013-1-22上午10:19:19
 */
public class SaveImage {

	/**
	 * @param args
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public static void main(String[] args) throws MalformedURLException, IOException {
		for (int i = 0; i < 100; i++) {
			File file = new File("D:\\05_Document\\Downloads\\12306\\" + i + ".jpg");
			BinaryTest.binaryImage(new URL("http://dynamic.12306.cn/otsweb/passCodeAction.do?rand=sjrand").openStream(), "jpg", file);
		}
	}

}
