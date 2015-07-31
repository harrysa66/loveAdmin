package com.love.blog.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.love.blog.biz.VisitorBusiness;
import com.love.blog.po.Visitor;
import com.love.framework.common.Constants;
import com.love.framework.controller.BaseController;
import com.love.framework.dao.jdbc.Page;
import com.love.util.DateUtil;
import com.love.util.HtmlUtil;
import com.love.util.PageUtil;

@Controller
@RequestMapping("blog/visitor")
public class VisitorController extends BaseController{
	
	@Resource
	private VisitorBusiness visitorBusiness;
	
	@RequestMapping("/query")
	public ModelAndView  doQuery(HttpServletRequest request) throws Exception{
		Map<String,Object>  context = getRootMap();
		return forword("blog/visitor",context); 
	}
	
	@RequestMapping("/dataList")
	public void  dataList(HttpServletRequest request,HttpServletResponse response) throws ParseException{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ip", ServletRequestUtils.getStringParameter(request, "ip", "").trim());
		map.put("ipAddress", ServletRequestUtils.getStringParameter(request, "ipAddress", "").trim());
		map.put("status", ServletRequestUtils.getStringParameter(request, "status", "").trim());
		Page<Visitor> page = PageUtil.getPageObj(request);
		page = visitorBusiness.queryPage(map, page);
		
		Date date = new Date();
		List<Visitor> visitorList= new ArrayList<Visitor>();
		for(Visitor visitor : page.getResult()){
			if(visitor.getStatus().equals(Constants.STATUS_FORBID)){
				int subDates = DateUtil.datesBetween(date, visitor.getForbidEnd());
				if(subDates <= 0){
					visitor.setStatus(Constants.STATUS_DEFAULT);
					visitor.setForbidStart(null);
					visitor.setForbidDay(null);
					visitor.setForbidEnd(null);
					visitorBusiness.updateByStatus(visitor);
				}
			}
			visitorList.add(visitor);
		}
		page.setResult(visitorList);
		
		//设置页面数据
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("total",page.getTotalCount());
		jsonMap.put("rows", page.getResult());
		HtmlUtil.writerJson(response, jsonMap);
	}
	
	@RequestMapping("/save")
	public void save(Visitor visitor,HttpServletResponse response){
		visitor.setStatus(Constants.STATUS_FORBID);
		Date date = new Date();
		visitor.setForbidStart(date);
		visitor.setForbidEnd(DateUtil.addDateReDate(date, DateUtil.TYPE_DAY, Integer.parseInt(visitor.getForbidDay())));
		visitorBusiness.updateByStatus(visitor);
		sendSuccessMessage(response, "禁言成功");
	}
	
	@RequestMapping("/view")
	public void view(String id,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = new HashMap<String,Object> ();
		Visitor visitor = visitorBusiness.findById(id);
		if(visitor == null){
			sendFailureMessage(response, "没有找到对应的记录!");
			return;
		}
		
		//将对象转成Map
		Map<String,Object> data = BeanUtils.describe(visitor);
		context.put(SUCCESS, true);
		context.put("data", data);
		HtmlUtil.writerJson(response, context);
	}
	
	@RequestMapping("/inBlack")
	public void inBlack(String id,HttpServletResponse response){
		Map<String,Object>  context = new HashMap<String,Object> ();
		Visitor visitor = new Visitor();
		visitor.setId(id);
		visitor.setStatus(Constants.STATUS_BLACK);
		visitor.setForbidStart(null);
		visitor.setForbidDay(null);
		visitor.setForbidEnd(null);
		visitorBusiness.updateByStatus(visitor);
		context.put(SUCCESS, true);
		context.put("msg", "拉黑成功");
		HtmlUtil.writerJson(response, context);
	}
	
	@RequestMapping("/outBlack")
	public void outBlack(String id,HttpServletResponse response){
		Map<String,Object>  context = new HashMap<String,Object> ();
		Visitor visitor = new Visitor();
		visitor.setId(id);
		visitor.setStatus(Constants.STATUS_DEFAULT);
		visitorBusiness.updateByStatus(visitor);
		context.put(SUCCESS, true);
		context.put("msg", "移出黑名单成功");
		HtmlUtil.writerJson(response, context);
	}
	
	@RequestMapping("/outForbid")
	public void outForbid(String id,HttpServletResponse response){
		Map<String,Object>  context = new HashMap<String,Object> ();
		Visitor visitor = new Visitor();
		visitor.setId(id);
		visitor.setStatus(Constants.STATUS_DEFAULT);
		visitor.setForbidStart(null);
		visitor.setForbidDay(null);
		visitor.setForbidEnd(null);
		visitorBusiness.updateByStatus(visitor);
		context.put(SUCCESS, true);
		context.put("msg", "解除禁言成功");
		HtmlUtil.writerJson(response, context);
	}
	
	@RequestMapping("/viewByStatus")
	public void  viewByStatus(HttpServletRequest request,HttpServletResponse response){
		String status = ServletRequestUtils.getStringParameter(request, "status", "");
		List<Visitor> findList = visitorBusiness.findListByStatus(status);
		
		//设置页面数据
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("rows", findList);
		HtmlUtil.writerJson(response, jsonMap);
	}

}
