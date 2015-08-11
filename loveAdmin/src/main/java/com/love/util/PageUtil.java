package com.love.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;

import com.love.framework.dao.jdbc.Page;

public class PageUtil {
	public PageUtil() {}
    
	/**
	 * 获得页面中的页号信息，放到page对象中
	 * @param <T>
	 * @param request
	 * @return
	 */
	public static <T> Page<T> getPageObj(HttpServletRequest request) {
    	int pageNumber = 1;
    	String pageSize = ServletRequestUtils.getStringParameter(request, "rows", "10");
		String pageIndexName = ServletRequestUtils.getStringParameter(request, "page", "1");
		String totalCount = ServletRequestUtils.getStringParameter(request, "totalCount", "0");
		if (pageIndexName != null && StringUtils.isNotBlank(pageIndexName) && (pageIndexName.indexOf("null")== -1)) {
			pageNumber = Integer.parseInt(pageIndexName);
		}
		Page<T> page = new Page<T>();
		page.setPageNumber(pageNumber);
		page.setPageSize(Integer.parseInt(pageSize));
		page.setTotalCount(Long.parseLong(totalCount));
		if(page.getPageSize()<(page.getPageSize()*page.pageNumber-page.getTotalCount()) && page.getTotalCount()!=0){
			page.setPageNumber((int)Math.ceil(page.getTotalCount()/page.getPageSize())+1);
		}
		page.setOrder(ServletRequestUtils.getStringParameter(request, "order", "desc"));
		page.setOrderBy(ServletRequestUtils.getStringParameter(request, "sort", ""));
		return page;
    }
	
	/**
	 * 获得Map中的页号信息，放到page对象中
	 * @param <T>
	 * @param map(rows,page,totalCount)
	 * @return
	 */
	public static <T> Page<T> getPageObj(Map<String, Object> map) {
    	int pageNumber = 1;
    	String pageSize = MapUtils.getString(map, "rows");//ServletRequestUtils.getStringParameter(request, "rows", "10");
		String pageIndexName = MapUtils.getString(map, "page");//ServletRequestUtils.getStringParameter(request, "page", "1");
		String totalCount = MapUtils.getString(map, "totalCount");//ServletRequestUtils.getStringParameter(request, "totalCount", "0");
		if (pageIndexName != null && StringUtils.isNotBlank(pageIndexName) && (pageIndexName.indexOf("null")== -1)) {
			pageNumber = Integer.parseInt(pageIndexName);
		}
		Page<T> page = new Page<T>();
		page.setPageNumber(pageNumber);
		page.setPageSize(Integer.parseInt(pageSize));
		page.setTotalCount(Long.parseLong(totalCount));
		if(page.getPageSize()<(page.getPageSize()*page.pageNumber-page.getTotalCount()) && page.getTotalCount()!=0){
			page.setPageNumber((int)Math.ceil(page.getTotalCount()/page.getPageSize())+1);
		}
		return page;
    }
	
	
}