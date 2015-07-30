package com.love.framework.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.love.framework.common.Constants;
import com.love.framework.common.TreeNode;
import com.love.framework.security.SpringSecurityUtils;
import com.love.system.biz.MenuBtnBusiness;
import com.love.system.biz.MenuBusiness;
import com.love.system.biz.RoleBusiness;
import com.love.system.biz.UserBusiness;
import com.love.system.po.Menu;
import com.love.system.po.MenuBtn;
import com.love.system.po.Role;
import com.love.system.po.User;
import com.love.util.HtmlUtil;
import com.love.util.MD5Util;
import com.love.util.SessionUtils;
import com.love.util.TreeUtil;
import com.love.util.URLUtils;

@Controller
public class MainController extends BaseController{
	private final static Logger log= Logger.getLogger(MainController.class);
	
	@Resource
	private MenuBusiness menuBusiness;
	
	@Resource
	private MenuBtnBusiness menuBtnBusiness;
	
	@Resource
	private RoleBusiness roleBusiness;
	
	@Resource
	private UserBusiness userBusiness; 
	
	@RequestMapping("/login.s")
	public ModelAndView login(){
		Map<String,Object>  context = getRootMap();
		return forword("login",context); 
	}
	
	@RequestMapping("/timeout.s")
	public void timeout(){
		
	}
	
	@RequestMapping("/modifyPassword")
	public void modifyPassword(String id,String oldPwd,String newPwd,HttpServletResponse response){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("oldPassword", MD5Util.MD5(oldPwd));
		map.put("newPassword", MD5Util.MD5(newPwd));
		String message = userBusiness.updatePassword(map);
		sendSuccessMessage(response, message);
	}
	
	@RequestMapping("/index")
	public ModelAndView  main(HttpServletRequest request){
		Map<String,Object>  context = getRootMap();
		User user = SpringSecurityUtils.getCurrentUser();
		String userIp = SpringSecurityUtils.getCurrentUserIp();
		Map<String,Object> parements=new HashMap<String,Object>();
   		parements.put("status",Constants.STATUS_DEFAULT);
   		parements.put("isvalid",Constants.ISVALIAD_SHOW);
   		parements.put("type",Constants.MENU_ADMIN);
		List<Menu> rootMenuList = menuBusiness.selectListByNull(parements);
		List<Menu> childMenuList = menuBusiness.selectListByNotNull(parements);
		List<MenuBtn> childBtnList = menuBtnBusiness.selectList(parements);
		List<Menu> rootMenus = initMenu(rootMenuList, user);
		List<Menu> childMenus = initMenu(childMenuList, user);
		List<MenuBtn> childBtns = initBtn(childBtnList,user);
		buildData(childMenus,childBtns,request); //构建必要的数据
		context.put("user", user);
		context.put("userIp", userIp);
		context.put("menuList", treeMenu(rootMenus, childMenus));
		return forword("index",context); 
	}
	
	@RequestMapping("/getActionBtn")
	public void  getActionBtn(String url,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, Object> result = new HashMap<String, Object>();
		List<String> actionTypes = new ArrayList<String>();
		//判断是否超级管理员
		if(SessionUtils.isAdmin()){
			result.put("allType", true);
		}else{
			String menuUrl = URLUtils.getReqUri(url);
			menuUrl = StringUtils.remove(menuUrl,request.getContextPath());
			//获取权限按钮
			actionTypes = SessionUtils.getMemuBtnListVal(request, StringUtils.trim(menuUrl));
			result.put("allType", false);
			result.put("types", actionTypes);
		}
		result.put(SUCCESS, true);
		HtmlUtil.writerJson(response, result);
	}
	
	/**
	 * 构建树形数据
	 * @param rootMenus
	 * @param childMenus
	 * @return
	 */
	private List<TreeNode> treeMenu(List<Menu> rootMenus,List<Menu> childMenus){
		TreeUtil treeUtil = new TreeUtil(rootMenus, childMenus);
		return treeUtil.getTreeNode();
	}
	
