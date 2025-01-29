package org.opengis.cite.indoorgml10.annexa2_2;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * <p>
 * NavigationRequirementChecker class.
 * </p>
 *
 */
public class NavigationRequirementChecker {

	/*
	 * Thick door model and thin door models shall not be defined in a same IndoorGML
	 * encoding. Return true if Requirement 5 is met
	 */
	/**
	 * <p>
	 * checkRequirement5.
	 * </p>
	 * @param indoorGMLFile a {@link java.net.URL} object
	 * @return a boolean
	 * @throws java.lang.Exception if any.
	 */
	public boolean checkRequirement5(URL indoorGMLFile) throws Exception {

		boolean thickDoorModelDetected = false;
		boolean thinDoorModelDetected = false;

		String[] thickDoorModelElementNames = { "AnchorSpace", "ConnectionSpace" };

		String[] thinDoorModelElementNames = { "ConnectionBoundary", "AnchorBoundary" };

		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLEventReader eventReader = factory.createXMLEventReader(indoorGMLFile.openStream());

		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();
			switch (event.getEventType()) {
				case XMLStreamConstants.START_ELEMENT:
					StartElement startElement = event.asStartElement();
					String qName = startElement.getName().getLocalPart();

					for (String thickDoorModelElement : thickDoorModelElementNames) {
						if (qName.equals(thickDoorModelElement)) {
							thickDoorModelDetected = true;
						}
					}

					for (String thinDoorModelElement : thinDoorModelElementNames) {
						if (qName.equals(thinDoorModelElement)) {
							thinDoorModelDetected = true;
						}
					}

					break;

			}
		}

		// Thick door model and thin door models shall not be defined in a same IndoorGML
		// encoding.
		if (thickDoorModelDetected == true && thinDoorModelDetected == true)
			return false;
		return true;

	}

	/*
	 * Requirement 6: Every thick door shall be encoded as an instance of either
	 * ConnectionSpace or AnchorSpace.
	 */
	/**
	 * <p>
	 * checkRequirement6.
	 * </p>
	 * @param indoorGMLFile a {@link java.net.URL} object
	 * @return a boolean
	 * @throws java.lang.Exception if any.
	 */
	public boolean checkRequirement6(URL indoorGMLFile) throws Exception {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(indoorGMLFile.openStream());
		document.getDocumentElement().normalize();
		Element root = document.getDocumentElement();

		NodeList connectionBoundaryList = document
			.getElementsByTagNameNS("http://www.opengis.net/indoorgml/1.0/navigation", "ConnectionBoundary");

		NodeList anchorBoundaryList = document.getElementsByTagNameNS("http://www.opengis.net/indoorgml/1.0/navigation",
				"AnchorBoundary");

		if (connectionBoundaryList.getLength() > 0 || anchorBoundaryList.getLength() > 0)
			return false; // a thick door was found encoded as an instance of either
							// ConnectionBoundary or AnchorBoundary

		return true;
	}

	/*
	 * Requirement 7: every thin door shall be encoded as an instance of either
	 * ConnectionBoundary or AnchorBoundary.
	 */
	/**
	 * <p>
	 * checkRequirement7.
	 * </p>
	 * @param indoorGMLFile a {@link java.net.URL} object
	 * @return a boolean
	 * @throws java.lang.Exception if any.
	 */
	public boolean checkRequirement7(URL indoorGMLFile) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(indoorGMLFile.openStream());
		document.getDocumentElement().normalize();
		Element root = document.getDocumentElement();

		NodeList connectionSpaceList = document
			.getElementsByTagNameNS("http://www.opengis.net/indoorgml/1.0/navigation", "ConnectionSpace");

		NodeList anchorSpaceList = document.getElementsByTagNameNS("http://www.opengis.net/indoorgml/1.0/navigation",
				"AnchorSpace");

		if (connectionSpaceList.getLength() > 0 || anchorSpaceList.getLength() > 0)
			return false; // a thin door was found encoded as an instance of either
							// ConnectionSpace or AnchorSpace

		return true;
	}

}
