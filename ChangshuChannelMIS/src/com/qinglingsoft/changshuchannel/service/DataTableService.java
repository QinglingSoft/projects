package com.qinglingsoft.changshuchannel.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.qinglingsoft.changshuchannel.model.DataTable;

@Repository
@Transactional
public class DataTableService {
	@Resource
	private SessionFactory sessionFactory;
	private boolean cache;
	private List<DataTable> allDataTable;
	private Map<String, DataTable> dataTableMap;

	@Resource
	public void setDataTableCache(Boolean dataTableCache) {
		if (dataTableCache == null) {
			return;
		}
		cache = dataTableCache;
	}

	public DataTable findByName(String name) {
		if (cache) {
			return findAllNameMap().get(name);
		} else {
			return (DataTable) sessionFactory.getCurrentSession().get(
					DataTable.class, name);
		}
	}

	public void persist(DataTable dataTable) {
		sessionFactory.getCurrentSession().persist(dataTable);
		dataTableMap = null;
	}

	private Map<String, DataTable> findAllNameMap() {
		if (dataTableMap == null) {
			dataTableMap = new HashMap<String, DataTable>();
			List<DataTable> dataTableList = findAll();
			for (DataTable dataTable : dataTableList) {
				dataTableMap.put(dataTable.getName(), dataTable);
			}
		}
		return dataTableMap;
	}

	@SuppressWarnings("unchecked")
	public List<DataTable> findByCatalog(String catalog) {
		return sessionFactory.getCurrentSession()
				.createCriteria(DataTable.class)
				.add(Restrictions.eq("catalog", catalog)).list();
	}

	@SuppressWarnings("unchecked")
	public List<DataTable> findAll() {
		if (cache) {
			if (allDataTable == null) {
				allDataTable = sessionFactory.getCurrentSession()
						.createCriteria(DataTable.class).list();
			}
			return allDataTable;
		} else {
			return sessionFactory.getCurrentSession()
					.createCriteria(DataTable.class).list();
		}
	}
}
