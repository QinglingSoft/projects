package com.chaos.roadbridge.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qinglingsoft.webframework.helper.PageHelper;
import com.qinglingsoft.webframework.transfer.ResultFragment;

import com.chaos.roadbridge.model.Article;
import com.chaos.roadbridge.service.ArticleService;

@Service
public class ArticlePageHelper extends PageHelper<Article> {
	@Resource
	private ArticleService articleService;
	private Article.Type articleType;

	public void setArticleType(Article.Type articleType) {
		this.articleType = articleType;
	}

	@Override
	protected ResultFragment<Article> getResultFragment() {
		return articleService.fragmentByType(articleType, this.getPageStart(),
				this.getPageSize());
	}

}
