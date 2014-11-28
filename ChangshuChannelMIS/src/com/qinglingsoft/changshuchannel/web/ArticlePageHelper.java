package com.qinglingsoft.changshuchannel.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qinglingsoft.changshuchannel.model.Article;
import com.qinglingsoft.changshuchannel.service.ArticleService;
import com.qinglingsoft.webframework.helper.PageHelper;
import com.qinglingsoft.webframework.transfer.ResultFragment;

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
