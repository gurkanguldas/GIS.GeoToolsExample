package com.GIS.MyGIS.DataAccess.Vector;

import java.io.IOException;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.filter.text.cql2.CQL;
import org.geotools.filter.text.cql2.CQLException;
import org.geotools.map.Layer;
import org.geotools.swing.table.FeatureCollectionTableModel;
import org.opengis.filter.Filter;

public class VectorAttribute {

	public FeatureCollectionTableModel tableModel(Layer layer, String text) throws IOException, CQLException
	{
		if(text == null)
			text = "include";
		Filter filter = CQL.toFilter(text);
		SimpleFeatureCollection features = (SimpleFeatureCollection) layer.getFeatureSource().getFeatures(filter);
		return new FeatureCollectionTableModel(features);
	}
	
}
