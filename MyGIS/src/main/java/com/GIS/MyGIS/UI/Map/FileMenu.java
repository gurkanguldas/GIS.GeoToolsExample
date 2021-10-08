package com.GIS.MyGIS.UI.Map;

import java.awt.event.ActionEvent;

import javax.swing.JMenu;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.swing.action.SafeAction;

import com.GIS.MyGIS.DataAccess.Raster.RasterData;
import com.GIS.MyGIS.DataAccess.Vector.VectorData;
import com.GIS.MyGIS.UI.CSVRead.CsvReadManager;

public class FileMenu extends JMenu
{
	VectorData vectorData = new VectorData();
	RasterData rasterData = new RasterData();
	MapContent mapContent;
	
	public FileMenu(MapContent mapContent) {
		super("File");
		this.mapContent = mapContent;
		add(OpenImageFile());
		add(OpenShapefile());
		add(OpenCSV());
		add(writeShapefile());
		addSeparator();
		add(exitMap());
	}
	
	public SafeAction OpenShapefile()
	{
		return new SafeAction("Open Shapefile") {
			
			@Override
			public void action(ActionEvent e) throws Throwable {
				Layer layer = vectorData.createLayer(vectorData.readShapefile(null, null));
				mapContent.layers().add(layer);
				
			}
		};
	}
	public SafeAction OpenImageFile()
	{
		return new SafeAction("Open Raster") {
			
			@Override
			public void action(ActionEvent e) throws Throwable {
				// TODO Auto-generated method stub
				mapContent.layers().add(rasterData.createLayer(rasterData.readImage("Image")));
			}
		};
	}
	public SafeAction OpenCSV()
	{
		return new SafeAction("Read CSV") {
			
			@Override
			public void action(ActionEvent e) throws Throwable {
				new CsvReadManager(null,mapContent);
				
			}
		};
	}
	public SafeAction writeShapefile()
	{
		return new SafeAction("Save Shapefile") {
			
			@Override
			public void action(ActionEvent e) throws Throwable {
				Layer layer = mapContent.layers().get(mapContent.layers().size() - 1);
				vectorData.writeShapefile((SimpleFeatureCollection) layer.getFeatureSource().getFeatures());
				
			}
		};
	}
	public SafeAction exitMap()
	{
		return new SafeAction("Exit") {
			
			@Override
			public void action(ActionEvent e) throws Throwable {
				System.exit(0);
				
			}
		};
	}
}
