package org.opengis.cite.indoorgml10.annexa1;

import java.io.File;
import java.io.FileWriter;
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

public class CoreRequirementChecker {

	public boolean checkRequirement1(URL fileURL) throws Exception {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(fileURL.openStream());		
		document.getDocumentElement().normalize();
		Element root = document.getDocumentElement();

		boolean eachCellSpaceSolidHasSurfaceGeometry = true;

		NodeList cellSpaceList = document.getElementsByTagNameNS("http://www.opengis.net/indoorgml/1.0/core",
				"CellSpace");
		for (int a = 0; a < cellSpaceList.getLength(); a++) {
			DeferredElementNSImpl cellSpace = (DeferredElementNSImpl) cellSpaceList.item(a);
			NodeList solidList = cellSpace.getElementsByTagNameNS("http://www.opengis.net/gml/3.2", "Solid");
			for (int b = 0; b < solidList.getLength(); b++) {
				DeferredElementNSImpl solid = (DeferredElementNSImpl) solidList.item(b);
				NodeList surfaceMemberList = solid.getElementsByTagNameNS("http://www.opengis.net/gml/3.2",
						"surfaceMember");
				if (surfaceMemberList.getLength() == 0) {
					eachCellSpaceSolidHasSurfaceGeometry = false;

				}

			}

		}

		return eachCellSpaceSolidHasSurfaceGeometry;

	}

	public boolean checkRequirement2(URL fileURL) throws Exception {

		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(fileURL.openStream());				
		document.getDocumentElement().normalize();
		Element root = document.getDocumentElement();

		ArrayList<String> floors = new ArrayList<String>();

		// extract linearRings of cellSpaces
		ArrayList<CellSpace> cellSpaceItems = new ArrayList<CellSpace>();
		NodeList cellSpaceList = document.getElementsByTagNameNS("http://www.opengis.net/indoorgml/1.0/core",
				"CellSpace");
		for (int a = 0; a < cellSpaceList.getLength(); a++) {

			CellSpace cellSpaceItem = new CellSpace();
			ArrayList<LinearRing> linearRings = new ArrayList<LinearRing>();

			DeferredElementNSImpl cellSpace = (DeferredElementNSImpl) cellSpaceList.item(a);
			cellSpaceItem.name = cellSpace.getAttributeNS("http://www.opengis.net/gml/3.2", "id");
			NodeList solidList = cellSpace.getElementsByTagNameNS("http://www.opengis.net/gml/3.2", "Solid");
			for (int b = 0; b < solidList.getLength(); b++) {

				DeferredElementNSImpl solid = (DeferredElementNSImpl) solidList.item(b);
				NodeList linearRingList = solid.getElementsByTagNameNS("http://www.opengis.net/gml/3.2", "LinearRing");

				for (int c = 0; c < linearRingList.getLength(); c++) {

					GeometryFactory gf = new GeometryFactory();

					DeferredElementNSImpl linearRing = (DeferredElementNSImpl) linearRingList.item(c);
					NodeList posList = linearRing.getElementsByTagNameNS("http://www.opengis.net/gml/3.2", "pos");
					Coordinate[] coords = new Coordinate[posList.getLength() + 1];
					for (int d = 0; d < posList.getLength(); d++) {
						DeferredElementNSImpl pos = (DeferredElementNSImpl) posList.item(d);
						String[] posCoords = pos.getTextContent().trim().split(" ");
						coords[d] = new Coordinate(Double.parseDouble(posCoords[0]), Double.parseDouble(posCoords[1]),
								Double.parseDouble(posCoords[2]));
						if (!floors.contains(posCoords[2]))
							floors.add(posCoords[2]);
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
		
		//===============
		
		boolean foundVerticalOverlaps = false;
	
		for (int q = 0; q < floors.size(); q++) {
			
			// determine which rings are on the same floor plan
			
			ArrayList<LinearRing> ringsOnFloorPlan = new ArrayList<LinearRing>();
			
			double floorZ = Double.parseDouble(floors.get(q));
			for (int e = 0; e < cellSpaceItems.size(); e++) {
				LinearRing[] ringsA = cellSpaceItems.get(e).linearRings;
				for (int f = 0; f < ringsA.length; f++) {
					boolean floorplan = true;
					Coordinate[] coords = ringsA[f].getCoordinates();
					for (int g = 0; g < coords.length; g++) {
						if (coords[g].z != floorZ)
							floorplan = false;
					}

					if (floorplan) {
						String geompol = ringsA[f].toText().replace("LINEARRING (", "POLYGON ((");
			
						ringsOnFloorPlan.add(ringsA[f]);
				
					}

				}
			}
			
			// now check whether any rings on the floor plan overlap

			for(int s=0; s<ringsOnFloorPlan.size(); s++)
			{
				for(int t=1; t<ringsOnFloorPlan.size(); t++) {
					boolean result = ringsOnFloorPlan.get(s).overlaps(ringsOnFloorPlan.get(t));
					//System.out.println(s+" "+t+" result "+result);  //Still under development

				}
				
			}
			
		}



		return foundVerticalOverlaps;
	}


}
