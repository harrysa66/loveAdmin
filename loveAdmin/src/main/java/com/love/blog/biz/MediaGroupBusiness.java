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
import com.love.blog.po.MediaGroup;
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
public class MediaGroupBusiness {
	
	@Resource
	private AttachmentBusiness attachmentBusiness;
	
	@Resource
	private MediaBusiness mediaBusiness;
	
	private BaseDao<MediaGroup, String> mediaGroupDao;

	@Resource
	private void init(SqlSessionFactory sqlSessionFactory) {
		mediaGroupDao = new BaseDao<MediaGroup, String>(sqlSessionFactory, MediaGroup.class);
	}
	
	public Page<MediaGroup> queryPage(Map<String, Object> map,Page<MediaGroup> page){
		return mediaGroupDao.pageQueryBy(map, page);
	}
	
	public MediaGroup findById(String id) {
		return mediaGroupDao.findById(id);
	}
	
	public List<MediaGroup> findListByType(Map<String, Object> map){
		return mediaGroupDao.findListByMap("findListByType", map);
	}
	
	@Transactional
	public String save(MediaGroup mediaGroup) {
		if (StringUtils.isEmpty(mediaGroup.getId())) {
			mediaGroup.setId(UUIDGenerator.getUUID());
			try {
				mediaGroup.setStatus(Constants.STATUS_DEFAULT);
				mediaGroup.setIsvalid(Constants.ISVALIAD_HIDDEN);
				mediaGroup.setUserId(SpringSecurityUtils.getCurrentUser().getId());
				mediaGroup.setCreateTime(new Date());
				mediaGroupDao.insert(mediaGroup);
				return Constants.ADD_SUCCESS;
			} catch (Exception e) {
				throw new ApplicationRuntimeException(Constants.ADD_ERROR, e);
			}
		}
		try {
			mediaGroup.setModifyTime(new Date());
			mediaGroupDao.update(mediaGroup);
			return Constants.EDIT_SUCCESS;
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.EDIT_ERROR, e);
		}
	}
	
	@Transactional
	public void delete(String id) {
		try {
			Map<String,Object> searchKey = new HashMap<String,Object>();
			searchKey.put("entityId", id);
			Attachment oldAttach = attachmentBusiness.findAttachmentByEntity(searchKey);
			if(oldAttach != null){
				attachmentBusiness.deleteFile(oldAttach.getId());
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("status", Constants.STATUS_DELETED);
			mediaGroupDao.updateObject("deleteById", map);
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.DELETE_ERROR, e);
		}
	}
	
	@Transactional
	public boolean removeMediaGroups(String[] ids) {
		boolean flag = true;
		for(String id : ids){
			if(!isSelf(id)){
				flag = false;
				continue;
			}
			List<Media> mediaList = mediaBusiness.findListByGroup(id);
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
			mediaGroupDao.updateObject("runById", map);
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.DO_ERROR, e);
		}
	}
	
	@Transactional
	public boolean runMediaGroups(String[] ids) {
		boolean flag = true;
		for(String id : ids){
			MediaGroup mediaGroup = findById(id);
			if(!isSelf(id)){
				flag = false;
				continue;
			}
			toogle(id, mediaGroup.getIsvalid());
		}
		return flag;
	}
	
	public boolean isSelf(String id){
		boolean flag = true;
		MediaGroup mediaGroup = findById(id);
		User user = SpringSecurityUtils.getCurrentUser();
		if(!mediaGroup.getUserId().equals(user.getId()) && !user.getUsername().equals(Constants.USER_ADMIN_CODE)){
			flag = false;
		}
		return flag;
	}

	@Transactional
	public void coverUpload(String groupId, Attachment attach) {
		User user = SpringSecurityUtils.getCurrentUser();
		Map<String,Object> searchKey = new HashMap<String,Object>();
		searchKey.put("entityId", groupId);
		searchKey.put("entityType", attach.getEntityType());
		Attachment oldAttach = attachmentBusiness.findAttachmentByEntity(searchKey);
		if(oldAttach != null){
			attachmentBusiness.deleteFile(oldAttach.getId());
		}
		attach.setEntityId(groupId);
		attach.setUploadTime(new Date());
		attach.setUploadUserId(user.getId());
		attach.setUploadUserName(user.getUsername());
		Attachment att = attachmentBusiness.upload(attach);
		MediaGroup mediaGroup = new MediaGroup();
		mediaGroup.setId(groupId);
		mediaGroup.setFileId(att.getId());
		mediaGroupDao.update(mediaGroup);
	}

	public List<MediaGroup> findListByMap(Map<String, Object> map) {
		return mediaGroupDao.findListByMap("findListByMap", map);
	}

}
