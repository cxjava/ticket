package com.cxjava.ticket.bean;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpHost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Maty Chen
 * @date 2013-1-24上午10:40:52
 */
public class ClientCore {

	private static final Logger LOG = LoggerFactory.getLogger(Login.class);

	private DefaultHttpClient httpClient;
	private String isProxy;
	private String proxyIp;
	private Integer proxyPort;
	private String proxyUserName;
	private String proxyPassword;

	/** 自定义所有参数信息 */
	private HashMap<String, Object> params;

	private ClientConnectionManager connectionManager;

	private static X509TrustManager trustManager = new X509TrustManager() {
		public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	};

	private void init() throws KeyManagementException, NoSuchAlgorithmException {
		SSLContext sslcontext = SSLContext.getInstance("TLS");
		sslcontext.init(null, new TrustManager[] { trustManager }, null);
		SSLSocketFactory ssf = new SSLSocketFactory(sslcontext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

		SchemeRegistry registry = connectionManager.getSchemeRegistry();
		registry.register(new Scheme("https", 443, ssf));
		httpClient = new DefaultHttpClient(connectionManager);
	}

	private void setParams() {
		if ("true".equals(isProxy)) {
			HttpHost proxy = new HttpHost(proxyIp, proxyPort, HttpHost.DEFAULT_SCHEME_NAME);
			this.httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		}
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			httpClient.getParams().setParameter(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * @return the httpClient
	 */
	public DefaultHttpClient getHttpClient() {
		return httpClient;
	}

	/**
	 * @param httpClient
	 *            the httpClient to set
	 */
	public void setHttpClient(DefaultHttpClient httpClient) {
		this.httpClient = httpClient;
	}

	/**
	 * @return the isProxy
	 */
	public String getIsProxy() {
		return isProxy;
	}

	/**
	 * @param isProxy
	 *            the isProxy to set
	 */
	public void setIsProxy(String isProxy) {
		this.isProxy = isProxy;
	}

	/**
	 * @return the proxyIp
	 */
	public String getProxyIp() {
		return proxyIp;
	}

	/**
	 * @param proxyIp
	 *            the proxyIp to set
	 */
	public void setProxyIp(String proxyIp) {
		this.proxyIp = proxyIp;
	}

	/**
	 * @return the proxyPort
	 */
	public Integer getProxyPort() {
		return proxyPort;
	}

	/**
	 * @param proxyPort
	 *            the proxyPort to set
	 */
	public void setProxyPort(Integer proxyPort) {
		this.proxyPort = proxyPort;
	}

	/**
	 * @return the proxyUserName
	 */
	public String getProxyUserName() {
		return proxyUserName;
	}

	/**
	 * @param proxyUserName
	 *            the proxyUserName to set
	 */
	public void setProxyUserName(String proxyUserName) {
		this.proxyUserName = proxyUserName;
	}

	/**
	 * @return the proxyPassword
	 */
	public String getProxyPassword() {
		return proxyPassword;
	}

	/**
	 * @param proxyPassword
	 *            the proxyPassword to set
	 */
	public void setProxyPassword(String proxyPassword) {
		this.proxyPassword = proxyPassword;
	}

	/**
	 * @return the params 自定义所有参数信息
	 */
	public HashMap<String, Object> getParams() {
		return params;
	}

	/**
	 * @param params
	 *            the params to set 自定义所有参数信息
	 */
	public void setParams(HashMap<String, Object> params) {
		this.params = params;
	}

	/**
	 * @return the connectionManager
	 */
	public ClientConnectionManager getConnectionManager() {
		return connectionManager;
	}

	/**
	 * @param connectionManager
	 *            the connectionManager to set
	 */
	public void setConnectionManager(ClientConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
	}

}
