package com.chaos.roadbridge.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chaos.roadbridge.model.MapLayer;

@Repository
@Transactional
public class MapLayerService {
	@Resource
	private SessionFactory sessionFactory;

	private boolean cache;
	private List<MapLayer> all;

	@Resource
	public void setDataTableCache(Boolean dataTableCache) {
		if (dataTableCache == null) {
			return;
		}
		cache = dataTableCache;
	}

	@SuppressWarnings("unchecked")
	public List<MapLayer> findAll() {
		if (!cache || all == null) {
			all = sessionFactory.getCurrentSession()
					.createCriteria(MapLayer.class).list();
		}
		return all;
	}

	public List<Integer> findDefaultSelectedIds() {
		List<Integer> ids = new ArrayList<Integer>();
		for (MapLayer mapLayer : findAll()) {
			if (mapLayer.isDefaultSelected()) {
				ids.add(mapLayer.getId());
			}
		}
		return ids;
	}
}
