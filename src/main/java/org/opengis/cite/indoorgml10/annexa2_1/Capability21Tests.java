package org.opengis.cite.indoorgml10.annexa2_1;

import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.opengis.cite.indoorgml10.CommonFixture;
import org.opengis.cite.indoorgml10.ErrorMessage;
import org.opengis.cite.indoorgml10.ErrorMessageKeys;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Includes various tests of Annex A.2.1.
 */
public class Capability21Tests extends CommonFixture {

	

    /**
     * Verify that the IndoorGML instance document follows the IndoorGML Core module’s rules for encoding of 
     * objects and properties and adheres to all its conformance requirements. This test case is mandatory 
     * for all IndoorGML instance documents
     * A.2.1.1 IndoorGML Core Module - Mandatory conformance requirements
     * 
     * @throws org.xml.sax.SAXException
     *            If something goes wrong with parsing the document
     * @throws java.io.IOException
     *            If something goes wrong with input
     *            
     * @throws javax.xml.parsers.ParserConfigurationException
     *            If something goes wrong with parsing the document         
     * 
     */
    @Test(description = "OGC 14-005r5, A.2.1.1")
    public void verifyIndoorGMLCoreModuleMandatoryConformaceRequirements() throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
	    db = dbf.newDocumentBuilder();	          
        Document document = db.parse(indoorGMLFile.openStream());
        Element root = document.getDocumentElement();
		
		
		if(!root.getNodeName().endsWith("IndoorFeatures"))
		{
			Assert.assertTrue(root.getNodeName().endsWith("IndoorFeatures"), ErrorMessageKeys.MISSING_CORRECT_ROOT_ELEMENT);
		}
		
		NodeList primalSpaceFeaturesList = root.getElementsByTagName("primalSpaceFeatures");
		NodeList multiLayeredGraphList = root.getElementsByTagName("multiLayeredGraph");

		
		if((primalSpaceFeaturesList.getLength()+multiLayeredGraphList.getLength())==0) {
			Assert.assertTrue((primalSpaceFeaturesList.getLength()+multiLayeredGraphList.getLength())>0, ErrorMessageKeys.MISSING_CONTENT_IN_INDOORFEATURES_ELEMENT);
		}
    }
    
    /**
     * Verify the validity of the IndoorGML instance document against the XML Schema definition of the IndoorGML 
     * Core module. This test case is mandatory for all IndoorGML instance documents.
     * A.2.1.2 IndoorGML Core Module - Valid IndoorGML instance document
     * 
     * @throws org.xml.sax.SAXException
     *            If something goes wrong with parsing the document
     * @throws java.io.IOException
     *            If something goes wrong with input
     * 
     */
    @Test(description = "OGC 14-005r5, A.2.1.2")
    public void verifyIndoorGMLCoreModuleValidIndoorGMLInstanceDocument() throws SAXException, IOException{
    	
    	StreamSource[] schemaDocuments = new StreamSource[1];
		schemaDocuments[0] = new StreamSource(
				new URL("http://schemas.opengis.net/indoorgml/1.0/indoorgmlcore.xsd").openStream());
		
		Source instanceDocument = new StreamSource(indoorGMLFile.openStream());

		SchemaFactory sf = null;
		Schema s = null;
		boolean schemaValid = false;
		StringBuffer validationErrorMessage = new StringBuffer();

		try {
		
			sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			
			s = sf.newSchema(schemaDocuments);
			
		

			Validator v = s.newValidator();
			v.validate(instanceDocument);
			schemaValid = true;
		
		} catch (IllegalArgumentException iae) {			
			schemaValid = false;
			validationErrorMessage.append(". "+ iae.getMessage()+"\n");

		} catch (Exception ee) {			
			schemaValid = false;
			validationErrorMessage.append(". "+ ee.getMessage()+"\n");
		}

		Assert.assertTrue(schemaValid, validationErrorMessage.toString());    	
    }    

}
