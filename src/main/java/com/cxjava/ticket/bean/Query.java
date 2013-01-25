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
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cxjava.ticket.ocr.OCR;

/**
 * 查询车票
 * 
 * @author Maty Chen
 * @date 2013-1-24下午11:49:56
 */
public class Query {
	private static final Logger LOG = LoggerFactory.getLogger(Query.class);
	/** 自定义所有消息头 */
	private HashMap<String, String> headers;
	/** httpClient */
	private HttpClient httpClient;
	/** 静态提交参数 */
	private HashMap<String, String> queryStaticParameters;
	/** 动态提交参数 */
	private HashMap<String, String> queryDynamicParameters;
	/** 是否开启代理 */
	private String isProxy;
	/** 代理ip */
	private String proxyIp;
	/** 代理端口 */
	private Integer proxyPort;
	/** 查询车票时的引用地址 */
	private String queryReferer;
	/** 提交订单时的引用地址 */
	private String confirmReferer;
	/** 获取车站代码地址 */
	private String stationNameUrl;
	/** 截取标志 */
	private String stationOpen;
	/** 截取标志 */
	private String stationEnd;
	/** 查询列表页面地址 */
	private String queryTicketPageUrl;
	/** 查询表单提交地址 */
	private String queryFormUrl;
	/** 选定某辆车的地址 */
	private String submitOrderRequestUrl;
	/** 选定某辆车的地址 */
	private String orderCodeUrl;
	/** 提交订单休息时间 */
	private Integer sleepOrder;
	/** 提交验证码时间 */
	private Integer sleepCode;
	/** 出发地 */
	private String fromStation;
	/** 出发时间 */
	private String goTime;
	/** 目的地 */
	private String toStation;
	/** 出发日期 */
	private String trainDate;
	/** 车站信息 */
	private String citys;

	/**
	 * 查询车票信息
	 * 
	 * @return
	 */
	public String querySingleAction() {
		HttpGet get = new HttpGet(this.queryFormUrl + this.addQueryParameters());
		String result = "";
		HttpResponse response = null;
		try {
			addHeader(get, this.queryReferer);
			response = this.getHttpClient().execute(get);
			HttpEntity entity = response.getEntity();
			String body = IOUtils.toString(new GZIPInputStream(entity.getContent()), "UTF-8").replaceAll("&nbsp;", "");
			LOG.debug("querySingleAction body : {}.", body);
			Document doc = Jsoup.parse(body);
			LOG.debug("doc.text() : {}.", doc.text());
			
			body = StringUtils.substringBetween(body, "'onStopOut()'>"+"L1034", "订</a>");
			LOG.debug("body : {}.", body);
			
		} catch (Exception e) {
			LOG.error("Exception: {}", e);
		} finally {
			HttpClientUtils.closeQuietly(response);
			get.releaseConnection();
		}
		return result;
	}

