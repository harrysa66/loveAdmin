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
import com.love.system.po.Auth;
import com.love.system.po.Role;
import com.love.util.HtmlUtil;
import com.love.util.PageUtil;

@Controller
@RequestMapping("system/role")
public class RoleController extends BaseController{
	
	@Resource
	private RoleBusiness roleBusiness;
	
	@Resource
	private AuthBusiness authBusiness;
	
	@RequestMapping("/query")
	public ModelAndView  doQuery(HttpServletRequest request) throws Exception{
		Map<String,Object>  context = getRootMap();
		return forword("system/role",context); 
	}
	
	@RequestMapping("/dataList")
	public void  dataList(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", ServletRequestUtils.getStringParameter(request, "code", "").trim());
		map.put("name", ServletRequestUtils.getStringParameter(request, "name", "").trim());
		map.put("isvalid", ServletRequestUtils.getStringParameter(request, "isvalid", "").trim());
		Page<Role> page = PageUtil.getPageObj(request);
		page = roleBusiness.queryPage(map, page);
		
		//设置页面数据
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("total",page.getTotalCount());
		jsonMap.put("rows", page.getResult());
		HtmlUtil.writerJson(response, jsonMap);
	}
	
	@RequestMapping("/save")
	public void save(Role role,HttpServletResponse response){
		String roleSubCode = "";
		if(role.getCode().length() > 4){
			roleSubCode = role.getCode().substring(0, Constants.CODE_RULE_ROLE.length());
		}
		if(!roleSubCode.equals(Constants.CODE_RULE_ROLE)){
			role.setCode(Constants.CODE_RULE_ROLE+role.getCode());
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", role.getId());
		map.put("code", role.getCode());
		map.put("name", role.getName());
		Role vaCode = roleBusiness.isRepeatCode(map);
		Role vaName = roleBusiness.isRepeatName(map);
		if(vaCode != null){
			sendFailureMessage(response, "角色编号已存在，请重新填写!");
		}else if(vaName != null){
			sendFailureMessage(response, "角色名称已存在，请重新填写!");
		}else{
			String message = roleBusiness.saveRole(role);
			sendSuccessMessage(response, message);
		}
	}
	
	@RequestMapping("/view")
	public void view(String id,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = new HashMap<String,Object> ();
		Role role = roleBusiness.findRoleById(id);
		if(role == null){
			sendFailureMessage(response, "没有找到对应的记录!");
			return;
		}
		
		//将对象转成Map
		Map<String,Object> data = BeanUtils.describe(role);
		context.put(SUCCESS, true);
		context.put("data", data);
		HtmlUtil.writerJson(response, context);
	}
	
	@RequestMapping("/remove")
	public void remove(String[] id,HttpServletResponse response) throws Exception{
		if(null == id || "".equals(id) || id.length<1){
			sendFailureMessage(response, Constants.DELETE_ERROR);
		}
		boolean isNoAdmin = roleBusiness.removeRoles(id);
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
		boolean isNoAdmin = roleBusiness.runRoles(id);
		if(isNoAdmin){
			sendSuccessMessage(response, Constants.DO_SUCCESS);
		}else{
			sendSuccessMessage(response, "超级管理员不能进行该操作,其他数据操作成功!");
		}
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
	
	@RequestMapping("/getAuthIdsByRole")
	public void  getAuthIdsByRole(String id,HttpServletResponse response){
		List<String> authIds = new ArrayList<String>();
		List<Auth> authList = authBusiness.findAuthByRole(id);
		for(Auth auth : authList){
			authIds.add(auth.getId());
		}
		HtmlUtil.writerJson(response, authIds);
	}
	
	@RequestMapping("/grantAuth")
	public void grantAuth(String roleId,String[] ids,HttpServletResponse response){
		Map<String,Object>  context = new HashMap<String,Object> ();
		roleBusiness.clearAuthToRole(roleId);
		if(ids.length > 0){
			roleBusiness.grantAuthToRole(roleId, ids);
		}
		context.put(SUCCESS, true);
		context.put("msg", Constants.ALLOT_AUTH_SUCCESS);
		HtmlUtil.writerJson(response, context);
	}
	
	@RequestMapping("/viewAuth")
	public void  viewAuth(HttpServletRequest request,HttpServletResponse response){
		String roleId = ServletRequestUtils.getStringParameter(request, "roleId", "");
		Role role = roleBusiness.findRoleById(roleId);
		List<Auth> findList = null;
		if(role.getCode().equals(Constants.ROLE_ADMIN_CODE)){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", Constants.STATUS_DEFAULT);
			findList = authBusiness.findListBy(map);
		}else{
			findList = roleBusiness.findAuthByRole(roleId);
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
