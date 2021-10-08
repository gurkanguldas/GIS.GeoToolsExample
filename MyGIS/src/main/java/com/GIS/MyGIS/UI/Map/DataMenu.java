package com.GIS.MyGIS.UI.Map;

import java.awt.event.ActionEvent;

import javax.swing.JMenu;

import org.geotools.map.MapContent;
import org.geotools.swing.action.SafeAction;

import com.GIS.MyGIS.UI.Attribute.AttributeManager;

public class DataMenu extends JMenu
{
	MapContent mapContent;
	AttributeManager attributeManager;
	public DataMenu(MapContent mapContent) 
	{
		super("Data");
		this.mapContent = mapContent;
		add(showAttribute());
	}
	public SafeAction showAttribute()
	{
		return new SafeAction("Show Attribute Table") {
			
			@Override
			public void action(ActionEvent e) throws Throwable {

				attributeManager = new AttributeManager(mapContent);
				attributeManager.showAttribute();
			}
		};
	}
}
