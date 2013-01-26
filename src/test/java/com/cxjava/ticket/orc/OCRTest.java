/**
 * 
 */
package com.cxjava.ticket.orc;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cxjava.ticket.ocr.OCR;

/**
 * @author cx
 * @date Jan 23, 2013 10:32:36 PM
 */
public class OCRTest {
	private static final Logger LOG = LoggerFactory.getLogger(OCRTest.class);
	private static String URL = "http://dynamic.12306.cn/otsweb/passCodeAction.do?rand=sjrand";
//	private static String SAVE_PATH = "D:\\05_Document\\Downloads\\12306\\";
	private static String SAVE_PATH = "f:\\Downloads\\12306\\";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			for (int i = 1; i < 111; i++) {
				InputStream input = new URL(URL+"&"+Math.random()).openStream();
				BufferedImage bufferedImage = ImageIO.read(input);
				String code = OCR.imageToString(bufferedImage);
				LOG.debug("code : {}.", code);
				File file = new File(SAVE_PATH + i + ".png");
				ImageIO.write(bufferedImage, "png", file);
			}
		} catch (IOException e) {
			LOG.error("e : {}.", e);
		}

	}

}
