package com.chaos.roadbridge.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.chaos.roadbridge.model.Article;
import com.chaos.roadbridge.model.Article.Type;
import com.chaos.roadbridge.service.ArticleService;

@Component
@Scope("prototype")
public class PublishedArticleHelper {
	@Resource
	private ArticleService articleService;

	public List<Article> getNews() {
		return articleService.findPublished(Type.NEWS);
	}

	public List<Article> getNotices() {
		return articleService.findPublished(Type.NOTICE);
	}

}
