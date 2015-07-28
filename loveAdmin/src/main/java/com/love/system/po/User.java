package com.love.system.po;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.ibatis.type.Alias;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Alias("user")
@Component
public class User implements UserDetails {
	private static final long serialVersionUID = 2399354238882372255L;
	private String id;
	private String username;
	private String password;
	private String nickname;
	private Date createTime;
	private Date modifyTime;
	private String status;
	private String isvalid;
	private String roleCodeList;
	boolean enabled = true;
	boolean accountNonExpired = true;
	boolean credentialsNonExpired = true;
	boolean accountNonLocked = true;

	private Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
	private Map<String, Object> extend = new HashMap<String, Object>();

	public String toString() {
		return String.format(
				"id=%s username=%s nickname=%s modifyTime=%s status=%s",
				new Object[] { this.id, this.username, this.nickname, "",
						this.status });
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public Collection<GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	public Map<String, Object> getExtend() {
		return this.extend;
	}

	public void addAuthoritie(String authorityCode) {
		if (authorityCode != null)
			this.authorities.add(new SimpleGrantedAuthority(authorityCode));
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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
		return isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public void setExtend(Map<String, Object> extend) {
		this.extend = extend;
	}
	public String getRoleCodeList() {
		return roleCodeList;
	}

	public void setRoleCodeList(String roleCodeList) {
		this.roleCodeList = roleCodeList;
	}

}
