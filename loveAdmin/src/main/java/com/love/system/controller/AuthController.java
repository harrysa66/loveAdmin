package com.love.system.controller;

import java.util.ArrayList;
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

import com.love.framework.common.Constants;
import com.love.framework.controller.BaseController;
import com.love.framework.dao.jdbc.Page;
import com.love.system.biz.AuthBusiness;
import com.love.system.biz.MenuBtnBusiness;
import com.love.system.biz.MenuBusiness;
import com.love.system.po.Auth;
import com.love.system.po.Menu;
import com.love.system.po.MenuBtn;
import com.love.util.HtmlUtil;
import com.love.util.PageUtil;

@Controller
@RequestMapping("system/auth")
public class AuthController extends BaseController{
	
	@Resource
	private AuthBusiness authBusiness;
	
	@Resource
	private MenuBusiness menuBusiness;
	
	@Resource
	private MenuBtnBusiness menuBtnBusiness;
	
	@RequestMapping("/query")
	public ModelAndView  doQuery(HttpServletRequest request) throws Exception{
		Map<String,Object>  context = getRootMap();
		return forword("system/auth",context); 
	}
	
	@RequestMapping("/dataList")
	public void  dataList(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", ServletRequestUtils.getStringParameter(request, "code", "").trim());
		map.put("name", ServletRequestUtils.getStringParameter(request, "name", "").trim());
		map.put("authType", ServletRequestUtils.getStringParameter(request, "authType", "").trim());
		map.put("isvalid", ServletRequestUtils.getStringParameter(request, "isvalid", "").trim());
		String menuCode = ServletRequestUtils.getStringParameter(request, "menuCode", "").trim();
		List<String> menuIds = new ArrayList<String>();
		List<Menu> menuList = menuBusiness.findListByCode(menuCode);
		for(Menu menu : menuList){
			menuIds.add(menu.getId().toString());
		}
		if(menuIds.size() > 0){
			map.put("menuIds", menuIds);
		}
		Page<Auth> page = PageUtil.getPageObj(request);
		page = authBusiness.queryPage(map, page);
		List<Auth> authList= new ArrayList<Auth>();
		for(Auth auth : page.getResult()){
			auth.setFullName(authBusiness.getFullParentName(auth.getId()));
			authList.add(auth);
		}
		page.setResult(authList);
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
		map.put("name", auth.getName());
		Auth tempAuth = authBusiness.isRepeatCode(map);
		Auth vaName = authBusiness.isRepeatName(map);
		if(tempAuth != null){
			sendFailureMessage(response, "此菜单已分配权限!");
		}else if(vaName != null){
			sendFailureMessage(response, "权限名称已存在，请重新填写!");
		}else{
			String message = "";
			if(null == auth.getId() || "".equals(auth.getId())){
				message = Constants.ADD_SUCCESS;
			}else{
				message = Constants.EDIT_SUCCESS;
			}
			authBusiness.saveAuthWithMenu(auth,menuId,menuCode);
			sendSuccessMessage(response, message);
		}
	}
	
	@RequestMapping("/view")
	public void view(String id,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = new HashMap<String,Object> ();
		Auth auth = authBusiness.findAuthById(id);
		if(auth == null){
			sendFailureMessage(response, "没有找到对应的记录!");
			return;
		}
		String menuId = "";
		Menu menu = menuBusiness.findByAuthId(auth.getId());
		MenuBtn menuBtn = menuBtnBusiness.findByAuthId(auth.getId());
		if(menu != null){
			menuId = menu.getCode();
		}
		if(menuBtn != null){
			menuId = menuBtn.getCode();
		}
		String menuFullName = authBusiness.getFullParentName(id);
		
		//将对象转成Map
		Map<String,Object> data = BeanUtils.describe(auth);
		data.put("menuId", menuId);
		context.put(SUCCESS, true);
		context.put("data", data);
		context.put("menuFullName", menuFullName);
		HtmlUtil.writerJson(response, context);
	}
	
	@RequestMapping("/remove")
	public void remove(String[] id,HttpServletResponse response) throws Exception{
		if(null == id || "".equals(id) || id.length<1){
			sendFailureMessage(response, Constants.DELETE_ERROR);
		}
		authBusiness.removeAuths(id);
		sendSuccessMessage(response, Constants.DELETE_SUCCESS);
	}
	
	@RequestMapping("/run")
	public void run(String[] id,HttpServletResponse response) throws Exception{
		if(null == id || "".equals(id) || id.length<1){
			sendFailureMessage(response, Constants.DO_ERROR);
		}
		authBusiness.runAuths(id);
		sendSuccessMessage(response, Constants.DO_SUCCESS);
	}

}
