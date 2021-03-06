package com.love.framework.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.love.framework.common.MyEditor;
import com.love.system.po.Attachment;
import com.love.util.FileUtil;
import com.love.util.HtmlUtil;
import com.love.util.URLUtils;

public class BaseController {
public final static String SUCCESS ="success";  
	
	public final static String MSG ="msg";  
	
	
	public final static String DATA ="data";  
	
	public final static String LOGOUT_FLAG = "logoutFlag";  
	
	
   @InitBinder  
   protected void initBinder(WebDataBinder binder) {  
		 binder.registerCustomEditor(Date.class, new CustomDateEditor(
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));  
		 binder.registerCustomEditor(int.class,new MyEditor()); 
   }  
	 
	 /**
	  * 获取IP地址
	  * @param request
	  * @return
	  */
	 public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	 
	 /**
	  * 所有ActionMap 统一从这里获取
	  * @return
	  */
	public Map<String,Object> getRootMap(){
		Map<String,Object> rootMap = new HashMap<String, Object>();
		//添加url到 Map中
		rootMap.putAll(URLUtils.getUrlMap());
		return rootMap;
	}
	
	public ModelAndView forword(String viewName,Map context){
		return new ModelAndView(viewName,context); 
	}
	
	public ModelAndView error(String errMsg){
		return new ModelAndView("error"); 
	}
	
	/**
	 *
	 * 提示成功信息
	 *
	 * @param message
	 *
	 */
	public void sendSuccessMessage(HttpServletResponse response,  String message) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SUCCESS, true);
		result.put(MSG, message);
		HtmlUtil.writerJson(response, result);
	}

	/**
	 *
	 * 提示失败信息
	 *
	 * @param message
	 *
	 */
	public void sendFailureMessage(HttpServletResponse response,String message) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SUCCESS, false);
		result.put(MSG, message);
		HtmlUtil.writerJson(response, result);
	}
	
	protected Attachment getAttachment(HttpServletRequest request,String fileName) throws IOException{
		if(!(request instanceof MultipartHttpServletRequest)){
			return null;
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile(fileName);
		if(file == null){
			return null;
		}
		InputStream in = (InputStream) file.getInputStream();//定义文件输入流
		Attachment attachment = new Attachment();
		attachment.setSize(FileUtil.FormetFileSize(file.getSize()));
		attachment.setFileName(file.getOriginalFilename());
		attachment.setEntityType(fileName);
		attachment.setContentType(file.getContentType());
		attachment.setIn(in);
		return attachment;
	}
	
	protected List<Attachment> getAttachmentList(HttpServletRequest request,String fileName) throws IOException{
		List<Attachment> attachList = new ArrayList<Attachment>();
		if(!(request instanceof MultipartHttpServletRequest)){
			return null;
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Iterator<String> itr =  multipartRequest.getFileNames();
		MultipartFile file = null;
		Attachment attachment = null;
		while(itr.hasNext()){
			file = multipartRequest.getFile(itr.next());
			if(file == null){
				return null;
			}
			InputStream in = (InputStream) file.getInputStream();//定义文件输入流
			attachment = new Attachment();
			attachment.setFileName(file.getOriginalFilename());
			attachment.setSize(FileUtil.FormetFileSize(file.getSize()));
			attachment.setEntityType(fileName);
			attachment.setContentType(file.getContentType());
			attachment.setIn(in);
			attachList.add(attachment);
		}
		return attachList;
	}
}
