package com.love.restful.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.love.blog.po.Article;
import com.love.framework.common.PageBean;

@XmlRootElement(name="articleList")
public class ArticleList {
	
	private List<Article> articleList;
	private PageBean<Article> articlePage;

	public List<Article> getArticleList() {
		return articleList;
	}

	public void setArticleList(List<Article> articleList) {
		this.articleList = articleList;
	}

	public PageBean<Article> getArticlePage() {
		return articlePage;
	}

	public void setArticlePage(PageBean<Article> articlePage) {
		this.articlePage = articlePage;
	}

}
