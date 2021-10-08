package com.GIS.MyGIS.UI.CSVRead;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class CsvReadFrame extends JFrame {
	public JComboBox<String> longitude;
	public JComboBox<String> latitude;
	public JButton createLayer;

	public CsvReadFrame() {
		super("CSV TO SHAPEFILE");
		setLayout(null);
		JLabel longitudeLabel = new JLabel("Longitude :", SwingConstants.RIGHT);
		JLabel latitudeLabel = new JLabel("Latitude :", SwingConstants.RIGHT);

		longitude = new JComboBox<String>();
		latitude = new JComboBox<String>();
		createLayer = new JButton("CREATE LAYER");

		longitudeLabel.setBounds(10, 15, 80, 30);
		latitudeLabel.setBounds(10, 45, 80, 30);
		longitude.setBounds(120, 20, 200, 25);
		latitude.setBounds(120, 50, 200, 25);
		createLayer.setBounds(0, 100, 350, 30);

		add(longitudeLabel);
		add(latitudeLabel);
		add(longitude);
		add(latitude);
		add(createLayer);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(350, 168);
		setResizable(false);
	}

}
