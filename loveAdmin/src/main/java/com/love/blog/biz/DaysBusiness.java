package com.love.blog.biz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.love.blog.po.Days;
import com.love.framework.common.Constants;
import com.love.framework.dao.jdbc.BaseDao;
import com.love.framework.dao.jdbc.Page;
import com.love.framework.exception.ApplicationRuntimeException;
import com.love.util.UUIDGenerator;

@Service
public class DaysBusiness {
	
	private BaseDao<Days, String> daysDao;

	@Resource
	private void init(SqlSessionFactory sqlSessionFactory) {
		daysDao = new BaseDao<Days, String>(sqlSessionFactory, Days.class);
	}
	
	public Page<Days> queryPage(Map<String, Object> map,Page<Days> page){
		return daysDao.pageQueryBy(map, page);
	}
	
	public Days findById(String id) {
		return daysDao.findById(id);
	}
	
	@Transactional
	public String save(Days days) {
		if (StringUtils.isEmpty(days.getId())) {
			days.setId(UUIDGenerator.getUUID());
			try {
				days.setStatus(Constants.STATUS_DEFAULT);
				days.setIsvalid(Constants.ISVALIAD_HIDDEN);
				daysDao.insert(days);
				return Constants.ADD_SUCCESS;
			} catch (Exception e) {
				throw new ApplicationRuntimeException(Constants.ADD_ERROR, e);
			}
		}
		try {
			daysDao.update(days);
			return Constants.EDIT_SUCCESS;
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.EDIT_ERROR, e);
		}
	}
	
	@Transactional
	public void delete(String id) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("status", Constants.STATUS_DELETED);
			daysDao.updateObject("deleteById", map);
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.DELETE_ERROR, e);
		}
	}
	
	@Transactional
	public void removeDays(String[] ids) {
		for(String id : ids){
			delete(id);
		}
	}
	
	@Transactional
	public void toogle(String id, String isvalid) {
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
			daysDao.updateObject("runById", map);
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.DO_ERROR, e);
		}
	}
	
	@Transactional
	public void runDays(String[] ids) {
		for(String id : ids){
			Days days = findById(id);
			toogle(id,days.getIsvalid());
		}
	}
	
	public Days isRepeatTitle(Map<String, String> map) {
		return daysDao.findByMap("isRepeatTitle", map);
	}

	public List<Days> findListByMap(Map<String, Object> listMap) {
		return daysDao.findListByMap("findListByMap", listMap);
	}

}
