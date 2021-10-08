package com.GIS.MyGIS.DataAccess.Vector;

import java.util.ArrayList;
import java.util.List;

import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.map.Layer;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

public class VectorDataType extends VectorAttribute
{
	public List<Layer> layers = new ArrayList<Layer>();
	public SimpleFeatureBuilder featureBuilder;
	public GeometryFactory geometryFactory;
	public int numInValidGeometries = 0;
	
	public SimpleFeatureType createFeatureType(String name , int lat , int lon , String[] header)
	{
		SimpleFeatureTypeBuilder featureTypeBuilder = new SimpleFeatureTypeBuilder();
		featureTypeBuilder.setName(name);
		featureTypeBuilder.setCRS(DefaultGeographicCRS.WGS84);
		
		featureTypeBuilder.add("the_geom",Point.class);
		
		for (int i = 0; i < header.length; i++) {
			if(i == lat || i == lon)
				continue;
			else
				featureTypeBuilder.add(header[i].trim(),String.class);	
		}

		return featureTypeBuilder.buildFeatureType();
	}
	
	public void addFeatures(String[] tokens , int lat , int lon) 
	{
		double latitude = Double.valueOf(tokens[lat]);
		double longitude = Double.valueOf(tokens[lon]);
		
		Point point = geometryFactory.createPoint(new Coordinate(longitude,latitude));
		featureBuilder.add(point);

		for (int i = 0; i < tokens.length; i++) {
			if(i == lat || i == lon)
				continue;
			else
				featureBuilder.add(tokens[i].trim());	
		}
	}
	
	public SimpleFeatureCollection setRenameFeatureCollection(SimpleFeatureCollection featureCollection, String name)
	{
		SimpleFeatureType schema = featureCollection.getSchema();
		
		SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
	    builder.setName(name);
	    builder.setSuperType((SimpleFeatureType) schema.getSuper());
	    builder.addAll(schema.getAttributeDescriptors());
	    
	    SimpleFeatureType featureType = builder.buildFeatureType();
	    
	    List<SimpleFeature> features = new ArrayList<>();
	    SimpleFeatureIterator featureIterator = featureCollection.features();
	    
	    try {
	        while( featureIterator.hasNext()  ){
	             SimpleFeature feature = featureIterator.next();
	             features.add(feature);
	        }
	    }
	    finally {
	    	featureIterator.close();
	    }
	    return new ListFeatureCollection(featureType, features);
	}
}
