package com.GIS.MyGIS.UI.Map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.geotools.filter.text.cql2.CQLException;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.swing.JMapFrame;


public class MapFrame{
	private List<Layer> layers = new ArrayList<>();
	
	public void subLayers(List<Layer>... layerLists)
	{
		for (List<Layer> layerList : layerLists) 
			layers.addAll(layerList);
	}
	
	public MapContent getMap()
	{	
		MapContent map = new MapContent();
		for (Layer layer : layers) 
			map.addLayer(layer);
		return map;
	}
	public void showMap() throws CQLException, IOException
	{
		MapContent map = new MapContent();
		JMapFrame mapFrame = new JMapFrame(map);
		
		mapFrame.enableToolBar(true);
		mapFrame.enableStatusBar(true);
		mapFrame.enableLayerTable(true);

		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new FileMenu(map);
		JMenu layerMenu = new LayerMenu(map);
		JMenu dataMenu = new DataMenu(map);

		menuBar.add(fileMenu);
		menuBar.add(layerMenu);
		menuBar.add(dataMenu);
		
		
		mapFrame.setJMenuBar(menuBar);
		mapFrame.setSize(800, 600);
		mapFrame.setVisible(true);
		
		
		
	}
	
}
