package com.qinglingsoft.changshuchannel.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.qinglingsoft.changshuchannel.model.User;
import com.qinglingsoft.webframework.transfer.ResultFragment;

@Repository
@Transactional
public class UserService {
	@Resource
	private SessionFactory sessionFactory;

	public User findByLoginName(String loginName) {
		if (loginName == null) {
			return null;
		}
		return (User) sessionFactory.getCurrentSession().get(User.class,
				loginName);
	}

	@SuppressWarnings("unchecked")
	public ResultFragment<User> fragment(int firstResult, int maxResults) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(
				User.class);
		crit.setProjection(Projections.rowCount());
		long totalCount = ((Number) crit.uniqueResult()).longValue();

		crit.setProjection(null);
		crit.setFirstResult(firstResult);
		crit.setMaxResults(maxResults);
		List<User> fragment = crit.list();

		ResultFragment<User> rf = new ResultFragment<User>(totalCount, fragment);
		return rf;
	}

	public void save(User user) {
		sessionFactory.getCurrentSession().persist(user);
	}

	public void update(User user) {
		sessionFactory.getCurrentSession().merge(user);
	}

	public void delete(String loginName) {
		sessionFactory.getCurrentSession().delete(findByLoginName(loginName));
	}
}
