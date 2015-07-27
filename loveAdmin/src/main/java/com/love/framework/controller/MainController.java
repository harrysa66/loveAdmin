package com.love.framework.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.love.framework.common.Constants;
import com.love.framework.common.TreeNode;
import com.love.framework.security.SpringSecurityUtils;
import com.love.system.biz.MenuBusiness;
import com.love.system.biz.RoleBusiness;
import com.love.system.po.Menu;
import com.love.system.po.Role;
import com.love.system.po.User;
import com.love.util.TreeUtil;

@Controller
public class MainController extends BaseController{
	private final static Logger log= Logger.getLogger(MainController.class);
	
	@Resource
	private MenuBusiness menuBusiness;
	
	@Resource
	private RoleBusiness roleBusiness;
	
	@RequestMapping("/login.s")
	public ModelAndView login(){
		Map<String,Object>  context = getRootMap();
		return forword("login",context); 
	}
	
	@RequestMapping("/timeout.s")
	public void timeout(){
		
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
		List<Menu> rootMenus = initMenu(rootMenuList, user);
		List<Menu> childMenus = initMenu(childMenuList, user);
		context.put("user", user);
		context.put("userIp", userIp);
		context.put("menuList", treeMenu(rootMenus, childMenus));
		return forword("index",context); 
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
	
	public List<Menu> initChildMenu(){
		
		
		
		return null;
	}

}
