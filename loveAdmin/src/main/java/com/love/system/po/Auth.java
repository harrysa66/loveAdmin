package com.love.system.po;

import java.io.Serializable;
import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

@Alias("auth")
@Component
public class Auth implements Serializable {
	private static final long serialVersionUID = 6654262770693279284L;
	private String id;
	private String code;
	private String name;
	private String status;
	private String isvalid;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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
