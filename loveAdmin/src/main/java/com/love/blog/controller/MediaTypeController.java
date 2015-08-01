package com.love.blog.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.love.blog.biz.MediaTypeBusiness;
import com.love.blog.po.MediaType;
import com.love.framework.common.Constants;
import com.love.framework.controller.BaseController;
import com.love.framework.dao.jdbc.Page;
import com.love.util.HtmlUtil;
import com.love.util.PageUtil;

@Controller
@RequestMapping("blog/mediaType")
public class MediaTypeController extends BaseController{
	
	@Resource
	private MediaTypeBusiness mediaTypeBusiness;
	
	@RequestMapping("/query")
	public ModelAndView  doQuery(HttpServletRequest request) throws Exception{
		Map<String,Object>  context = getRootMap();
		return forword("blog/mediaType",context); 
	}
	
	@RequestMapping("/dataList")
	public void  dataList(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", ServletRequestUtils.getStringParameter(request, "code", "").trim());
		map.put("name", ServletRequestUtils.getStringParameter(request, "name", "").trim());
		map.put("isvalid", ServletRequestUtils.getStringParameter(request, "isvalid", "").trim());
		Page<MediaType> page = PageUtil.getPageObj(request);
		page = mediaTypeBusiness.queryPage(map, page);
		
		//设置页面数据
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("total",page.getTotalCount());
		jsonMap.put("rows", page.getResult());
		HtmlUtil.writerJson(response, jsonMap);
	}
	
	@RequestMapping("/save")
	public void save(MediaType mediaType,HttpServletResponse response){
		String subCode = "";
		if(mediaType.getCode().length() > 5){
			subCode = mediaType.getCode().substring(0, Constants.MEDIA_RULE_ROLE.length());
		}
		if(!subCode.equals(Constants.MEDIA_RULE_ROLE)){
			mediaType.setCode(Constants.MEDIA_RULE_ROLE+mediaType.getCode());
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", mediaType.getId());
		map.put("code", mediaType.getCode());
		map.put("name", mediaType.getName());
		MediaType vaCode = mediaTypeBusiness.isRepeatCode(map);
		MediaType vaName = mediaTypeBusiness.isRepeatName(map);
		if(vaCode != null){
			sendFailureMessage(response, "类型编号已存在，请重新填写!");
		}else if(vaName != null){
			sendFailureMessage(response, "类型名称已存在，请重新填写!");
		}else{
			String message = mediaTypeBusiness.save(mediaType);
			sendSuccessMessage(response, message);
		}
	}
	
	@RequestMapping("/view")
	public void view(String id,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = new HashMap<String,Object> ();
		MediaType mediaType = mediaTypeBusiness.findById(id);
		if(mediaType == null){
			sendFailureMessage(response, "没有找到对应的记录!");
			return;
		}
		
		//将对象转成Map
		Map<String,Object> data = BeanUtils.describe(mediaType);
		context.put(SUCCESS, true);
		context.put("data", data);
		HtmlUtil.writerJson(response, context);
	}
	
	@RequestMapping("/remove")
	public void remove(String[] id,HttpServletResponse response) throws Exception{
		if(null == id || "".equals(id) || id.length<1){
			sendFailureMessage(response, Constants.DELETE_ERROR);
		}
		boolean isAllow = mediaTypeBusiness.removeMediaTypeTypes(id);
		if(isAllow){
			sendSuccessMessage(response, Constants.DELETE_SUCCESS);
		}else{
			sendSuccessMessage(response, "不能删除拥有文章的类型!");
		}
	}
	
	@RequestMapping("/run")
	public void run(String[] id,HttpServletResponse response) throws Exception{
		if(null == id || "".equals(id) || id.length<1){
			sendFailureMessage(response, Constants.DO_ERROR);
		}
		boolean isAllow = mediaTypeBusiness.runAuths(id);
		if(isAllow){
			sendSuccessMessage(response, Constants.DO_SUCCESS);
		}else{
			sendSuccessMessage(response, "不能操作拥有文章的类型!");
		}
	}

}
