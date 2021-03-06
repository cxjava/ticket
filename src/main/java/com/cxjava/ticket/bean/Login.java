package com.cxjava.ticket.bean;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.GZIPInputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
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
	/** 登录信息返回结果截取的开始部分 */
	private String loginInfoOpen;
	/** 登录信息返回结果截取的后半部分 */
	private String loginInfoEnd;
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
	/** 是否开启代理 */
	private String isProxy;
	/** 代理ip */
	private String proxyIp;
	/** 代理端口 */
	private Integer proxyPort;

	/**
	 * 真正登录请求
	 * 
	 * @return
	 */
	public boolean login() {
		// 到主页溜达一圈
		getMainPage();
		HttpPost post = new HttpPost(this.loginFormUrl);
		String result = "";
		HttpResponse response = null;
		try {
			addHeader(post);
			post.setEntity(this.addParameters());
			response = this.getHttpClient().execute(post);
			HttpEntity entity = response.getEntity();
			String body = IOUtils.toString(getInputStream(entity), "UTF-8");
			LOG.debug("login body : {}.", body);
			// 消息判断
			result = StringUtils.substringBetween(body, loginInfoOpen, loginInfoEnd);
			if (body.contains("密码修改")) {
				return true;
			} else {
				LOG.info("登录失败消息 : {}", result);
				return false;
			}
		} catch (Exception e) {
			LOG.error("Exception: {}", e);
		} finally {
			HttpClientUtils.closeQuietly(response);
			post.releaseConnection();
		}
		return false;
	}

	/**
	 * 获取任意网页内容
	 */
	public String doHttpGet(String url) {
		HttpGet get = new HttpGet(url);
		String body = "";
		HttpResponse response = null;
		try {
			addHeader(get);
			response = this.getHttpClient().execute(get);
			HttpEntity entity = response.getEntity();
			body = IOUtils.toString(getInputStream(entity), "UTF-8");
			LOG.debug("doHttpGet {} : {}.", url, body);
		} catch (Exception e) {
			LOG.error("Exception: {}", e);
		} finally {
			HttpClientUtils.closeQuietly(response);
			get.releaseConnection();
		}
		return body;
	}

	/**
	 * cookie
	 * 
	 * @param cookieValue
	 */
	public void setCookieBIGipServerotsweb(String cookieValue) {
		BasicClientCookie temp = new BasicClientCookie("BIGipServerotsweb", cookieValue);
		temp.setDomain("dynamic.12306.cn");
		temp.setPath("/");
		((DefaultHttpClient) this.getHttpClient()).getCookieStore().addCookie(temp);
	}

	/**
	 * set cookie
	 * 
	 * @param cookieValue
	 */
	public void setCookieJSESSIONID(String cookieValue) {
		BasicClientCookie temp = new BasicClientCookie("JSESSIONID", cookieValue);
		temp.setDomain("dynamic.12306.cn");
		temp.setPath("/otsweb");
		((DefaultHttpClient) this.getHttpClient()).getCookieStore().addCookie(temp);
	}

	/**
	 * 访问下主页,获取cookie信息
	 */
	public void getMainPage() {
		HttpGet get = new HttpGet(this.loginMainPageUrl);
		HttpResponse response = null;
		try {
			addHeader(get);
			response = this.getHttpClient().execute(get);
			HttpEntity entity = response.getEntity();
			String body = IOUtils.toString(getInputStream(entity), "UTF-8");
			LOG.debug("getMainPage body : {}.", body);
		} catch (Exception e) {
			LOG.error("Exception: {}", e);
		} finally {
			HttpClientUtils.closeQuietly(response);
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
		HttpResponse response = null;
		try {
			addHeader(post);
			response = this.getHttpClient().execute(post);
			HttpEntity entity = response.getEntity();
			String body = IOUtils.toString(getInputStream(entity), "UTF-8");
			LOG.debug("getRandomCode body : {}.", body);
			body = StringUtils.substringBetween(body, randomCodeOpen, randomCodeClose);
			LOG.info("随机数为 : {}", body);
			return body;
		} catch (Exception e) {
			LOG.error("Exception: {}", e);
		} finally {
			HttpClientUtils.closeQuietly(response);
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
		HttpGet get = new HttpGet(this.loginCodeUrl + "&" + Math.random());
		String captcha = "";
		HttpResponse response = null;
		try {
			addHeader(get);
			response = this.getHttpClient().execute(get);
			HttpEntity entity = response.getEntity();
			InputStream inputStream = entity.getContent();
			BufferedImage bufferedImage = ImageIO.read(inputStream);
			// 识别验证码
			captcha = OCR.imageToString(bufferedImage);
			LOG.info("验证码 : {}.", captcha);

		} catch (Exception e) {
			LOG.error("Exception: {}", e);
		} finally {
			HttpClientUtils.closeQuietly(response);
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
			// treeMap可以根据key排序，铁道部TMD非要顺序正确
			Map<String, String> tree = new TreeMap<String, String>();
			// 放入静态参数
			tree.putAll(this.staticParameters);
			Map<String, String> dynamic = new HashMap<String, String>();
			dynamic.put("username", this.getUsername());
			dynamic.put("password", this.getPassword());
			// dynamic.put("captcha", this.getCaptcha());
			dynamic.put("captcha", "ABCD");
			dynamic.put("loginRand", this.getRandomCode());
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			// 组装动态参数
			for (Map.Entry<String, String> entry : this.dynamicParameters.entrySet()) {
				// 参数名称可以动态修改
				tree.put(entry.getKey(), dynamic.get(entry.getValue()));
			}
			// 组装排序后的参数
			for (Map.Entry<String, String> entry : tree.entrySet()) {
				// 去掉序号和#号
				parameters.add(new BasicNameValuePair(entry.getKey().replaceFirst("\\d{1,3}#", ""), entry.getValue()));
			}
			// return new UrlEncodedFormEntity(parameters, HTTP.DEF_PROTOCOL_CHARSET.name());
			return new UrlEncodedFormEntity(parameters, Consts.UTF_8.name());
		} catch (Exception e) {
			LOG.error("Exception: {}", e);
		}
		return null;
	}

	private static InputStream getInputStream(HttpEntity entity) throws IOException {
		Header encoding = entity.getContentEncoding();
		if (encoding != null) {
			if (encoding.getValue().equals("gzip") || encoding.getValue().equals("zip")
					|| encoding.getValue().equals("application/x-gzip-compressed")) {
				return new GZIPInputStream(entity.getContent());
			}
		}
		return entity.getContent();
	}

	/**
	 * @return the httpClient httpClient
	 */
	public HttpClient getHttpClient() {
		if ("true".equals(this.getIsProxy())) {
			HttpHost proxy = new HttpHost(this.getProxyIp(), this.getProxyPort(), HttpHost.DEFAULT_SCHEME_NAME);
			this.httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		}
		httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.NETSCAPE);
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
	 * @return the loginFormUrl 登录表单提交地址
	 */
	public String getLoginFormUrl() {
		return loginFormUrl;
	}

	/**
	 * @param loginFormUrl
	 *            the loginFormUrl to set 登录表单提交地址
	 */
	public void setLoginFormUrl(String loginFormUrl) {
		this.loginFormUrl = loginFormUrl;
	}

	/**
	 * @return the loginMainPageUrl 登录首页地址
	 */
	public String getLoginMainPageUrl() {
		return loginMainPageUrl;
	}

	/**
	 * @param loginMainPageUrl
	 *            the loginMainPageUrl to set 登录首页地址
	 */
	public void setLoginMainPageUrl(String loginMainPageUrl) {
		this.loginMainPageUrl = loginMainPageUrl;
	}

	/**
	 * @return the randomCodeOpen 截取随机数的前半部分
	 */
	public String getRandomCodeOpen() {
		return randomCodeOpen;
	}

	/**
	 * @param randomCodeOpen
	 *            the randomCodeOpen to set 截取随机数的前半部分
	 */
	public void setRandomCodeOpen(String randomCodeOpen) {
		this.randomCodeOpen = randomCodeOpen;
	}

	/**
	 * @return the randomCodeClose 截取随机数的后半部分
	 */
	public String getRandomCodeClose() {
		return randomCodeClose;
	}

	/**
	 * @param randomCodeClose
	 *            the randomCodeClose to set 截取随机数的后半部分
	 */
	public void setRandomCodeClose(String randomCodeClose) {
		this.randomCodeClose = randomCodeClose;
	}

	/**
	 * @return the referer 引用地址
	 */
	public String getReferer() {
		return referer;
	}

	/**
	 * @param referer
	 *            the referer to set 引用地址
	 */
	public void setReferer(String referer) {
		this.referer = referer;
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
	 * @return the staticParameters 静态提交参数
	 */
	public HashMap<String, String> getStaticParameters() {
		return staticParameters;
	}

	/**
	 * @param staticParameters
	 *            the staticParameters to set 静态提交参数
	 */
	public void setStaticParameters(HashMap<String, String> staticParameters) {
		this.staticParameters = staticParameters;
	}

	/**
	 * @return the dynamicParameters 动态提交参数
	 */
	public HashMap<String, String> getDynamicParameters() {
		return dynamicParameters;
	}

	/**
	 * @param dynamicParameters
	 *            the dynamicParameters to set 动态提交参数
	 */
	public void setDynamicParameters(HashMap<String, String> dynamicParameters) {
		this.dynamicParameters = dynamicParameters;
	}

	/**
	 * @return the username 用户名
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set 用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password 密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set 密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the isProxy 是否开启代理
	 */
	public String getIsProxy() {
		return isProxy;
	}

	/**
	 * @param isProxy
	 *            the isProxy to set 是否开启代理
	 */
	public void setIsProxy(String isProxy) {
		this.isProxy = isProxy;
	}

	/**
	 * @return the proxyIp 代理ip
	 */
	public String getProxyIp() {
		return proxyIp;
	}

	/**
	 * @param proxyIp
	 *            the proxyIp to set 代理ip
	 */
	public void setProxyIp(String proxyIp) {
		this.proxyIp = proxyIp;
	}

	/**
	 * @return the proxyPort 代理端口
	 */
	public Integer getProxyPort() {
		return proxyPort;
	}

	/**
	 * @param proxyPort
	 *            the proxyPort to set 代理端口
	 */
	public void setProxyPort(Integer proxyPort) {
		this.proxyPort = proxyPort;
	}

	/**
	 * @return the loginInfoOpen 登录信息返回结果截取的开始部分
	 */
	public String getLoginInfoOpen() {
		return loginInfoOpen;
	}

	/**
	 * @param loginInfoOpen
	 *            the loginInfoOpen to set 登录信息返回结果截取的开始部分
	 */
	public void setLoginInfoOpen(String loginInfoOpen) {
		this.loginInfoOpen = loginInfoOpen;
	}

	/**
	 * @return the loginInfoEnd 登录信息返回结果截取的后半部分
	 */
	public String getLoginInfoEnd() {
		return loginInfoEnd;
	}

	/**
	 * @param loginInfoEnd
	 *            the loginInfoEnd to set 登录信息返回结果截取的后半部分
	 */
	public void setLoginInfoEnd(String loginInfoEnd) {
		this.loginInfoEnd = loginInfoEnd;
	}

}
