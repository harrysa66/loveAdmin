package com.love.restful.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.love.blog.po.Article;

@XmlRootElement(name="articleList")
public class ArticleList {
	
	private List<Article> articleList;

	public List<Article> getArticleList() {
		return articleList;
	}

	public void setArticleList(List<Article> articleList) {
		this.articleList = articleList;
	}

}
