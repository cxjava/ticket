/**
 * 
 */
package com.cxjava.ticket.ocr;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Binary {
	private static final Logger LOG = LoggerFactory.getLogger(Binary.class);
	public static void binaryImage(File inputFile, String formatName, File outFile) throws IOException {
		binaryImage(ImageIO.read(inputFile), formatName, outFile);
	}

	public static void binaryImage(InputStream inputStream, String formatName, File outFile) throws IOException {
		binaryImage(ImageIO.read(inputStream), formatName, outFile);
	}

	public static void ClearNoise(BufferedImage bufferedImage,int dgGrayValue, int MaxNearPoints) {
		int h = bufferedImage.getHeight();
		int w = bufferedImage.getWidth();
		int nearDots = 0;
		// 逐点判断
		for (int i = 0; i < w; i++)
			for (int j = 0; j < h; j++) {
				int argb = bufferedImage.getRGB(i, j);
				// 图像加亮（调整亮度识别率非常高）
				int r = (int) (((argb >> 16) & 0xFF));
				if (r < dgGrayValue) {
					nearDots = 0;
					// 判断周围8个点是否全为空
					if (i == 0 || i == w - 1 || j == 0 || j == h - 1) // 边框全去掉
					{
//						bmpobj.SetPixel(i, j, Color.WHITE);
						bufferedImage.setRGB(i, j, Color.WHITE.getRGB());
					} else {
						if (new Color(bufferedImage.getRGB(i - 1, j - 1)).getRed() < dgGrayValue)
							nearDots++;
						if (new Color(bufferedImage.getRGB(i, j - 1)).getRed() < dgGrayValue)
							nearDots++;
						if (new Color(bufferedImage.getRGB(i + 1, j - 1)).getRed() < dgGrayValue)
							nearDots++;
						if (new Color(bufferedImage.getRGB(i - 1, j)).getRed() < dgGrayValue)
							nearDots++;
						if (new Color(bufferedImage.getRGB(i + 1, j)).getRed() < dgGrayValue)
							nearDots++;
						if (new Color(bufferedImage.getRGB(i - 1, j + 1)).getRed() < dgGrayValue)
							nearDots++;
						if (new Color(bufferedImage.getRGB(i, j + 1)).getRed() < dgGrayValue)
							nearDots++;
						if (new Color(bufferedImage.getRGB(i + 1, j + 1)).getRed() < dgGrayValue)
							nearDots++;
					}

					if (nearDots < MaxNearPoints)
						bufferedImage.setRGB(i, j, Color.WHITE.getRGB()); // 去掉单点 && 粗细小3邻边点
				} else
					// 背景
					bufferedImage.setRGB(i, j, Color.WHITE.getRGB());
			}
	}
	/**
	 * 图像灰度化与 二值化
	 * 
	 * @see http://www.oschina.net/code/snippet_147955_17496
	 * 
	 * @param bufferedImage
	 * @param formatName
	 *            后缀名
	 * @param outFile
	 * @return
	 * @throws IOException
	 */
	public static void binaryImage(BufferedImage bufferedImage, String formatName, File outFile) throws IOException {
		int h = bufferedImage.getHeight();
		int w = bufferedImage.getWidth();

		// 灰度化
		int[][] gray = new int[w][h];
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				int argb = bufferedImage.getRGB(x, y);
				// 图像加亮（调整亮度识别率非常高）
				int r = (int) (((argb >> 16) & 0xFF));// * 1.1 + 40);//
				int g = (int) (((argb >> 8) & 0xFF));// * 1.1 + 40);
				int b = (int) (((argb >> 0) & 0xFF));// * 1.1 + 40);
				if (r >= 255) {
					r = 255;
				}
				if (g >= 255) {
					g = 255;
				}
				if (b >= 255) {
					b = 255;
				}
				gray[x][y] = (int) Math.pow((Math.pow(r, 2.2) * 0.2973 + Math.pow(g, 2.2) * 0.6274 + Math.pow(b, 2.2) * 0.0753), 1 / 2.2);
			}
		}

		// 二值化
