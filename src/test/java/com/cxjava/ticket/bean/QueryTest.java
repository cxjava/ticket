package com.cxjava.ticket.bean;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cxjava.ticket.Base;

/**
 * @author Maty Chen
 * @date 2013-1-24上午10:33:03
 */

public class QueryTest extends Base {
	private static final Logger LOG = LoggerFactory.getLogger(QueryTest.class);

	@Autowired
	private Login login;
	@Autowired
	private Query query;

	@Before
	public void before() {
//		assertTrue(login.login());
		login.setCookieBIGipServerotsweb("2698248458.62495.0000");
		login.setCookieJSESSIONID("01C21A4946E5846ED91D178EF69B0188");
		query.getQueryPage();
	}

	@Test
	public void getCaptcha() {
		String captcha = query.getCaptcha();
		assertNotNull(captcha);
		LOG.debug("captcha : {}.", captcha);
	}
	@Test
	public void getStationName() {
		assertNotNull(query.getStationName());
		assertNotNull(query.getStationNameStatic());
	}
	@Test
	public void querySingleAction() {
		assertNotNull(query.querySingleAction());
	}
	@Test
	public void submutOrder() {
		String info=query.querySingleAction();
		assertNotNull(info);
		query.submutOrder(info);
	}

}
