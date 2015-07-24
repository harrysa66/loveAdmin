package com.love.framework.common;

import java.util.Map;

public class GlobalBean {
	
	private Map<String, String> appConfig;

	  public Map<String, String> getAppConfig()
	  {
	    return this.appConfig;
	  }

	  public void setAppConfig(Map<String, String> appConfig) {
	    this.appConfig = appConfig;
	  }

}
