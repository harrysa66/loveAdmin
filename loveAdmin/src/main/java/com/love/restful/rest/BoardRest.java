package com.love.restful.rest;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("services")
public class BoardRest {
	
static Logger log = Logger.getLogger(ArticleRest.class.getName());
	
	@Resource
	private Jaxb2Marshaller jaxb2Mashaller;
	private static final String XML_VIEW_NAME = "board";//对应rest-servelt.xml文件
	private static final String XML_MODEL_NAME = "board";

}
