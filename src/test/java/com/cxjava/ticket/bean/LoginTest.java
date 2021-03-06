package com.cxjava.ticket.bean;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cxjava.ticket.Base;

/**
 * @author Maty Chen
 * @date 2013-1-24上午10:33:03
 */

public class LoginTest extends Base {
	private static final Logger LOG = LoggerFactory.getLogger(LoginTest.class);

	@Autowired
	private Login login;

	@Test
	public void getMainPage() {
		login.getMainPage();
	}

	@Test
	public void parameters() {
		for (Map.Entry<String, String> entry : login.getHeaders().entrySet()) {
			LOG.info(" {} : {}", entry.getKey(), entry.getValue());
		}
		assertNotNull(login.getLoginCodeUrl());
		assertNotNull(login.getLoginRandomCodeUrl());
		assertNotNull(login.getLoginFormUrl());
	}

	@Test
	public void getRandomCode() {
		LOG.debug("login.getRandomCode() : {}", login.getRandomCode());
	}
	@Test
	public void getCaptcha() {
		LOG.debug("login.getCaptcha() : {}", login.getCaptcha());
		assertNotNull(login.getCaptcha());
	}
	@Test
	public void login() {
		assertTrue(login.login());
		//获取个人资料来验证是否登录成功
		String body=login.doHttpGet("https://dynamic.12306.cn/otsweb/sysuser/editMemberAction.do?method=initEdit");
		assertTrue(body.contains("手机号码")||body.contains("系统例行维护时间"));
	}
	@Test
	public void setCookie() {
		login.setCookieBIGipServerotsweb("2496921866.48160.0000");
		login.setCookieJSESSIONID("201E216629BD14C066E1D2FE452F6C9F");
		//获取个人资料来验证是否登录成功
		String body=login.doHttpGet("https://dynamic.12306.cn/otsweb/sysuser/editMemberAction.do?method=initEdit");
		assertTrue(body.contains("手机号码")||body.contains("系统例行维护时间"));
	}

}
