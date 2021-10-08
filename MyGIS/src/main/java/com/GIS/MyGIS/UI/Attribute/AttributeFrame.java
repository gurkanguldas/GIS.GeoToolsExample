package com.GIS.MyGIS.UI.Attribute;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class AttributeFrame extends JFrame{

	JTable JTlayerTable;
	JTextField JTFfilter;
	JButton JBaddLayer;
	JButton JBshowAttribute;
	JComboBox<String> JCBselectLayer;
	
	public AttributeFrame()
	{
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());

		JTFfilter = new JTextField();
		JTFfilter.setText("include");
		
		JTlayerTable = new JTable();
		JTlayerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JTlayerTable.setModel(new DefaultTableModel(5,5));
		
		JTlayerTable.setPreferredScrollableViewportSize(new Dimension(380,80));
		
		JScrollPane JSPlayerTable = new JScrollPane(JTlayerTable);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JBaddLayer = new JButton("Add Layer");
		JBshowAttribute = new JButton("Show Attribute");
		JCBselectLayer = new JComboBox<>();
		menuBar.add(JBaddLayer);
		menuBar.add(JBshowAttribute);
		menuBar.add(JCBselectLayer);
		
		getContentPane().add(JTFfilter, BorderLayout.NORTH);
		getContentPane().add(JSPlayerTable, BorderLayout.CENTER);
		pack();
	}
	public void showAttribute()
	{
		setVisible(true);
	}
}
