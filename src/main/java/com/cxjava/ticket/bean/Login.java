package com.cxjava.ticket.bean;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cxjava.ticket.ocr.OCR;

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
	/** 登录表单提交地址 */
	private String loginFormUrl;
	/** 登录首页地址 */
	private String loginMainPageUrl;
	/** httpClient */
	private HttpClient httpClient;
	/** 截取随机数的前半部分 */
	private String randomCodeOpen;
	/** 截取随机数的后半部分 */
	private String randomCodeClose;
	/** 引用地址 */
	private String referer;
	/** 自定义所有消息头 */
	private HashMap<String, String> headers;
	/** 静态提交参数 */
	private HashMap<String, String> staticParameters;
	/** 动态提交参数 */
	private HashMap<String, String> dynamicParameters;
	/** 用户名 */
	private String username;
	/** 密码 */
	private String password;

	/**
	 * 真正登录请求
	 * @return
	 */
	public String login() {
		HttpPost post = new HttpPost(this.loginFormUrl);
		String result = "";
		try {
			addHeader(post);
			post.setEntity(this.addParameters());
			HttpResponse response = this.getHttpClient().execute(post);
			HttpEntity entity = response.getEntity();
			String body = IOUtils.toString(
					new GZIPInputStream(entity.getContent()), "UTF-8");
			LOG.debug("body : {}.", body);
			//消息判断
			if (body.contains("密码修改")) {

			} else if (body.contains("")) {
			} else if (body.contains("")) {
			} else if (body.contains("")) {
			} else if (body.contains("")) {

			}
			
		} catch (Exception e) {
			LOG.error("Exception: {}", e);
		} finally {
			post.releaseConnection();
		}
		return result;
	}

	/**
	 * 获取任意网页内容
	 */
	public String doHttpGet(String url) {
		HttpGet get = new HttpGet(url);
		String body = null;
		try {
			addHeader(get);
			HttpResponse response = this.getHttpClient().execute(get);
			HttpEntity entity = response.getEntity();
			body = IOUtils.toString(new GZIPInputStream(entity.getContent()),
					"UTF-8");
			LOG.debug("{} : {}.", url, body);
		} catch (Exception e) {
			LOG.error("Exception: {}", e);
		} finally {
			get.releaseConnection();
		}
		return body;
	}

	/**
	 * 访问下主页,获取cookie信息
	 */
	public void getMainPage() {
		HttpGet get = new HttpGet(this.loginMainPageUrl);
		try {
			addHeader(get);
			HttpResponse response = this.getHttpClient().execute(get);
			HttpEntity entity = response.getEntity();
			String body = IOUtils.toString(
					new GZIPInputStream(entity.getContent()), "UTF-8");
			LOG.debug("body : {}.", body);
		} catch (Exception e) {
			LOG.error("Exception: {}", e);
		} finally {
			get.releaseConnection();
		}
	}

	/**
	 * 获取随机数
	 * 
	 * @return 随机数
	 */
	public String getRandomCode() {
		LOG.debug("获取随机数。");
		HttpPost post = new HttpPost(this.loginRandomCodeUrl);
		try {
			addHeader(post);
			HttpResponse response = this.getHttpClient().execute(post);
			HttpEntity entity = response.getEntity();
			String body = IOUtils.toString(entity.getContent(), "UTF-8");
			LOG.debug("body : {}.", body);
			return StringUtils.substringBetween(body, randomCodeOpen,
					randomCodeClose);
		} catch (Exception e) {
			LOG.error("Exception: {}", e);
		} finally {
			post.releaseConnection();
		}
		return null;
	}

	/**
	 * 获取验证码
	 * 
	 * @return 验证码
	 */
	public String getCaptcha() {
		HttpGet get = new HttpGet(this.loginCodeUrl);
		String captcha = "";
		String savePath = "F:\\Downloads\\12306\\";
		try {
			addHeader(get);
			HttpResponse response = this.getHttpClient().execute(get);
			HttpEntity entity = response.getEntity();
			InputStream inputStream = entity.getContent();
			BufferedImage bufferedImage = ImageIO.read(inputStream);
			// 识别验证码
			captcha = OCR.imageToString(bufferedImage);
			LOG.debug("captcha : {}.", captcha);

			// test
			File file = new File(savePath + captcha + ".jpg");
			ImageIO.write(bufferedImage, "jpg", file);
			// test

		} catch (Exception e) {
			LOG.error("Exception: {}", e);
		} finally {
			get.releaseConnection();
		}
		return captcha;
	}

	/**
	 * 添加消息头
	 * 
	 * @param request
	 */
	private void addHeader(HttpRequestBase request) {
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			request.addHeader(entry.getKey(), entry.getValue());
		}
		// 单独添加
		request.addHeader("Referer", this.referer);
	}

	/**
	 * 添加登录信息
	 * 
	 * @return
	 */
	private HttpEntity addParameters() {
		try {
			Map<String, String> dynamic = new HashMap<String, String>();
			dynamic.put("username", this.getUsername());
			dynamic.put("password", this.getPassword());
			dynamic.put("loginRand", this.getRandomCode());
			dynamic.put("captcha", this.getCaptcha());
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			// 组装静态参数
			for (Map.Entry<String, String> entry : this.staticParameters
					.entrySet()) {
				parameters.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
			// 组装动态参数
			for (Map.Entry<String, String> entry : this.dynamicParameters
					.entrySet()) {
				// 参数名称可以动态修改
				parameters.add(new BasicNameValuePair(entry.getKey(), dynamic
						.get(entry.getValue())));
			}

			return new UrlEncodedFormEntity(parameters,
					HTTP.DEF_PROTOCOL_CHARSET.name());
		} catch (Exception e) {
			LOG.error("Exception: {}", e);
		}
		return null;
	}

	/**
	 * @return the httpClient httpClient
	 */
	public HttpClient getHttpClient() {
		HttpHost proxy = new HttpHost("127.0.0.1", 8888,
				HttpHost.DEFAULT_SCHEME_NAME);
		this.httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
				proxy);
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

	/**
	 * @return the referer
	 */
	public String getReferer() {
		return referer;
	}

	/**
	 * @param referer
	 *            the referer to set
	 */
	public void setReferer(String referer) {
		this.referer = referer;
	}

	/**
	 * @return the staticParameters
	 */
	public HashMap<String, String> getStaticParameters() {
		return staticParameters;
	}

	/**
	 * @param staticParameters
	 *            the staticParameters to set
	 */
	public void setStaticParameters(HashMap<String, String> staticParameters) {
		this.staticParameters = staticParameters;
	}

	/**
	 * @return the dynamicParameters
	 */
	public HashMap<String, String> getDynamicParameters() {
		return dynamicParameters;
	}

	/**
	 * @param dynamicParameters
	 *            the dynamicParameters to set
	 */
	public void setDynamicParameters(HashMap<String, String> dynamicParameters) {
		this.dynamicParameters = dynamicParameters;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the loginFormUrl
	 */
	public String getLoginFormUrl() {
		return loginFormUrl;
	}

	/**
	 * @param loginFormUrl
	 *            the loginFormUrl to set
	 */
	public void setLoginFormUrl(String loginFormUrl) {
		this.loginFormUrl = loginFormUrl;
	}

	/**
	 * @return the loginMainPageUrl
	 */
	public String getLoginMainPageUrl() {
		return loginMainPageUrl;
	}

	/**
	 * @param loginMainPageUrl
	 *            the loginMainPageUrl to set
	 */
	public void setLoginMainPageUrl(String loginMainPageUrl) {
		this.loginMainPageUrl = loginMainPageUrl;
	}

}
