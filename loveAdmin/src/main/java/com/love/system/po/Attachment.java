package com.love.system.po;

import java.io.InputStream;
import java.util.Date;

import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

@Alias("attachment")
@Component
public class Attachment {
	
	private String id;
	private String url;
	private String fileName;
	private String savePath;
	private String saveName;
	private String contentType;
	private String entityType;
	private String entityId;
	private String uploadUserId;
	private String uploadUserName;
	private Date uploadTime;
	private String size;
	private InputStream in;
	
private byte[] byt ;
	
	public byte[] getByt() {
		return byt;
	}

	public void setByt(byte[] byt) {
		this.byt = byt;
	}
	
	public InputStream getIn() {
		return in;
	}

	public void setIn(InputStream in) {
		this.in = in;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the savedPath
	 */
	public String getSavePath() {
		return savePath;
	}

	/**
	 * @param savedPath the savedPath to set
	 */
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	/**
	 * @return the savedName
	 */
	public String getSaveName() {
		return saveName;
	}

	/**
	 * @param savedName the savedName to set
	 */
	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

	/**
	 * @return the entityType
	 */
	public String getEntityType() {
		return entityType;
	}

	/**
	 * @param entityType the entityType to set
	 */
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	/**
	 * @return the entityId
	 */
	public String getEntityId() {
		return entityId;
	}

	/**
	 * @param entityId the entityId to set
	 */
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	/**
	 * @return the uploadUserId
	 */
	public String getUploadUserId() {
		return uploadUserId;
	}

	/**
	 * @param uploadUserId the uploadUserId to set
	 */
	public void setUploadUserId(String uploadUserId) {
		this.uploadUserId = uploadUserId;
	}

	/**
	 * @return the uploadUserName
	 */
	public String getUploadUserName() {
		return uploadUserName;
	}

	/**
	 * @param uploadUserName the uploadUserName to set
	 */
	public void setUploadUserName(String uploadUserName) {
		this.uploadUserName = uploadUserName;
	}


	/**
	 * @return the uploadTime
	 */
	public Date getUploadTime() {
		return uploadTime;
	}

	/**
	 * @param uploadTime the uploadTime to set
	 */
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	

	
}
