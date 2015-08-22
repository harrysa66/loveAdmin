package com.love.blog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.love.blog.biz.DaysBusiness;
import com.love.blog.po.Days;
import com.love.framework.common.Constants;
import com.love.framework.controller.BaseController;
import com.love.framework.dao.jdbc.Page;
import com.love.util.DateUtil;
import com.love.util.HtmlUtil;
import com.love.util.PageUtil;

@Controller
@RequestMapping("blog/days")
public class DaysController extends BaseController{
	
	@Resource
	private DaysBusiness daysBusiness;
	
	@RequestMapping("/query")
	public ModelAndView  doQuery(HttpServletRequest request) throws Exception{
		Map<String,Object>  context = getRootMap();
		return forword("blog/days",context); 
	}
	
	@RequestMapping("/dataList")
	public void  dataList(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memorialTitle", ServletRequestUtils.getStringParameter(request, "memorialTitle", "").trim());
		map.put("isvalid", ServletRequestUtils.getStringParameter(request, "isvalid", "").trim());
		Page<Days> page = PageUtil.getPageObj(request);
		page = daysBusiness.queryPage(map, page);
		
		//设置页面数据
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("total",page.getTotalCount());
		jsonMap.put("rows", page.getResult());
		HtmlUtil.writerJson(response, jsonMap);
	}
	
	@RequestMapping("/save")
	public void save(Days days,HttpServletResponse response){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", days.getId());
		map.put("memorialTitle", days.getMemorialTitle());
		Days vaTitle = daysBusiness.isRepeatTitle(map);
		Map<String, Object> listMap = new HashMap<String, Object>();
		listMap.put("status", Constants.STATUS_DEFAULT);
		List<Days> daysList = daysBusiness.findListByMap(listMap);
		if(vaTitle != null){
			sendFailureMessage(response, "标题已存在，请重新填写!");
		}else if(StringUtils.isEmpty(days.getId()) && daysList.size() >= 5){
			sendFailureMessage(response, "暂时只能保存5个纪念日!");
		}else{
			String message = daysBusiness.save(days);
			sendSuccessMessage(response, message);
		}
	}
	
	@RequestMapping("/view")
	public void view(String id,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = new HashMap<String,Object> ();
		Days days = daysBusiness.findById(id);
		if(days == null){
			sendFailureMessage(response, "没有找到对应的记录!");
			return;
		}
		String fmtDate = DateUtil.dateToString(days.getMemorialDate(), "yyyy-MM-dd HH:mm:ss");
		days.setFmtDate(fmtDate);
		
		//将对象转成Map
		Map<String,Object> data = BeanUtils.describe(days);
		context.put(SUCCESS, true);
		context.put("data", data);
		HtmlUtil.writerJson(response, context);
	}
	
	@RequestMapping("/remove")
	public void remove(String[] id,HttpServletResponse response) throws Exception{
		if(null == id || "".equals(id) || id.length<1){
			sendFailureMessage(response, Constants.DELETE_ERROR);
		}
		daysBusiness.removeDays(id);
		sendSuccessMessage(response, Constants.DELETE_SUCCESS);
	}
	
	@RequestMapping("/run")
	public void run(String[] id,HttpServletResponse response) throws Exception{
		if(null == id || "".equals(id) || id.length<1){
			sendFailureMessage(response, Constants.DO_ERROR);
		}
		daysBusiness.runDays(id);
		sendSuccessMessage(response, Constants.DO_SUCCESS);
	}

}
