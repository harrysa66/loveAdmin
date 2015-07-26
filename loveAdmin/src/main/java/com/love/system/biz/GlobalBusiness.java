package com.love.system.biz;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.love.framework.common.GlobalBean;

/**
 * 
 * @author 李斌
 * @history
 * <TABLE id="HistoryTable" border="1">
 * 	<TR><TD>时间</TD><TD>描述</TD><TD>作者</TD></TR>
 *	<TR><TD>2014-4-8</TD><TD>创建初始版本</TD><TD>张军伟</TD></TR>
 * </TABLE>
 */
@Service
public class GlobalBusiness {
	
	private static final Logger log = Logger.getLogger(GlobalBusiness.class);
	
	private static ServletContext sc;
	
	@Resource
	private GlobalBean globalBean;
	
	public void init(ServletContext sc) {
		this.sc = sc;
		if(globalBean.getAppConfig() != null) {
			for(String k : globalBean.getAppConfig().keySet()) {
				log.info(String.format("%s = %s", k, globalBean.getAppConfig().get(k)));
			}
			sc.setAttribute("globalBean", globalBean.getAppConfig());
		}
	}

	/**
	 * 返回提示信息
	 * @param key
	 * @return
	 */
	public String getValue(String key) {  
		if(sc.getAttribute("globalBean") != null) {
			Map<String,String> map = (Map<String,String>)sc.getAttribute("globalBean");
			return map.get(key);
		}
	  return null;
	} 
}

/**
 * Copyright ? 2014,中汇联   All rights reserved.
 */