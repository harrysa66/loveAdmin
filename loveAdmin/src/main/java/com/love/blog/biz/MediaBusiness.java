package com.love.blog.biz;

import java.util.ArrayList;
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
import com.love.system.biz.AttachmentBusiness;
import com.love.system.po.Attachment;
import com.love.system.po.User;
import com.love.util.UUIDGenerator;

@Service
public class MediaBusiness {
	
	@Resource
	private AttachmentBusiness attachmentBusiness;
	
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
			Map<String,Object> searchKey = new HashMap<String,Object>();
			searchKey.put("entityId", id);
			Attachment oldAttach = attachmentBusiness.findAttachmentByEntity(searchKey);
			if(oldAttach != null){
				attachmentBusiness.deleteFile(oldAttach.getId());
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("status", Constants.STATUS_DELETED);
			mediaDao.updateObject("deleteById", map);
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.DELETE_ERROR, e);
		}
	}
	
	@Transactional
	public boolean removeMedias(String[] ids) {
		boolean flag = true;
		for(String id : ids){
			if(!isSelf(id)){
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
			mediaDao.updateObject("runById", map);
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.DO_ERROR, e);
		}
	}
	
	@Transactional
	public boolean runMedias(String[] ids) {
		boolean flag = true;
		for(String id : ids){
			Media media = findById(id);
			if(!isSelf(id)){
				flag = false;
				continue;
			}
			toogle(id, media.getIsvalid());
		}
		return flag;
	}
	
	public boolean isSelf(String id){
		boolean flag = true;
		Media media = findById(id);
		User user = SpringSecurityUtils.getCurrentUser();
		if(!media.getUserId().equals(user.getId()) && !user.getUsername().equals(Constants.USER_ADMIN_CODE)){
			flag = false;
		}
		return flag;
	}

	@Transactional
	public void fileUpload(String mediaId, Attachment attach) {
		User user = SpringSecurityUtils.getCurrentUser();
		Map<String,Object> searchKey = new HashMap<String,Object>();
		searchKey.put("entityId", mediaId);
		searchKey.put("entityType", attach.getEntityType());
		Attachment oldAttach = attachmentBusiness.findAttachmentByEntity(searchKey);
		if(oldAttach != null){
			attachmentBusiness.deleteFile(oldAttach.getId());
		}
		attach.setEntityId(mediaId);
		attach.setUploadTime(new Date());
		attach.setUploadUserId(user.getId());
		attach.setUploadUserName(user.getUsername());
		Attachment att = attachmentBusiness.upload(attach);
		Media media = new Media();
		media.setId(mediaId);
		media.setFileId(att.getId());
		mediaDao.update(media);
	}

	@Transactional
	public String setGroup(String groupId, String[] ids) {
		String message = "设置成功";
		Media media = null;
		for(String id : ids){
			if(!isSelf(id)){
				message = "除了非本人上传的文件以外，其他文件设置成功(管理员除外)!";
			}
			media = new Media();
			media.setId(id);
			media.setGroupId(groupId);
			mediaDao.update(media);
		}
		return message;
	}

	@Transactional
	public List<Attachment> fileUpload(List<Attachment> attachList) {
		List<Attachment> result = new ArrayList<Attachment>();
		Media media = null;
		User user = SpringSecurityUtils.getCurrentUser();
		for(Attachment attach: attachList){
			media = new Media();
			media.setId(UUIDGenerator.getUUID());
			media.setUserId(user.getId());
			media.setCreateTime(new Date());
			media.setStatus(Constants.STATUS_DEFAULT);
			media.setIsvalid(Constants.ISVALIAD_HIDDEN);
			
			Map<String,Object> searchKey = new HashMap<String,Object>();
			searchKey.put("entityId", media.getId());
			searchKey.put("entityType", attach.getEntityType());
			Attachment oldAttach = attachmentBusiness.findAttachmentByEntity(searchKey);
			if(oldAttach != null){
				attachmentBusiness.deleteFile(oldAttach.getId());
			}
			attach.setEntityId(media.getId());
			attach.setUploadTime(new Date());
			attach.setUploadUserId(user.getId());
			attach.setUploadUserName(user.getUsername());
			Attachment att = attachmentBusiness.upload(attach);
			media.setFileId(att.getId());
			mediaDao.insert(media);
			result.add(att);
			/*Media media = new Media();
			media.setId(mediaId);
			media.setFileId(att.getId());
			mediaDao.update(media);*/
		}
		
		
		return result;
	}
	
	public List<Media> findListByGroup(String groupId){
		return mediaDao.findListByProperty("findListByGroup", groupId);
	}

}