	/**
	 * 获取任意网页内容
	 */
	public String doHttpGet(String url) {
		HttpGet get = new HttpGet(url);
		String body = "";
		HttpResponse response = null;
		try {
			addHeader(get, this.queryReferer);
			response = this.getHttpClient().execute(get);
			HttpEntity entity = response.getEntity();
			body = IOUtils.toString(new GZIPInputStream(entity.getContent()), "UTF-8");
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
	 * 访问下查询的主页
	 */
	public void getQueryPage() {
		HttpGet get = new HttpGet(this.queryTicketPageUrl);
		HttpResponse response = null;
		try {
			addHeader(get, this.queryReferer);
			response = this.getHttpClient().execute(get);
			HttpEntity entity = response.getEntity();
			String body = IOUtils.toString(new GZIPInputStream(entity.getContent()), "UTF-8");
			LOG.debug("getQueryPage body : {}.", body);
		} catch (Exception e) {
			LOG.error("Exception: {}", e);
		} finally {
			HttpClientUtils.closeQuietly(response);
			get.releaseConnection();
		}
	}

	/**
	 * 获取车站代码
	 */
	public Map<String, String> getStationName() {
		Map<String, String> cityMap = new HashMap<String, String>();
		HttpGet get = new HttpGet(this.stationNameUrl);
		HttpResponse response = null;
		try {
			addHeader(get, this.queryReferer);
			response = this.getHttpClient().execute(get);
			HttpEntity entity = response.getEntity();
			String body = IOUtils.toString(new GZIPInputStream(entity.getContent()), "UTF-8");
			LOG.debug("getStationName body : {}.", body);
			body = StringUtils.substringBetween(body, this.stationOpen, this.stationEnd);
			for (String temp : body.split("@")) {
				if (StringUtils.isNotBlank(temp)) {
					String[] name = temp.split("\\|");
					cityMap.put(name[1], name[2]);
				}
			}
			LOG.debug("getStationName cityMap : {}.", cityMap);
		} catch (Exception e) {
			LOG.error("Exception: {}", e);
		} finally {
			HttpClientUtils.closeQuietly(response);
			get.releaseConnection();
		}
		return cityMap;
	}
	/**
	 * 获取车站代码，静态的
	 */
	public Map<String, String> getStationNameStatic() {
		Map<String, String> cityMap = new HashMap<String, String>();
		for (String temp : citys.split("@")) {
			if (StringUtils.isNotBlank(temp)) {
				String[] name = temp.split("\\|");
				cityMap.put(name[1], name[2]);
			}
		}
		LOG.debug("getStationName cityMap : {}.", cityMap);
		return cityMap;
	}

	/**
	 * 获取验证码
	 * 
	 * @return 验证码
	 */
	public String getCaptcha() {
		HttpGet get = new HttpGet(this.orderCodeUrl);
		String captcha = "";

		// String savePath = "F:\\Downloads\\12306\\";
		String savePath = "D:\\05_Document\\Downloads\\12306\\";

		HttpResponse response = null;
		try {
			addHeader(get, this.confirmReferer);
			response = this.getHttpClient().execute(get);
			HttpEntity entity = response.getEntity();
			InputStream inputStream = entity.getContent();
			BufferedImage bufferedImage = ImageIO.read(inputStream);
			// 识别验证码
			captcha = OCR.imageToString(bufferedImage);
			LOG.info("验证码 : {}.", captcha);

			// test
			File file = new File(savePath + captcha + ".jpg");
			ImageIO.write(bufferedImage, "jpg", file);
			// test

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
	private void addHeader(HttpRequestBase request, String referer) {
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			request.addHeader(entry.getKey(), entry.getValue());
		}
		// 单独添加
		request.addHeader("Referer", referer);
	}

	/**
	 * 添加登录信息
	 * 
	 * @return
	 */
	private String addQueryParameters() {
		try {
			// 获取最新车站信息
//			Map<String, String> cityMap = getStationName();
			Map<String, String> cityMap = getStationNameStatic();
			LOG.debug("(fromStation) : {}.", fromStation);
			LOG.debug("(toStation) : {}.", toStation);
			LOG.debug("cityMap.get(fromStation) : {}.", cityMap.get(fromStation));
			LOG.debug("cityMap.get(toStation) : {}.", cityMap.get(toStation));
			Map<String, String> dynamic = new HashMap<String, String>();
			dynamic.put("fromStation", cityMap.get(fromStation));
			if (StringUtils.isNotBlank(this.goTime)) {
				dynamic.put("goTime", this.goTime);
			} else {
				dynamic.put("goTime", "00:00--24:00");
			}
			dynamic.put("toStation", cityMap.get(toStation));
			dynamic.put("trainDate", this.trainDate);
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			// 组装静态参数
			for (Map.Entry<String, String> entry : this.queryStaticParameters.entrySet()) {
				parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			// 组装动态参数
			for (Map.Entry<String, String> entry : this.queryDynamicParameters.entrySet()) {
				// 参数名称可以动态修改
				parameters.add(new BasicNameValuePair(entry.getKey(), dynamic.get(entry.getValue())));
			}
			return URLEncodedUtils.format(parameters, Consts.UTF_8.name());
		} catch (Exception e) {
			LOG.error("Exception: {}", e);
		}
		return null;
	}

	/**
	 * @return the httpClient httpClient
	 */
	public HttpClient getHttpClient() {
		if ("true".equals(this.getIsProxy())) {
			HttpHost proxy = new HttpHost(this.getProxyIp(), this.getProxyPort(), HttpHost.DEFAULT_SCHEME_NAME);
			this.httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		}
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
	 * @return the queryStaticParameters 静态提交参数
	 */
	public HashMap<String, String> getQueryStaticParameters() {
		return queryStaticParameters;
	}

	/**
	 * @param queryStaticParameters
	 *            the queryStaticParameters to set 静态提交参数
	 */
	public void setQueryStaticParameters(HashMap<String, String> queryStaticParameters) {
		this.queryStaticParameters = queryStaticParameters;
	}

	/**
	 * @return the queryDynamicParameters 动态提交参数
	 */
	public HashMap<String, String> getQueryDynamicParameters() {
		return queryDynamicParameters;
	}

	/**
	 * @param queryDynamicParameters
	 *            the queryDynamicParameters to set 动态提交参数
	 */
	public void setQueryDynamicParameters(HashMap<String, String> queryDynamicParameters) {
		this.queryDynamicParameters = queryDynamicParameters;
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
	 * @return the queryReferer 查询车票时的引用地址
	 */
	public String getQueryReferer() {
		return queryReferer;
	}

	/**
	 * @param queryReferer
	 *            the queryReferer to set 查询车票时的引用地址
	 */
	public void setQueryReferer(String queryReferer) {
		this.queryReferer = queryReferer;
	}

	/**
	 * @return the confirmReferer 提交订单时的引用地址
	 */
	public String getConfirmReferer() {
		return confirmReferer;
	}

	/**
	 * @param confirmReferer
	 *            the confirmReferer to set 提交订单时的引用地址
	 */
	public void setConfirmReferer(String confirmReferer) {
		this.confirmReferer = confirmReferer;
	}

	/**
	 * @return the stationNameUrl 获取车站代码地址
	 */
	public String getStationNameUrl() {
		return stationNameUrl;
	}

	/**
	 * @param stationNameUrl
	 *            the stationNameUrl to set 获取车站代码地址
	 */
	public void setStationNameUrl(String stationNameUrl) {
		this.stationNameUrl = stationNameUrl;
	}

	/**
	 * @return the stationOpen 截取标志
	 */
	public String getStationOpen() {
		return stationOpen;
	}

	/**
	 * @param stationOpen
	 *            the stationOpen to set 截取标志
	 */
	public void setStationOpen(String stationOpen) {
		this.stationOpen = stationOpen;
	}

	/**
	 * @return the stationEnd 截取标志
	 */
	public String getStationEnd() {
		return stationEnd;
	}

	/**
	 * @param stationEnd
	 *            the stationEnd to set 截取标志
	 */
	public void setStationEnd(String stationEnd) {
		this.stationEnd = stationEnd;
	}

	/**
	 * @return the queryTicketPageUrl 查询列表页面地址
	 */
	public String getQueryTicketPageUrl() {
		return queryTicketPageUrl;
	}

	/**
	 * @param queryTicketPageUrl
	 *            the queryTicketPageUrl to set 查询列表页面地址
	 */
	public void setQueryTicketPageUrl(String queryTicketPageUrl) {
		this.queryTicketPageUrl = queryTicketPageUrl;
	}

	/**
	 * @return the queryFormUrl 查询表单提交地址
	 */
	public String getQueryFormUrl() {
		return queryFormUrl;
	}

	/**
	 * @param queryFormUrl
	 *            the queryFormUrl to set 查询表单提交地址
	 */
	public void setQueryFormUrl(String queryFormUrl) {
		this.queryFormUrl = queryFormUrl;
	}

	/**
	 * @return the submitOrderRequestUrl 选定某辆车的地址
	 */
	public String getSubmitOrderRequestUrl() {
		return submitOrderRequestUrl;
	}

	/**
	 * @param submitOrderRequestUrl
	 *            the submitOrderRequestUrl to set 选定某辆车的地址
	 */
	public void setSubmitOrderRequestUrl(String submitOrderRequestUrl) {
		this.submitOrderRequestUrl = submitOrderRequestUrl;
	}

	/**
	 * @return the orderCodeUrl 选定某辆车的地址
	 */
	public String getOrderCodeUrl() {
		return orderCodeUrl;
	}

	/**
	 * @param orderCodeUrl
	 *            the orderCodeUrl to set 选定某辆车的地址
	 */
	public void setOrderCodeUrl(String orderCodeUrl) {
		this.orderCodeUrl = orderCodeUrl;
	}

	/**
	 * @return the sleepOrder 提交订单休息时间
	 */
	public Integer getSleepOrder() {
		return sleepOrder;
	}

	/**
	 * @param sleepOrder
	 *            the sleepOrder to set 提交订单休息时间
	 */
	public void setSleepOrder(Integer sleepOrder) {
		this.sleepOrder = sleepOrder;
	}

	/**
	 * @return the sleepCode 提交验证码时间
	 */
	public Integer getSleepCode() {
		return sleepCode;
	}

	/**
	 * @param sleepCode
	 *            the sleepCode to set 提交验证码时间
	 */
	public void setSleepCode(Integer sleepCode) {
		this.sleepCode = sleepCode;
	}

	/**
	 * @return the fromStation 出发地
	 */
	public String getFromStation() {
		return fromStation;
	}

	/**
	 * @param fromStation
	 *            the fromStation to set 出发地
	 */
	public void setFromStation(String fromStation) {
		this.fromStation = fromStation;
	}

	/**
	 * @return the goTime 出发时间
	 */
	public String getGoTime() {
		return goTime;
	}

	/**
	 * @param goTime
	 *            the goTime to set 出发时间
	 */
	public void setGoTime(String goTime) {
		this.goTime = goTime;
	}

	/**
	 * @return the toStation 目的地
	 */
	public String getToStation() {
		return toStation;
	}

	/**
	 * @param toStation
	 *            the toStation to set 目的地
	 */
	public void setToStation(String toStation) {
		this.toStation = toStation;
	}

	/**
	 * @return the trainDate 出发日期
	 */
	public String getTrainDate() {
		return trainDate;
	}

	/**
	 * @param trainDate
	 *            the trainDate to set 出发日期
	 */
	public void setTrainDate(String trainDate) {
		this.trainDate = trainDate;
	}

	/**
	 * @return the citys 车站信息
	 */
	public String getCitys() {
		return citys;
	}

	/**
	 * @param citys
	 *            the citys to set 车站信息
	 */
	public void setCitys(String citys) {
		this.citys = citys;
	}

}
