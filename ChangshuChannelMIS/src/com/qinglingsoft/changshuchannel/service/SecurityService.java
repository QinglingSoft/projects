package com.qinglingsoft.changshuchannel.service;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.qinglingsoft.changshuchannel.model.SecurityUser;

@Repository
@Transactional
public class SecurityService {
	@Resource
	private SessionFactory sessionFactory;
	@Resource
	private EncryptService encryptService;

	public void initPasswordByLoginName(String loginName) {
		updatePassword(loginName, SecurityUser.DEFAULT_PASSWORD);
	}

	public boolean checkPassword(String loginName, String password) {
		SecurityUser securityUser = (SecurityUser) sessionFactory
				.getCurrentSession()
				.createCriteria(SecurityUser.class)
				.add(Restrictions.eq("loginName", loginName))
				.add(Restrictions.eq("encryptedPassword",
						encryptService.encrypt(password))).uniqueResult();
		return securityUser != null;
	}

	public void updatePassword(String loginName, String password) {
		SecurityUser su = (SecurityUser) sessionFactory.getCurrentSession()
				.get(SecurityUser.class, loginName);
		su.setEncryptedPassword(encryptService.encrypt(password));
	}
}
