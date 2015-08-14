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

import com.love.blog.biz.BoardBusiness;
import com.love.blog.biz.VisitorBusiness;
import com.love.blog.po.Board;
import com.love.blog.po.Visitor;
import com.love.framework.exception.ApplicationRuntimeException;
import com.love.restful.vo.BoardList;
import com.love.restful.vo.CodeMessgae;

@Controller
@RequestMapping("services")
public class BoardRest {
	
static Logger log = Logger.getLogger(BoardRest.class.getName());
	
	@Resource
	private Jaxb2Marshaller jaxb2Mashaller;
	private static final String XML_VIEW_NAME = "board";//对应rest-servelt.xml文件
	private static final String XML_MODEL_NAME = "board";
	
	@Resource
	private BoardBusiness boardBusiness;
	
	@Resource
	private VisitorBusiness visitorBusiness;
	
	/**
	 * 根据状态查询
	 * @param body(status,isvalid)
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST, value="/board/queryBoards.rest")
	public ModelAndView queryBoards(@RequestBody String body) {
		//输入参数日志
		log.info("RESTFUL interface name queryBoards in!");
		log.info(body);
		Source source = new StreamSource(new StringReader(body));
		Board board = (Board) jaxb2Mashaller.unmarshal(source);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", board.getStatus());
		map.put("isvalid", board.getIsvalid());
		List<Board> boardList = boardBusiness.findListByStatus(map);
		BoardList listVo = new BoardList();
		listVo.setBoardList(boardList);
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
		log.info("RESTFUL interface name queryBoards out!");
		return new ModelAndView(XML_VIEW_NAME, XML_MODEL_NAME, listVo);
	}
	
	/**
	 * 根据访客查询最大留言时间
	 * @param body(content,ip)
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(method=RequestMethod.POST, value="/board/writeBoard.rest")
	public ModelAndView writeBoard(@RequestBody String body) throws Exception {
		//输入参数日志
		log.info("RESTFUL interface name writeBoard in!");
		log.info(body);
		Source source = new StreamSource(new StringReader(body));
		Board board = (Board) jaxb2Mashaller.unmarshal(source);
		Visitor visitor = visitorBusiness.findByIp(board.getIp());
		CodeMessgae codeMessgae = new CodeMessgae();
		if(visitor == null){
			codeMessgae = boardBusiness.writeBoardNoVisitor(board.getIp(),board.getContent());
		}else{
			codeMessgae = boardBusiness.writeBoardWithVisitor(visitor.getId(),board.getContent());
		}
		//输出参数日志
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    Result result = new StreamResult(baos);
		jaxb2Mashaller.marshal(codeMessgae, result);
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
		log.info("RESTFUL interface name writeBoard out!");
		return new ModelAndView(XML_VIEW_NAME, XML_MODEL_NAME, codeMessgae);
	}

}
