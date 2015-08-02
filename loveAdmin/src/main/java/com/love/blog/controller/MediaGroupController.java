package com.love.blog.controller;

import java.io.IOException;
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
import com.love.blog.biz.MediaGroupBusiness;
import com.love.blog.biz.MediaTypeBusiness;
import com.love.blog.po.MediaGroup;
import com.love.blog.po.MediaType;
import com.love.framework.common.Constants;
import com.love.framework.controller.BaseController;
import com.love.framework.dao.jdbc.Page;
import com.love.system.biz.UserBusiness;
import com.love.system.po.Attachment;
import com.love.system.po.User;
import com.love.util.HtmlUtil;
import com.love.util.PageUtil;

@Controller
@RequestMapping("blog/mediaGroup")
public class MediaGroupController extends BaseController{
	
	@Resource
	private MediaGroupBusiness mediaGroupBusiness;
	
	@Resource
	private MediaTypeBusiness mediaTypeBusiness;
	
	@Resource
	private UserBusiness userBusiness;
	
	@RequestMapping("/query")
	public ModelAndView  doQuery(HttpServletRequest request) throws Exception{
		Map<String,Object>  context = getRootMap();
		return forword("blog/mediaGroup",context); 
	}
	
	@RequestMapping("/dataList")
	public void  dataList(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("typeId", ServletRequestUtils.getStringParameter(request, "typeId", "").trim());
		map.put("userId", ServletRequestUtils.getStringParameter(request, "userId", "").trim());
		map.put("name", ServletRequestUtils.getStringParameter(request, "name", "").trim());
		map.put("isvalid", ServletRequestUtils.getStringParameter(request, "isvalid", "").trim());
		Page<MediaGroup> page = PageUtil.getPageObj(request);
		page = mediaGroupBusiness.queryPage(map, page);
		
		//设置页面数据
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("total",page.getTotalCount());
		jsonMap.put("rows", page.getResult());
		HtmlUtil.writerJson(response, jsonMap);
	}
	
	@RequestMapping("/save")
	public void save(MediaGroup mediaGroup,HttpServletResponse response){
		String message = mediaGroupBusiness.save(mediaGroup);
		sendSuccessMessage(response, message);
	}
	
	@RequestMapping("/view")
	public void view(String id,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = new HashMap<String,Object> ();
		MediaGroup mediaGroup = mediaGroupBusiness.findById(id);
		if(mediaGroup == null){
			sendFailureMessage(response, "没有找到对应的记录!");
			return;
		}
		
		//将对象转成Map
		Map<String,Object> data = BeanUtils.describe(mediaGroup);
		context.put(SUCCESS, true);
		context.put("data", data);
		HtmlUtil.writerJson(response, context);
	}
	
	@RequestMapping("/remove")
	public void remove(String[] id,HttpServletResponse response) throws Exception{
		if(null == id || "".equals(id) || id.length<1){
			sendFailureMessage(response, Constants.DELETE_ERROR);
		}
		boolean isAllow = mediaGroupBusiness.removeMediaGroups(id);
		if(isAllow){
			sendSuccessMessage(response, Constants.DELETE_SUCCESS);
		}else{
			sendSuccessMessage(response, "除管理员外，不能删除非本人的文件!");
		}
	}
	
	@RequestMapping("/run")
	public void run(String[] id,HttpServletResponse response) throws Exception{
		if(null == id || "".equals(id) || id.length<1){
			sendFailureMessage(response, Constants.DO_ERROR);
		}
		boolean isAllow = mediaGroupBusiness.runMediaGroups(id);
		if(isAllow){
			sendSuccessMessage(response, Constants.DO_SUCCESS);
		}else{
			sendSuccessMessage(response, "除管理员外，不能操作非本人的文件!");
		}
	}
	
	@RequestMapping("/selectType")
	public void selectType(HttpServletResponse response) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", Constants.STATUS_DEFAULT);
		map.put("isvalid", Constants.ISVALIAD_SHOW);
		List<MediaType> typeList = mediaTypeBusiness.findListByMap(map);
		Object context = JSONArray.toJSON(typeList);
		HtmlUtil.writerJson(response, context);
	}
	
	@RequestMapping("/coverUpload")
	public void coverUpload(String groupId,HttpServletRequest request,HttpServletResponse response) throws IOException{
		boolean isSelf = mediaGroupBusiness.isSelf(groupId);
		if(isSelf){
			Attachment attach = getAttachment(request, "groupCover");
			if(attach.getContentType().indexOf("image/") == -1){
				sendFailureMessage(response, "只能上传图片格式!");
			}else{
				mediaGroupBusiness.coverUpload(groupId,attach);
				sendSuccessMessage(response, "上传成功");
			}
		}else{
			sendFailureMessage(response, "除管理员外，不能设置非本人的封面!");
		}
	}
	
	@RequestMapping("/selectUser")
	public void selectUser(HttpServletResponse response) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", Constants.STATUS_DEFAULT);
		map.put("isvalid", Constants.ISVALIAD_SHOW);
		List<User> userList = userBusiness.findUserListByMap(map);
		Object context = JSONArray.toJSON(userList);
		HtmlUtil.writerJson(response, context);
	}

}
