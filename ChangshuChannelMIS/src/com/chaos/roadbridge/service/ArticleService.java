package com.chaos.roadbridge.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chaos.roadbridge.model.Article;
import com.chaos.roadbridge.model.Article.Type;
import com.qinglingsoft.webframework.transfer.ResultFragment;

@Repository
@Transactional
public class ArticleService {

	@Resource
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public ResultFragment<Article> fragmentByType(Type type, int firstResult,
			int maxResults) {

		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				Article.class);
		crit.add(Restrictions.eq("type", type));
		crit.setProjection(Projections.rowCount());
		long totalCount = ((Number) crit.uniqueResult()).longValue();
		crit.setProjection(null);
		crit.addOrder(Order.desc("latestModified"));
		crit.setFirstResult(firstResult);
		crit.setMaxResults(maxResults);
		List<Article> fragment = crit.list();
		ResultFragment<Article> rf = new ResultFragment<Article>(totalCount,
				fragment);
		return rf;
	}

	public void add(Article article) {
		article.setLatestModified(new Date());
		sessionFactory.getCurrentSession().persist(article);
	}

	public void deleteById(Long id) {
		sessionFactory.getCurrentSession().delete(findById(id));
	}

	public Article findById(Long id) {
		return (Article) sessionFactory.getCurrentSession().get(Article.class,
				id);
	}

	public void update(Long id, Article newValue) {
		Article old = findById(id);
		old.setContent(newValue.getContent());
		old.setLatestModified(new Date());
		old.setTitle(newValue.getTitle());
	}

	public void publishById(Long id) {
		Article article = findById(id);
		article.setPublished(true);
		article.setPublishTime(new Date());
	}

	public void unpublishById(Long id) {
		Article article = findById(id);
		article.setPublished(false);
	}

	@SuppressWarnings("unchecked")
	public List<Article> findPublished(Type type) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				Article.class);
		crit.add(Restrictions.eq("published", true));
		crit.add(Restrictions.eq("type", type));
		crit.addOrder(Order.desc("publishTime"));
		return crit.list();
	}

}
