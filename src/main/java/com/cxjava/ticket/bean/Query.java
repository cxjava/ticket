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
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
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
	/** 有票的意思 */
	private static final String HAVE = "有";
	/** 自定义所有消息头 */
	private HashMap<String, String> headers;
	/** httpClient */
	private HttpClient httpClient;
	/** 查询静态提交参数 */
	private HashMap<String, String> queryStaticParameters;
	/** 查询动态提交参数 */
	private HashMap<String, String> queryDynamicParameters;
	/** 表单提交静态参数 */
	private HashMap<String, String> confirmStaticParameters;
	/** 表单提交动态参数之来源于页面 */
	private HashMap<String, String> confirmMapDynamicParameters;
	/** 表单提交动态参数之来源于查询结果 */
	private HashMap<String, String> confirmArrayDynamicParameters;
	/** 检查订单时候的参数 */
	private HashMap<String, String> checkOrderStaticParameters;
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
	/** 车次代码 */
	private String trainCode;
	/** 席别类型 */
	private String seatType;
	/** 信息返回结果截取的开始部分 */
	private String loginInfoOpen;
	/** 信息返回结果截取的后半部分 */
	private String loginInfoEnd;
	/** 加载联系人URL */
	private String passengerJsonUrl;
	/** 检查订单 */
	private String checkOrderInfoUrl;

	/**
	 * 提交车票订单
	 * 
	 * @return
	 */
	public boolean submutOrder(String info) {
		HttpPost post = new HttpPost(this.submitOrderRequestUrl);
		String result = "";
		HttpResponse response = null;
		try {
			addHeader(post, this.queryReferer);
			post.setEntity(this.addConfirmParameters(info));
			((DefaultHttpClient) this.getHttpClient()).setRedirectStrategy(new DefaultRedirectStrategy() {
				public boolean isRedirected(HttpRequest request, HttpResponse response, HttpContext context) {
					boolean isRedirect = false;
					try {
						isRedirect = super.isRedirected(request, response, context);
					} catch (ProtocolException e) {
						LOG.error("ProtocolException:{}", e);
					}
					if (!isRedirect) {
						int responseCode = response.getStatusLine().getStatusCode();
						if (responseCode == 301 || responseCode == 302) {
							return true;
						}
					}
					return isRedirect;
				}
			});
			response = this.getHttpClient().execute(post);
			HttpEntity entity = response.getEntity();
			String body = IOUtils.toString(getInputStream(entity), "UTF-8");
			LOG.debug("submutOrder body : {}.", body);
			result = StringUtils.substringBetween(body, loginInfoOpen, loginInfoEnd);
			// 消息判断
			if (body.contains("常用联系人加载中，请稍候...")) {
				LOG.info("常用联系人加载中，请稍候...");
				Document doc = Jsoup.parse(body);
				String TOKEN = doc.select("input[name=org.apache.struts.taglib.html.TOKEN]").attr("value");
				String leftTicketStr = doc.select("input[name=leftTicketStr]").attr("value");
				LOG.info("TOKEN：{}", TOKEN);
				LOG.info("leftTicketStr：{}", leftTicketStr);
				// TODO:
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
	 * 查询车票信息
	 * 
	 * @return
	 */
	public String querySingleAction() {
		HttpGet get = new HttpGet(this.queryFormUrl + this.addQueryParameters());
		HttpResponse response = null;
		try {
			addHeader(get, this.queryReferer);
			response = this.getHttpClient().execute(get);
			HttpEntity entity = response.getEntity();
			String body = IOUtils.toString(getInputStream(entity), "UTF-8").replaceAll("&nbsp;", "");
			LOG.debug("querySingleAction body : {}.", body);
			Document doc = Jsoup.parse(body);
			String text = doc.text();
			String onclickText = doc.select("a").removeAttr("name").removeAttr("class").removeAttr("style").toString();
			LOG.debug("text() : {}.", text);
			LOG.debug("onclickText() : {}.", onclickText);
			return find(text, onclickText);
			// return new String[]{text, onclickText};
		} catch (Exception e) {
			LOG.error("Exception: {}", e);
		} finally {
			HttpClientUtils.closeQuietly(response);
			get.releaseConnection();
		}
		return null;
	}

	/**
	 * 获取联系人列表
	 */
	public void postPassenger() {
		HttpPost post = new HttpPost(this.passengerJsonUrl);
		HttpResponse response = null;
		try {
			addHeader(post, this.confirmReferer);
			response = this.getHttpClient().execute(post);
			HttpEntity entity = response.getEntity();
			String body = IOUtils.toString(getInputStream(entity), "UTF-8");
			LOG.debug("submutOrder body : {}.", body);
		} catch (Exception e) {
			LOG.error("Exception: {}", e);
		} finally {
			HttpClientUtils.closeQuietly(response);
			post.releaseConnection();
		}
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
	 * 访问查询的主页
	 */
	public void getQueryPage() {
		HttpGet get = new HttpGet(this.queryTicketPageUrl);
		HttpResponse response = null;
		try {
			addHeader(get, this.queryReferer);
			response = this.getHttpClient().execute(get);
			HttpEntity entity = response.getEntity();
			String body = IOUtils.toString(getInputStream(entity), "UTF-8");
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
		LOG.info("获取车站代码(在线)");
		Map<String, String> cityMap = new HashMap<String, String>();
		HttpGet get = new HttpGet(this.stationNameUrl);
		HttpResponse response = null;
		try {
			addHeader(get, this.queryReferer);
			response = this.getHttpClient().execute(get);
			HttpEntity entity = response.getEntity();
			String body = IOUtils.toString(getInputStream(entity), "UTF-8");
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
		LOG.info("获取车站代码(静态)");
		Map<String, String> cityMap = new HashMap<String, String>();
		for (String temp : citys.split("@")) {
			if (StringUtils.isNotBlank(temp)) {
				String[] name = temp.split("\\|");
				cityMap.put(name[1], name[2]);
			}
		}
		LOG.debug("getStationNameStatic cityMap : {}.", cityMap);
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
		} catch (Exception e) {
			LOG.error("Exception: {}", e);
		} finally {
			HttpClientUtils.closeQuietly(response);
			get.releaseConnection();
		}
		return captcha;
	}

	/**
	 * 判断是否有票
	 * 
	 * @param bodyText
	 *            0,K1094,成都东 00:35,汉口 20:07,19:32,--,--,--,--,--,--,无,--,有,有,--,预订\n1,L20,成都东....
	 * @param onclick
	 *            <a onclick=
	 *            "javascript:getSelected('T126#16:42#22:49#760000T12602#CDW#WCN#15:31#成都#武昌#01#12#1*****33784*****00001*****05943*****0000#B8D4989A7284E8DD60F8CC0A5115F662310F968C71D2E1BF84BDA222#W1')"
	 *            >预订</a>
	 * 
	 */
	public String find(String bodyText, String onclick) {
		Map<String, String> seats = new TreeMap<String, String>();
		// trainCode=T248|K1094|K530
		String[] trains = StringUtils.split(trainCode, "\\|");
		for (String train : trains) {
			if (bodyText.contains(train)) {
				// infos=,成都东 00:35,汉口 20:07,19:32,--,--,--,--,--,--,无,--,有,有,--,预订
				String infos = StringUtils.substringBetween(bodyText, train, "\\n");
				// 分割为每个数组,把有票的席别类型取出来
				String[] info = StringUtils.split(infos, ",");
				for (int i = 0; i < info.length; i++) {
					// 如果这个席别有票，就取出来，和期望的seatType做比较
					if (HAVE.equals(info[i])) {
						// 用getSelected里面的值做value
						// getSelected('K390#22:09#10:10#760000K39002#CDW#WCN#08:19#成都#武昌#01#16#1*****32544*****00001*****04243*****0095#F2AA3083846AE60533BCE14599AD641B41446B21B49BBEEA4AA18F69#W1')">预订</a>
						seats.put(train + Integer.toString(i), train + StringUtils.substringBetween(onclick, train, "')"));
					}
				}
			}
		}
		LOG.debug("seats : {}.", seats);
		// 和期望的席别做比较
		String[] seatTypes = StringUtils.split(seatType, "\\|");
		for (String seatType : seatTypes) {
			for (String train : trains) {
				if (seats != null && seats.containsKey(train + seatType)) {
					LOG.info("此车次有你需要的票：" + seats.get(train + seatType));
					LOG.debug(seatType + "：{}", seats.get(train + seatType));
					return seatType + "#" + seats.get(train + seatType);
				}
			}
		}
		return null;
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
	 * 添加查询车票信息参数
	 * 
	 * @return
	 */
	private String addQueryParameters() {
		try {
			// 获取最新车站信息
			// Map<String, String> cityMap = getStationName();
			Map<String, String> cityMap = getStationNameStatic();
			// treeMap可以根据key排序，铁道部TMD非要顺序正确
			Map<String, String> tree = new TreeMap<String, String>();
			// 放入静态参数
			tree.putAll(this.queryStaticParameters);
			Map<String, String> dynamic = new HashMap<String, String>();
			dynamic.put("trainDate", this.trainDate);
			if (StringUtils.isNotBlank(this.goTime)) {
				dynamic.put("goTime", this.goTime);
			} else {
				dynamic.put("goTime", "00:00--24:00");
			}
			dynamic.put("fromStation", cityMap.get(fromStation));
			dynamic.put("toStation", cityMap.get(toStation));
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			// 组装动态参数
			for (Map.Entry<String, String> entry : this.queryDynamicParameters.entrySet()) {
				// 参数名称可以动态修改
				tree.put(entry.getKey(), dynamic.get(entry.getValue()));
			}
			// 组装排序后的参数
			for (Map.Entry<String, String> entry : tree.entrySet()) {
				// 去掉序号和#号
				parameters.add(new BasicNameValuePair(entry.getKey().replaceFirst("\\d{1,3}#", ""), entry.getValue()));
			}

			return URLEncodedUtils.format(parameters, Consts.UTF_8.name());
		} catch (Exception e) {
			LOG.error("Exception: {}", e);
		}
		return null;
	}

	/**
	 * 添加提交表单参数
	 * 
	 * @return
	 */
	private HttpEntity addConfirmParameters(String info) {
		try {
			// 获取最新车站信息
			Map<String, String> cityMap = getStationName();
			// Map<String, String> cityMap = getStationNameStatic();
			// treeMap可以根据key排序，铁道部TMD非要顺序正确
			Map<String, String> tree = new TreeMap<String, String>();
			// 放入静态参数
			tree.putAll(this.confirmStaticParameters);

			Map<String, String> dynamic = new HashMap<String, String>();
			if (StringUtils.isNotBlank(this.goTime)) {
				dynamic.put("goTime", this.goTime);
			} else {
				dynamic.put("goTime", "00:00--24:00");
			}
			dynamic.put("trainDate", this.trainDate);
			dynamic.put("fromStationCode", cityMap.get(fromStation));
			dynamic.put("toStationCode", cityMap.get(toStation));
			dynamic.put("fromStation", fromStation);
			dynamic.put("toStation", toStation);
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			// 组装动态参数
			for (Map.Entry<String, String> entry : this.confirmMapDynamicParameters.entrySet()) {
				// 参数名称可以动态修改
				tree.put(entry.getKey(), dynamic.get(entry.getValue()));
			}
			if (StringUtils.isNotBlank(info)) {
				LOG.debug("info:{}", info);
				String[] arrays = StringUtils.split(info, "#");
				for (Map.Entry<String, String> entry : this.confirmArrayDynamicParameters.entrySet()) {
					// 参数名称可以动态修改
					int index = Integer.valueOf(entry.getValue());
					tree.put(entry.getKey(), arrays[index]);
				}
			}
			// 组装排序后的参数
			for (Map.Entry<String, String> entry : tree.entrySet()) {
				// 去掉序号和#号
				parameters.add(new BasicNameValuePair(entry.getKey().replaceFirst("\\d{1,3}#", ""), entry.getValue()));
			}
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

	/**
	 * @return the trainCode
	 */
	public String getTrainCode() {
		return trainCode;
	}

	/**
	 * @param trainCode
	 *            the trainCode to set
	 */
	public void setTrainCode(String trainCode) {
		this.trainCode = trainCode;
	}

	/**
	 * @return the seatType
	 */
	public String getSeatType() {
		return seatType;
	}

	/**
	 * @param seatType
	 *            the seatType to set
	 */
	public void setSeatType(String seatType) {
		this.seatType = seatType;
	}

	/**
	 * @return the confirmStaticParameters
	 */
	public HashMap<String, String> getConfirmStaticParameters() {
		return confirmStaticParameters;
	}

	/**
	 * @param confirmStaticParameters
	 *            the confirmStaticParameters to set
	 */
	public void setConfirmStaticParameters(HashMap<String, String> confirmStaticParameters) {
		this.confirmStaticParameters = confirmStaticParameters;
	}

	/**
	 * @return the confirmMapDynamicParameters
	 */
	public HashMap<String, String> getConfirmMapDynamicParameters() {
		return confirmMapDynamicParameters;
	}

	/**
	 * @param confirmMapDynamicParameters
	 *            the confirmMapDynamicParameters to set
	 */
	public void setConfirmMapDynamicParameters(HashMap<String, String> confirmMapDynamicParameters) {
		this.confirmMapDynamicParameters = confirmMapDynamicParameters;
	}

	/**
	 * @return the confirmArrayDynamicParameters
	 */
	public HashMap<String, String> getConfirmArrayDynamicParameters() {
		return confirmArrayDynamicParameters;
	}

	/**
	 * @param confirmArrayDynamicParameters
	 *            the confirmArrayDynamicParameters to set
	 */
	public void setConfirmArrayDynamicParameters(HashMap<String, String> confirmArrayDynamicParameters) {
		this.confirmArrayDynamicParameters = confirmArrayDynamicParameters;
	}

	/**
	 * @return the loginInfoOpen
	 */
	public String getLoginInfoOpen() {
		return loginInfoOpen;
	}

	/**
	 * @param loginInfoOpen
	 *            the loginInfoOpen to set
	 */
	public void setLoginInfoOpen(String loginInfoOpen) {
		this.loginInfoOpen = loginInfoOpen;
	}

	/**
	 * @return the loginInfoEnd
	 */
	public String getLoginInfoEnd() {
		return loginInfoEnd;
	}

	/**
	 * @param loginInfoEnd
	 *            the loginInfoEnd to set
	 */
	public void setLoginInfoEnd(String loginInfoEnd) {
		this.loginInfoEnd = loginInfoEnd;
	}

	/**
	 * @return the passengerJsonUrl 加载联系人URL
	 */
	public String getPassengerJsonUrl() {
		return passengerJsonUrl;
	}

	/**
	 * @param passengerJsonUrl
	 *            the passengerJsonUrl to set 加载联系人URL
	 */
	public void setPassengerJsonUrl(String passengerJsonUrl) {
		this.passengerJsonUrl = passengerJsonUrl;
	}

	/**
	 * @return the checkOrderInfoUrl
	 检查订单 */
	public String getCheckOrderInfoUrl() {
		return checkOrderInfoUrl;
	}

	/**
	 * @param checkOrderInfoUrl the checkOrderInfoUrl to set
	 检查订单 */
	public void setCheckOrderInfoUrl(String checkOrderInfoUrl) {
		this.checkOrderInfoUrl = checkOrderInfoUrl;
	}

	/**
	 * @return the checkOrderStaticParameters
	 检查订单时候的参数 */
	public HashMap<String, String> getCheckOrderStaticParameters() {
		return checkOrderStaticParameters;
	}

	/**
	 * @param checkOrderStaticParameters the checkOrderStaticParameters to set
	 检查订单时候的参数 */
	public void setCheckOrderStaticParameters(HashMap<String, String> checkOrderStaticParameters) {
		this.checkOrderStaticParameters = checkOrderStaticParameters;
	}

}
