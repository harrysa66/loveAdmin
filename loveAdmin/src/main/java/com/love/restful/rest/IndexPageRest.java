package com.love.restful.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

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

import com.love.blog.biz.IndexPageBusiness;
import com.love.blog.po.IndexPage;
import com.love.framework.exception.ApplicationRuntimeException;

@Controller
@RequestMapping("services")
public class IndexPageRest {
	
	static Logger log = Logger.getLogger(IndexPageRest.class.getName());
	
	@Resource
	private Jaxb2Marshaller jaxb2Mashaller;
	private static final String XML_VIEW_NAME = "indexPage";//对应rest-servelt.xml文件
	private static final String XML_MODEL_NAME = "indexPage";
	
	@Resource
	private IndexPageBusiness indexPageBusiness;
	
	/**
	 * 根据是否显示,查询文章类型集合
	 * @param body
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST, value="/indexPage/queryIndex.rest")
	public ModelAndView queryIndex(@RequestBody String body) {
		//输入参数日志
		log.info("RESTFUL interface name queryArticleType in!");
		log.info(body);
		Source source = new StreamSource(new StringReader(body));
		IndexPage indexPage = (IndexPage)jaxb2Mashaller.unmarshal(source);
		IndexPage info = indexPageBusiness.findById(indexPage.getId());
		//输出参数日志
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    Result result = new StreamResult(baos);
		jaxb2Mashaller.marshal(info, result);
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
		log.info("RESTFUL interface name queryIndex out!");
		return new ModelAndView(XML_VIEW_NAME, XML_MODEL_NAME, info);
	}

}
