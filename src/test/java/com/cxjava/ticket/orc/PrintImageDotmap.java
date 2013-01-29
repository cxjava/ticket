package com.cxjava.ticket.orc;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author leeass
 * 
 */
public class PrintImageDotmap {
	private static final Logger LOG = LoggerFactory.getLogger(PrintImageDotmap.class);
	public static void printImage(File imgfile) {
		BufferedImage bi;
		try {
			bi = ImageIO.read(imgfile);
			int[][] data = bitmap2DotArray(bi);

			StringBuffer sb = new StringBuffer();
			sb.append("{");
			for (int i = 0; i < data.length; i++) {
				sb.append("\r\n{");
				for (int j = 0; j < data[0].length; j++) {
					sb.append(data[i][j]).append(",");
				}
				if (i == data.length - 1) {
					sb.append("}");
				} else {
					sb.append("},");
				}
			}
			sb.append("\r\n}\r\n");
			LOG.info(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void allImageDot(String folder) {
		File dir = new File(folder);
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (File imgfile : files) {
				try {
						LOG.info(imgfile.getName());
						printImage(imgfile);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}

	private static int[][] bitmap2DotArray(BufferedImage buffImage) {
		int[][] array = new int[buffImage.getHeight()][buffImage.getWidth()];

		for (int i = 0; i < buffImage.getHeight(); i++) {
			for (int j = 0; j < buffImage.getWidth(); j++) {

				if (168 < getColorWeight(new Color(buffImage.getRGB(j, i)))) {
					array[i][j] = 0;
				} else {
					array[i][j] = 1;
				}
			}
		}
		return array;
	}

	private static int getColorWeight(Color color) {
		return (int) color.getRed() * 19595 + (int) color.getGreen() * 38469 + (int) color.getBlue() * 7472 >> 16;
	}

}
