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

import com.love.blog.biz.ArticleTypeBusiness;
import com.love.blog.po.ArticleType;
import com.love.framework.common.Constants;
import com.love.framework.controller.BaseController;
import com.love.framework.dao.jdbc.Page;
import com.love.util.HtmlUtil;
import com.love.util.PageUtil;

@Controller
@RequestMapping("blog/articleType")
public class ArticleTypeController extends BaseController{
	
	@Resource
	private ArticleTypeBusiness articleTypeBusiness;
	
	@RequestMapping("/query")
	public ModelAndView  doQuery(HttpServletRequest request) throws Exception{
		Map<String,Object>  context = getRootMap();
		return forword("blog/articleType",context); 
	}
	
	@RequestMapping("/dataList")
	public void  dataList(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", ServletRequestUtils.getStringParameter(request, "code", "").trim());
		map.put("name", ServletRequestUtils.getStringParameter(request, "name", "").trim());
		map.put("isvalid", ServletRequestUtils.getStringParameter(request, "isvalid", "").trim());
		map.put("isshow", ServletRequestUtils.getStringParameter(request, "isshow", "").trim());
		Page<ArticleType> page = PageUtil.getPageObj(request);
		page = articleTypeBusiness.queryPage(map, page);
		
		//设置页面数据
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("total",page.getTotalCount());
		jsonMap.put("rows", page.getResult());
		HtmlUtil.writerJson(response, jsonMap);
	}
	
	@RequestMapping("/save")
	public void save(ArticleType articleType,HttpServletResponse response){
		String subCode = "";
		if(articleType.getCode().length() > 7){
			subCode = articleType.getCode().substring(0, Constants.ARTICLE_RULE_ROLE.length());
		}
		if(!subCode.equals(Constants.ARTICLE_RULE_ROLE)){
			articleType.setCode(Constants.ARTICLE_RULE_ROLE+articleType.getCode());
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", articleType.getId());
		map.put("code", articleType.getCode());
		map.put("name", articleType.getName());
		map.put("display", articleType.getDisplay().toString());
		ArticleType vaCode = articleTypeBusiness.isRepeatCode(map);
		ArticleType vaName = articleTypeBusiness.isRepeatName(map);
		ArticleType vaDisplay = articleTypeBusiness.isRepeatDisplay(map);
		if(vaCode != null){
			sendFailureMessage(response, "类型编号已存在，请重新填写!");
		}else if(vaName != null){
			sendFailureMessage(response, "类型名称已存在，请重新填写!");
		}else if(vaDisplay != null){
			sendFailureMessage(response, "排序已存在，请重新填写!");
		}else{
			String message = articleTypeBusiness.save(articleType);
			sendSuccessMessage(response, message);
		}
	}
	
	@RequestMapping("/view")
	public void view(String id,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = new HashMap<String,Object> ();
		ArticleType articleType = articleTypeBusiness.findById(id);
		if(articleType == null){
			sendFailureMessage(response, "没有找到对应的记录!");
			return;
		}
		
		//将对象转成Map
		Map<String,Object> data = BeanUtils.describe(articleType);
		context.put(SUCCESS, true);
		context.put("data", data);
		HtmlUtil.writerJson(response, context);
	}
	
	@RequestMapping("/remove")
	public void remove(String[] id,HttpServletResponse response) throws Exception{
		if(null == id || "".equals(id) || id.length<1){
			sendFailureMessage(response, Constants.DELETE_ERROR);
		}
		boolean isAllow = articleTypeBusiness.removeArticleTypes(id);
		if(isAllow){
			sendSuccessMessage(response, Constants.DELETE_SUCCESS);
		}else{
			sendSuccessMessage(response, "不能删除拥有文章的类型!");
		}
	}
	
	@RequestMapping("/run")
	public void run(String[] id,HttpServletResponse response) throws Exception{
		if(null == id || "".equals(id) || id.length<1){
			sendFailureMessage(response, Constants.DO_ERROR);
		}
		boolean isAllow = articleTypeBusiness.runArticles(id);
		if(isAllow){
			sendSuccessMessage(response, Constants.DO_SUCCESS);
		}else{
			sendSuccessMessage(response, "不能操作拥有文章的类型!");
		}
	}
	
	@RequestMapping("/show")
	public void show(String[] id,HttpServletResponse response) throws Exception{
		if(null == id || "".equals(id) || id.length<1){
			sendFailureMessage(response, Constants.DO_ERROR);
		}
		articleTypeBusiness.showArticles(id);;
		sendSuccessMessage(response, Constants.DO_SUCCESS);
	}

}
