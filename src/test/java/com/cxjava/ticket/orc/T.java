/**
 * 
 */
package com.cxjava.ticket.orc;

/**
 * @author cx
 * @date Jan 26, 2013 10:43:29 PM
 */
public class T {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("1#1#method2#".replaceFirst("\\d{1,3}#", ""));
		System.out.println("11#method2#".replaceFirst("\\d{1,3}#", ""));
		System.out.println("111#method2#".replaceFirst("\\d{1,3}#", ""));
		System.out.println("1111#method2#".replaceFirst("\\d{1,3}#", ""));
	}

}
