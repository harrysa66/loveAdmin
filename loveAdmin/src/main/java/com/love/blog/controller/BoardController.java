package com.love.blog.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.love.blog.biz.BoardBusiness;
import com.love.blog.po.Board;
import com.love.framework.common.Constants;
import com.love.framework.controller.BaseController;
import com.love.framework.dao.jdbc.Page;
import com.love.util.HtmlUtil;
import com.love.util.PageUtil;

@Controller
@RequestMapping("blog/board")
public class BoardController extends BaseController{
	
	@Resource
	private BoardBusiness boardBusiness;
	
	@RequestMapping("/query")
	public ModelAndView  doQuery(HttpServletRequest request) throws Exception{
		Map<String,Object>  context = getRootMap();
		return forword("blog/board",context); 
	}
	
	@RequestMapping("/dataList")
	public void  dataList(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("visitorId", ServletRequestUtils.getStringParameter(request, "visitorId", "").trim());
		map.put("userId", ServletRequestUtils.getStringParameter(request, "userId", "").trim());
		map.put("isvalid", ServletRequestUtils.getStringParameter(request, "isvalid", "").trim());
		Page<Board> page = PageUtil.getPageObj(request);
		page = boardBusiness.queryPage(map, page);
		
		//设置页面数据
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("total",page.getTotalCount());
		jsonMap.put("rows", page.getResult());
		HtmlUtil.writerJson(response, jsonMap);
	}
	
	@RequestMapping("/save")
	public void save(Board board,HttpServletResponse response){
		boardBusiness.replyContent(board);
		sendSuccessMessage(response, "回复成功");
	}
	
	@RequestMapping("/view")
	public void view(String id,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = new HashMap<String,Object> ();
		Board board = boardBusiness.findById(id);
		if(board == null){
			sendFailureMessage(response, "没有找到对应的记录!");
			return;
		}
		
		//将对象转成Map
		Map<String,Object> data = BeanUtils.describe(board);
		context.put(SUCCESS, true);
		context.put("data", data);
		HtmlUtil.writerJson(response, context);
	}
	
	@RequestMapping("/remove")
	public void remove(String[] id,HttpServletResponse response) throws Exception{
		if(null == id || "".equals(id) || id.length<1){
			sendFailureMessage(response, Constants.DELETE_ERROR);
		}else{
			boardBusiness.removeBoards(id);
			sendSuccessMessage(response, Constants.DELETE_SUCCESS);
		}
	}

}
