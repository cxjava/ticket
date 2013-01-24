package com.cxjava.ticket.ocr;

import java.util.ArrayList;

/**
 * @author Maty Chen
 * @date 2013-1-24上午10:21:50
 */
public class Font {

	// Fields
	private char imagechar;
	private int[][] fonts;
	private ArrayList<Long> Lfontshw;
	private ArrayList<Long> Lfontswh;
	private int effectcount = 0;

	// Methods

	public Font(int[][] fonts) {
		this.initialization(fonts);
	}

	public Font(char imagechar, int[][] fonts) {
		this(fonts);

		this.imagechar = imagechar;
	}

	public char getImageChar() {
		return imagechar;
	}

	public int getFontlengthH() {
		return fonts.length;
	}

	public int getFontlengthW() {
		return fonts[0].length;
	}

	public ArrayList<Long> getLFontsHW() {
		return Lfontshw;
	}

	public ArrayList<Long> getLFontsWH() {
		return Lfontswh;
	}

	public int[][] getFonts() {
		return fonts;
	}

	public int getEffectCount() {
		return effectcount;
	}

	private ArrayList<Long> getLfontsHW(int[][] fonts) {
		ArrayList<Long> list = new ArrayList<Long>();
		for (int i = 0; i < fonts.length; i++) {
			list.add(0L);
			for (int j = 0; j < fonts[0].length; j++) {
				list.set(
						i,
						(list.get(i) & 0xFFFFFFFFL)
								+ ((Integer.parseInt(Integer.toString(fonts[i][j])) & 0xFFFFFFFFL) * ((/* uint */long) Math.pow(2.0,
										(double) ((fonts.length - 1) - j)))));
			}
		}
		return list;
	}

	private ArrayList<Long> getLfontsWH(int[][] fonts) {
		ArrayList<Long> list = new ArrayList<Long>();
		for (int i = 0; i < fonts[0].length; i++) {
			list.add(0L);
			for (int j = 0; j < fonts.length; j++) {
				list.set(
						i,
						(list.get(i) & 0xFFFFFFFFL)
								+ ((Integer.parseInt((fonts[j][i] & 0xFFFFFFFFL) + "")) * ((/* uint */long) Math.pow(2.0,
										(double) ((fonts.length - 1) - j)))));
				if (fonts[j][i] == 1) {
					this.effectcount++;
				}
			}
		}
		return list;
	}

	private void initialization(int[][] fonts) {
		this.fonts = fonts;
		this.Lfontshw = new ArrayList<Long>();
		this.Lfontshw = this.getLfontsHW(fonts);
		this.Lfontswh = new ArrayList<Long>();
		this.Lfontswh = getLfontsWH(fonts);
	}

}
