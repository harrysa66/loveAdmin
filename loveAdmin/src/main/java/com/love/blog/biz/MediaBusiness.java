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

import com.love.blog.po.Media;
import com.love.framework.common.Constants;
import com.love.framework.dao.jdbc.BaseDao;
import com.love.framework.dao.jdbc.Page;
import com.love.framework.exception.ApplicationRuntimeException;
import com.love.framework.security.SpringSecurityUtils;
import com.love.util.UUIDGenerator;

@Service
public class MediaBusiness {
	
	private BaseDao<Media, String> mediaDao;

	@Resource
	private void init(SqlSessionFactory sqlSessionFactory) {
		mediaDao = new BaseDao<Media, String>(sqlSessionFactory, Media.class);
	}
	
	public Page<Media> queryPage(Map<String, Object> map,Page<Media> page){
		return mediaDao.pageQueryBy(map, page);
	}
	
	public Media findById(String id) {
		return mediaDao.findById(id);
	}
	
	public List<Media> findListByType(Map<String, Object> map){
		return mediaDao.findListByMap("findListByType", map);
	}
	
	@Transactional
	public String save(Media media) {
		if (StringUtils.isEmpty(media.getId())) {
			media.setId(UUIDGenerator.getUUID());
			try {
				media.setStatus(Constants.STATUS_DEFAULT);
				media.setIsvalid(Constants.ISVALIAD_HIDDEN);
				media.setCreateTime(new Date());
				mediaDao.insert(media);
				return Constants.ADD_SUCCESS;
			} catch (Exception e) {
				throw new ApplicationRuntimeException(Constants.ADD_ERROR, e);
			}
		}
		try {
			media.setModifyTime(new Date());
			mediaDao.update(media);
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
			mediaDao.updateObject("deleteById", map);
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.DELETE_ERROR, e);
		}
	}
	
	/*@Transactional
	public boolean removeMedias(String[] ids) {
		boolean flag = true;
		for(String id : ids){
			Media media = findById(id);
			User user = SpringSecurityUtils.getCurrentUser();
			if(!article.getUserId().equals(user.getId()) && !user.getUsername().equals(Constants.USER_ADMIN_CODE)){
				flag = false;
				continue;
			}
			delete(id);
		}
		return flag;
	}*/
	
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
			mediaDao.updateObject("runById", map);
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.DO_ERROR, e);
		}
	}
	
	/*@Transactional
	public boolean runArticles(String[] ids) {
		boolean flag = true;
		for(String id : ids){
			Media media = findById(id);
			User user = SpringSecurityUtils.getCurrentUser();
			if(!article.getUserId().equals(user.getId()) && !user.getUsername().equals(Constants.USER_ADMIN_CODE)){
				flag = false;
				continue;
			}
			toogle(id, media.getIsvalid());
		}
		return flag;
	}*/

}
