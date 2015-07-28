package com.love.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

public class URLUtils {
	
	private static ResourceBundle res = ResourceBundle.getBundle("urls");
	private static 	Map<String,String> urlsMap = null;
	
	/**
	 * 获取urlMap
	 * @return
	 */
	public static Map<String,String> getUrlMap(){
		if(urlsMap != null && !urlsMap.isEmpty()){
			return urlsMap;
		}
		urlsMap= new HashMap<String, String>();
		Enumeration e = res.getKeys();
		while(e.hasMoreElements()){
			String key = e.nextElement().toString();
			String value = get(key);
			urlsMap.put(key, value);
			System.out.println(key+"---"+value);
		}
		return urlsMap;
	}
	
	
	public static String get(String key){
		return res.getString(key);
	}
	
	
	public static String getReqUri(String reqUrl){
		try {
			URL url  = new URL(reqUrl);
			return url.getPath();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * 组装按钮下URL
	 * @param menuUrl
	 * @param actionUrls
	 * @return
	 */
	public static void getBtnAccessUrls(String menuUrl,String actionUrls,List<String> accessUrls){
		if(menuUrl == null || actionUrls == null || accessUrls == null ){
			return;
		}
		String url = "save.do| action.do |/user/manger/abcd.do";
		String[] actions =StringUtils.split(actionUrls , "|");
		String menuUri = StringUtils.substringBeforeLast(menuUrl, "/");
		for(String action : actions){
			action = StringUtils.trim(action);
			if(StringUtils.startsWith(action, "/"))
				accessUrls.add(action);
			else
				accessUrls.add(menuUri+"/"+action);
		}
	}
	
}
