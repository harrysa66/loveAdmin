package com.love.system.biz;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.love.framework.common.Constants;
import com.love.framework.dao.jdbc.BaseDao;
import com.love.framework.dao.jdbc.Page;
import com.love.system.po.UserLogin;
import com.love.util.ConnectionURL;
import com.love.util.DateUtil;
import com.love.util.IPUtil;
import com.love.util.UUIDGenerator;

@Service
public class UserLoginBusiness {
	
	private BaseDao<UserLogin, String> userLoginDao;

	@Resource
	private void init(SqlSessionFactory sqlSessionFactory) {
		userLoginDao = new BaseDao<UserLogin, String>(sqlSessionFactory, UserLogin.class);
	}

	@Transactional
	public void insertLogin(String username, HttpServletRequest request) throws IOException {
		UserLogin userLogin = new UserLogin();
		userLogin.setId(UUIDGenerator.getUUID());
		userLogin.setUsername(username);
		String ip = IPUtil.getIp(request);
		userLogin.setIp(ip);
		String ipAddress = IPUtil.getIpAddress(ip);
		userLogin.setIpAddress(ipAddress);
		userLogin.setLoginTime(DateUtil.getCurrentBJDate());
		userLoginDao.insert(userLogin);
	}
	
	public Page<UserLogin> queryPage(Map<String, Object> map,Page<UserLogin> page){
		return userLoginDao.pageQueryBy(map, page);
	}
	

}
