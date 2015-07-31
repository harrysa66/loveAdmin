package com.love.blog.biz;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.love.blog.po.Visitor;
import com.love.framework.dao.jdbc.BaseDao;
import com.love.framework.dao.jdbc.Page;

@Service
public class VisitorBusiness {
	
	private BaseDao<Visitor, String> visitorDao;

	@Resource
	private void init(SqlSessionFactory sqlSessionFactory) {
		visitorDao = new BaseDao<Visitor, String>(sqlSessionFactory, Visitor.class);
	}
	
	public Page<Visitor> queryPage(Map<String, Object> map,Page<Visitor> page){
		return visitorDao.pageQueryBy(map, page);
	}
	
	public Visitor findById(String id) {
		return visitorDao.findById(id);
	}
	
	public List<Visitor> findListByStatus(String status){
		return visitorDao.findListByProperty("findListByStatus", status);
	}
	
	@Transactional
	public void updateByStatus(Visitor visitor){
		visitorDao.update("updateByStatus", visitor);
	}
	
}
