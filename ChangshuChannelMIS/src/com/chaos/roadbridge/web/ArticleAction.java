package com.chaos.roadbridge.web;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.chaos.roadbridge.model.Article;
import com.chaos.roadbridge.service.ArticleService;
import com.opensymphony.xwork2.Action;

@Component
@Scope("prototype")
public class ArticleAction {
	@Resource
	private ArticleService articleService;
	private Article article;
	private Long articleId;
	private String errorMessage;

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String add() {
		if (article == null) {
			errorMessage = "没有填写内容";
			return Action.ERROR;
		}
		articleService.add(article);
		return Action.SUCCESS;
	}

	public String delete() {
		if (articleId == null) {
			errorMessage = "没有指定文章";
			return Action.ERROR;
		}
		articleService.deleteById(articleId);
		return Action.SUCCESS;
	}

	public String update() {
		if (articleId == null) {
			errorMessage = "没有指定文章";
			return Action.ERROR;
		}
		articleService.update(articleId, article);
		return Action.SUCCESS;
	}

	public String publish() {
		if (articleId == null) {
			errorMessage = "没有指定文章";
			return Action.ERROR;
		}
		articleService.publishById(articleId);
		return Action.SUCCESS;
	}

	public String unpublish() {
		if (articleId == null) {
			errorMessage = "没有指定文章";
			return Action.ERROR;
		}
		articleService.unpublishById(articleId);
		return Action.SUCCESS;
	}
}
