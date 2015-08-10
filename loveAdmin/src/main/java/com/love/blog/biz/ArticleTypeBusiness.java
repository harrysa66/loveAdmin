package com.love.blog.biz;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.love.blog.po.Article;
import com.love.blog.po.ArticleType;
import com.love.framework.common.Constants;
import com.love.framework.dao.jdbc.BaseDao;
import com.love.framework.dao.jdbc.Page;
import com.love.framework.exception.ApplicationRuntimeException;
import com.love.util.UUIDGenerator;

@Service
public class ArticleTypeBusiness {
	
	@Resource
	private ArticleBusiness articleBusiness;
	
	private BaseDao<ArticleType, String> articleTypeDao;

	@Resource
	private void init(SqlSessionFactory sqlSessionFactory) {
		articleTypeDao = new BaseDao<ArticleType, String>(sqlSessionFactory, ArticleType.class);
	}
	
	public Page<ArticleType> queryPage(Map<String, Object> map,Page<ArticleType> page){
		return articleTypeDao.pageQueryBy(map, page);
	}
	
	public ArticleType findById(String id) {
		return articleTypeDao.findById(id);
	}
	
	public ArticleType findByDisplay(String display) {
		return articleTypeDao.findByProperty("findByDisplay", display);
	}
	
	@Transactional
	public String save(ArticleType articleType) {
		if (StringUtils.isEmpty(articleType.getId())) {
			articleType.setId(UUIDGenerator.getUUID());
			try {
				articleType.setStatus(Constants.STATUS_DEFAULT);
				articleType.setIsvalid(Constants.ISVALIAD_HIDDEN);
				articleType.setIsshow(Constants.ISSHOW_HIDDEN);
				articleType.setCreateTime(new Date());
				articleTypeDao.insert(articleType);
				return Constants.ADD_SUCCESS;
			} catch (Exception e) {
				throw new ApplicationRuntimeException(Constants.ADD_ERROR, e);
			}
		}
		try {
			articleType.setModifyTime(new Date());
			articleTypeDao.update(articleType);
			return Constants.EDIT_SUCCESS;
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.EDIT_ERROR, e);
		}
	}
	
	@Transactional
	public void delete(String id) {
		try {
			ArticleType articleType = findById(id);
			articleType.setCode(articleType.getCode()+Constants.DELETE_CODE);
			articleTypeDao.update(articleType);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("status", Constants.STATUS_DELETED);
			articleTypeDao.updateObject("deleteById", map);
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.DELETE_ERROR, e);
		}
	}
	
	@Transactional
	public boolean removeArticleTypes(String[] ids) {
		boolean flag = true;
		for(String id : ids){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("typeId", id);
			List<Article> articleList = articleBusiness.findListByType(map);
			if(articleList.size() > 0){
				flag = false;
				continue;
			}
			delete(id);
		}
		return flag;
	}
	
	@Transactional
	public void toogle(String id, String isvalid) {
		try {
			if ((isvalid == Constants.ISVALIAD_SHOW)
					|| (Constants.ISVALIAD_SHOW.equals(isvalid))) {
				isvalid = Constants.ISVALIAD_HIDDEN;
			} else if ((isvalid == Constants.ISVALIAD_HIDDEN)
					|| (Constants.ISVALIAD_HIDDEN.equals(isvalid))) {
				isvalid = Constants.ISVALIAD_SHOW;
			}else {
				throw new Exception();
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("isvalid", isvalid);
			articleTypeDao.updateObject("runById", map);
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.DO_ERROR, e);
		}
	}
	
	@Transactional
	public void show(String id, String isshow) {
		try {
			if ((isshow == Constants.ISSHOW_SHOW)
					|| (Constants.ISSHOW_SHOW.equals(isshow))) {
				isshow = Constants.ISSHOW_HIDDEN;
			} else if ((isshow == Constants.ISSHOW_HIDDEN)
					|| (Constants.ISSHOW_HIDDEN.equals(isshow))) {
				isshow = Constants.ISSHOW_SHOW;
			}else {
				throw new Exception();
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("isshow", isshow);
			articleTypeDao.updateObject("showById", map);
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.DO_ERROR, e);
		}
	}
	
	@Transactional
	public boolean runArticles(String[] ids) {
		boolean flag = true;
		for(String id : ids){
			ArticleType articleType = findById(id);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("typeId", id);
			List<Article> articleList = articleBusiness.findListByType(map);
			if(articleList.size() > 0){
				flag = false;
				continue;
			}
			toogle(id,articleType.getIsvalid());
		}
		return flag;
	}
	
	@Transactional
	public void showArticles(String[] ids) {
		for(String id : ids){
			ArticleType articleType = findById(id);
			show(id,articleType.getIsshow());
		}
	}
	
	public ArticleType isRepeatCode(Map<String, String> map) {
		return this.articleTypeDao.findByMap("isRepeatCode", map);
	}

	public ArticleType isRepeatName(Map<String, String> map) {
		return this.articleTypeDao.findByMap("isRepeatName", map);
	}
	
	public ArticleType isRepeatDisplay(Map<String, String> map) {
		return this.articleTypeDao.findByMap("isRepeatDisplay", map);
	}
	
	public List<ArticleType> findListByMap(Map<String, Object> map){
		return articleTypeDao.findListByMap("findListByMap", map);
	}

	public List<ArticleType> findListByShow(Map<String, Object> map) {
		return articleTypeDao.findListByMap("findListByShow", map);
	}

}
