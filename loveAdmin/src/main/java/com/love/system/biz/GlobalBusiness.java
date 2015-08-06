package com.love.system.biz;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.love.framework.common.GlobalBean;

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