package com.GIS.MyGIS.UI.Attribute;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.filter.text.cql2.CQLException;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;

import com.GIS.MyGIS.DataAccess.Vector.VectorData;

public class AttributeManager {

	AttributeFrame attributeFrame = new AttributeFrame();
	VectorData vectorData = new VectorData();
	MapContent mapContent;
	Layer mapLayer;
	int layerIndex = 0;
	public AttributeManager(MapContent mapContent)
	{
		this.mapContent = mapContent;
		attributeFrame.JBshowAttribute.addActionListener(showLayerAttributeAction());
		attributeFrame.JBaddLayer.addActionListener(addLayer());
		
		for(Layer layer : mapContent.layers())
		{
			String Name = layer.getFeatureSource().getName().toString();
			attributeFrame.JCBselectLayer.addItem(Name);
		}
		
		attributeFrame.JCBselectLayer.addItemListener(selectLayer());
	}
	public void showAttribute()
	{
		if(mapLayer == null)
			mapLayer = mapContent.layers().get(mapContent.layers().size() - 1);
		try {
			attributeFrame.JTlayerTable.setModel(vectorData.tableModel(mapLayer , null));
		} catch (CQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		attributeFrame.showAttribute();
	}
	public ActionListener showLayerAttributeAction()
	{
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String filter = attributeFrame.JTFfilter.getText();
				try {
					attributeFrame.JTlayerTable.setModel(vectorData.tableModel(mapLayer , filter));
				} catch (CQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		};
	}
	public ActionListener addLayer()
	{
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String filter = attributeFrame.JTFfilter.getText();
				System.out.println(filter);
				try {
					SimpleFeatureCollection featureCollection = (SimpleFeatureCollection) mapLayer.getFeatureSource().getFeatures();
					featureCollection = vectorData.setRenameFeatureCollection(featureCollection, featureCollection.getSchema().getName()+"("+filter+")");
					mapContent.layers().add(vectorData.createLayer(featureCollection , filter));
				} catch (CQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		};
	}
	public ItemListener selectLayer()
	{
		return new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED)
				{
					
					try {
						for(Layer layer : mapContent.layers())
						{
							String Name = layer.getFeatureSource().getName().toString();
							if(e.getItem().toString().equals(Name))
							{
								mapLayer = layer;
								break;
							}	
						}
						attributeFrame.JTlayerTable.setModel(vectorData.tableModel(mapLayer , null));
					} catch (CQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		};
	}
}
