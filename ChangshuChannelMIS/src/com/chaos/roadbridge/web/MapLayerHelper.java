package com.chaos.roadbridge.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.chaos.roadbridge.model.MapLayer;
import com.chaos.roadbridge.service.MapLayerService;

@Component
@Scope("prototype")
public class MapLayerHelper {
	@Resource
	private MapLayerService mapLayerService;

	public List<MapLayer> getAll() {
		return mapLayerService.findAll();
	}
}
