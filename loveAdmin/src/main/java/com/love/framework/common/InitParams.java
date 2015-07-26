package com.love.framework.common;


import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.love.system.biz.GlobalBusiness;

@Component
public class InitParams implements ServletContextAware {
	
	@Resource
	private GlobalBusiness globalBusiness;
	
	@Override
	public void setServletContext(ServletContext sc) {
		globalBusiness.init(sc);
	}
}

/**
 * Copyright ? 2014,中汇联   All rights reserved.
 */