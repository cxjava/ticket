package com.cxjava.ticket.ocr;

import java.util.ArrayList;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.lang.Math;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Maty Chen
 * @date 2013-1-24上午10:24:21
 */
public class OCR {

	public static String imageToString(BufferedImage Image) {
		Font b = new Font(imageToInt(Image));
		Font[] bArray = WordStock.getFontsInstance();
//		return getImageResult(process2list(b, bArray));
		return getImageResult(getImageRsults(b, bArray));
	}

	private static int getColorWeight(Color color) {
		return (int) color.getRed() * 19595 + (int) color.getGreen() * 38469 + (int) color.getBlue() * 7472 >> 16;
	}

	public static int[][] imageToInt(BufferedImage img) {
		int[][] numArray = new int[img.getHeight()][img.getWidth()];
		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				// old 170 get model use 170
				if (190 < getColorWeight(new Color(img.getRGB(j, i)))) {
					numArray[i][j] = 0;
				} else {
					numArray[i][j] = 1;
				}
			}
		}
		return numArray;
	}
	private static ArrayList<ImageResult> process2list(Font A_0, Font[] A_1) {
		ArrayList<ImageResult> list = new ArrayList<ImageResult>();
		for (int i = 0; i < A_1.length; i++) {
			for (int j = 0; j <= A_0.getFontlengthH() - 1; j++) {
				int num = A_0.getFontlengthH() - (A_1[i].getFontlengthH() / 2 + j);
				for (int k = 0; k <= A_0.getFontlengthW(); k++) {
					int num2 = k - A_1[i].getFontlengthW() / 2;
					if (num2 < 0) {
						num2 = 0;
					}
					int num3 = (A_1[i].getFontlengthW() + 1) / 2 + k;
					if (num3 > A_0.getFontlengthW()) {
						num3 = A_0.getFontlengthW();
					}
					int num4 = 0;
					int num5 = 0;
					int l;
					for (l = num2; l < num3; l++) {
						int num6 = (A_1[i].getFontlengthW() / 2 - k > 0) ? (A_1[i].getFontlengthW() / 2 - k) : 0;
						int num7;
						if (num > 0) {
							num7 = (int) (A_1[i].getLFontsWH().get((num6 + l - num2)) << num);
						} else {
							num7 = (int) (A_1[i].getLFontsWH().get(num6 + l - num2) >> num * -1);
						}
						num7 &= (int) Math.pow(2.0, (double) A_0.getFontlengthH()) - 1;
						num4 += chuliNumber(num7);
						if (num4 == 0) {
							num5++;
						}
						if (((num7 & A_0.getLFontsWH().get(l)) ^ num7) != 0) {
							break;
						}
					}
					if (l == num3 && num4 != 0) {
						if (num > 0) {
							if (A_1[i].getFontlengthH() + num < A_0.getFontlengthH()) {
								A_1[i].getFontlengthH();
							} else {
								A_0.getFontlengthH();
							}
						} else {
							A_1[i].getFontlengthH();
						}
						ImageResult item = new ImageResult(A_1[i].getImageChar(), num2 + num5, (float) num4 / (float) A_1[i].getEffectCount(),
								A_1[i].getEffectCount());
						list.add(item);
					}
				}
			}
		}
		return list;
	}
	private static int chuliNumber(int A_0) {
		int num = 0;
		while ((long) num < (long) (A_0)) {
			num++;
			A_0 &= A_0 - 1;
		}
		return num;
	}
	private static ArrayList<ImageResult> getImageRsults(Font font, Font[] fontArray) {
		ArrayList<ImageResult> list = new ArrayList<ImageResult>();
		for (int i = 0; i < fontArray.length; i++) {//遍历特征库
			//getFontlengthH 验证码高度
			for (int j = 0; j <= (font.getFontlengthH() - 1); j++) {
				//目标验证码减去特征库验证码高度一半
				int num3 = font.getFontlengthH() - ((fontArray[i].getFontlengthH() / 2) + j);
				//getFontlengthH 验证码宽度
				for (int k = 0; k <= font.getFontlengthW(); k++) {
					//目标验证码减去特征库验证码宽度一半
					int num5 = k - (fontArray[i].getFontlengthW() / 2);
					if (num5 < 0) {
						num5 = 0;
					}
					//特征库验证码宽度一半
					int num6 = ((fontArray[i].getFontlengthW() + 1) / 2) + k;
					if (num6 > font.getFontlengthW()) {
						num6 = font.getFontlengthW();
					}
					int num8 = 0;
					int num9 = 0;
					int num7 = num5;
					while (num7 < num6) {
						/* uint */long num10;
						int num11 = (((fontArray[i].getFontlengthW() / 2) - k) > 0) ? ((fontArray[i].getFontlengthW() / 2) - k) : 0;
						if (num3 > 0) {
							num10 = ((fontArray[i].getLFontsWH().get((num11 + num7) - num5) << (((num3 & 0x1f)) & 0x1F)) & 0xFFFFFFFFL);
						} else {
							num10 = (fontArray[i].getLFontsWH().get((num11 + num7) - num5) & 0xFFFFFFFFL) >> ((num3 * -1) & 0x1f);
						}
						num10 &= ((/* uint */long) Math.pow(2.0, (double) font.getFontlengthH())) - 1;
						num8 += getEffCount(num10);
						if (num8 == 0) {
							num9++;
						}
						if ((((num10 & 0xFFFFFFFFL) & (font.getLFontsWH().get(num7) & 0xFFFFFFFFL)) ^ (num10 & 0xFFFFFFFFL)) != 0) {
							break;
						}
						num7++;
					}
					 if ((num6 == num7) && (num8 != 0)) {
						ImageResult item = new ImageResult(fontArray[i].getImageChar(), num5 + num9, ((float) num8)
								/ ((float) fontArray[i].getEffectCount()), fontArray[i].getEffectCount());
						list.add(item);
					}
				}
			}
		}
		return list;
	}

	private static String getImageResult(ArrayList<ImageResult> lImageresult) {
		StringBuilder builder = new StringBuilder();
		Collections.sort(lImageresult);
		Map<Integer, ImageResult> list = new TreeMap<Integer, ImageResult>();
		for (int i = 0; i < 4; i++) {
			if (lImageresult.size() < 4) {
				return "";
			}
			list.put(lImageresult.get(i).getBotResult(), lImageresult.get(i));
			for (int j = i + 1; j < lImageresult.size(); j++) {
				if (Math.abs((int) (lImageresult.get(i).getBotResult() - lImageresult.get(j).getBotResult())) < 3) {
					if (((ImageResult) list.get(lImageresult.get(i).getBotResult())).getEffectCount() < lImageresult.get(j)
							.getEffectCount()) {
						list.put(lImageresult.get(i).getBotResult(), lImageresult.get(j));
					}
					lImageresult.remove(j);
					j--;
				}
			}
		}
		for (Map.Entry<Integer, ImageResult> entry : list.entrySet()) {
			builder.append(((ImageResult) entry.getValue()).getRecognizechar());
		}
		return builder.toString();
	}

	private static int getEffCount(/* uint */long Num) {
		int num = 0;
		while (num < (Num & 0xFFFFFFFFL)) {
			num++;
			Num &= (Num & 0xFFFFFFFFL) - 1;
		}
		return num;
	}
}
