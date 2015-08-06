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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.love.blog.biz.ArticleBusiness;
import com.love.blog.po.Article;
import com.love.blog.po.ArticleType;
import com.love.framework.common.Constants;
import com.love.framework.exception.ApplicationRuntimeException;
import com.love.restful.vo.ArticleListVo;

@Controller
@RequestMapping("services")
public class ArticleRest {
	
	static Logger log = Logger.getLogger(ArticleRest.class.getName());
	
	@Resource
	private Jaxb2Marshaller jaxb2Mashaller;
	private static final String XML_VIEW_NAME = "article";//对应rest-servelt.xml文件
	
	@Resource
	private ArticleBusiness articleBusiness;
	
	@RequestMapping(method=RequestMethod.POST, value="/article/queryByType.rest")
	public ModelAndView queryByType(@RequestBody String body) {
		//输入参数日志
		log.info("RESTFUL interface name queryByType in!");
		log.info(body);
		Source source = new StreamSource(new StringReader(body));
		ArticleType articleType = (ArticleType) jaxb2Mashaller.unmarshal(source);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("typeId", articleType.getId());
		param.put("status", Constants.STATUS_DEFAULT);
		List<Article> articleList = articleBusiness.findListByType(param);
		ArticleListVo articleListVo = new ArticleListVo();
		articleListVo.setArticleList(articleList);
		//输出参数日志
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    Result result = new StreamResult(baos);
	    jaxb2Mashaller.marshal(articleListVo, result);
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
		log.info("RESTFUL interface name queryByType out!");
		return new ModelAndView(XML_VIEW_NAME, "article", articleListVo);
	}
	
	public static void main(String[] args) {
		RestTemplate restTemplate = new RestTemplate(); 
		ArticleType articleType = new ArticleType();
		articleType.setId("d963a7f734424f0d89f895665681d953");
		String url="http://127.0.0.1:8080/loveAdmin/services/article/queryByType.rest";//请求路径
		ArticleListVo listVo = restTemplate.postForObject(url, articleType, ArticleListVo.class);
		for(Article article : listVo.getArticleList()){
			System.out.println(article.getContent());
		}
	}

}
