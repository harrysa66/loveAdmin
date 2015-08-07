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

import com.love.blog.po.MediaGroup;
import com.love.blog.po.MediaType;
import com.love.framework.common.Constants;
import com.love.framework.dao.jdbc.BaseDao;
import com.love.framework.dao.jdbc.Page;
import com.love.framework.exception.ApplicationRuntimeException;
import com.love.util.UUIDGenerator;

@Service
public class MediaTypeBusiness {
	
	@Resource
	private MediaGroupBusiness mediaGroupBusiness;
	
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
				mediaType.setIsshow(Constants.ISSHOW_HIDDEN);
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
			List<MediaGroup> mediaList = mediaGroupBusiness.findListByType(map);
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
			mediaTypeDao.updateObject("showById", map);
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.DO_ERROR, e);
		}
	}
	
	@Transactional
	public boolean runMediaTypes(String[] ids) {
		boolean flag = true;
		for(String id : ids){
			MediaType mediaType = findById(id);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("typeId", id);
			List<MediaGroup> mediaList = mediaGroupBusiness.findListByType(map);
			if(mediaList.size() > 0){
				flag = false;
				continue;
			}
			toogle(id,mediaType.getIsvalid());
		}
		return flag;
	}
	
	@Transactional
	public void showMediaType(String[] ids) {
		for(String id : ids){
			MediaType mediaType = findById(id);
			show(id,mediaType.getIsshow());
		}
	}
	
	public MediaType isRepeatCode(Map<String, String> map) {
		return this.mediaTypeDao.findByMap("isRepeatCode", map);
	}

	public MediaType isRepeatName(Map<String, String> map) {
		return this.mediaTypeDao.findByMap("isRepeatName", map);
	}
	
	public MediaType isRepeatDisplay(Map<String, String> map) {
		return this.mediaTypeDao.findByMap("isRepeatDisplay", map);
	}
	
	public List<MediaType> findListByMap(Map<String, Object> map){
		return mediaTypeDao.findListByMap("findListByMap", map);
	}

	public List<MediaType> findListByShow(Map<String, Object> map) {
		return mediaTypeDao.findListByMap("findListByShow", map);
	}

}
