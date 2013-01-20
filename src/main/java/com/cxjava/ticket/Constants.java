package com.cxjava.ticket;

import java.util.HashMap;

/**
 * 基本配置的类,由spring配置文件管理
 * 
 * @author cx
 * @date Jan 20, 2013 6:01:11 PM
 */
public class Constants {

	/** 登录链接 */
	public static String loginUrl;
	/** 登录验证码 */
	public static String loginCodeUrl;
	/** 登录随机数 */
	public static String loginRandomCodeUrl;
	/** 查询链接 */
	public static String queryUrl;
	/** 预订链接 */
	public static String bookUrl;
	/** 申请令牌 */
	public static String tokenUrl;
	/** 提交订单验证码 */
	public static String orderCodeUrl;
	/** 提交订单请求 */
	public static String submitOrderUrl;
	/** 查询余票 */
	public static String queryQueueCountUrl;
	/** 确认订单 */
	public static String confirmOrderUrl;
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

}
