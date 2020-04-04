package org.opengis.cite.indoorgml10.level1;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.SchemaFactory;

import org.apache.xerces.dom.DeferredElementNSImpl;
import org.opengis.cite.indoorgml10.CellSpace;
import org.opengis.cite.indoorgml10.CellSpacePairing;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.WKTReader;

public class RequirementChecker {
	
	public boolean checkRequirement1(URL fileURL) throws  Exception
	{
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new File(fileURL.toURI()));
		document.getDocumentElement().normalize();
		Element root = document.getDocumentElement();
		 
		boolean eachCellSpaceSolidHasSurfaceGeometry = true;
		
		NodeList cellSpaceList = document.getElementsByTagNameNS("http://www.opengis.net/indoorgml/1.0/core","CellSpace");
		for(int a=0; a<cellSpaceList.getLength(); a++)
		{
			DeferredElementNSImpl cellSpace = (DeferredElementNSImpl) cellSpaceList.item(a);
			NodeList solidList = cellSpace.getElementsByTagNameNS("http://www.opengis.net/gml/3.2","Solid");
			for(int b=0; b<solidList.getLength(); b++)
			{
				DeferredElementNSImpl solid = (DeferredElementNSImpl) solidList.item(b);
				NodeList surfaceMemberList = solid.getElementsByTagNameNS("http://www.opengis.net/gml/3.2","surfaceMember");
				if(surfaceMemberList.getLength()==0) {
					  eachCellSpaceSolidHasSurfaceGeometry = false;
					  
				}
				
			}	
			
		}
		
		return eachCellSpaceSolidHasSurfaceGeometry;
		
	}
	
	public boolean checkRequirement2(URL fileURL) throws  Exception
	{
		
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new File(fileURL.toURI()));
		document.getDocumentElement().normalize();
		Element root = document.getDocumentElement();
		 
		//extract linearRings of cellSpaces
		ArrayList<CellSpace> cellSpaceItems = new ArrayList<CellSpace>();
		NodeList cellSpaceList = document.getElementsByTagNameNS("http://www.opengis.net/indoorgml/1.0/core","CellSpace");
		for(int a=0; a<cellSpaceList.getLength(); a++)
		{
			
			CellSpace cellSpaceItem = new CellSpace();
			ArrayList<LinearRing> linearRings = new ArrayList<LinearRing>();
			
			DeferredElementNSImpl cellSpace = (DeferredElementNSImpl) cellSpaceList.item(a);
			cellSpaceItem.name = cellSpace.getAttributeNS("http://www.opengis.net/gml/3.2","id");
			NodeList solidList = cellSpace.getElementsByTagNameNS("http://www.opengis.net/gml/3.2","Solid");
			for(int b=0; b<solidList.getLength(); b++)
			{
				DeferredElementNSImpl solid = (DeferredElementNSImpl) solidList.item(b);
				NodeList linearRingList = solid.getElementsByTagNameNS("http://www.opengis.net/gml/3.2","LinearRing");
				
				for(int c=0; c<linearRingList.getLength(); c++) {
				
					GeometryFactory gf = new GeometryFactory();
				
					DeferredElementNSImpl linearRing = (DeferredElementNSImpl) linearRingList.item(c);
					NodeList posList = solid.getElementsByTagNameNS("http://www.opengis.net/gml/3.2","pos");
					Coordinate[] coords = new Coordinate[posList.getLength()+1];
					for(int d=0; d<posList.getLength(); d++) {
						DeferredElementNSImpl pos = (DeferredElementNSImpl) posList.item(d);
						String[] posCoords = pos.getTextContent().trim().split(" ");
						coords[d] = new Coordinate(Double.parseDouble(posCoords[0]),Double.parseDouble(posCoords[1]),Double.parseDouble(posCoords[2]));
						
					}
					coords[posList.getLength()] = coords[0];
					LinearRing geom = gf.createLinearRing(coords);
					linearRings.add(geom);
				}
				
			}	
			LinearRing[] collectionA = new LinearRing[linearRings.size()];
			collectionA = (LinearRing[]) linearRings.toArray(collectionA);
			cellSpaceItem.linearRings = collectionA;
			cellSpaceItems.add(cellSpaceItem);
		}
		
		// Check for horizontal overlaps between cellSpaces
		
		ArrayList<CellSpacePairing> overlapsList = new ArrayList<CellSpacePairing>();
		ArrayList<String> overlapStrings = new ArrayList<String>();
		
		for(int e=0; e<cellSpaceItems.size();e++) {
			LinearRing[] ringsA = cellSpaceItems.get(e).linearRings;
			for(int f=0; f<ringsA.length;f++) {
				for(int g=0; g<cellSpaceItems.size();g++) {
					LinearRing[] ringsB = cellSpaceItems.get(g).linearRings;
					for(int h=0; h<ringsB.length;h++) {
						boolean overlaps = ringsA[f].overlaps(ringsB[h]); //use JTS Overlaps method
						if(overlaps) {
							String overText = cellSpaceItems.get(e).name+" overlaps "+cellSpaceItems.get(g).name;
							String revOverText = cellSpaceItems.get(g).name+" overlaps "+cellSpaceItems.get(e).name;
							if((!overlapsList.contains(overText)) && (!overlapsList.contains(revOverText))) {
								overlapsList.add(new CellSpacePairing(cellSpaceItems.get(e),cellSpaceItems.get(g)));
							}
						}
					}
				}			   
			}
		}

		//Check for vertical overlap of cellSpaces that have horizontal spaces (i.e. eliminate rooms on different floors)
		boolean foundVerticalOverlaps = false;
		
		for(int i=0; i < overlapsList.size(); i++)
		{
			CellSpacePairing csp = overlapsList.get(i);
			CellSpace cs1 = csp.cellSpace1;
			CellSpace cs2 = csp.cellSpace2;
			LinearRing[] linearRingsA = cs1.linearRings;
			
			double minz = Double.POSITIVE_INFINITY;
			double maxz = Double.NEGATIVE_INFINITY;
			for(int j =0; j < linearRingsA.length; j++)
			{
				Coordinate[] coordsA = linearRingsA[j].getCoordinates();
				for(int k =0; k < coordsA.length; k++)
				{
					if(coordsA[k].z<minz) minz = coordsA[k].z;
					if(coordsA[k].z>maxz) maxz = coordsA[k].z;
				}
			}
			
			LinearRing[] linearRingsB = cs2.linearRings;
			
			for(int j =0; j < linearRingsB.length; j++)
			{
				Coordinate[] coordsB = linearRingsB[j].getCoordinates();
				for(int k =0; k < coordsB.length; k++)
				{
					if(coordsB[k].z>minz && coordsB[k].z<maxz)
					{
						foundVerticalOverlaps = true;
						
						if((!overlapStrings.contains(cs2.name+" "+cs1.name)) && (!overlapStrings.contains(cs1.name+" "+cs2.name))) {
							overlapStrings.add(cs1.name+" "+cs2.name);
							System.out.println(cs1.name+" "+cs2.name);
						}
					}
				}
			}
			
		}
		System.out.println(foundVerticalOverlaps+"");
		
		return foundVerticalOverlaps;
	}	
	


}
