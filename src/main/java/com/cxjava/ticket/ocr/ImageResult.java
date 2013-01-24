package com.cxjava.ticket.ocr;

/**
 * @author Maty Chen
 * @date 2013-1-24上午10:22:36
 */
public class ImageResult implements Comparable<ImageResult> {

	private int effectCount, botResult;
	private float similarity;
	private char recognizechar;

	public ImageResult(char recognizechar, int botResult, float similarity, int effectCount) {
		this.recognizechar = recognizechar;
		this.botResult = botResult;
		this.effectCount = effectCount;
		this.similarity = similarity;
	}

	public float getSimilarity() {
		return similarity;
	}

	public int getEffectCount() {
		return effectCount;
	}

	public int getBotResult() {
		return botResult;
	}

	public char getRecognizechar() {
		return recognizechar;
	}

	public int compareTo(ImageResult obj) {
		ImageResult g = obj;
		if (this.getSimilarity() < g.getSimilarity()) {
			return 1;
		}
		if (this.getSimilarity() == g.getSimilarity()) {
			return 0;
		}
		return -1;
	}

}