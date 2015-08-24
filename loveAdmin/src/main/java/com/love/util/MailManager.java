package com.love.util;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class MailManager {
	
	static Logger log = Logger.getLogger(MailManager.class.getName());
	
	private JavaMailSender mailSender;
	
	private  String textTemplate;//要发送的文本信息
	
	private static final String  EMAIL_FROM = "admin@hebingqing.cn";

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	
	public void setTextTemplate(String textTemplate) {
		this.textTemplate = textTemplate;
	}

	/**
	 * 发送纯文本的通知邮件.
	 */
	public void sendNotificationMail(String subject , String  content , String  toEmail) {
		SimpleMailMessage msg = new SimpleMailMessage();//SimpleMailMessage只能用来发送text文本
		msg.setFrom(EMAIL_FROM);//此处用QQ测试必须和xml中发送端保持的一致否则报错(但是网上说发送者,这里还可以另起Email别名，不用和xml里的username一致？？) 
		msg.setTo(toEmail);//接受者
		msg.setSubject(subject);//主题
		msg.setText(content);//正文内容
		try {
			mailSender.send(msg);//发送邮件
			log.info("邮件发送成功!");
		} catch (Exception e) {
			log.info("邮件发送失败:"+e);
		}
	}
	
	public static void main(String[] args) {  
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");     
        MailManager mailManager=(MailManager) ctx.getBean("mailManager");  
        mailManager.sendNotificationMail("test", "测试spring发邮件功能！！！！", "harrysa66@163.com");  
        System.out.println("已发送！！！");  
  
    }  

}
