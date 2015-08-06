package com.love.restful.vo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="codeMessage")
public class CodeMessgae {
	
public final static String CODE_SUCCESS = "1000";
	
	private String code;
	private String message;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
