package com.cxjava.ticket.ocr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OCR {
	private static final Logger LOG = LoggerFactory.getLogger(OCR.class);
	private final static String LANG_OPTION = "-l";
	private final static String EOL = System.getProperty("line.separator");
	private static String tessPath = "D:\\Program Files\\Tesseract-OCR";

	public static String read(File imageFile) throws Exception {
		File outputFile = new File(imageFile.getParentFile(), "output");
		StringBuffer sb = new StringBuffer();
		List<String> cmd = new ArrayList<String>();
		cmd.add(tessPath + "\\tesseract");
		cmd.add("");
		cmd.add(outputFile.getName());
		cmd.add(LANG_OPTION);
//		 cmd.add("verify");
		cmd.add("12306");
		 cmd.add("nobatch");
		 cmd.add("digits");

		ProcessBuilder pb = new ProcessBuilder();
		pb.directory(imageFile.getParentFile());

		cmd.set(1, imageFile.getName());
		pb.command(cmd);
		pb.redirectErrorStream(true);
		Process process = pb.start();
		int w = process.waitFor();
		LOG.debug("Exit value = {}", w);
		if (w == 0) {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(outputFile.getAbsolutePath() + ".txt"),
					"UTF-8"));
			String str;
			while ((str = in.readLine()) != null) {
				sb.append(str).append(EOL);
			}
			IOUtils.closeQuietly(in);
		} else {
			String msg;
			switch (w) {
			case 1:
				msg = "Errors accessing files. There may be spaces in your image's filename.";
				break;
			case 29:
				msg = "Cannot recognize the image or its selected region.";
				break;
			case 31:
				msg = "Unsupported image format.";
				break;
			default:
				msg = "Errors occurred.";
			}
			LOG.error("验证码获取失败 : {}.", msg);
		}
		FileUtils.deleteQuietly(new File(outputFile.getAbsolutePath() + ".txt"));
		return sb.toString().trim();
	}

}
