package com.love.blog.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.love.blog.biz.IndexPageBusiness;
import com.love.blog.po.IndexPage;
import com.love.framework.controller.BaseController;

@Controller
@RequestMapping("blog/indexPage")
public class IndexPageController extends BaseController{
	
	static Logger log = Logger.getLogger(IndexPageController.class.getName());
	
	@Resource
	private IndexPageBusiness indexPageBusiness;
	
	@RequestMapping("/query")
	public ModelAndView  doQuery(HttpServletRequest request) throws Exception{
		Map<String,Object>  context = getRootMap();
		IndexPage indexPage = indexPageBusiness.findById(null);
		context.put("indexPage", indexPage);
		return forword("blog/indexPage",context); 
	}
	
	@RequestMapping("/save")
	public void save(IndexPage indexPage,HttpServletResponse response){
		String message = indexPageBusiness.save(indexPage);
		sendSuccessMessage(response, message);
	}

}
