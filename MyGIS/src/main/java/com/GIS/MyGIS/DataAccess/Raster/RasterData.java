package com.GIS.MyGIS.DataAccess.Raster;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.coverage.grid.io.GridCoverage2DReader;
import org.geotools.coverage.grid.io.GridFormatFinder;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.gce.geotiff.GeoTiffFormat;
import org.geotools.map.GridReaderLayer;
import org.geotools.map.Layer;
import org.geotools.styling.ChannelSelection;
import org.geotools.styling.ContrastEnhancement;
import org.geotools.styling.RasterSymbolizer;
import org.geotools.styling.SLD;
import org.geotools.styling.SelectedChannelType;
import org.geotools.styling.Style;
import org.geotools.styling.StyleFactory;
import org.geotools.swing.data.JFileDataStoreChooser;
import org.geotools.util.factory.Hints;
import org.opengis.filter.FilterFactory;
import org.opengis.style.ContrastMethod;

public class RasterData extends TileRasterData{

	private StyleFactory styleFactory = CommonFactoryFinder.getStyleFactory();
	private FilterFactory filterFactory = CommonFactoryFinder.getFilterFactory2();
	private List<Layer> layers = new ArrayList<Layer>();
	
	public void createLayers(Raster... rasters)
	{
		for (Raster raster : rasters) 
	        layers.add(createLayer(raster));
	}
	public Layer createLayer(Raster raster)
	{
			Layer imageLayer = new GridReaderLayer(raster.getReader(), raster.getImageStyle());
			imageLayer.setTitle(raster.layerName);
			return imageLayer;
	}
	public Raster readImage(String name) throws IOException
	{
		return new Raster(readImageFile(),imageStyle(),name);
	}
	
	public Raster readImage(String name, int band) throws IOException
	{
		return new Raster(readImageFile(),grayImageStyle(band),name);
	}
	
	private GridCoverage2DReader readImageFile() throws IOException
	{
		GridCoverage2DReader reader;
		
		File file = JFileDataStoreChooser.showOpenFile(".", null);
		AbstractGridFormat gridFormat = GridFormatFinder.findFormat(file);
		Hints hints = new Hints();
		
		if(gridFormat instanceof GeoTiffFormat)
			hints = new Hints(Hints.FORCE_LONGITUDE_FIRST_AXIS_ORDER, Boolean.TRUE);
		
		reader = gridFormat.getReader(file, hints);
		
		return reader;
	}
	private SelectedChannelType[] selectBands() throws IOException
	{
        ContrastEnhancement contrastEnhancement = styleFactory.contrastEnhancement(filterFactory.literal(1.0), ContrastMethod.NORMALIZE);
		
        return new SelectedChannelType[]
        		{
        				styleFactory.createSelectedChannelType("1", contrastEnhancement),
        				styleFactory.createSelectedChannelType("2", contrastEnhancement),
        				styleFactory.createSelectedChannelType("3", contrastEnhancement)
        		};
	}
	private Style imageStyle() throws IOException
	{
		RasterSymbolizer rasterSymbolizer = styleFactory.getDefaultRasterSymbolizer();
		SelectedChannelType[] selectedChannelTypes = selectBands();
		ChannelSelection channelSelection = styleFactory.channelSelection(selectedChannelTypes[0], selectedChannelTypes[1], selectedChannelTypes[2]);
		
		rasterSymbolizer.setChannelSelection(channelSelection);
		return SLD.wrapSymbolizers(rasterSymbolizer);
	}
	private Style grayImageStyle(int band)
	{
		RasterSymbolizer rasterSymbolizer = styleFactory.getDefaultRasterSymbolizer();
		ContrastEnhancement contrastEnhancement = styleFactory.contrastEnhancement(filterFactory.literal(1.0), ContrastMethod.NORMALIZE);
		SelectedChannelType selectedChannelType = styleFactory.createSelectedChannelType(String.valueOf(band), contrastEnhancement);
		
		ChannelSelection channelSelection = styleFactory.channelSelection(selectedChannelType);
		rasterSymbolizer.setChannelSelection(channelSelection);
		return SLD.wrapSymbolizers(rasterSymbolizer);
	}
}