//		int threshold = ostu(gray, w, h);
		LOG.debug(" ostu2(gray, w, h) : {}.",  ostu2(gray, w, h));
		LOG.debug(" ostu(gray, w, h) : {}.",  ostu(gray, w, h));
		int threshold = ostu2(gray, w, h);
		ClearNoise(bufferedImage,threshold,w*h);
		BufferedImage binaryBufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY);
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				if (gray[x][y] > threshold) {
					gray[x][y] |= 0x00FFFF;
				} else {
					gray[x][y] &= 0xFF0000;
				}
				binaryBufferedImage.setRGB(x, y, gray[x][y]);
			}
		}
		// 矩阵打印
		// for (int y = 0; y < h; y++) {
		// for (int x = 0; x < w; x++) {
		// if (isBlack(binaryBufferedImage.getRGB(x, y))) {
		// System.out.print("*");
		// } else {
		// System.out.print(" ");
		// }
		// }
		// System.out.println();
		// }
		ImageIO.write(binaryBufferedImage, formatName, outFile);

	}

	public static boolean isBlack(int colorInt) {
		Color color = new Color(colorInt);
		if (color.getRed() + color.getGreen() + color.getBlue() <= 300) {
			return true;
		}
		return false;
	}

	public static boolean isWhite(int colorInt) {
		Color color = new Color(colorInt);
		if (color.getRed() + color.getGreen() + color.getBlue() > 300) {
			return true;
		}
		return false;
	}

	public static int isBlackOrWhite(int colorInt) {
		if (getColorBright(colorInt) < 30 || getColorBright(colorInt) > 730) {
			return 1;
		}
		return 0;
	}

	public static int getColorBright(int colorInt) {
		Color color = new Color(colorInt);
		return color.getRed() + color.getGreen() + color.getBlue();
	}

	public static int ostu2(int[][] gray, int w, int h) {
		int[] pixelNum = new int[w * h]; // 图象直方图，共256个点
		int n, n1, n2;
		int total = w * h; // total为总和，累计值
		double m1, m2, sum, csum, fmax, sb; // sb为类间方差，fmax存储最大方差值
		int k, t, q;
		int threshValue = 1; // 阈值
		// //生成直方图
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				// 返回各个点的颜色，以RGB表示
				int red = 0xFF & gray[x][y];
				pixelNum[red]++; // 相应的直方图加1
			}
		}
		// 直方图平滑化
		for (k = 0; k <= 255; k++) {
			total = 0;
			for (t = -2; t <= 2; t++) // 与附近2个灰度做平滑化，t值应取较小的值
			{
				q = k + t;
				if (q < 0) // 越界处理
					q = 0;
				if (q > 255)
					q = 255;
				total = total + pixelNum[q]; // total为总和，累计值
			}
			pixelNum[k] = (int) ((float) total / 5.0 + 0.5); // 平滑化，左边2个+中间1个+右边2个灰度，共5个，所以总和除以5，后面加0.5是用修正值
		}
		// 求阈值
		sum = csum = 0.0;
		n = 0;
		// 计算总的图象的点数和质量矩，为后面的计算做准备
		for (k = 0; k <= 255; k++) {
			sum += (double) k * (double) pixelNum[k]; // x*f(x)质量矩，也就是每个灰度的值乘以其点数（归一化后为概率），sum为其总和
			n += pixelNum[k]; // n为图象总的点数，归一化后就是累积概率
		}

		fmax = -1.0; // 类间方差sb不可能为负，所以fmax初始值为-1不影响计算的进行
		n1 = 0;
		for (k = 0; k < 256; k++) // 对每个灰度（从0到255）计算一次分割后的类间方差sb
		{
			n1 += pixelNum[k]; // n1为在当前阈值遍前景图象的点数
			if (n1 == 0) {
				continue;
			} // 没有分出前景后景
			n2 = n - n1; // n2为背景图象的点数
			if (n2 == 0) {
				break;
			} // n2为0表示全部都是后景图象，与n1=0情况类似，之后的遍历不可能使前景点数增加，所以此时可以退出循环
			csum += (double) k * pixelNum[k]; // 前景的“灰度的值*其点数”的总和
			m1 = csum / n1; // m1为前景的平均灰度
			m2 = (sum - csum) / n2; // m2为背景的平均灰度
			sb = (double) n1 * (double) n2 * (m1 - m2) * (m1 - m2); // sb为类间方差
			if (sb > fmax) // 如果算出的类间方差大于前一次算出的类间方差
			{
				fmax = sb; // fmax始终为最大类间方差（otsu）
				threshValue = k; // 取最大类间方差时对应的灰度的k就是最佳阈值
			}
		}
		return threshValue;
	}

	public static int ostu(int[][] gray, int w, int h) {
		int[] histData = new int[w * h];
		// Calculate histogram
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				int red = 0xFF & gray[x][y];
				histData[red]++;
			}
		}

		// Total number of pixels
		int total = w * h;

		float sum = 0;
		for (int t = 0; t < 256; t++)
			sum += t * histData[t];

		float sumB = 0;
		int wB = 0;
		int wF = 0;

		float varMax = 0;
		int threshold = 0;

		for (int t = 0; t < 256; t++) {
			wB += histData[t]; // Weight Background
			if (wB == 0)
				continue;

			wF = total - wB; // Weight Foreground
			if (wF == 0)
				break;

			sumB += (float) (t * histData[t]);

			float mB = sumB / wB; // Mean Background
			float mF = (sum - sumB) / wF; // Mean Foreground

			// Calculate Between Class Variance
			float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);

			// Check if new maximum found
			if (varBetween > varMax) {
				varMax = varBetween;
				threshold = t;
			}
		}

		return threshold;
	}
}