package com.GIS.MyGIS.DataAccess.Raster;

import org.geotools.coverage.grid.io.GridCoverage2DReader;
import org.geotools.styling.Style;

public class Raster {
	
	GridCoverage2DReader reader;
	Style imageStyle;
	String layerName;
	
	public Raster(GridCoverage2DReader reader, Style imageStyle, String layerName) 
	{
		this.reader = reader;
		this.imageStyle = imageStyle;
		this.layerName = layerName;
	}

	public GridCoverage2DReader getReader() {
		return reader;
	}

	public void setReader(GridCoverage2DReader reader) {
		this.reader = reader;
	}

	public Style getImageStyle() {
		return imageStyle;
	}

	public void setImageStyle(Style imageStyle) {
		this.imageStyle = imageStyle;
	}

	public String getLayerName() {
		return layerName;
	}

	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}
	
	
	
}

