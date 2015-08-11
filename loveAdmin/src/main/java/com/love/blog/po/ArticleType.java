package com.love.blog.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

@Alias("articleType")
@XmlRootElement(name="articleType")
@Component
public class ArticleType implements Serializable {

	private static final long serialVersionUID = 4842386784482001527L;
	
	private String id;
	private String code;
	private String name;
	private Date createTime;
	private Date modifyTime;
	private String status;
	private String isvalid;
	private String isshow;
	private Integer display;
	private Map<String, Object> pageMap;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		if(code != null){
			return code.toUpperCase();
		}else{
			return code;
		}
	}
	public void setCode(String code) {
		if(code != null){
			this.code = code.toUpperCase();
		}else{
			this.code = code;
		}
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
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
	public String getIsshow() {
		return isshow;
	}
	public void setIsshow(String isshow) {
		this.isshow = isshow;
	}
	public Integer getDisplay() {
		return display;
	}
	public void setDisplay(Integer display) {
		this.display = display;
	}
	public Map<String, Object> getPageMap() {
		return pageMap;
	}
	public void setPageMap(Map<String, Object> pageMap) {
		this.pageMap = pageMap;
	}

}
