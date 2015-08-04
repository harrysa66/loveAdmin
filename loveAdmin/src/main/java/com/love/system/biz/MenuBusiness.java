package com.love.system.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import com.love.framework.common.Constants;
import com.love.framework.dao.jdbc.BaseDao;
import com.love.system.po.Menu;

@Service
public class MenuBusiness {
	
	private BaseDao<Menu, String> menuDao;
	
	@Resource
	private void init(SqlSessionFactory sqlSessionFactory) {
		menuDao = new BaseDao<Menu, String>(sqlSessionFactory, Menu.class);
	}

	public List<Menu> selectListByNull(Map<String, Object> parements) {
		List<Menu> menuList = new ArrayList<Menu>();
		List<Menu> menus = menuDao.findListByMap("selectListByNull", parements);
		for(Menu menu : menus){
			menu.setMenuFullName(menu.getName());
			menuList.add(menu);
		}
		return menuList;
	}

	public List<Menu> selectListByNotNull(Map<String, Object> parements) {
		List<Menu> menuList = new ArrayList<Menu>();
		List<Menu> menus = menuDao.findListByMap("selectListByNotNull", parements);
		for(Menu menu : menus){
			menu.setMenuFullName(getFullParentName(menu.getId().toString()));
			menuList.add(menu);
		}
		return menuList;
	}

	public void updateAuth(Integer menuId, String authId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", menuId);
		map.put("authId", authId);
		menuDao.updateObject("updateAuth", map);
	}

	public Menu findByAuthId(String authId) {
		return menuDao.findByProperty("findByAuthId", authId);
	}
	
	public Menu findById(String id){
		return menuDao.findById(id);
	}
	
	public String getFullParentName(String menuId){
		String menuName = null;
		while(menuId != ""){
			Menu tempMenu = findById(menuId);
			menuName = menuName == null ? tempMenu.getName() : String.format("%s / %s", tempMenu.getName(), menuName);
			if(null == tempMenu.getParentId() || "".equals(tempMenu.getParentId())){
				menuId = "";
			}else{
				menuId = tempMenu.getParentId().toString();
			}
		}
		return menuName;
	}

	/**
	 * 通过编号获得菜单及其子菜单
	 * @param menuCode
	 * @return
	 */
	public List<Menu> findListByCode(String menuCode) {
		List<Menu> menuList = new ArrayList<Menu>();
		Menu menu = findByCode(menuCode);
		if(menu != null){
			menuList.add(menu);
			List<Menu> childMenus = findListByParent(menu.getId());
			if(childMenus.size() > 0){
				menuList.addAll(childMenus);
			}
		}
		return menuList;
	}

	private List<Menu> findListByParent(Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentId", id);
		map.put("status", Constants.STATUS_DEFAULT);
		map.put("isvalid", Constants.ISVALIAD_SHOW);
		return menuDao.findListByMap("findListByParent", map);
	}

	private Menu findByCode(String code) {
		return menuDao.findByProperty("findByCode", code);
	}

}
