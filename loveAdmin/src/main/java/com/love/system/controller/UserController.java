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
import com.love.system.biz.RoleBusiness;
import com.love.system.biz.UserBusiness;
import com.love.system.po.Auth;
import com.love.system.po.Role;
import com.love.system.po.User;
import com.love.util.HtmlUtil;
import com.love.util.PageUtil;

@Controller
@RequestMapping("system/user")
public class UserController extends BaseController{
	
	@Resource
	private UserBusiness userBusiness;
	
	@Resource
	private RoleBusiness roleBusiness;
	
	@Resource
	private AuthBusiness authBusiness;
	
	@RequestMapping("/query")
	public ModelAndView  doQuery(HttpServletRequest request) throws Exception{
		Map<String,Object>  context = getRootMap();
		return forword("system/user",context); 
	}
	
	@RequestMapping("/dataList")
	public void  dataList(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", ServletRequestUtils.getStringParameter(request, "username", "").trim());
		map.put("nickname", ServletRequestUtils.getStringParameter(request, "nickname", "").trim());
		map.put("isvalid", ServletRequestUtils.getStringParameter(request, "isvalid", "").trim());
		Page<User> page = PageUtil.getPageObj(request);
		page = userBusiness.queryPage(map, page);
		
		//设置页面数据
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("total",page.getTotalCount());
		jsonMap.put("rows", page.getResult());
		HtmlUtil.writerJson(response, jsonMap);
	}
	
	@RequestMapping("/save")
	public void save(User user,HttpServletResponse response){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", user.getId());
		map.put("username", user.getUsername());
		map.put("nickname", user.getNickname());
		User vaUsername = userBusiness.isRepeatUsername(map);
		User vaNickname = userBusiness.isRepeatNickname(map);
		if(vaUsername != null){
			sendFailureMessage(response, "用户名已存在，请重新填写!");
		}else if(vaNickname != null){
			sendFailureMessage(response, "昵称已存在，请重新填写!");
		}else{
			String message = userBusiness.saveUser(user);
			sendSuccessMessage(response, message);
		}
	}
	
	@RequestMapping("/view")
	public void view(String id,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = new HashMap<String,Object> ();
		User user = userBusiness.findUserById(id);
		if(user == null){
			sendFailureMessage(response, "没有找到对应的记录!");
			return;
		}
		
		//将对象转成Map
		Map<String,Object> data = BeanUtils.describe(user);
		context.put(SUCCESS, true);
		context.put("data", data);
		HtmlUtil.writerJson(response, context);
	}
	
	@RequestMapping("/remove")
	public void remove(String[] id,HttpServletResponse response) throws Exception{
		if(null == id || "".equals(id) || id.length<1){
			sendFailureMessage(response, Constants.DELETE_ERROR);
		}
		boolean isNoAdmin = userBusiness.removeUsers(id);
		if(isNoAdmin){
			sendSuccessMessage(response, Constants.DELETE_SUCCESS);
		}else{
			sendSuccessMessage(response, "超级管理员不能进行该操作,其他数据删除成功!");
		}
	}
	
	@RequestMapping("/run")
	public void run(String[] id,HttpServletResponse response) throws Exception{
		if(null == id || "".equals(id) || id.length<1){
			sendFailureMessage(response, Constants.DO_ERROR);
		}
		boolean isNoAdmin = userBusiness.runUsers(id);
		if(isNoAdmin){
			sendSuccessMessage(response, Constants.DO_SUCCESS);
		}else{
			sendSuccessMessage(response, "超级管理员不能进行该操作,其他数据操作成功!");
		}
	}
	
	@RequestMapping("/roleDataList")
	public void  roleDataList(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", ServletRequestUtils.getStringParameter(request, "code", "").trim());
		map.put("name", ServletRequestUtils.getStringParameter(request, "name", "").trim());
		Page<Role> page = PageUtil.getPageObj(request);
		page = roleBusiness.queryPage(map, page);
		
		//设置页面数据
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("total",page.getTotalCount());
		jsonMap.put("rows", page.getResult());
		HtmlUtil.writerJson(response, jsonMap);
	}
	
	@RequestMapping("/getRoleIdsByUser")
	public void  getRoleIdsByUser(String id,HttpServletResponse response){
		List<String> roleIds = new ArrayList<String>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", id);
		List<Role> roleList = roleBusiness.findListByUser(map);
		for(Role role : roleList){
			roleIds.add(role.getId());
		}
		HtmlUtil.writerJson(response, roleIds);
	}
	
	@RequestMapping("/grantRole")
	public void grantRole(String userId,String[] ids,HttpServletResponse response){
		Map<String,Object>  context = new HashMap<String,Object> ();
		roleBusiness.grantRoleToUser(userId, ids);
		context.put(SUCCESS, true);
		context.put("msg", Constants.ALLOT_ROLE_SUCCESS);
		HtmlUtil.writerJson(response, context);
	}
	
	@RequestMapping("/authDataList")
	public void  authDataList(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", ServletRequestUtils.getStringParameter(request, "code", "").trim());
		map.put("name", ServletRequestUtils.getStringParameter(request, "name", "").trim());
		map.put("authType", ServletRequestUtils.getStringParameter(request, "authType", "").trim());
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
	
	@RequestMapping("/getAuthIdsByUser")
	public void  getAuthIdsByUser(String id,HttpServletResponse response){
		List<String> authIds = new ArrayList<String>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", id);
		List<Auth> authList = authBusiness.findListByUser(map);
		for(Auth auth : authList){
			authIds.add(auth.getId());
		}
		HtmlUtil.writerJson(response, authIds);
	}
	
	@RequestMapping("/grantAuth")
	public void grantAuth(String userId,String[] ids,HttpServletResponse response){
		Map<String,Object>  context = new HashMap<String,Object> ();
		authBusiness.grantAuthToUser(userId, ids);
		context.put(SUCCESS, true);
		context.put("msg", Constants.ALLOT_AUTH_SUCCESS);
		HtmlUtil.writerJson(response, context);
	}
	
	@RequestMapping("/viewRole")
	public void  viewRole(HttpServletRequest request,HttpServletResponse response){
		String userId = ServletRequestUtils.getStringParameter(request, "userId", "");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("status", Constants.STATUS_DEFAULT);
		List<Role> findList = roleBusiness.findListByUser(map);
		
		//设置页面数据
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("rows", findList);
		HtmlUtil.writerJson(response, jsonMap);
	}
	
	@RequestMapping("/viewAuth")
	public void  viewAuth(HttpServletRequest request,HttpServletResponse response){
		String userId = ServletRequestUtils.getStringParameter(request, "userId", "");
		User user = userBusiness.findUserById(userId);
		List<User> userList = roleBusiness.findUserListByRoleCode(Constants.ROLE_ADMIN_CODE);
		List<Auth> findList = null;
		if(user.getUsername().equals(Constants.USER_ADMIN_CODE) || userList.contains(user)){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", Constants.STATUS_DEFAULT);
			findList = authBusiness.findListBy(map);
		}else{
			findList = roleBusiness.findAuthByUser(userId);
		}
		List<Auth> authList= new ArrayList<Auth>();
		for(Auth auth : findList){
			auth.setFullName(authBusiness.getFullParentName(auth.getId()));
			authList.add(auth);
		}
		
		//设置页面数据
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("rows", authList);
		HtmlUtil.writerJson(response, jsonMap);
	}

}
