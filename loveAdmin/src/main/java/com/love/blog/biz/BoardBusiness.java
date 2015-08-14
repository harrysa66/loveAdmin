package com.love.blog.biz;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.love.blog.po.Board;
import com.love.blog.po.Visitor;
import com.love.framework.common.Constants;
import com.love.framework.dao.jdbc.BaseDao;
import com.love.framework.dao.jdbc.Page;
import com.love.framework.exception.ApplicationRuntimeException;
import com.love.framework.security.SpringSecurityUtils;
import com.love.restful.vo.CodeMessgae;
import com.love.util.ConnectionURL;
import com.love.util.DateUtil;
import com.love.util.UUIDGenerator;

@Service
public class BoardBusiness {
	
	@Resource
	private VisitorBusiness visitorBusiness;
	
	private BaseDao<Board, String> boardDao;

	@Resource
	private void init(SqlSessionFactory sqlSessionFactory) {
		boardDao = new BaseDao<Board, String>(sqlSessionFactory, Board.class);
	}
	
	public void insert(Board board){
		boardDao.insert(board);
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
	
	public Board queryMaxCreate(String visitorId){
		return boardDao.findByProperty("queryMaxCreate", visitorId);
	}

	@Transactional
	public CodeMessgae writeBoardNoVisitor(String ip,String content) throws IOException {
		CodeMessgae codeMessgae = new CodeMessgae();
		Board board = new Board();
		Visitor visitor = new Visitor();
		Date currentDate = DateUtil.getCurrentBJDate();
		String url = "http://apis.baidu.com/apistore/iplookupservice/iplookup?ip="+ip;
		JSONObject json = ConnectionURL.getConnectionData(url, "apikey", Constants.API_STORE_KEY);
		StringBuilder ipAddress = new StringBuilder();
		if(json != null){
			JSONObject retData = json.getJSONObject("retData");
			if(retData != null){
				ipAddress.append(retData.getString("country")).append("-");
				ipAddress.append(retData.getString("province")).append("-");
				ipAddress.append(retData.getString("city")).append("-");
				ipAddress.append(retData.getString("district")).append("-");
				ipAddress.append(retData.getString("carrier"));
			}
		}
		visitor.setId(UUIDGenerator.getUUID());
		visitor.setIp(ip);
		visitor.setIpAddress(ipAddress.toString());
		visitor.setCreateTime(currentDate);
		visitor.setLoginTime(currentDate);
		visitor.setLoginCount("1");
		visitor.setStatus(Constants.STATUS_DEFAULT);
		visitorBusiness.insert(visitor);
		board.setId(UUIDGenerator.getUUID());
		board.setContent(content);
		board.setVisitorId(visitor.getId());
		board.setCreateTime(currentDate);
		board.setStatus(Constants.STATUS_DEFAULT);
		board.setIsvalid(Constants.ISVALIAD_HIDDEN);
		insert(board);
		codeMessgae.setCode(CodeMessgae.CODE_SUCCESS);
		codeMessgae.setMessage("留言成功");
		return codeMessgae;
	}

	public CodeMessgae writeBoardWithVisitor(String visitorId,String content) throws ParseException {
		CodeMessgae codeMessgae = new CodeMessgae();
		Board board = new Board();
		Date currentDate = DateUtil.getCurrentBJDate();
		Board tempBoard = queryMaxCreate(visitorId);
		int minsBetween = DateUtil.minsBetween(tempBoard.getCreateTime(), currentDate);
		if(minsBetween > 3){
			Visitor visitor = visitorBusiness.findById(visitorId);
			visitor.setLoginTime(currentDate);
			visitor.setLoginCount(String.valueOf(Integer.parseInt(visitor.getLoginCount())+1));
			visitorBusiness.update(visitor);
			board.setId(UUIDGenerator.getUUID());
			board.setContent(content);
			board.setVisitorId(visitor.getId());
			board.setCreateTime(currentDate);
			board.setStatus(Constants.STATUS_DEFAULT);
			board.setIsvalid(Constants.ISVALIAD_HIDDEN);
			insert(board);
			codeMessgae.setCode(CodeMessgae.CODE_SUCCESS);
			codeMessgae.setMessage("留言成功");
		}else{
			codeMessgae.setCode("1001");
			codeMessgae.setMessage("两次留言时间间隔不能小于3分钟");
		}
		return codeMessgae;
	}

}
