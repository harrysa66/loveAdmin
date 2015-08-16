package com.love.blog.po;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

@Alias("days")
@XmlRootElement(name="days")
@Component
public class Days implements Serializable{

	private static final long serialVersionUID = -3882425316629639344L;
	
	private String id;
	private Date memorialDate;
	private String fmtDate;
	private String memorialTitle;
	private String memorialContent;
	private String status;
	private String isvalid;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getMemorialDate() {
		return memorialDate;
	}
	public void setMemorialDate(Date memorialDate) {
		this.memorialDate = memorialDate;
	}
	public String getFmtDate() {
		return fmtDate;
	}
	public void setFmtDate(String fmtDate) {
		this.fmtDate = fmtDate;
	}
	public String getMemorialTitle() {
		return memorialTitle;
	}
	public void setMemorialTitle(String memorialTitle) {
		this.memorialTitle = memorialTitle;
	}
	public String getMemorialContent() {
		return memorialContent;
	}
	public void setMemorialContent(String memorialContent) {
		this.memorialContent = memorialContent;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIsvalid() {
		return isvalid;
	}
	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}

}
