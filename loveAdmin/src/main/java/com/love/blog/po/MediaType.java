package com.love.blog.po;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

@Alias("mediaType")
@Component
public class MediaType implements Serializable {

	private static final long serialVersionUID = -4248540508969670937L;
	
	private String id;
	private String code;
	private String name;
	private Date createTime;
	private Date modifyTime;
	private String status;
	private String isvalid;
	private String isshow;
	private Integer display;
	
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

}
