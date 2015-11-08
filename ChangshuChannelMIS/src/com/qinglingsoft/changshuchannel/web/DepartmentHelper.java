package com.qinglingsoft.changshuchannel.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.qinglingsoft.changshuchannel.model.T_GL_HDZZJG;
import com.qinglingsoft.changshuchannel.service.DepartmentService;

@Component
@Scope("prototype")
public class DepartmentHelper {
	@Resource
	private DepartmentService departmentService;

	public List<T_GL_HDZZJG> getAll() {
		return departmentService.findAll();
	}
}
	