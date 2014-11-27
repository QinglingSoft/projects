package com.chaos.roadbridge.web;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.chaos.roadbridge.model.Article;
import com.chaos.roadbridge.service.ArticleService;

@Component
@Scope("prototype")
public class ArticleHelper {
	@Resource
	private ArticleService articleService;
	private Long articleId;

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public Article getArticle() {
		return articleService.findById(articleId);
	}

}
