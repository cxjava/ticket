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
		login.setCookieBIGipServerotsweb("2496921866.62495.0000");
		login.setCookieJSESSIONID("EBF7EDD5BBC3C356183640040759B668");
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
	public void submutOrder() throws InterruptedException {
		String info=query.querySingleAction();
		assertNotNull(info);
		Thread.sleep(3000);
		query.submutOrder(info);
	}
	@Test
	public void postPassenger() {
		query.postPassenger();
	}

}
