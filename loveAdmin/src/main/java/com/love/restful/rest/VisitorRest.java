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

import com.love.framework.exception.ApplicationRuntimeException;

@Controller
@RequestMapping("services")
public class VisitorRest {
	
static Logger log = Logger.getLogger(VisitorRest.class.getName());
	
	@Resource
	private Jaxb2Marshaller jaxb2Mashaller;
	private static final String XML_VIEW_NAME = "visitor";//对应rest-servelt.xml文件
	private static final String XML_MODEL_NAME = "visitor";
	

}
