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
import com.love.blog.po.MediaType;
import com.love.framework.common.Constants;
import com.love.framework.dao.jdbc.BaseDao;
import com.love.framework.dao.jdbc.Page;
import com.love.framework.exception.ApplicationRuntimeException;
import com.love.util.UUIDGenerator;

@Service
public class MediaTypeBusiness {
	
	@Resource
	private MediaBusiness mediaBusiness;
	
	private BaseDao<MediaType, String> mediaTypeDao;

	@Resource
	private void init(SqlSessionFactory sqlSessionFactory) {
		mediaTypeDao = new BaseDao<MediaType, String>(sqlSessionFactory, MediaType.class);
	}
	
	public Page<MediaType> queryPage(Map<String, Object> map,Page<MediaType> page){
		return mediaTypeDao.pageQueryBy(map, page);
	}
	
	public MediaType findById(String id) {
		return mediaTypeDao.findById(id);
	}
	
	@Transactional
	public String save(MediaType mediaType) {
		if (StringUtils.isEmpty(mediaType.getId())) {
			mediaType.setId(UUIDGenerator.getUUID());
			try {
				mediaType.setStatus(Constants.STATUS_DEFAULT);
				mediaType.setIsvalid(Constants.ISVALIAD_HIDDEN);
				mediaType.setCreateTime(new Date());
				mediaTypeDao.insert(mediaType);
				return Constants.ADD_SUCCESS;
			} catch (Exception e) {
				throw new ApplicationRuntimeException(Constants.ADD_ERROR, e);
			}
		}
		try {
			mediaType.setModifyTime(new Date());
			mediaTypeDao.update(mediaType);
			return Constants.EDIT_SUCCESS;
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.EDIT_ERROR, e);
		}
	}
	
	@Transactional
	public void delete(String id) {
		try {
			MediaType mediaType = findById(id);
			mediaType.setCode(mediaType.getCode()+Constants.DELETE_CODE);
			mediaTypeDao.update(mediaType);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("status", Constants.STATUS_DELETED);
			mediaTypeDao.updateObject("deleteById", map);
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.DELETE_ERROR, e);
		}
	}
	
	@Transactional
	public boolean removeMediaTypeTypes(String[] ids) {
		boolean flag = true;
		for(String id : ids){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("typeId", id);
			List<Media> mediaList = mediaBusiness.findListByType(map);
			if(mediaList.size() > 0){
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
			mediaTypeDao.updateObject("runById", map);
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.DO_ERROR, e);
		}
	}
	
	@Transactional
	public boolean runAuths(String[] ids) {
		boolean flag = true;
		for(String id : ids){
			MediaType mediaType = findById(id);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("typeId", id);
			List<Media> mediaList = mediaBusiness.findListByType(map);
			if(mediaList.size() > 0){
				flag = false;
				continue;
			}
			toogle(id,mediaType.getIsvalid());
		}
		return flag;
	}
	
	public MediaType isRepeatCode(Map<String, String> map) {
		return this.mediaTypeDao.findByMap("isRepeatCode", map);
	}

	public MediaType isRepeatName(Map<String, String> map) {
		return this.mediaTypeDao.findByMap("isRepeatName", map);
	}
	
	public List<MediaType> findListById(Map<String, Object> map){
		return mediaTypeDao.findListByMap("findListByMap", map);
	}

}
