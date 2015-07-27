package com.love.system.biz;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

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
		
		return null;
	}

	public List<Menu> selectListByNotNull(Map<String, Object> parements) {
		
		return null;
	}
	
	

}
