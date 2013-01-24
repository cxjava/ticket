package com.cxjava.ticket.bean;

import static org.junit.Assert.*;

import java.util.Map;

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

public class LoginTest extends Base {
	private static final Logger LOG = LoggerFactory.getLogger(LoginTest.class);

	@Autowired
	private Login login;

	@Before
	public void before() {
	}

	@Test
	public void parameters() {
		LOG.info("login.getLogin : {}.", login.getLoginCodeUrl());
		LOG.info("login.getLogin : {}.", login.getLoginRandomCodeUrl());
		LOG.info("login.getLogin : {}.", login.getLoginUrl());
		for (Map.Entry<String, String> entry : login.getHeaders().entrySet()) {
			LOG.info(" {} : {}", entry.getKey(), entry.getValue());
		}
		assertNotNull(login.getLoginCodeUrl());
		assertNotNull(login.getLoginRandomCodeUrl());
		assertNotNull(login.getLoginUrl());
	}

	@Test
	public void getRandomCode() {
		LOG.debug("login.getRandomCode() : {}", login.getRandomCode());
	}
}
