package com.cxjava.ticket.ocr;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoundBinImage {
	private static final Logger LOG = LoggerFactory.getLogger(SoundBinImage.class);

	/**
	 * @param inputFile
	 * @param outFile
	 * @param Threshold
	 *            二值化程度
	 * @throws IOException
	 */
	public static void releaseSound(File inputFile, File outFile, int Threshold, String formatType) throws IOException {
		releaseSound(ImageIO.read(inputFile), outFile, Threshold, formatType);
	}

	public static void releaseSound(InputStream inputStream, File outFile, int Threshold, String formatType) throws IOException {
		releaseSound(ImageIO.read(inputStream), outFile, Threshold, formatType);
	}

	public static void releaseSound(BufferedImage bufferedImage, File outFile, int Threshold, String formatType) {
		// 过滤背景色进行黑白二值化处理
		try {
			BufferedImage bi = bufferedImage;// ImageIO.read(inputFile);
			int width = bi.getWidth();
			int height = bi.getHeight();
			BufferedImage bi2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Raster raster = bi.getRaster();
			WritableRaster wr = bi2.getRaster();
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					int[] a = new int[4];
					raster.getPixel(i, j, a);
					// System.out.println("("+a[0]+", "+a[1]+", "+a[2]+", "+a[3]+")");
					if ((a[0] + a[1] + a[2]) / 3 > Threshold) {
						a[0] = 255;
						a[1] = 255;
						a[2] = 255;
						a[3] = 255;
						wr.setPixel(i, j, a);
					} else {
						a[0] = 0;
						a[1] = 0;
						a[2] = 0;
						a[3] = 255;
						wr.setPixel(i, j, a);

					}
				}
			}
			ImageIO.write(bi2, formatType, outFile);
		} catch (IOException e) {
			LOG.error("e : {}.", e);
		}
	}
}