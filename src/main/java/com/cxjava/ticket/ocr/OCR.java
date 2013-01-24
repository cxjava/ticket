package com.cxjava.ticket.ocr;

import java.util.ArrayList;
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
		return getImageResult(getImageRsults(b, bArray));
	}

	private static Map<String, Integer> GetPixel(BufferedImage img, int x, int y) {
		Map<String, Integer> rgbMap = new TreeMap<String, Integer>();
		rgbMap.put("R", (img.getRGB(x, y) & 0xff0000) >> 16);
		rgbMap.put("G", (img.getRGB(x, y) & 0xff00) >> 8);
		rgbMap.put("B", (img.getRGB(x, y) & 0xff));
		return rgbMap;
	}

	private static int[][] imageToInt(BufferedImage img) {
		int[][] numArray = new int[img.getHeight()][img.getWidth()];
		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				if (170 < colorToInt(GetPixel(img, j, i))) {
					numArray[i][j] = 0;
				} else {
					numArray[i][j] = 1;
				}
			}
		}
		return numArray;
	}

	private static int colorToInt(Map<String, Integer> color) {
		return (((((color.get("R") & 0xFF) * 0x4c8b) + ((color.get("G") & 0xFF) * 0x9645)) + ((color.get("B") & 0xFF) * 0x1d30)) >> 0x10);
	}

	private static ArrayList<ImageResult> getImageRsults(Font font, Font[] fontArray) {
		ArrayList<ImageResult> list = new ArrayList<ImageResult>();
		for (int i = 0; i < fontArray.length; i++) {
			for (int j = 0; j <= (font.getFontlengthH() - 1); j++) {
				int num3 = font.getFontlengthH() - ((fontArray[i].getFontlengthH() / 2) + j);
				for (int k = 0; k <= font.getFontlengthW(); k++) {
					int num5 = k - (fontArray[i].getFontlengthW() / 2);
					if (num5 < 0) {
						num5 = 0;
					}
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
					if ((num7 == num6) && (num8 != 0)) {
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
