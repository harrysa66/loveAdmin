package com.love.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.love.framework.common.Constants;
import com.love.framework.common.TreeNode;
import com.love.framework.controller.BaseController;
import com.love.system.biz.MenuBtnBusiness;
import com.love.system.biz.MenuBusiness;
import com.love.system.po.Menu;
import com.love.system.po.MenuBtn;
import com.love.util.HtmlUtil;
import com.love.util.TreeUtil;

@Controller
@RequestMapping("system/menu")
public class MenuController extends BaseController{
	
	@Resource
	private MenuBusiness menuBusiness;
	
	@Resource
	private MenuBtnBusiness menuBtnBusiness;
	
	@RequestMapping("/getMenuTree")
	public void getMenuTree(Integer id,HttpServletResponse response) throws Exception{
		List<TreeNode> menuTree = treeMenu();
		HtmlUtil.writerJson(response, menuTree);
	}
	
	/**
	 * 构建树形菜单
	 * @return
	 */
	public List<TreeNode> treeMenu(){
		Map<String,Object> parements=new HashMap<String,Object>();
   		parements.put("status",Constants.STATUS_DEFAULT);
   		parements.put("isvalid",Constants.ISVALIAD_SHOW);
   		parements.put("type",Constants.MENU_ADMIN);
		List<Menu> rootMenus = menuBusiness.selectListByNull(parements);
		List<Menu> childMenus = menuBusiness.selectListByNotNull(parements);
		List<MenuBtn> childBtns = menuBtnBusiness.selectList(parements);
		TreeUtil util = new TreeUtil(rootMenus,childMenus,childBtns);
		return util.getTreeNode();
	}

}
