package com.qinglingsoft.changshuchannel.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.qinglingsoft.changshuchannel.model.T_GL_HDZZJG;

@Repository
@Transactional
public class DepartmentService {
	@Resource
	private SessionFactory sessionFactory;


	@SuppressWarnings("unchecked")
	public List<T_GL_HDZZJG> findAll() {
		return sessionFactory.getCurrentSession()
				.createCriteria(T_GL_HDZZJG.class).list();
	}

	public T_GL_HDZZJG get(Long id) {
		return (T_GL_HDZZJG)sessionFactory.getCurrentSession().get(T_GL_HDZZJG.class, id);
	}

}
