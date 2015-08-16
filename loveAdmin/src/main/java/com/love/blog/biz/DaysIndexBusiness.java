package com.love.blog.biz;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.love.blog.po.DaysIndex;
import com.love.framework.common.Constants;
import com.love.framework.dao.jdbc.BaseDao;
import com.love.framework.exception.ApplicationRuntimeException;
import com.love.framework.security.SpringSecurityUtils;
import com.love.system.biz.AttachmentBusiness;
import com.love.system.po.Attachment;
import com.love.system.po.User;
import com.love.util.UUIDGenerator;

@Service
public class DaysIndexBusiness {
	
	static Logger log = Logger.getLogger(DaysIndexBusiness.class.getName());
	
	@Resource
	private AttachmentBusiness attachmentBusiness;
	
	private BaseDao<DaysIndex, String> daysIndexDao;

	@Resource
	private void init(SqlSessionFactory sqlSessionFactory) {
		daysIndexDao = new BaseDao<DaysIndex, String>(sqlSessionFactory, DaysIndex.class);
	}
	
	@Transactional
	public String save(DaysIndex daysIndex, Attachment boyAtt, Attachment girlAtt) {
		if (StringUtils.isEmpty(daysIndex.getId())) {
			daysIndex.setId(UUIDGenerator.getUUID());
			try {
				daysIndexDao.insert(daysIndex);
				if(boyAtt != null && boyAtt.getSize() != null && !boyAtt.getSize().equals("") && !boyAtt.getSize().equals("0")){
					fileUpload(daysIndex.getId(), boyAtt);
				}
				if(girlAtt != null && girlAtt.getSize() != null && !girlAtt.getSize().equals("") && !girlAtt.getSize().equals("0")){
					fileUpload(daysIndex.getId(), girlAtt);
				}
				return Constants.ADD_SUCCESS;
			} catch (Exception e) {
				throw new ApplicationRuntimeException(Constants.ADD_ERROR, e);
			}
		}
		try {
			daysIndexDao.update(daysIndex);
			if(boyAtt != null && boyAtt.getSize() != null && !boyAtt.getSize().equals("") && !boyAtt.getSize().equals("0")){
				fileUpload(daysIndex.getId(), boyAtt);
			}
			if(girlAtt != null && girlAtt.getSize() != null && !girlAtt.getSize().equals("") && !girlAtt.getSize().equals("0")){
				fileUpload(daysIndex.getId(), girlAtt);
			}
			return Constants.EDIT_SUCCESS;
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.EDIT_ERROR, e);
		}
	}
	
	public DaysIndex findById(String id) {
		DaysIndex daysIndex = daysIndexDao.findById(id);
		Attachment boyAtt = attachmentBusiness.getById(daysIndex.getBoyId());
		Attachment girlAtt = attachmentBusiness.getById(daysIndex.getGirlId());
		if(boyAtt != null){
			daysIndex.setBoyUrl(boyAtt.getUrl());
		}
		if(girlAtt != null){
			daysIndex.setGirlUrl(girlAtt.getUrl());
		}
		return daysIndex;
	}

	@Transactional
	public void fileUpload(String daysIndexId,Attachment attach){
		if(attach.getContentType().indexOf(Constants.IMAGE_TYPE) >= 0){
			User user = SpringSecurityUtils.getCurrentUser();
			Map<String,Object> searchKey = new HashMap<String,Object>();
			searchKey.put("entityId", daysIndexId);
			searchKey.put("entityType", attach.getEntityType());
			Attachment oldAttach = attachmentBusiness.findAttachmentByEntity(searchKey);
			if(oldAttach != null){
				attachmentBusiness.deleteFile(oldAttach.getId());
			}
			attach.setEntityId(daysIndexId);
			attach.setUploadTime(new Date());
			attach.setUploadUserId(user.getId());
			attach.setUploadUserName(user.getUsername());
			Attachment att = attachmentBusiness.upload(attach);
			DaysIndex daysIndex = new DaysIndex();
			daysIndex.setId(daysIndexId);
			if(attach.getEntityType().equals("boy")){
				daysIndex.setBoyId(att.getId());
			}
			if(attach.getEntityType().equals("girl")){
				daysIndex.setGirlId(att.getId());
			}
			daysIndexDao.update(daysIndex);
		}
	}

	public boolean isAllow(Attachment attach) {
		boolean flag = true;
		if(attach != null){
			BufferedImage img = null;
			try {
				img = ImageIO.read(attach.getIn());
			} catch (IOException e1) {
				log.error(e1.toString());
				log.error("打开文件失败   ");
			}
			if(img != null){
				if(img.getWidth() != 148 && img.getHeight() != 148){
					flag = false;
				}
			}
		}
		return flag;
	}

}
