package com.qinglingsoft.changshuchannel.service;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.qinglingsoft.changshuchannel.model.CodeTable;

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
