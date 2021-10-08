package com.GIS.MyGIS.DataAccess.Raster;

import org.geotools.map.Layer;
import org.geotools.tile.TileService;
import org.geotools.tile.impl.osm.OSMService;
import org.geotools.tile.util.TileLayer;

public class TileRasterData {

	TileService service;
	
	public Layer addTileLayer(String mapName, String baseURL)
	{
		service = new OSMService("Road", baseURL);
		Layer layer = new TileLayer(service);
		layer.setTitle(mapName);
		return layer;
	}
}
