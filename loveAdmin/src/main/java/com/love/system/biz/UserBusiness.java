package com.love.system.biz;

import com.love.framework.common.Constants;
import com.love.framework.dao.jdbc.BaseDao;
import com.love.framework.dao.jdbc.Page;
import com.love.framework.exception.ApplicationRuntimeException;
import com.love.system.po.User;
import com.love.util.MD5Util;
import com.love.util.UUIDGenerator;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserBusiness {

	private BaseDao<User, String> userDao;

	@Resource
	private void init(SqlSessionFactory sqlSessionFactory) {
		userDao = new BaseDao<User, String>(sqlSessionFactory, User.class);
	}

	/*@Transactional
	public void save(User user) throws ApplicationRuntimeException {
		try {
			user.setId(UUIDGenerator.getUUID());
			userDao.insert(user);
		} catch (ApplicationRuntimeException e) {
			throw new ApplicationRuntimeException(Constants.ADD_ERROR, e);
		}
	}

	@Transactional
	public void update(User user) throws ApplicationRuntimeException {
		try {
			userDao.update(user);
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.EDIT_ERROR, e);
		}
	}*/
	
	@Transactional
	public String saveUser(User user) throws ApplicationRuntimeException {
		if (StringUtils.isEmpty(user.getId())) {
			user.setId(UUIDGenerator.getUUID());
			user.setPassword(MD5Util.MD5(Constants.INIT_PASSWORD));
			try {
				user.setCreateTime(new Date());
				user.setStatus(Constants.STATUS_DEFAULT);
				user.setIsvalid(Constants.ISVALIAD_HIDDEN);
				userDao.insert(user);
				return Constants.ADD_SUCCESS;
			} catch (Exception e) {
				throw new ApplicationRuntimeException(Constants.ADD_ERROR, e);
			}
		}
		try {
			user.setModifyTime(new Date());
			userDao.update(user);
			return Constants.EDIT_SUCCESS;
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.EDIT_ERROR, e);
		}
	}

	@Transactional
	public String updateValid(String id, String isvalid) {
		String successMessage = null;
		String errorMessage = null;
		try {
			if ((isvalid == Constants.ISVALIAD_SHOW)
					|| (Constants.ISVALIAD_SHOW.equals(isvalid))) {
				successMessage = Constants.STOP_SUCCESS;
				errorMessage = Constants.STOP_ERROR;
				isvalid = Constants.ISVALIAD_HIDDEN;
			} else if ((isvalid == Constants.ISVALIAD_HIDDEN)
					|| (Constants.ISVALIAD_HIDDEN.equals(isvalid))) {
				successMessage = Constants.START_SUCCESS;
				errorMessage = Constants.START_ERROR;
				isvalid = Constants.ISVALIAD_SHOW;
			} else {
				successMessage = Constants.DO_SUCCESS;
				errorMessage = Constants.DO_ERROR;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("isvalid", isvalid);
			userDao.updateObject("runById", map);
			return successMessage;
		} catch (Exception e) {
			throw new ApplicationRuntimeException(errorMessage, e);
		}
	}

	@Transactional
	public void deleted(String id)
			throws ApplicationRuntimeException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("status", Constants.STATUS_DELETED);
			userDao.updateObject("deleted", map);
		} catch (ApplicationRuntimeException e) {
			throw new ApplicationRuntimeException(Constants.DELETE_ERROR, e);
		}
	}

	public Page<User> queryPage(Map<String, Object> map, Page<User> page) {
		return userDao.pageQueryBy(map, page);
	}

	public User findUserById(String id) {
		return userDao.findById(id);
	}

	public User findUserByUsername(String username) {
		return userDao.findByProperty("findUserByUsername", username);
	}
	
	public List<User> findUserListByRoleId(String roleId) {
		return userDao.findListByProperty("findUserListByRoleId", roleId);
	}

	/*public Map<String, Object> findUserByCodePt(String code) {
		Map resMap = new HashMap();
		resMap.put("code", code);
		List userForm = this.UserFormDao
				.findListByMap("findUserByCode", resMap);
		resMap.put("userFormDetail", userForm);
		return resMap;
	}*/

	public User isRepeatUsername(Map<String, String> map) {
		if (map == null) {
			map = new HashMap<String, String>();
		}
		return userDao.findByMap("isRepeat", map);
	}

	public User isRepeatNickname(Map<String, String> map) {
		if (map == null) {
			map = new HashMap<String, String>();
		}
		return userDao.findByMap("isRepeatNick", map);
	}

	@Transactional
	public String updatePassword(Map<String, String> map)
			throws ApplicationRuntimeException {
		try {
			if (userDao.countByMap("passwordCorrect", map) > 0) {
				userDao.updateObject("updatePassword", map);
				return Constants.UPDATE_PASSWORD_SUCCESS;
			} else {
				return Constants.OLD_PASSWORD_ERROR;
			}
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.UPDATE_PASSWORD_ERROR, e);
		}
	}

	@Transactional
	public void resetPassword(String id) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("password", MD5Util.MD5(Constants.INIT_PASSWORD));
			userDao.updateObject("resetPassword", map);
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.RESET_PASSWORD_ERROR, e);
		}
	}

	@Transactional
	public boolean removeUsers(String[] ids) {
		boolean flag = true;
		for(String id : ids){
			User user = findUserById(id);
			if(user.getUsername().equals(Constants.USER_ADMIN_CODE)){
				flag = false;
				continue;
			}
			deleted(id);
		}
		return flag;
	}

	@Transactional
	public boolean runUsers(String[] ids) {
		boolean flag = true;
		for(String id : ids){
			User user = findUserById(id);
			if(user.getUsername().equals(Constants.USER_ADMIN_CODE)){
				flag = false;
				continue;
			}
			updateValid(id,user.getIsvalid());
		}
		return flag;
	}

	@Transactional
	public boolean resetUserPasswords(String[] ids) {
		boolean flag = true;
		for(String id : ids){
			User user = findUserById(id);
			if(user != null){
				if(user.getUsername().equals(Constants.USER_ADMIN_CODE)){
					flag = false;
					continue;
				}
				resetPassword(id);
			}
		}
		return flag;
	}
}
