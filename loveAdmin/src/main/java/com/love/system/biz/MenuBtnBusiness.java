package com.love.system.biz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import com.love.framework.dao.jdbc.BaseDao;
import com.love.system.po.MenuBtn;

@Service
public class MenuBtnBusiness {
	
	private BaseDao<MenuBtn, String> menuBtnDao;
	
	@Resource
	private void init(SqlSessionFactory sqlSessionFactory) {
		menuBtnDao = new BaseDao<MenuBtn, String>(sqlSessionFactory, MenuBtn.class);
	}

	public List<MenuBtn> selectList(Map<String, Object> parements) {
		return menuBtnDao.findListByMap("selectListAll", parements);
	}

	public void updateAuth(Integer menuId, String authId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", menuId);
		map.put("authId", authId);
		menuBtnDao.updateObject("updateAuth", map);
	}

}
