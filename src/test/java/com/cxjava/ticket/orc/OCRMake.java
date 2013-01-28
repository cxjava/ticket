package com.cxjava.ticket.orc;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;


/**
 * 产生字符特征库
 * 
 * @author Maty Chen
 * @date 2013-1-28下午2:37:07
 */
public class OCRMake {
	private static String URL = "http://dynamic.12306.cn/otsweb/passCodeAction.do?rand=sjrand";
//	private static String SAVE_PATH = "D:\\05_Document\\Downloads\\12306\\";
	private static String SAVE_PATH = "F:\\Downloads\\12306\\";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		save();
		// 可以先筛选质量好的图片，再打印
		print();
	}

	/**
	 * 打印点阵
	 */
	private static void print() {
		PrintImageDotmap.allImageDot(SAVE_PATH);
	}

	/**
	 * 获取图片
	 */
	private static void save() {
		try {
			for (int i = 1; i < 50; i++) {
				InputStream input = new URL(URL + "&" + Math.random()).openStream();
				BufferedImage bufferedImage = ImageIO.read(input);
				// String code = OCR.imageToString(bufferedImage);
				// LOG.debug("code : {}.", code);
				File file = new File(SAVE_PATH + i + ".png");
				ImageIO.write(bufferedImage, "png", file);
			}
		} catch (IOException e) {
		}

	}

}
