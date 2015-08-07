package com.love.restful.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.love.blog.po.ArticleType;

@XmlRootElement(name="articleTypeList")
public class ArticleTypeList {
	
	private List<ArticleType> articleTypeList;

	public List<ArticleType> getArticleTypeList() {
		return articleTypeList;
	}

	public void setArticleTypeList(List<ArticleType> articleTypeList) {
		this.articleTypeList = articleTypeList;
	}

}
