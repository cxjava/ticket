package com.cxjava.ticket.utils;

import java.util.HashMap;

/**
 * 基本配置的类,由spring配置文件管理
 * 
 * @author cx
 * @date Jan 20, 2013 6:01:11 PM
 */
public class Constants {

	/** 数量 */
	public static HashMap<String, Integer> seatNumMap;
	/** 火车类型 */
	public static HashMap<String, Integer> trainPriorityMap;
	/** 席别类型 */
	public static HashMap<String, String> seatNameMap;
	/** 出发时间 */
	public static HashMap<String, String> trainRang;
	/** 证件类型 */
	public static HashMap<String, String> cardType;
	/** 票种类型 */
	public static HashMap<String, String> ticketType;
	/** 卧铺类型 */
	public static HashMap<String, String> berth;

	/**
	 * @return the seatNumMap 数量
	 */
	public static HashMap<String, Integer> getSeatNumMap() {
		return seatNumMap;
	}

	/**
	 * @param seatNumMap
	 *            the seatNumMap to set 数量
	 */
	public void setSeatNumMap(HashMap<String, Integer> seatNumMap) {
		Constants.seatNumMap = seatNumMap;
	}

	/**
	 * @return the trainPriorityMap 火车类型
	 */
	public static HashMap<String, Integer> getTrainPriorityMap() {
		return trainPriorityMap;
	}

	/**
	 * @param trainPriorityMap
	 *            the trainPriorityMap to set 火车类型
	 */
	public void setTrainPriorityMap(HashMap<String, Integer> trainPriorityMap) {
		Constants.trainPriorityMap = trainPriorityMap;
	}

	/**
	 * @return the seatNameMap 席别类型
	 */
	public static HashMap<String, String> getSeatNameMap() {
		return seatNameMap;
	}

	/**
	 * @param seatNameMap
	 *            the seatNameMap to set 席别类型
	 */
	public void setSeatNameMap(HashMap<String, String> seatNameMap) {
		Constants.seatNameMap = seatNameMap;
	}

	/**
	 * @return the trainRang 出发时间
	 */
	public static HashMap<String, String> getTrainRang() {
		return trainRang;
	}

	/**
	 * @param trainRang
	 *            the trainRang to set 出发时间
	 */
	public void setTrainRang(HashMap<String, String> trainRang) {
		Constants.trainRang = trainRang;
	}

	/**
	 * @return the cardType 证件类型
	 */
	public static HashMap<String, String> getCardType() {
		return cardType;
	}

	/**
	 * @param cardType
	 *            the cardType to set 证件类型
	 */
	public void setCardType(HashMap<String, String> cardType) {
		Constants.cardType = cardType;
	}

	/**
	 * @return the ticketType 票种类型
	 */
	public static HashMap<String, String> getTicketType() {
		return ticketType;
	}

	/**
	 * @param ticketType
	 *            the ticketType to set 票种类型
	 */
	public void setTicketType(HashMap<String, String> ticketType) {
		Constants.ticketType = ticketType;
	}

	/**
	 * @return the berth 卧铺类型
	 */
	public static HashMap<String, String> getBerth() {
		return berth;
	}

	/**
	 * @param berth
	 *            the berth to set 卧铺类型
	 */
	public void setBerth(HashMap<String, String> berth) {
		Constants.berth = berth;
	}

}
