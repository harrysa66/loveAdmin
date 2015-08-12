package com.love.blog.biz;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.love.blog.po.Board;
import com.love.framework.common.Constants;
import com.love.framework.dao.jdbc.BaseDao;
import com.love.framework.dao.jdbc.Page;
import com.love.framework.exception.ApplicationRuntimeException;
import com.love.framework.security.SpringSecurityUtils;

@Service
public class BoardBusiness {
	
	private BaseDao<Board, String> boardDao;

	@Resource
	private void init(SqlSessionFactory sqlSessionFactory) {
		boardDao = new BaseDao<Board, String>(sqlSessionFactory, Board.class);
	}
	
	public Page<Board> queryPage(Map<String, Object> map,Page<Board> page){
		return boardDao.pageQueryBy(map, page);
	}
	
	public Board findById(String id) {
		return boardDao.findById(id);
	}

	@Transactional
	public void replyContent(Board board) {
		board.setUserId(SpringSecurityUtils.getCurrentUser().getId());
		board.setReplyTime(new Date());
		board.setIsvalid(Constants.ISVALIAD_SHOW);
		boardDao.update(board);
	}
	
	@Transactional
	public void delete(String id) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id);
			map.put("status", Constants.STATUS_DELETED);
			boardDao.updateObject("deleteById", map);
		} catch (Exception e) {
			throw new ApplicationRuntimeException(Constants.DELETE_ERROR, e);
		}
	}

	@Transactional
	public void removeBoards(String[] ids) {
		for(String id : ids){
			delete(id);
		}
	}
	
	public List<Board> findListByStatus(Map<String, Object> map){
		return boardDao.findListByMap("findListByStatus",map);
	}

}
