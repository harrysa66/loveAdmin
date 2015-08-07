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
import com.love.framework.common.Constants;
import com.love.framework.dao.jdbc.BaseDao;
import com.love.framework.dao.jdbc.Page;
import com.love.framework.exception.ApplicationRuntimeException;
import com.love.framework.security.SpringSecurityUtils;
import com.love.system.biz.AttachmentBusiness;
import com.love.system.po.Attachment;
import com.love.system.po.User;
import com.love.util.UUIDGenerator;

@Service
public class ArticleBusiness {
	
	@Resource
	private AttachmentBusiness attachmentBusiness;
	
	private BaseDao<Article, String> articleDao;

	@Resource
	private void init(SqlSessionFactory sqlSessionFactory) {
		articleDao = new BaseDao<Article, String>(sqlSessionFactory, Article.class);
	}
	
	public Page<Article> queryPage(Map<String, Object> map,Page<Article> page){
		return articleDao.pageQueryBy(map, page);
	}
	
	public Article findById(String id) {
		return articleDao.findById(id);
	}
	
	public List<Article> findListByType(Map<String, Object> map){
		return articleDao.findListByMap("findListByType", map);
	}
	
	@Transactional
	public String save(Article article) {
		if (StringUtils.isEmpty(article.getId())) {
			article.setId(UUIDGenerator.getUUID());
			try {
				article.setStatus(Constants.STATUS_DEFAULT);
				article.setIsvalid(Constants.ISVALIAD_HIDDEN);
				article.setUserId(SpringSecurityUtils.getCurrentUser().getId());
				article.setCreateTime(new Date());
				articleDao.insert(article);
				return Constants.ADD_SUCCESS;
			} catch (Exception e) {
				throw new ApplicationRuntimeException(Constants.ADD_ERROR, e);
			}
		}
		try {
			article.setModifyTime(new Date());
			articleDao.update(article);
			return Constants.EDIT_SUCCESS;
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.EDIT_ERROR, e);
		}
	}
	
	@Transactional
	public void delete(String id) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("status", Constants.STATUS_DELETED);
			articleDao.updateObject("deleteById", map);
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.DELETE_ERROR, e);
		}
	}
	
	@Transactional
	public boolean removeArticles(String[] ids) {
		boolean flag = true;
		for(String id : ids){
			Article article = findById(id);
			User user = SpringSecurityUtils.getCurrentUser();
			if(!article.getUserId().equals(user.getId()) && !user.getUsername().equals(Constants.USER_ADMIN_CODE)){
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
			articleDao.updateObject("runById", map);
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.DO_ERROR, e);
		}
	}
	
	@Transactional
	public boolean runArticles(String[] ids) {
		boolean flag = true;
		for(String id : ids){
			Article article = findById(id);
			User user = SpringSecurityUtils.getCurrentUser();
			if(!article.getUserId().equals(user.getId()) && !user.getUsername().equals(Constants.USER_ADMIN_CODE)){
				flag = false;
				continue;
			}
			toogle(id, article.getIsvalid());
		}
		return flag;
	}
	
	public Article isRepeatTitle(Map<String, String> map) {
		return this.articleDao.findByMap("isRepeatTitle", map);
	}
	
	public boolean isSelf(String id){
		boolean flag = true;
		Article article = findById(id);
		User user = SpringSecurityUtils.getCurrentUser();
		if(!article.getUserId().equals(user.getId()) && !user.getUsername().equals(Constants.USER_ADMIN_CODE)){
			flag = false;
		}
		return flag;
	}
	
	@Transactional
	public void coverUpload(String articleId, Attachment attach) {
		User user = SpringSecurityUtils.getCurrentUser();
		Map<String,Object> searchKey = new HashMap<String,Object>();
		searchKey.put("entityId", articleId);
		searchKey.put("entityType", attach.getEntityType());
		Attachment oldAttach = attachmentBusiness.findAttachmentByEntity(searchKey);
		if(oldAttach != null){
			attachmentBusiness.deleteFile(oldAttach.getId());
		}
		attach.setEntityId(articleId);
		attach.setUploadTime(new Date());
		attach.setUploadUserId(user.getId());
		attach.setUploadUserName(user.getUsername());
		Attachment att = attachmentBusiness.upload(attach);
		Article article = new Article();
		article.setId(articleId);
		article.setFileId(att.getId());
		articleDao.update(article);
	}

}
