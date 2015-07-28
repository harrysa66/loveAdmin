package com.love.system.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.love.framework.common.Constants;
import com.love.framework.controller.BaseController;
import com.love.framework.dao.jdbc.Page;
import com.love.system.biz.AuthBusiness;
import com.love.system.po.Auth;
import com.love.util.HtmlUtil;
import com.love.util.PageUtil;

@Controller
@RequestMapping("system/auth")
public class AuthController extends BaseController{
	
	@Resource
	private AuthBusiness authBusiness;
	
	@RequestMapping("/query")
	public ModelAndView  doQuery(HttpServletRequest request) throws Exception{
		Map<String,Object>  context = getRootMap();
		return forword("system/auth",context); 
	}
	
	@RequestMapping("/dataList")
	public void  dataList(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		Page<Auth> page = PageUtil.getPageObj(request);
		page = authBusiness.queryPage(map, page);
		//设置页面数据
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("total",page.getTotalCount());
		jsonMap.put("rows", page.getResult());
		HtmlUtil.writerJson(response, jsonMap);
	}
	
	@RequestMapping("/save")
	public void save(Auth auth,Integer menuId,String menuCode,HttpServletResponse response){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", auth.getId());
		map.put("code", menuCode);
		Auth tempAuth = authBusiness.isRepeatCode(map);
		if(tempAuth != null){
			sendFailureMessage(response, "此菜单已分配权限!");
		}else{
			authBusiness.saveAuthWithMenu(auth,menuId,menuCode);
			sendSuccessMessage(response, Constants.ADD_SUCCESS);
		}
	}

}
