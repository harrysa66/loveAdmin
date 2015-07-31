package com.love.blog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.love.blog.biz.ArticleBusiness;
import com.love.blog.biz.ArticleTypeBusiness;
import com.love.blog.po.Article;
import com.love.blog.po.ArticleType;
import com.love.framework.common.Constants;
import com.love.framework.controller.BaseController;
import com.love.framework.dao.jdbc.Page;
import com.love.system.biz.UserBusiness;
import com.love.system.po.User;
import com.love.util.HtmlUtil;
import com.love.util.PageUtil;

@Controller
@RequestMapping("blog/article")
public class ArticleController extends BaseController{
	
	@Resource
	private ArticleBusiness articleBusiness;
	
	@Resource
	private ArticleTypeBusiness articleTypeBusiness;
	
	@Resource
	private UserBusiness userBusiness;
	
	@RequestMapping("/query")
	public ModelAndView  doQuery(HttpServletRequest request) throws Exception{
		Map<String,Object>  context = getRootMap();
		return forword("blog/article",context); 
	}
	
	@RequestMapping("/dataList")
	public void  dataList(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("typeId", ServletRequestUtils.getStringParameter(request, "typeId", "").trim());
		map.put("title", ServletRequestUtils.getStringParameter(request, "title", "").trim());
		map.put("subtitle", ServletRequestUtils.getStringParameter(request, "subtitle", "").trim());
		map.put("userId", ServletRequestUtils.getStringParameter(request, "userId", "").trim());
		map.put("isvalid", ServletRequestUtils.getStringParameter(request, "isvalid", "").trim());
		Page<Article> page = PageUtil.getPageObj(request);
		page = articleBusiness.queryPage(map, page);
		
		//设置页面数据
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("total",page.getTotalCount());
		jsonMap.put("rows", page.getResult());
		HtmlUtil.writerJson(response, jsonMap);
	}
	
	@RequestMapping("/save")
	public void save(Article article,HttpServletResponse response){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", article.getId());
		map.put("title", article.getTitle());
		Article vaTitle = articleBusiness.isRepeatTitle(map);
		if(vaTitle != null){
			sendFailureMessage(response, "改标题已存在，请重新填写!");
		}else{
			String message = articleBusiness.save(article);
			sendSuccessMessage(response, message);
		}
	}
	
	@RequestMapping("/view")
	public void view(String id,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = new HashMap<String,Object> ();
		Article article = articleBusiness.findById(id);
		if(article == null){
			sendFailureMessage(response, "没有找到对应的记录!");
			return;
		}
		
		//将对象转成Map
		Map<String,Object> data = BeanUtils.describe(article);
		context.put(SUCCESS, true);
		context.put("data", data);
		HtmlUtil.writerJson(response, context);
	}
	
	@RequestMapping("/remove")
	public void remove(String[] id,HttpServletResponse response) throws Exception{
		if(null == id || "".equals(id) || id.length<1){
			sendFailureMessage(response, Constants.DELETE_ERROR);
		}
		boolean isAllow = articleBusiness.removeArticles(id);
		if(isAllow){
			sendSuccessMessage(response, Constants.DELETE_SUCCESS);
		}else{
			sendSuccessMessage(response, "不能删除非本人的文章!");
		}
	}
	
	@RequestMapping("/run")
	public void run(String[] id,HttpServletResponse response) throws Exception{
		if(null == id || "".equals(id) || id.length<1){
			sendFailureMessage(response, Constants.DO_ERROR);
		}
		boolean isAllow = articleBusiness.runArticles(id);
		if(isAllow){
			sendSuccessMessage(response, Constants.DO_SUCCESS);
		}else{
			sendSuccessMessage(response, "不能操作非本人的文章!");
		}
	}
	
	@RequestMapping("/selectType")
	public void selectType(String id,HttpServletResponse response) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", Constants.STATUS_DEFAULT);
		map.put("isvalid", Constants.ISVALIAD_SHOW);
		List<ArticleType> typeList = articleTypeBusiness.findListById(map);
		Object context = JSONArray.toJSON(typeList);
		HtmlUtil.writerJson(response, context);
	}
	
	@RequestMapping("/selectUser")
	public void selectUser(String id,HttpServletResponse response) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", Constants.STATUS_DEFAULT);
		map.put("isvalid", Constants.ISVALIAD_SHOW);
		List<User> userList = userBusiness.findUserListByMap(map);
		Object context = JSONArray.toJSON(userList);
		HtmlUtil.writerJson(response, context);
	}
	
}
