package com.love.system.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import com.love.framework.dao.jdbc.BaseDao;
import com.love.system.po.Menu;
import com.love.system.po.MenuBtn;

@Service
public class MenuBtnBusiness {
	
	@Resource
	private MenuBusiness menuBusiness;
	
	private BaseDao<MenuBtn, String> menuBtnDao;
	
	@Resource
	private void init(SqlSessionFactory sqlSessionFactory) {
		menuBtnDao = new BaseDao<MenuBtn, String>(sqlSessionFactory, MenuBtn.class);
	}

	public List<MenuBtn> selectList(Map<String, Object> parements) {
		List<MenuBtn> menuBtnList = new ArrayList<MenuBtn>();
		List<MenuBtn> menuBtns = menuBtnDao.findListByMap("selectListAll", parements);
		for(MenuBtn menuBtn : menuBtns){
			menuBtn.setMenuFullName(getFullParentName(menuBtn.getId().toString()));
			menuBtnList.add(menuBtn);
		}
		
		return menuBtnList;
	}

	public void updateAuth(Integer menuId, String authId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", menuId);
		map.put("authId", authId);
		menuBtnDao.updateObject("updateAuth", map);
	}
	
	public MenuBtn findByAuthId(String authId){
		return menuBtnDao.findByProperty("findByAuthId", authId);
	}
	
	public MenuBtn findById(String id){
		return menuBtnDao.findById(id);
	}
	
	public String getFullParentName(String btnId){
		String menuId = "";
		String menuName = null;
		MenuBtn menuBtn = findById(btnId);
		if(null != menuBtn){
			menuId = menuBtn.getMenuId().toString();
			menuName = menuBtn.getName();
		}
		while(menuId != ""){
			Menu tempMenu = menuBusiness.findById(menuId);
			menuName = menuName == null ? tempMenu.getName() : String.format("%s / %s", tempMenu.getName(), menuName);
			if(null == tempMenu.getParentId() || "".equals(tempMenu.getParentId())){
				menuId = "";
			}else{
				menuId = tempMenu.getParentId().toString();
			}
		}
		return menuName;
	}

}
