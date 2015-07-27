package com.love.framework.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.love.util.VerifyCodeUtils;

@Controller
public class VerifyCodeController {

	@RequestMapping("/verifyCode.s")
	public void verifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		//生成随机字串
		String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
		//存入会话session
		HttpSession session = request.getSession(true);
		session.setAttribute("rand_verifyCode", verifyCode.toLowerCase());
		//生成图片
		int w = 200, h = 80;
		VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
	}
}

/**
 * Copyright © 2014,天安怡和 All rights reserved.
 */