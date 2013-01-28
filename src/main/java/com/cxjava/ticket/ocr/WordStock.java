package com.cxjava.ticket.ocr;

import java.util.ArrayList;
import java.util.List;

/**
 * 字库点阵
 * 
 * @author Maty Chen
 * @date 2013-1-24上午10:19:01
 */
public class WordStock {

	private static Font[] _fonts = null;

	public static Font[] getFontsInstance() {
		if (_fonts == null) {
			_fonts = getFonts();
		}
		System.out.println(_fonts.length);
		return _fonts;
	}

	private static Font[] getFonts() {
		List<Font> list=new ArrayList<Font>();
		int[][] numArray = new int[][] { { 0, 0, 0, 1, 1, 1, 1, 1, 0, 0 }, { 0, 0, 1, 1, 1, 1, 1, 1, 1, 0 },
				{ 0, 0, 1, 0, 0, 0, 0, 1, 1, 1 }, { 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 }, { 0, 0, 0, 0, 0, 0, 0, 1, 1, 0 },
				{ 0, 0, 0, 0, 0, 0, 1, 1, 1, 0 }, { 0, 0, 0, 0, 0, 1, 1, 0, 0, 0 }, { 0, 0, 0, 0, 1, 1, 0, 0, 0, 0 },
				{ 0, 0, 0, 1, 1, 0, 0, 0, 0, 0 }, { 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 1, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 0, 0 }, { 1, 1, 1, 1, 1, 1, 1, 1, 0, 0 } };
		list.add(  new Font('Z', numArray));
		numArray = new int[][] {
				{1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0},
				{0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1},
				{0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1},
				{0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0},
				{0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0},
				{0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0},
				{0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0},
				{0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0},
				{0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0},
				{0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0},
				{0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0},
				{0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0},
				{1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0},
				{1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0},
				{0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0}
				};
		list.add(new Font('N', numArray));
		numArray = new int[][] {
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
				{0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
				{0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1},
				{0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1},
				{0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1},
				{0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1},
				{0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
				{0, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0},
				{0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
				{0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
				{0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
				{0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
				{1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0},
				{1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0},
				{1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0}};
		list.add( new Font('P', numArray));
		numArray = new int[][] {
				{0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0},
				{0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
				{1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0},
				{1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 0},
				{1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 0},
				{1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1},
				{1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1},
				{1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1},
				{0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
				{0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
				{0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0},
				{1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0},
				{1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0},
				{1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0},
				{1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0}};
		list.add( new Font('9', numArray));
		numArray = new int[][] {
				{0, 0, 1, 1, 1, 1, 0, 1, 1, 1},
				{0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
				{0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
				{0, 0, 0, 1, 1, 1, 0, 0, 1, 1},
				{0, 0, 0, 1, 1, 1, 0, 0, 1, 1},
				{0, 0, 0, 1, 1, 1, 0, 0, 1, 1},
				{0, 0, 1, 1, 1, 1, 0, 0, 1, 1},
				{0, 0, 1, 1, 1, 1, 0, 0, 1, 1},
				{0, 0, 1, 1, 1, 1, 0, 0, 1, 1},
				{0, 0, 1, 1, 1, 1, 0, 0, 1, 1},
				{1, 1, 1, 1, 1, 1, 0, 0, 1, 1},
				{1, 1, 1, 1, 1, 1, 0, 0, 0, 0}};
		list.add( new Font('n', numArray));
		numArray = new int[][] {
				{1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1},
				{0,0,1,1,1,0,0,0,0,0,0,1,1,1,0,0},
				{0,0,1,1,1,0,0,0,0,0,0,1,1,1,0,0},
				{0,0,1,1,1,0,0,0,0,0,0,1,1,1,0,0},
				{0,0,1,1,1,0,0,0,0,0,0,1,1,1,0,0},
				{0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0},
				{0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,0},
				{0,0,1,1,1,0,0,0,0,0,0,1,1,1,0,0},
				{0,0,1,1,1,0,0,0,0,0,0,1,1,1,0,0},
				{0,0,1,1,1,0,0,0,0,0,0,1,1,1,0,0},
				{0,0,1,1,1,0,0,0,0,0,0,1,1,1,0,0},
				{0,0,1,1,1,0,0,0,0,0,0,1,1,1,1,1},
				{1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1}
		};
		list.add( new Font('H', numArray));
		numArray = new int[][] {
				{1,1,1,1,1,0,0,0,1,1,1,1,1},
				{1,1,1,1,1,1,0,0,1,1,1,1,1},
				{0,1,1,1,1,0,0,0,0,1,1,1,0},
				{0,0,1,1,1,0,0,0,0,1,1,0,0},
				{0,0,1,1,1,1,0,0,1,1,0,0,0},
				{0,0,0,1,1,1,1,1,1,1,0,0,0},
				{0,0,0,1,1,1,1,1,1,0,0,0,0},
				{0,0,0,0,1,1,1,1,1,0,0,0,0},
				{0,0,0,0,0,1,1,1,0,0,0,0,0},
				{0,0,0,0,0,1,1,1,0,0,0,0,0},
				{0,0,0,0,0,1,1,1,0,0,0,0,0},
				{0,0,0,0,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,1,0,0,0,0,0},
				{0,0,0,1,1,1,1,1,1,1,0,0,0},
				{0,0,0,1,1,1,1,1,1,1,0,1,0}
		};
		list.add( new Font('Y', numArray));
		numArray = new int[][] {
				{0,0,0,0,0,1,1,1,0,0,0},
				{0,0,0,0,0,1,1,1,0,0,0},
				{0,0,0,0,1,1,1,1,0,0,0},
				{0,0,0,0,1,0,1,1,0,0,0},
				{0,0,0,1,1,0,1,1,0,0,0},
				{0,0,1,1,0,0,1,1,0,0,0},
				{0,1,1,0,0,0,1,1,0,0,0},
				{0,1,1,0,0,0,1,1,0,0,0},
				{1,1,0,0,0,0,1,1,0,0,0},
				{1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,0,0,0,1,1,0,0,0},
				{0,0,0,1,1,1,1,1,1,1,1},
				{0,1,1,1,1,1,1,1,0,0,0},
				{0,0,0,0,1,1,1,1,1,1,0}
		};
		list.add( new Font('4', numArray));
		numArray = new int[][] {
				{1,1,1,1,1,0,0,1,1,1,1,1},
				{1,1,1,1,1,0,0,1,1,1,1,1},
				{0,1,1,1,0,0,0,0,1,1,0,0},
				{0,0,1,1,1,0,0,1,1,1,0,0},
				{0,0,1,1,1,0,0,1,1,0,0,0},
				{0,0,1,1,1,0,0,1,1,0,0,0},
				{0,0,0,1,1,1,1,1,1,0,0,0},
				{0,0,0,1,1,1,1,1,0,0,0,0},
				{0,0,0,1,0,1,0,0,0,0,0,0},
				{0,0,0,0,1,1,1,0,0,0,0,0},
				{0,0,0,0,1,1,1,0,0,0,0,0}
				};
		list.add( new Font('V', numArray));
		return list.toArray(new Font[]{});
	}

}
