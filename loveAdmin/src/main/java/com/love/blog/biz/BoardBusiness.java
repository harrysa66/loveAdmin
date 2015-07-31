package com.love.blog.biz;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import com.love.blog.po.Board;
import com.love.framework.dao.jdbc.BaseDao;
import com.love.framework.dao.jdbc.Page;

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

}
