package com.love.system.biz;

import com.love.framework.common.Constants;
import com.love.util.UUIDGenerator;
import com.love.framework.dao.jdbc.BaseDao;
import com.love.framework.dao.jdbc.Page;
import com.love.framework.exception.ApplicationRuntimeException;
import com.love.system.po.Auth;
import com.love.system.po.Role;
import com.love.system.po.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleBusiness {
	
	private BaseDao<Role, String> roleDao;

	@Resource
	private void init(SqlSessionFactory sqlSessionFactory) {
		roleDao = new BaseDao<Role, String>(sqlSessionFactory, Role.class);
	}

	@Resource
	private UserBusiness userBusiness;
	@Resource
	private AuthBusiness authBusiness;

	public Page<Role> queryPage(Map<String, Object> map, Page<Role> page) {
		return roleDao.pageQueryBy(map, page);
	}

	public Role findRoleById(String id) {
		return roleDao.findById(id);
	}

	@Transactional
	public String saveRole(Role role) {
		if (StringUtils.isEmpty(role.getId())) {
			role.setId(UUIDGenerator.getUUID());
			try {
				role.setStatus(Constants.STATUS_DEFAULT);
				role.setIsvalid(Constants.ISVALIAD_HIDDEN);
				role.setCreateTime(new Date());
				this.roleDao.insert(role);
				return Constants.ADD_SUCCESS;
			} catch (Exception e) {
				throw new ApplicationRuntimeException(Constants.ADD_ERROR, e);
			}
		}
		try {
			role.setModifyTime(new Date());
			roleDao.update(role);
			return Constants.EDIT_SUCCESS;
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.EDIT_ERROR, e);
		}
	}

	@Transactional
	public void deleteRole(String id) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("status", Constants.STATUS_DELETED);
			roleDao.updateObject("deleteById", map);
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.DELETE_ERROR, e);
		}
	}

	/*@Transactional
	public String toogleRole(String id, String isvalid) {
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
				throw new Exception();
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("isvalid", isvalid);
			roleDao.updateObject("runById", map);
			return successMessage;
		} catch (Exception e) {
			throw new ApplicationRuntimeException(errorMessage, e);
		}
	}*/
	
	@Transactional
	public void toogleRole(String id, String isvalid) {
		try {
			if ((isvalid == Constants.ISVALIAD_SHOW)
					|| (Constants.ISVALIAD_SHOW.equals(isvalid))) {
				isvalid = Constants.ISVALIAD_HIDDEN;
			} else if ((isvalid == Constants.ISVALIAD_HIDDEN)
					|| (Constants.ISVALIAD_HIDDEN.equals(isvalid))) {
				isvalid = Constants.ISVALIAD_SHOW;
			} else {
				throw new Exception();
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("isvalid", isvalid);
			roleDao.updateObject("runById", map);
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.DO_ERROR, e);
		}
	}

	public List<Role> findListBy(Map<String, Object> map) {
		return roleDao.findListByMap(map);
	}

	public List<Role> findListByUser(Map<String, Object> map) {
		List<Role> roleListByUser = roleDao.findListByMap("findListByUser", map);

		Set<Role> roleSet = new HashSet<Role>();
		roleSet.addAll(roleListByUser);
		List<Role> resultList = new ArrayList<Role>();
		resultList.addAll(roleSet);
		return resultList;
	}

	public Page<Role> findPageByUser(Map<String, Object> map,
			Page<Role> page) {
		return roleDao.pageQueryBy("findPageByUser", map, page);
	}

	public Page<Auth> queryAuthPage(Map<String, Object> map,
			Page<Auth> page) {
		return authBusiness.queryPage(map, page);
	}

	@Transactional
  public String grantAuthToRole(String roleId, String[] ids)
  {
    Map<String, Object> map = null;
    try {
      //String[] id = ids.split(",");
      if (ids.length > 0) {
        clearAuthToRole(roleId);
        for (String authId : ids) {
            if ((authId != null) && (!"".equals(authId))) {
              map = new HashMap<String, Object>();
              map.put("id", UUIDGenerator.getUUID());
              map.put("roleId", roleId);
              map.put("authId", authId);
              roleDao.updateObject("grantAuthToRole", map);
            }
          }
          return Constants.ALLOT_AUTH_SUCCESS;
        }
        return Constants.ALLOT_AUTH_ERROR;
    }
    catch (Exception e) {
      throw new ApplicationRuntimeException(Constants.ALLOT_AUTH_ERROR, e);
    }
  }

	public List<Auth> findAuthByRole(String roleId) {
		return authBusiness.findAuthByRole(roleId);
	}

	@Transactional
	public String grantRoleToUser(String userId, String[] ids) {
		Map<String, Object> map = null;
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
	    	paramMap.put("userId", userId);
			roleDao.deleteByMap("clearRoleToUser", paramMap);
			if ((ids != null) && (ids.length > 0)) {
				//String[] id = ids.split(",");
				for (String roleId : ids) {
					map = new HashMap<String, Object>();
					map.put("id", UUIDGenerator.getUUID());
					map.put("userId", userId);
					map.put("roleId", roleId);
					roleDao.updateObject("grantRoleToUser", map);
				}
			}
			return Constants.ALLOT_ROLE_SUCCESS;
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.ALLOT_ROLE_ERROR, e);
		}
	}

	/**
	 * 查找用户所具有的权限(去重)
	 * @param userId 用户ID
	 * @return 权限集合
	 */
	public List<Auth> findAuthByUser(String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		List<Auth> result = new ArrayList<Auth>();
		//通过角色迭代出权限
		List<Auth> authListByRole = new ArrayList<Auth>();
		List<Role> roleListByUser = findListByUser(map);
		for (Role role : roleListByUser) {
			authListByRole.addAll(findAuthByRole(role.getId()));
		}
		result.addAll(authListByRole);
		//通过用户查出权限
		List<Auth> authList = authBusiness.findListByUser(map);
		result.addAll(authList);
		//全部的权限集合
		Set<Auth> authSet = new HashSet<Auth>();
		authSet.addAll(result);
		List<Auth> resultList = new ArrayList<Auth>();
		resultList.addAll(authSet);
		return resultList;
	}

	@Transactional
	public void clearAuthToRole(String roleId) {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
	    	paramMap.put("roleId", roleId);
			roleDao.deleteByMap("clearAuthToRole", paramMap);
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.ALLOT_AUTH_ERROR, e);
		}
	}

	public Role isRepeatCode(Map<String, String> map) {
		return roleDao.findByMap("isRepeatCode", map);
	}

	public Role findRoleByCode(String code) {
		return roleDao.findByProperty("findRoleByCode", code);
	}

	public Role isRepeatName(Map<String, String> map) {
		return roleDao.findByMap("isRepeatName", map);
	}

	public List<User> findUserListByRoleCode(String code) {
		List<User> userList = new ArrayList<User>();
		if ((code != null) && (!("".equals(code)))) {
			String roleId = findRoleByCode(code).getId();
			userList = userBusiness.findUserListByRoleId(roleId);
		}
		return userList;
	}

	public List<Auth> findAuthByUsername(String username) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", username);
		List<Auth> result = new ArrayList<Auth>();

		List<Auth> authListByRole = new ArrayList<Auth>();
		List<Role> roleListByUser = findListByUser(map);
		for (Role role : roleListByUser) {
			authListByRole.addAll(findAuthByRole(role.getId()));
		}
		result.addAll(authListByRole);

		List<Auth> authList = authBusiness.findListByUser(map);
		result.addAll(authList);

		Set<Auth> authSet = new HashSet<Auth>();
		authSet.addAll(result);
		List<Auth> resultList = new ArrayList<Auth>();
		resultList.addAll(authSet);
		return resultList;
	}

	@Transactional
	public boolean removeRoles(String[] ids) {
		boolean flag = true;
		for(String id : ids){
			Role role = findRoleById(id);
			if(role.getCode().equals(Constants.ROLE_ADMIN_CODE)){
				flag = false;
				continue;
			}
			deleteRole(id);
		}
		return flag;
	}

	@Transactional
	public boolean runRoles(String[] ids) {
		boolean flag = true;
		for(String id : ids){
			Role role = findRoleById(id);
			if(role.getCode().equals(Constants.ROLE_ADMIN_CODE)){
				flag = false;
				continue;
			}
			toogleRole(id,role.getIsvalid());
		}
		return flag;
	}
}
