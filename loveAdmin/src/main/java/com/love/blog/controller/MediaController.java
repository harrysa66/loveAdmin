package com.love.blog.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.love.blog.biz.MediaBusiness;
import com.love.blog.biz.MediaGroupBusiness;
import com.love.blog.po.Media;
import com.love.blog.po.MediaGroup;
import com.love.framework.common.Constants;
import com.love.framework.controller.BaseController;
import com.love.framework.dao.jdbc.Page;
import com.love.system.biz.AttachmentBusiness;
import com.love.system.biz.UserBusiness;
import com.love.system.po.Attachment;
import com.love.system.po.User;
import com.love.util.HtmlUtil;
import com.love.util.PageUtil;

@Controller
@RequestMapping("blog/media")
public class MediaController extends BaseController{
	
	static Logger log = Logger.getLogger(MediaGroupController.class.getName());
	
	@Resource
	private MediaBusiness mediaBusiness;
	
	@Resource
	private MediaGroupBusiness mediaGroupBusiness;
	
	@Resource
	private UserBusiness userBusiness;
	
	@Resource
	private AttachmentBusiness attachmentBusiness;
	
	@RequestMapping("/query")
	public ModelAndView  doQuery(HttpServletRequest request) throws Exception{
		Map<String,Object>  context = getRootMap();
		return forword("blog/media",context); 
	}
	
	@RequestMapping("/dataList")
	public void  dataList(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupId", ServletRequestUtils.getStringParameter(request, "groupId", "").trim());
		map.put("userId", ServletRequestUtils.getStringParameter(request, "groupId", "").trim());
		map.put("name", ServletRequestUtils.getStringParameter(request, "name", "").trim());
		map.put("isvalid", ServletRequestUtils.getStringParameter(request, "isvalid", "").trim());
		Page<Media> page = PageUtil.getPageObj(request);
		page = mediaBusiness.queryPage(map, page);
		
		//设置页面数据
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("total",page.getTotalCount());
		jsonMap.put("rows", page.getResult());
		HtmlUtil.writerJson(response, jsonMap);
	}
	
	@RequestMapping("/save")
	public void save(Media media,HttpServletResponse response){
		String message = mediaBusiness.save(media);
		sendSuccessMessage(response, message);
	}
	
	@RequestMapping("/view")
	public void view(String id,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = new HashMap<String,Object> ();
		Media media = mediaBusiness.findById(id);
		if(media == null){
			sendFailureMessage(response, "没有找到对应的记录!");
			return;
		}
		
		//将对象转成Map
		Map<String,Object> data = BeanUtils.describe(media);
		context.put(SUCCESS, true);
		context.put("data", data);
		HtmlUtil.writerJson(response, context);
	}
	
	@RequestMapping("/remove")
	public void remove(String[] id,HttpServletResponse response) throws Exception{
		if(null == id || "".equals(id) || id.length<1){
			sendFailureMessage(response, Constants.DELETE_ERROR);
		}
		boolean isAllow = mediaBusiness.removeMedias(id);
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
		boolean isAllow = mediaBusiness.runMedias(id);
		if(isAllow){
			sendSuccessMessage(response, Constants.DO_SUCCESS);
		}else{
			sendSuccessMessage(response, "除管理员外，不能操作非本人的文件!");
		}
	}
	
	@RequestMapping("/selectGroup")
	public void selectGroup(HttpServletResponse response) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", Constants.STATUS_DEFAULT);
		map.put("isvalid", Constants.ISVALIAD_SHOW);
		List<MediaGroup> typeList = mediaGroupBusiness.findListByMap(map);
		Object context = JSONArray.toJSON(typeList);
		HtmlUtil.writerJson(response, context);
	}
	
	@RequestMapping("/setGroup")
	public void setGroup(String groupId,String[] ids,HttpServletRequest request,HttpServletResponse response) throws IOException{
		String message = mediaBusiness.setGroup(groupId,ids);
		sendSuccessMessage(response, message);
	}
	
	@RequestMapping("/setName")
	public void setName(String params,HttpServletRequest request,HttpServletResponse response) throws IOException{
		String message = Constants.EDIT_SUCCESS;
		List<Media> mediaList = JSON.parseArray(params, Media.class);
		for(Media media : mediaList){
			if(!mediaBusiness.isSelf(media.getId())){
				message = "除了非本人上传的文件以外，其他文件设置成功(管理员除外)!";
			}
			mediaBusiness.save(media);
		}
		sendSuccessMessage(response, message);
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
	
	@RequestMapping("/getFileUrl")
	public void getFileUrl(String id,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = new HashMap<String,Object> ();
		Attachment attach = attachmentBusiness.getById(id);
		if(attach == null){
			sendFailureMessage(response, "没有找到对应的文件!");
			return;
		}
		File file = new File(attach.getSavePath()+"/"+attach.getSaveName());
		if(attach.getContentType().indexOf(Constants.IMAGE_TYPE) >= 0){
			BufferedImage img = null;
			try {
				img = ImageIO.read(file);
			} catch (IOException e1) {
				log.error(e1.toString());
				log.error("打开文件失败   ");
			}
			if(img != null){
				int imgwidth = img.getWidth();
				int imgheight = img.getHeight();
				context.put("width", imgwidth);
				context.put("height", imgheight);
				context.put("fileType", "image");
			}
		}else if(attach.getContentType().indexOf(Constants.AUDIO_TYPE) >= 0){
			context.put("width", 350);
			context.put("height", 100);
			context.put("fileType", attach.getContentType());
		}else if(attach.getContentType().indexOf(Constants.VIDEO_TYPE) >= 0){
			context.put("width", 600);
			context.put("height", 400);
			context.put("fileType", attach.getContentType());
		}else{
			context.put("fileType", attach.getContentType());
		}
		
		//将对象转成Map
		Map<String,Object> data = BeanUtils.describe(attach);
		context.put(SUCCESS, true);
		context.put("data", data);
		HtmlUtil.writerJson(response, context);
	}
	
	@RequestMapping("/fileUpload")
	public void fileUpload(HttpServletRequest request,HttpServletResponse response) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		Map<String,Object>  context = new HashMap<String,Object> ();
		List<Attachment> files = null;
		List<Attachment> attachList = getAttachmentList(request, "mediaFile");
		if(attachList != null && attachList.size() > 0){
			files = mediaBusiness.fileUpload(attachList);
		}
		context.put("files", files);
		HtmlUtil.writerJson(response, context);
	}

}
