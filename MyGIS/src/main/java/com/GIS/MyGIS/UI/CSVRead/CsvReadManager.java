package com.GIS.MyGIS.UI.CSVRead;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;
import java.io.IOException;

import org.geotools.filter.text.cql2.CQLException;
import org.geotools.map.MapContent;
import org.geotools.swing.data.JFileDataStoreChooser;

import com.GIS.MyGIS.DataAccess.Vector.VectorData;

public class CsvReadManager {
	
	CsvReadFrame csvReadFrame = new CsvReadFrame();
	VectorData vectorData = new VectorData();
	MapContent mapContent;
	BufferedReader reader;
	int lat = 0 , lon = 0;
	public CsvReadManager(final String filter, MapContent mapContent) {
		try {
			createFeatureType(filter , mapContent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void createFeatureType(final String filter , final MapContent mapContent) throws IOException
	{
		final File file = JFileDataStoreChooser.showOpenFile("csv", null);
		csvReadFrame.setVisible(true);
		reader = new BufferedReader(new FileReader(file));
		final String[] header = reader.readLine().split(",");
		
		for (String string : header) 
		{
			csvReadFrame.latitude.addItem(string);
			csvReadFrame.longitude.addItem(string);
		}
		
		csvReadFrame.createLayer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				lat = csvReadFrame.latitude.getSelectedIndex();
				lon = csvReadFrame.longitude.getSelectedIndex();
				
				try {
					mapContent.layers().add(vectorData.createLayer(vectorData.createShapefileFromCsv(file.getName(), filter, reader, lat, lon , header)));
				} catch (CQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
}
