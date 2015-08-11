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

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.love.blog.biz.ArticleBusiness;
import com.love.blog.biz.ArticleTypeBusiness;
import com.love.blog.po.Article;
import com.love.blog.po.ArticleType;
import com.love.framework.common.Constants;
import com.love.framework.common.PageBean;
import com.love.framework.dao.jdbc.Page;
import com.love.framework.exception.ApplicationRuntimeException;
import com.love.restful.vo.ArticleList;
import com.love.restful.vo.ArticleTypeList;
import com.love.util.PageUtil;

@Controller
@RequestMapping("services")
public class ArticleRest {
	
	static Logger log = Logger.getLogger(ArticleRest.class.getName());
	
	@Resource
	private Jaxb2Marshaller jaxb2Mashaller;
	private static final String XML_VIEW_NAME = "article";//对应rest-servelt.xml文件
	private static final String XML_MODEL_NAME = "article";
	
	@Resource
	private ArticleBusiness articleBusiness;
	
	@Resource
	private ArticleTypeBusiness articleTypeBusiness;
	
	/**
	 * 根据是否显示,查询文章类型集合
	 * @param body
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST, value="/article/queryArticleType.rest")
	public ModelAndView queryArticleType(@RequestBody String body) {
		//输入参数日志
		log.info("RESTFUL interface name queryArticleType in!");
		log.info(body);
		Source source = new StreamSource(new StringReader(body));
		ArticleType articleType = (ArticleType) jaxb2Mashaller.unmarshal(source);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isshow", articleType.getIsshow());
		List<ArticleType> typeList = articleTypeBusiness.findListByShow(map);
		ArticleTypeList listVo = new ArticleTypeList();
		listVo.setArticleTypeList(typeList);
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
		log.info("RESTFUL interface name queryArticleType out!");
		return new ModelAndView(XML_VIEW_NAME, XML_MODEL_NAME, listVo);
	}
	
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
		ArticleList articleListVo = new ArticleList();
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
		return new ModelAndView(XML_VIEW_NAME, XML_MODEL_NAME, articleListVo);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/article/queryByDisplay.rest")
	public ModelAndView queryByDisplay(@RequestBody String body) {
		//输入参数日志
		log.info("RESTFUL interface name queryByDisplay in!");
		log.info(body);
		Source source = new StreamSource(new StringReader(body));
		ArticleType articleType = (ArticleType) jaxb2Mashaller.unmarshal(source);
		List<Article> articleList = articleBusiness.findListByDisplay(articleType);
		ArticleList articleListVo = new ArticleList();
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
		log.info("RESTFUL interface name queryByDisplay out!");
		return new ModelAndView(XML_VIEW_NAME, XML_MODEL_NAME, articleListVo);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/article/findById.rest")
	public ModelAndView findById(@RequestBody String body) {
		//输入参数日志
		log.info("RESTFUL interface name findById in!");
		log.info(body);
		Source source = new StreamSource(new StringReader(body));
		Article param = (Article) jaxb2Mashaller.unmarshal(source);
		Article article = articleBusiness.findById(param.getId());
		//输出参数日志
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    Result result = new StreamResult(baos);
	    jaxb2Mashaller.marshal(article, result);
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
		log.info("RESTFUL interface name findById out!");
		return new ModelAndView(XML_VIEW_NAME, XML_MODEL_NAME, article);
	}
	
	/*@RequestMapping(method=RequestMethod.POST, value="/article/queryByPage.rest")
	public ModelAndView queryByPage(@RequestBody String body) {
		//输入参数日志
		log.info("RESTFUL interface name queryByPage in!");
		log.info(body);
		Source source = new StreamSource(new StringReader(body));
		ArticleType articleType = (ArticleType) jaxb2Mashaller.unmarshal(source);
		Page<Article> page = PageUtil.getPageObj(articleType.getPageMap());
		ArticleType type = articleTypeBusiness.findByDisplay(articleType.getDisplay().toString());
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("typeId", type.getId());
		page = articleBusiness.queryPage(param, page);
		PageBean<Article> pageBean = new PageBean<Article>();
		try {
			BeanUtils.copyProperties(pageBean, page);
		} catch (Exception e) {
			log.info(e);
		}
		//List<Article> articleList = articleBusiness.findListByDisplay(articleType);
		ArticleList articleListVo = new ArticleList();
		articleListVo.setArticleList(articleList);
		ArticleList articleList = new ArticleList();
		articleList.setArticlePage(pageBean);
		articleList.setArticleList(pageBean.getResult());
		//输出参数日志
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    Result result = new StreamResult(baos);
	    jaxb2Mashaller.marshal(articleList, result);
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
		log.info("RESTFUL interface name queryByPage out!");
		return new ModelAndView(XML_VIEW_NAME, XML_MODEL_NAME, articleList);
	}*/
	
	public static void main(String[] args) {
		RestTemplate restTemplate = new RestTemplate(); 
		ArticleType articleType = new ArticleType();
		articleType.setDisplay(1);
		Map<String, Object> pageMap = new HashMap<String, Object>();
		pageMap.put("rows", "1");
		pageMap.put("page", "2");
		pageMap.put("totalCount", "0");
		articleType.setPageMap(pageMap);
		String url="http://127.0.0.1:8080/loveAdmin/services/article/queryByPage.rest";//请求路径
		ArticleList listVo = restTemplate.postForObject(url, articleType, ArticleList.class);
		for(Article article : listVo.getArticleList()){
			System.out.println(article.getContent());
		}
		/*ArticleList listVo = restTemplate.postForObject(url, articleType, ArticleList.class);
		for(Article article : listVo.getArticleList()){
			System.out.println(article.getContent());
		}*/
		/*ArticleType articleType = new ArticleType();
		articleType.setIsshow(null);
		String url="http://127.0.0.1:8080/loveAdmin/services/article/queryArticleType.rest";//请求路径
		ArticleTypeList list = restTemplate.postForObject(url, articleType, ArticleTypeList.class);
		for(ArticleType type : list.getArticleTypeList()){
			System.out.println(type.getName());
		}*/
		
	}

}