	/**
	 * 初始化根节点
	 * @param menuList
	 * @param user
	 * @return
	 */
	public List<Menu> initMenu(List<Menu> menuList,User user){
		List<Menu> showList = new ArrayList<Menu>();
		Collection<GrantedAuthority> authority=user.getAuthorities();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status",Constants.STATUS_DEFAULT);
		map.put("userId",user.getId());
		List<Role> roleList = roleBusiness.findListByUser(map);
		Role role = roleBusiness.findRoleByCode(Constants.ROLE_ADMIN_CODE);
		if(roleList.contains(role) || "admin".equals(user.getUsername())){
			showList=menuList;
		}else{
			for(int i=0;i<menuList.size();i++)
   	   		{
   				 boolean flag=false;
   			     for (GrantedAuthority attribute : authority) 
   			    {  
   				  	String key = attribute.getAuthority();
   				  	String code=menuList.get(i).getCode().trim();
		   	   		if(key.startsWith(code))
		   	   		{
		   	   			flag=true;
		   	   			break;
		   	   		}
   		        }  
   	   	   		if(flag)
   	   	   		{
   	   	   			showList.add(menuList.get(i));	
   	   	   		}
   	   			
   	   		}
		}
		return showList;
	}
	
	/**
	 * 初始化按钮
	 * @param childBtnList
	 * @param user
	 * @return
	 */
	private List<MenuBtn> initBtn(List<MenuBtn> childBtnList, User user) {
		List<MenuBtn> showList = new ArrayList<MenuBtn>();
		Collection<GrantedAuthority> authority=user.getAuthorities();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status",Constants.STATUS_DEFAULT);
		map.put("userId",user.getId());
		List<Role> roleList = roleBusiness.findListByUser(map);
		Role role = roleBusiness.findRoleByCode(Constants.ROLE_ADMIN_CODE);
		if(roleList.contains(role) || "admin".equals(user.getUsername())){
			showList=childBtnList;
		}else{
			for(int i=0;i<childBtnList.size();i++)
   	   		{
   				 boolean flag=false;
   			     for (GrantedAuthority attribute : authority) 
   			    {  
   				  	String key = attribute.getAuthority();
   				  	String code=childBtnList.get(i).getCode().trim();
		   	   		if(key.startsWith(code))
		   	   		{
		   	   			flag=true;
		   	   			break;
		   	   		}
   		        }  
   	   	   		if(flag)
   	   	   		{
   	   	   			showList.add(childBtnList.get(i));	
   	   	   		}
   	   			
   	   		}
		}
		return showList;
	}
	
	/**
	 * 构建树形数据
	 * @return
	 */
	private void buildData(List<Menu> childMenus,List<MenuBtn> childBtns,HttpServletRequest request){
		//能够访问的url列表
		List<String> accessUrls  = new ArrayList<String>();
		//菜单对应的按钮
		Map<String,List<String>> menuBtnMap = new HashMap<String,List<String>>(); 
		for(Menu menu: childMenus){
			//判断URL是否为空
			if(StringUtils.isNotBlank(menu.getUrl())){
				List<String> btnNames = new ArrayList<String>();
				for(MenuBtn btn  : childBtns){
					if(menu.getId().equals(btn.getMenuId())){
						btnNames.add(btn.getType());
						URLUtils.getBtnAccessUrls(menu.getUrl(), btn.getUrl(),accessUrls);
					}
				}
				menuBtnMap.put(menu.getUrl(), btnNames);
				URLUtils.getBtnAccessUrls(menu.getUrl(), menu.getActions(),accessUrls);
				accessUrls.add(menu.getUrl());
			}
		}
		SessionUtils.setAccessUrl(request, accessUrls);//设置可访问的URL
		SessionUtils.setMemuBtnMap(request, menuBtnMap); //设置可用的按钮
	}
}
