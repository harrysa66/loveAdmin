package com.love.blog.po;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

@Alias("visitor")
@Component
public class Visitor implements Serializable{

	private static final long serialVersionUID = -1170730506635054315L;

	private String id;
    private String ip;
    private String ipAddress;
    private Date createTime;
    private Date loginTime;
    private String loginCount;
    private String status;
    private Date forbidStart;
    private Date forbidEnd;
    private String forbidDay;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public String getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(String loginCount) {
		this.loginCount = loginCount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getForbidStart() {
		return forbidStart;
	}
	public void setForbidStart(Date forbidStart) {
		this.forbidStart = forbidStart;
	}
	public Date getForbidEnd() {
		return forbidEnd;
	}
	public void setForbidEnd(Date forbidEnd) {
		this.forbidEnd = forbidEnd;
	}
	public String getForbidDay() {
		return forbidDay;
	}
	public void setForbidDay(String forbidDay) {
		this.forbidDay = forbidDay;
	}
}
