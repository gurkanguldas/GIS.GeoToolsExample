package com.GIS.MyGIS.DataAccess.Vector;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geotools.data.DefaultTransaction;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.Transaction;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.filter.text.cql2.CQL;
import org.geotools.filter.text.cql2.CQLException;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.data.JFileDataStoreChooser;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;


public class VectorData extends VectorDataType {

	public void createLayers(String[] filters, SimpleFeatureCollection... collections) {
		for (SimpleFeatureCollection simpleFeatureCollection : collections)
			layers.add(createLayer(simpleFeatureCollection));
	}

	public Layer createLayer(SimpleFeatureCollection collection, String filter) throws CQLException {
		if (filter == null)
			filter = "include";
		Filter f = CQL.toFilter(filter);
		Style style = SLD.createSimpleStyle(collection.subCollection(f).getSchema());
		return new FeatureLayer(collection.subCollection(f), style);
	}

	public Layer createLayer(SimpleFeatureCollection collection) {
		Style style = SLD.createSimpleStyle(collection.getSchema());
		return new FeatureLayer(collection, style);
	}
	public SimpleFeatureCollection createShapefileFromCsv(String name, String filter, BufferedReader reader, int lat , int lon , String header[]) throws IOException, CQLException {
		List<SimpleFeature> features = new ArrayList<>();
		geometryFactory = JTSFactoryFinder.getGeometryFactory();

		
		SimpleFeatureType featureType = createFeatureType(name , lat , lon , header);

		featureBuilder = new SimpleFeatureBuilder(featureType);
		String[] tokens ;
		String read = reader.readLine();
		while (read != null) {
			tokens = read.split(",");
			addFeatures(tokens, lat , lon);

			SimpleFeature feature = featureBuilder.buildFeature(null);
			features.add(feature);

			read = reader.readLine();
		}
		SimpleFeatureCollection featureCollection = new ListFeatureCollection(featureType, features);

		if (filter == null)
			filter = "include";
		Filter f = CQL.toFilter(filter);

		return featureCollection.subCollection(f);
	}

	public SimpleFeatureCollection readShapefile(String name, String filter) throws IOException, CQLException {
		File file = JFileDataStoreChooser.showOpenFile("shp", null);
		if (file == null) {
			return null;
		}
		FileDataStore dataStore = FileDataStoreFinder.getDataStore(file);
		SimpleFeatureSource featureSource = dataStore.getFeatureSource(dataStore.getTypeNames()[0]);
		
		if (filter == null)
			filter = "include";
		Filter f = CQL.toFilter(filter);

		if (name == null)
			return featureSource.getFeatures().subCollection(f);
		else
			return setRenameFeatureCollection(featureSource.getFeatures().subCollection(f), name);
	}

	public void writeShapefile(SimpleFeatureCollection featureCollection) throws IOException {
		JFileDataStoreChooser chooser = new JFileDataStoreChooser("shp");

		int returnVal = chooser.showSaveDialog(null);
		if (returnVal != JFileDataStoreChooser.APPROVE_OPTION)
			System.exit(0);

		File file = chooser.getSelectedFile();

		Map<String, Object> params = new HashMap<>();
		params.put("url", file.toURI().toURL());
		params.put("create spatial index", Boolean.TRUE);

		ShapefileDataStoreFactory shapeFileFactory = new ShapefileDataStoreFactory();
		ShapefileDataStore shapeFile = (ShapefileDataStore) shapeFileFactory.createDataStore(params);
		shapeFile.createSchema(featureCollection.getSchema());

		String typeName = shapeFile.getTypeNames()[0];
		SimpleFeatureSource featureSource = shapeFile.getFeatureSource(typeName);

		SimpleFeatureStore featureStore = (SimpleFeatureStore) featureSource;
		Transaction transaction = new DefaultTransaction();

		featureStore.setTransaction(transaction);
		featureStore.addFeatures(featureCollection);

		transaction.commit();
		transaction.close();

	}

	public List<Layer> getLayers() {
		return layers;
	}
}
