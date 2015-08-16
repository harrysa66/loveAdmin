package com.love.blog.biz;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.love.blog.po.IndexPage;
import com.love.framework.common.Constants;
import com.love.framework.dao.jdbc.BaseDao;
import com.love.framework.exception.ApplicationRuntimeException;
import com.love.util.UUIDGenerator;

@Service
public class IndexPageBusiness {
	
	private BaseDao<IndexPage, String> indexPageDao;

	@Resource
	private void init(SqlSessionFactory sqlSessionFactory) {
		indexPageDao = new BaseDao<IndexPage, String>(sqlSessionFactory, IndexPage.class);
	}
	
	@Transactional
	public String save(IndexPage indexPage) {
		if (StringUtils.isEmpty(indexPage.getId())) {
			indexPage.setId(UUIDGenerator.getUUID());
			try {
				indexPageDao.insert(indexPage);
				return Constants.ADD_SUCCESS;
			} catch (Exception e) {
				throw new ApplicationRuntimeException(Constants.ADD_ERROR, e);
			}
		}
		try {
			indexPageDao.update(indexPage);
			return Constants.EDIT_SUCCESS;
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.EDIT_ERROR, e);
		}
	}
	
	public IndexPage findById(String id) {
		return indexPageDao.findById(id);
	}
	
}
