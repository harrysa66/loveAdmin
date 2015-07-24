package com.love.system.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

@Alias("role")
@Component
public class Role implements Serializable {
	private static final long serialVersionUID = -5385547784671275719L;
	private String id;
	private String name;
	private String code;
	private Date createTime;
	private Date modifyTime;
	private String status;
	private String isvalid;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsvalid() {
		return this.isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}
}
