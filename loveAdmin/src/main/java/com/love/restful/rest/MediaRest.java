package com.love.restful.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.love.blog.biz.MediaBusiness;
import com.love.blog.biz.MediaGroupBusiness;
import com.love.blog.biz.MediaTypeBusiness;
import com.love.blog.po.Media;
import com.love.blog.po.MediaGroup;
import com.love.blog.po.MediaType;
import com.love.framework.common.Constants;
import com.love.framework.exception.ApplicationRuntimeException;
import com.love.restful.vo.MediaGroupList;
import com.love.restful.vo.MediaList;
import com.love.restful.vo.MediaTypeList;

@Controller
@RequestMapping("services")
public class MediaRest {
	
static Logger log = Logger.getLogger(MediaRest.class.getName());
	
	@Resource
	private Jaxb2Marshaller jaxb2Mashaller;
	private static final String XML_VIEW_NAME = "media";//对应rest-servelt.xml文件
	private static final String XML_MODEL_NAME = "media";
	
	@Resource
	private MediaTypeBusiness mediaTypeBusiness;
	
	@Resource
	private MediaGroupBusiness mediaGroupBusiness;
	
	@Resource
	private MediaBusiness mediaBusiness;
	
	@RequestMapping(method=RequestMethod.POST, value="/media/queryMediaType.rest")
	public ModelAndView queryMediaType(@RequestBody String body) {
		//输入参数日志
		log.info("RESTFUL interface name queryMediaType in!");
		log.info(body);
		Source source = new StreamSource(new StringReader(body));
		MediaType mediaType = (MediaType)jaxb2Mashaller.unmarshal(source);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isshow", mediaType.getIsshow());
		List<MediaType> typeList = mediaTypeBusiness.findListByShow(map);
		MediaTypeList listVo = new MediaTypeList();
		listVo.setMediaTypeList(typeList);
		
		//输出参数日志
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    Result result = new StreamResult(baos);
	    jaxb2Mashaller.marshal(listVo, result);
		String outXml="";
		try {
			outXml = new String(baos.toString().getBytes("GBK"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new ApplicationRuntimeException("字符串编码转换失败",e);
		} finally {
			try {
				baos.close();
			} catch (IOException e) {
				throw new ApplicationRuntimeException("关闭失败",e);
			}
		}
		log.info(outXml);
		log.info("RESTFUL interface name queryMediaType out!");
		return new ModelAndView(XML_VIEW_NAME, XML_MODEL_NAME, listVo);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/media/queryGroupByType.rest")
	public ModelAndView queryGroupByType(@RequestBody String body) {
		//输入参数日志
		log.info("RESTFUL interface name queryGroupByType in!");
		log.info(body);
		Source source = new StreamSource(new StringReader(body));
		MediaType mediaType = (MediaType)jaxb2Mashaller.unmarshal(source);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("typeId", mediaType.getId());
		param.put("status", Constants.STATUS_DEFAULT);
		List<MediaGroup> mediaGroupList = mediaGroupBusiness.findListByType(param);
		MediaGroupList listVo = new MediaGroupList();
		listVo.setMediaGroupList(mediaGroupList);
		//输出参数日志
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    Result result = new StreamResult(baos);
	    jaxb2Mashaller.marshal(listVo, result);
		String outXml="";
		try {
			outXml = new String(baos.toString().getBytes("GBK"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new ApplicationRuntimeException("字符串编码转换失败",e);
		} finally {
			try {
				baos.close();
			} catch (IOException e) {
				throw new ApplicationRuntimeException("关闭失败",e);
			}
		}
		log.info(outXml);
		log.info("RESTFUL interface name queryGroupByType out!");
		return new ModelAndView(XML_VIEW_NAME, XML_MODEL_NAME, listVo);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/media/queryGroupByDisplay.rest")
	public ModelAndView queryGroupByDisplay(@RequestBody String body) {
		//输入参数日志
		log.info("RESTFUL interface name queryGroupByDisplay in!");
		log.info(body);
		Source source = new StreamSource(new StringReader(body));
		MediaType mediaType = (MediaType)jaxb2Mashaller.unmarshal(source);
		List<MediaGroup> mediaGroupList = mediaGroupBusiness.findListByDisplay(mediaType);
		MediaGroupList listVo = new MediaGroupList();
		listVo.setMediaGroupList(mediaGroupList);
		//输出参数日志
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    Result result = new StreamResult(baos);
	    jaxb2Mashaller.marshal(listVo, result);
		String outXml="";
		try {
			outXml = new String(baos.toString().getBytes("GBK"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new ApplicationRuntimeException("字符串编码转换失败",e);
		} finally {
			try {
				baos.close();
			} catch (IOException e) {
				throw new ApplicationRuntimeException("关闭失败",e);
			}
		}
		log.info(outXml);
		log.info("RESTFUL interface name queryGroupByDisplay out!");
		return new ModelAndView(XML_VIEW_NAME, XML_MODEL_NAME, listVo);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/media/queryMediasByGroup.rest")
	public ModelAndView queryMediasByGroup(@RequestBody String body) {
		//输入参数日志
		log.info("RESTFUL interface name queryMediasByGroup in!");
		log.info(body);
		Source source = new StreamSource(new StringReader(body));
		MediaGroup mediaGroup = (MediaGroup)jaxb2Mashaller.unmarshal(source);
		List<Media> mediaList = mediaBusiness.findListByGroup(mediaGroup.getId());
		MediaList listVo = new MediaList();
		listVo.setMediaList(mediaList);
		//输出参数日志
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    Result result = new StreamResult(baos);
	    jaxb2Mashaller.marshal(listVo, result);
		String outXml="";
		try {
			outXml = new String(baos.toString().getBytes("GBK"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new ApplicationRuntimeException("字符串编码转换失败",e);
		} finally {
			try {
				baos.close();
			} catch (IOException e) {
				throw new ApplicationRuntimeException("关闭失败",e);
			}
		}
		log.info(outXml);
		log.info("RESTFUL interface name queryMediasByGroup out!");
		return new ModelAndView(XML_VIEW_NAME, XML_MODEL_NAME, listVo);
	}

}
