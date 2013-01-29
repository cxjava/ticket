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
			sb.append("numArray = new int[][] {");
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
			sb.append("\r\n};\r\n");
			sb.append("list.add( new Font('");
			sb.append(imgfile.getName().substring(0, 1));
			sb.append("', numArray));");
			LOG.info(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void allImageDot(String folder) {
		File dir = new File(folder);
		int i=0;
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (File imgfile : files) {
				try {
					if(i%70==0){
						LOG.info("return list; }");
						LOG.info("private static List<Font> getFonts"+i/70+"() {");
						LOG.info("List<Font> list = new ArrayList<Font>(); int[][]");
					}
//						LOG.info(imgfile.getName());
						printImage(imgfile);
						i++;
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			LOG.info("return list; }");
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
