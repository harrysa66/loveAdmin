package com.love.system.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.love.framework.controller.BaseController;
import com.love.framework.exception.ApplicationRuntimeException;
import com.love.framework.security.SpringSecurityUtils;
import com.love.system.biz.AttachmentBusiness;
import com.love.system.po.Attachment;

@Controller
@RequestMapping("system/attachment")
public class AttachmentController extends BaseController{
	
	@Resource
	private AttachmentBusiness attachmentBisiness;
	
	static Logger log = Logger.getLogger(AttachmentController.class.getName());
	
	/**
     * 上传数据界面 
     * @return 返回上传页面
     */
    @RequestMapping(value = "/processload.s")
    public String processLoad() {
        return "system/attachment/processload";
    } 
    
    /**
     * 通过附件实体上传
     * @param attachment
     * @param request
     * @throws Exception
     */
	@RequestMapping(value = "/save.s", method = RequestMethod.POST)
	@ResponseBody
	public void doSave(HttpServletRequest request,HttpServletResponse response) throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;    
        MultipartFile file = multipartRequest.getFile("processFile");
        InputStream in = (InputStream) file.getInputStream();//定义文件输入流
		String name = file.getOriginalFilename();
		System.out.println("文件大小:"+file.getSize()/1024);
		Attachment att = new Attachment();
		att.setUploadUserId(SpringSecurityUtils.getCurrentUser().getId());
		att.setUploadUserName(SpringSecurityUtils.getCurrentUser().getUsername());
        att.setFileName(name);
        att.setContentType(file.getContentType());
        att.setIn(in);
		attachmentBisiness.upload(att);
		sendSuccessMessage(response, "上传成功");
	}
	/**依据附件id附件下载
	 * @param id 附件id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/download.s", method = RequestMethod.GET)
	@ResponseBody
	public String download(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) {
		java.io.BufferedInputStream bis = null;    
		java.io.BufferedOutputStream bos = null;
		Attachment a = attachmentBisiness.getById(id);
		String downLoadPath = a.getSavePath() + File.separator + a.getSaveName();
		try {
			long fileLength = new File(downLoadPath).length();
			response.setContentType("application/x-msdownload;");
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(a.getFileName().getBytes("GBK"), "ISO8859-1"));
			response.setHeader("Content-Length", String.valueOf(fileLength));
			bis = new BufferedInputStream(new FileInputStream(downLoadPath));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			log.info("附件下载失败");
			throw new ApplicationRuntimeException("附件下载失败", e);
		} finally {
			if (bis != null) try {bis.close();} catch (IOException e) {throw new ApplicationRuntimeException("bis关闭失败", e);}
			if (bos != null) try {bos.close();} catch (IOException e) {throw new ApplicationRuntimeException("bos关闭失败", e);}
		}
		return null;
	}
	@RequestMapping(value = "/delete.s", method = RequestMethod.GET)
	@ResponseBody
	public String delete(@RequestParam("id") String id) {
		attachmentBisiness.deleteFile(id);
		return null;
	}
}