package com.love.blog.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.love.blog.biz.DaysIndexBusiness;
import com.love.blog.po.DaysIndex;
import com.love.framework.controller.BaseController;
import com.love.system.po.Attachment;

@Controller
@RequestMapping("blog/daysIndex")
public class DaysIndexController extends BaseController{
	
	@Resource
	private DaysIndexBusiness daysIndexBusiness;
	
	@RequestMapping("/query")
	public ModelAndView  doQuery(HttpServletRequest request) throws Exception{
		Map<String,Object>  context = getRootMap();
		DaysIndex daysIndex = daysIndexBusiness.findById(null);
		context.put("daysIndex", daysIndex);
		return forword("blog/daysIndex",context); 
	}
	
	@RequestMapping("/save")
	public void save(DaysIndex daysIndex,HttpServletRequest request,HttpServletResponse response) throws IOException{
		Attachment boyTempAtt = getAttachment(request, "boy");
		Attachment girTemplAtt = getAttachment(request, "girl");
		Attachment boyAtt = getAttachment(request, "boy");
		Attachment girlAtt = getAttachment(request, "girl");
		if(!daysIndexBusiness.isAllow(boyTempAtt) || !daysIndexBusiness.isAllow(girTemplAtt)){
			sendFailureMessage(response, "上传的图片尺寸必须为148x148");
		}else{
			String message = daysIndexBusiness.save(daysIndex,boyAtt,girlAtt);
			sendSuccessMessage(response, message);
		}
	}

}
