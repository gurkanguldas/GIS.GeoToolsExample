package com.GIS.MyGIS;

import java.io.IOException;
import org.geotools.filter.text.cql2.CQLException;
import com.GIS.MyGIS.UI.Map.MapFrame;

public class App 
{
    public static void main( String[] args ) throws CQLException, IOException
    {
        MapFrame mapFrame = new MapFrame();
        mapFrame.showMap();
    }
}
