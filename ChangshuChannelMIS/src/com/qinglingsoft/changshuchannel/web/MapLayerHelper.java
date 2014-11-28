package com.qinglingsoft.changshuchannel.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.qinglingsoft.changshuchannel.model.MapLayer;
import com.qinglingsoft.changshuchannel.service.MapLayerService;

@Component
@Scope("prototype")
public class MapLayerHelper {
	@Resource
	private MapLayerService mapLayerService;

	public List<MapLayer> getAll() {
		return mapLayerService.findAll();
	}
}
