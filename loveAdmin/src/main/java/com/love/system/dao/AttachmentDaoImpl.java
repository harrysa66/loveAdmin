package com.love.system.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.love.framework.dao.jdbc.Page;
import com.love.framework.exception.ApplicationRuntimeException;
import com.love.system.po.Attachment;

@Repository
public class AttachmentDaoImpl extends SqlSessionDaoSupport implements IAttachmentDao{
	
	@Resource
	public void init(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	@Override
	public void insert(Attachment attachment){
			getSqlSession().insert("Attachment.insert", attachment);

	}

	@Override
	public Attachment findAttachmentById(String id) {
		return getSqlSession().selectOne("Attachment.findAttachmentById", id);
	}

	@Override
	public void deleteAttachmentById(String id) {
		 getSqlSession().delete("Attachment.deleteAttachmentById", id);
		
	}

	@Override
	public void updateAttachmentById(Map<String, Object> map){
		 getSqlSession().update("Attachment.updateAttachmentById", map);
	}

	@Override
	public Attachment findPathById(Map<String,Object> map) throws ApplicationRuntimeException {
		return  getSqlSession().selectOne("Attachment.findPathByEntityId", map);
	} 
	@Override
	public List<Attachment> findAttachmentByEntityId(String id) throws ApplicationRuntimeException {
		return  getSqlSession().selectList("Attachment.findAttachmentByEntityId", id);
	}
	@Override
	public Integer findAttachmentCount(Map<String, Object> map) {
		return getSqlSession().selectOne("Attachment.queryPageCount",map);
	}
	@Override
	public Page<Attachment> findAttachmentByCustomerId(Map<String, Object> map,
			Page<Attachment> page) {
		page.setTotalCount(Long.valueOf(findAttachmentCount(map).toString()));
		List<Attachment> list=getSqlSession().selectList("Attachment.queryPage", map, new RowBounds(page.pageSize*(page.pageNumber-1), page.pageSize));
		page.setResult(list);
		return page; 
	}
}