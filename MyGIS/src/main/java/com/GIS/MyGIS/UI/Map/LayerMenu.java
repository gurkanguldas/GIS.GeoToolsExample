package com.GIS.MyGIS.UI.Map;

import java.awt.event.ActionEvent;

import javax.swing.JMenu;

import org.geotools.map.MapContent;
import org.geotools.swing.action.SafeAction;

import com.GIS.MyGIS.DataAccess.Raster.RasterData;

public class LayerMenu extends JMenu
{
	RasterData rasterData = new RasterData();
	MapContent mapContent;
	
	public LayerMenu(MapContent mapContent) 
	{
		super("Layer");
		this.mapContent = mapContent;
		add(addOpenStreetMap());
		add(addWmfLabs());
	}
	
	public SafeAction addOpenStreetMap()
	{
		return new SafeAction("Open Street Map") {
			@Override
			public void action(ActionEvent e) throws Throwable {
				mapContent.layers().add(rasterData.addTileLayer("OpenStreetMap","http://tile.openstreetmap.org/"));
			}
		};
	}
	public SafeAction addWmfLabs()
	{
		return new SafeAction("Wmflabs Map") {
			@Override
			public void action(ActionEvent e) throws Throwable {
				mapContent.layers().add(rasterData.addTileLayer("WmflabsMap","https://tiles.wmflabs.org/osm/"));
			}
		};
	}
}
