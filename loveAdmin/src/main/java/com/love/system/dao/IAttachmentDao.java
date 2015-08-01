package com.love.system.dao;

import java.util.List;
import java.util.Map;

import com.love.framework.dao.jdbc.Page;
import com.love.framework.exception.ApplicationRuntimeException;
import com.love.system.po.Attachment;

public interface IAttachmentDao {
	

	/**
	 * 
	 * @param attachment
	 * @throws ApplicationRuntimeException
	 */
	public void insert(Attachment attachment) ;
	/**
	 * 
	 * @param id
	 * @return
	 * @throws ApplicationRuntimeException
	 */
	public Attachment findPathById(Map<String, Object> map) throws ApplicationRuntimeException;
	/**
	 * 
	 * @param id
	 * @return
	 * @throws ApplicationRuntimeException
	 */
	public Attachment findAttachmentById(String id) throws ApplicationRuntimeException;
    /**
     * 
     * @param id
     * @throws ApplicationRuntimeException
     */
	public void deleteAttachmentById(String id) throws ApplicationRuntimeException;
	
	 /**
     * 
     * @param id
     * @throws ApplicationRuntimeException
     */
	public void updateAttachmentById(Map<String, Object> map) throws ApplicationRuntimeException;
	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<Attachment> findAttachmentByEntityId(String id);
	/**
	 * 
	 * @param id
	 * @return
	 */
	  public Integer findAttachmentCount(Map<String, Object> map);
	/**
	 * 
	 * @param customerId
	 * @param page
	 * @return
	 */
	public Page<Attachment> findAttachmentByCustomerId(Map<String, Object> map,Page<Attachment> page);
	 
}