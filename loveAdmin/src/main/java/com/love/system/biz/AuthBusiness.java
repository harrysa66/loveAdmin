package com.love.system.biz;

import com.love.framework.common.Constants;
import com.love.util.UUIDGenerator;
import com.love.framework.dao.jdbc.BaseDao;
import com.love.framework.dao.jdbc.Page;
import com.love.framework.exception.ApplicationRuntimeException;
import com.love.system.po.Auth;
import com.love.system.po.Menu;
import com.love.system.po.MenuBtn;

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
public class AuthBusiness {
	
	@Resource
	private MenuBusiness menuBusiness;
	
	@Resource
	private MenuBtnBusiness menuBtnBusiness;

	private BaseDao<Auth, String> authDao;

	@Resource
	private void init(SqlSessionFactory sqlSessionFactory) {
		authDao = new BaseDao<Auth, String>(sqlSessionFactory, Auth.class);
	}

	public Page<Auth> queryPage(Map<String, Object> map, Page<Auth> page) {
		return authDao.pageQueryBy(map, page);
	}

	public Auth findAuthById(String id) {
		return authDao.findById(id);
	}
	
	public List<Auth> findAuthByRole(String roleId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("roleId", roleId);
		return authDao.findListByMap("findAuthByRole", map);
	}

	@Transactional
	public String saveAuth(Auth auth) {
		if (StringUtils.isEmpty(auth.getId())) {
			auth.setId(UUIDGenerator.getUUID());
			try {
				auth.setStatus(Constants.STATUS_DEFAULT);
				auth.setIsvalid(Constants.ISVALIAD_HIDDEN);
				auth.setCreateTime(new Date());
				authDao.insert(auth);
				return Constants.ADD_SUCCESS;
			} catch (Exception e) {
				throw new ApplicationRuntimeException(Constants.ADD_ERROR, e);
			}
		}
		try {
			authDao.update(auth);
			auth.setModifyTime(new Date());
			return Constants.EDIT_SUCCESS;
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.EDIT_ERROR, e);
		}
	}
	
	@Transactional
	public String saveAuthReId(Auth auth) {
		if (StringUtils.isEmpty(auth.getId())) {
			auth.setId(UUIDGenerator.getUUID());
			try {
				auth.setStatus(Constants.STATUS_DEFAULT);
				auth.setIsvalid(Constants.ISVALIAD_HIDDEN);
				auth.setCreateTime(new Date());
				authDao.insert(auth);
				return auth.getId();
			} catch (Exception e) {
				throw new ApplicationRuntimeException(Constants.ADD_ERROR, e);
			}
		}
		try {
			auth.setModifyTime(new Date());
			authDao.update(auth);
			return auth.getId();
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.EDIT_ERROR, e);
		}
	}

	@Transactional
	public void deleteAuth(String id) {
		try {
			Auth auth = findAuthById(id);
			auth.setCode(auth.getCode()+Constants.DELETE_CODE);
			authDao.update(auth);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("status", Constants.STATUS_DELETED);
			authDao.updateObject("deleteById", map);
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.DELETE_ERROR, e);
		}
	}

	/*@Transactional
	public String toogleAuth(String id, String isvalid) {
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
			authDao.updateObject("runById", map);
			return successMessage;
		} catch (Exception e) {
			throw new ApplicationRuntimeException(errorMessage, e);
		}
	}*/
	
	@Transactional
	public void toogleAuth(String id, String isvalid) {
		try {
			if ((isvalid == Constants.ISVALIAD_SHOW)
					|| (Constants.ISVALIAD_SHOW.equals(isvalid))) {
				isvalid = Constants.ISVALIAD_HIDDEN;
			} else if ((isvalid == Constants.ISVALIAD_HIDDEN)
					|| (Constants.ISVALIAD_HIDDEN.equals(isvalid))) {
				isvalid = Constants.ISVALIAD_SHOW;
			}else {
				throw new Exception();
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("isvalid", isvalid);
			authDao.updateObject("runById", map);
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.DO_ERROR, e);
		}
	}

	public List<Auth> findListBy(Map<String, Object> map) {
		return authDao.findListByMap(map);
	}

	public List<Auth> findListByUser(Map<String, Object> map) {
		return authDao.findListByMap("findListByUser", map);
	}

	@Transactional
	public String grantAuthToUser(String userId, String ids) {
		Map<String, Object> map = null;
		try {
			authDao.deleteById("clearAuthToUser", userId);;
			if ((ids != null) && (!("".equals(ids)))) {
				String[] id = ids.split(",");
				for (String authId : id) {
					map = new HashMap<String, Object>();
					map.put("id", UUIDGenerator.getUUID());
					map.put("userId", userId);
					map.put("authId", authId);
					authDao.updateObject("grantAuthToUser", map);
				}
			}
			return Constants.ALLOT_AUTH_SUCCESS;
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.ALLOT_AUTH_ERROR, e);
		}
	}

	/*public String getFullParentName(String moduleId) {
		Map map = new HashMap();
		String s = null;
		while (moduleId != "") {
			map.put("id", moduleId);
			SystemModule po = this.authDao.queryModule(map);
			s = (s == null) ? po.getName() : String.format("%s / %s",
					new Object[] { po.getName(), s });
			if ((po.getParentId() == null) || ("".equals(po.getParentId())))
				moduleId = "";
			else {
				moduleId = po.getParentId();
			}
		}
		return s;
	}*/

	public Auth isRepeatCode(Map<String, String> map) {
		return this.authDao.findByMap("isRepeatCode", map);
	}

	public Auth isRepeatName(Map<String, String> map) {
		return this.authDao.findByMap("isRepeatName", map);
	}

	@Transactional
	public void saveAuthWithMenu(Auth auth,Integer menuId,String menuCode) {
		auth.setCode(menuCode);
		String authId = saveAuthReId(auth);
		if(menuCode.indexOf("BTN_") == -1){
			Menu tempMenu = menuBusiness.findByAuthId(authId);
			if(tempMenu != null){
				menuBusiness.updateAuth(tempMenu.getId(), "");
			}
			menuBusiness.updateAuth(menuId,authId);
		}else{
			MenuBtn tempMenuBtn = menuBtnBusiness.findByAuthId(authId);
			if(tempMenuBtn != null){
				menuBtnBusiness.updateAuth(tempMenuBtn.getId(), "");
			}
			menuBtnBusiness.updateAuth(menuId,authId);
		}
		
	}
	
	public String getFullParentName(String authId){
		MenuBtn menuBtn = menuBtnBusiness.findByAuthId(authId);
		Menu menu = menuBusiness.findByAuthId(authId);
		String menuId = "";
		String menuName = "";
		if(menuBtn != null){
			menuId = menuBtn.getMenuId().toString();
			menuName = menuBtn.getName();
		}else if(menu != null){
			menuId = menu.getId().toString();
			menuName = menu.getName();
		}
		while(menuId != ""){
			Menu tempMenu = menuBusiness.findById(menuId);
			menuName = String.format("%s / %s", tempMenu.getName(), menuName);
			if(null == tempMenu.getParentId() || "".equals(tempMenu.getParentId())){
				menuId = "";
			}else{
				menuId = tempMenu.getParentId().toString();
			}
		}
		return menuName;
	}

	@Transactional
	public void removeAuths(String[] ids) {
		for(String id : ids){
			Menu menu = menuBusiness.findByAuthId(id);
			if(menu != null){
				menuBusiness.updateAuth(menu.getId(), "");
			}
			MenuBtn menuBtn = menuBtnBusiness.findByAuthId(id);
			if(menuBtn != null){
				menuBtnBusiness.updateAuth(menuBtn.getId(), "");
			}
			deleteAuth(id);
		}
	}

	@Transactional
	public void runAuths(String[] ids) {
		for(String id : ids){
			Auth auth = findAuthById(id);
			toogleAuth(id, auth.getIsvalid());
		}
	}
}
