package com.cxjava.ticket.bean;

import java.util.HashMap;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 登录相关
 * 
 * @author Maty Chen
 * @date 2013-1-24上午10:28:56
 */
public class Login {
	private static final Logger LOG = LoggerFactory.getLogger(Login.class);
	/** 获取随机数地址 */
	private String loginRandomCodeUrl;
	/** 验证码地址 */
	private String loginCodeUrl;
	/** 登录地址 */
	private String loginUrl;
	/** 自定义所有消息头 */
	private HashMap<String, String> headers;
	/** httpClient */
	private HttpClient httpClient;

	/** 截取随机数的前半部分 */
	private String randomCodeOpen;
	/** 截取随机数的后半部分 */
	private String randomCodeClose;

	/**
	 * 获取随机数
	 * 
	 * @return
	 */
	public String getRandomCode() {
		LOG.debug("获取随机数。");
		HttpGet get = new HttpGet(this.loginRandomCodeUrl);
		try {
			HttpResponse response = httpClient.execute(get);
			HttpEntity entity = response.getEntity();
			String body = IOUtils.toString(entity.getContent());
			LOG.debug("body : {}.", body);
			return StringUtils.substringBetween(body, randomCodeOpen, randomCodeClose);
		} catch (Exception e) {
			LOG.error("Exception: {}", e);
		}
		return null;
	}

	/**
	 * @return the loginRandomCodeUrl 获取随机数地址
	 */
	public String getLoginRandomCodeUrl() {
		return loginRandomCodeUrl;
	}

	/**
	 * @param loginRandomCodeUrl
	 *            the loginRandomCodeUrl to set 获取随机数地址
	 */
	public void setLoginRandomCodeUrl(String loginRandomCodeUrl) {
		this.loginRandomCodeUrl = loginRandomCodeUrl;
	}

	/**
	 * @return the loginCodeUrl 验证码地址
	 */
	public String getLoginCodeUrl() {
		return loginCodeUrl;
	}

	/**
	 * @param loginCodeUrl
	 *            the loginCodeUrl to set 验证码地址
	 */
	public void setLoginCodeUrl(String loginCodeUrl) {
		this.loginCodeUrl = loginCodeUrl;
	}

	/**
	 * @return the loginUrl 登录地址
	 */
	public String getLoginUrl() {
		return loginUrl;
	}

	/**
	 * @param loginUrl
	 *            the loginUrl to set 登录地址
	 */
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	/**
	 * @return the headers 自定义所有消息头
	 */
	public HashMap<String, String> getHeaders() {
		return headers;
	}

	/**
	 * @param headers
	 *            the headers to set 自定义所有消息头
	 */
	public void setHeaders(HashMap<String, String> headers) {
		this.headers = headers;
	}

	/**
	 * @return the httpClient httpClient
	 */
	public HttpClient getHttpClient() {
		return httpClient;
	}

	/**
	 * @param httpClient
	 *            the httpClient to set httpClient
	 */
	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	/**
	 * @return the randomCodeOpen
	 */
	public String getRandomCodeOpen() {
		return randomCodeOpen;
	}

	/**
	 * @param randomCodeOpen
	 *            the randomCodeOpen to set
	 */
	public void setRandomCodeOpen(String randomCodeOpen) {
		this.randomCodeOpen = randomCodeOpen;
	}

	/**
	 * @return the randomCodeClose
	 */
	public String getRandomCodeClose() {
		return randomCodeClose;
	}

	/**
	 * @param randomCodeClose
	 *            the randomCodeClose to set
	 */
	public void setRandomCodeClose(String randomCodeClose) {
		this.randomCodeClose = randomCodeClose;
	}

}
