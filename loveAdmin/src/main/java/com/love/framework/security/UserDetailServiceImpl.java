package com.love.framework.security;

import com.love.framework.common.Constants;
import com.love.system.biz.AuthBusiness;
import com.love.system.biz.RoleBusiness;
import com.love.system.biz.UserBusiness;
import com.love.system.po.Auth;
import com.love.system.po.Role;
import com.love.system.po.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailServiceImpl implements UserDetailsService {
	static Logger log = Logger.getLogger(UserDetailServiceImpl.class.getName());

	@Resource
	private UserBusiness userBusiness;
	
	@Resource
	private AuthBusiness authBusiness;

	@Resource
	private RoleBusiness roleBusiness;

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		User user = userBusiness.findUserByUsername(username);
		if ((user == null)
				|| (Constants.ISVALIAD_HIDDEN.equals(user.getIsValid()))
				|| (Constants.STATUS_DELETED.equals(user.getStatus()))) {
			throw new UsernameNotFoundException("登录名错误, username=" + username);
		}
		List<Auth> listAuth = roleBusiness.findAuthByUser(user.getId());
		for (Auth auth : listAuth) {
			user.addAuthoritie(auth.getCode());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", Constants.STATUS_DEFAULT);
		map.put("userId", user.getId());
		List<Role> roleList = roleBusiness.findListByUser(map);
		StringBuilder roleCodeList = new StringBuilder();
		for (Role tempRole : roleList) {
			roleCodeList.append(tempRole.getCode());
			roleCodeList.append(",");
		}
		user.setRoleCodeList(roleCodeList.toString().substring(0,
				roleCodeList.length() - 1));
		Role role = roleBusiness.findRoleByCode(Constants.ROLE_ADMIN_CODE);
		if ((roleList.contains(role))
				|| ("admin".equals(user.getUsername()))) {
			user.addAuthoritie("ROLE_ADMIN");
		}
		user.addAuthoritie("ROLE_USER");
		log.info("登录名：" + user.toString());
		return user;
	}

}
