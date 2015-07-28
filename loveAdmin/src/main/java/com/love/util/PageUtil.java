package com.love.util;

import javax.servlet.http.HttpServletRequest;

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
	
	/*public static <T> Page<T> getPageObjForTenPageSize(HttpServletRequest request) {
    	int pageNumber = 1;
    	String pageSize = ServletRequestUtils.getStringParameter(request, "numPerPage", "10");
		String pageIndexName = ServletRequestUtils.getStringParameter(request, "pageNum", "1");
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
		page.setOrder(ServletRequestUtils.getStringParameter(request, "orderDirection", "desc"));
		page.setOrderBy(ServletRequestUtils.getStringParameter(request, "orderField", ""));
		return page;
    }*/
}