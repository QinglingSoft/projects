package com.chaos.roadbridge.service;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chaos.roadbridge.model.CodeTable;

@Repository
@Transactional
public class CodeTableService {

	@Resource
	private SessionFactory sessionFactory;

	public CodeTable findByName(String name) {
		return (CodeTable) sessionFactory.getCurrentSession().get(
				CodeTable.class, name);
	}

}
