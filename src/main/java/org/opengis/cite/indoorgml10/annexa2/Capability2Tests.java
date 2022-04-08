package org.opengis.cite.indoorgml10.annexa2;

import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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
 * Includes various tests of capability 2.
 */
public class Capability2Tests extends CommonFixture {

	

    /**
     * Verify that the IndoorGML instance document follows the IndoorGML Core module’s rules for encoding of 
     * objects and properties and adheres to all its conformance requirements. This test case is mandatory 
     * for all IndoorGML instance documents
     * A.2.1.1 IndoorGML Core Module - Mandatory conformance requirements
     */
    @Test(description = "OGC 14-005r5, A.2.1.1")
    public void verifyIndoorGMLCoreModuleMandatoryConformaceRequirements() throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
	    db = dbf.newDocumentBuilder();	          
        Document document = db.parse(indoorGMLFile.openStream());
        Element root = document.getDocumentElement();
		
		
		if(!root.getNodeName().equals("IndoorFeatures"))
		{
			Assert.assertTrue(root.getNodeName().equals("IndoorFeatures"), ErrorMessage.format(ErrorMessageKeys.MISSING_CORRECT_ROOT_ELEMENT));
		}
		
		NodeList primalSpaceFeaturesList = root.getElementsByTagName("primalSpaceFeatures");
		NodeList multiLayeredGraphList = root.getElementsByTagName("multiLayeredGraph");

		
		if((primalSpaceFeaturesList.getLength()+multiLayeredGraphList.getLength())==0) {
			Assert.assertTrue((primalSpaceFeaturesList.getLength()+multiLayeredGraphList.getLength())>0, ErrorMessage.format(ErrorMessageKeys.MISSING_CONTENT_IN_INDOORFEATURES_ELEMENT));
		}
    }
    
    /**
     * Verify the validity of the IndoorGML instance document against the XML Schema definition of the IndoorGML 
     * Core module. This test case is mandatory for all IndoorGML instance documents.
     * A.2.1.2 IndoorGML Core Module - Valid IndoorGML instance document
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

		try {
		
			sf = SchemaFactory.newInstance("http://www.w3.org/XML/XMLSchema/v1.0");
			
			s = sf.newSchema(schemaDocuments);
			
		

			Validator v = s.newValidator();
			v.validate(instanceDocument);
			schemaValid = true;
		
		} catch (IllegalArgumentException iae) {
			System.out.println(iae.getMessage());
			schemaValid = false;

		} catch (Exception ee) {
			System.out.println(ee.getMessage());
			schemaValid = false;

		}

		Assert.assertTrue(schemaValid, ErrorMessage.format(ErrorMessageKeys.NOT_SCHEMA_VALID));    	
    }    
    
    /**
     * Verify that the IndoorGML instance document follows the IndoorGML Indoor Navigation module’s rules for 
     * encoding of objects and properties and adheres to all its conformance requirements. This test case is 
     * mandatory for all IndoorGML instance documents which employ elements defined within the IndoorNavigation module
     * A.2.2.1 IndoorGML Indoor Navigation - Mandatory conformace requirements
     * @throws Exception 
     */
    @Test(description = "OGC 14-005r5, A.2.2.1")
    public void verifyIndoorGMLIndoorNavigationMandatoryConformaceRequirements() throws Exception{
    	
    	boolean atLeastOneNavigationElementFound = false;
    	
    	String[] navigationModuleElementNames = {
    			"NavigableSpace",
    			"GeneralSpace",
    			"TransferSpace",
    			"AnchorSpace",
    			"AnchorSpaceType",
    			"ConnectionSpace",
    			"TransitionSpace",
    			"Route",
    			"NavigableBoundary",
    			"TransferBoundary",
    			"ConnectionBoundary",
    			"AnchorBoundary",
    			"RouteSegment",
    			"RouteNode"};
    	
       
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = factory.createXMLEventReader(this.indoorGMLFile.openStream());
 
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                switch (event.getEventType()) {
                case XMLStreamConstants.START_ELEMENT:
                    StartElement startElement = event.asStartElement();
                    String qName = startElement.getName().getLocalPart();
                    for(String navigationModuleElement:navigationModuleElementNames) {
                    	if(qName.equals(navigationModuleElement)) {
                    	atLeastOneNavigationElementFound = true;
                    	}
                    }
      
                    break;

                }
            }
        
            if(atLeastOneNavigationElementFound==false) {
              Assert.assertTrue(atLeastOneNavigationElementFound, ErrorMessage.format(ErrorMessageKeys.NO_NAVIGATION_MODULE_ELEMENTS_FOUND));  
            }
            
            //----Check Requirement 5 is met
            
            NavigationRequirementChecker checker = new NavigationRequirementChecker();
            boolean passesRequirement5 = checker.checkRequirement5(indoorGMLFile);
            
            if(passesRequirement5==false) {
                Assert.assertTrue(passesRequirement5, ErrorMessage.format(ErrorMessageKeys.THICK_AND_THIN_DOOR_MODELS_FOUND));  
              }
            
            //----Check Requirement 6 is met
            

            boolean passesRequirement6 = checker.checkRequirement5(indoorGMLFile);
            
            if(passesRequirement6==false) {
                //Assert.assertTrue(passesRequirement6, ErrorMessage.format(ErrorMessageKeys.THICK_DOOR_MODEL_EITHER_CONNECTIONSPACE_OR_ANCHORSPACE));  
              }
            
            //----Check Requirement 7 is met
            
            boolean passesRequirement7 = checker.checkRequirement5(indoorGMLFile);
            
            if(passesRequirement7==false) {
                //Assert.assertTrue(passesRequirement7, ErrorMessage.format(ErrorMessageKeys.THIN_DOOR_MODEL_EITHER_CONNECTIONBOUNDARY_OR_ANCHORBOUNDARY));  
             }            
            
    }
    
    /**
     * Verify the validity of the IndoorGML instance document against the XML Schema definition of the IndoorGML Indoor 
     * Navigation module. This test case is mandatory for all IndoorGML instance documents.
     * A.2.2.2 IndoorGML Indoor Navigation - Valid IndoorGML instance document
     * @throws IOException 
     * @throws MalformedURLException 
     */
    @Test(description = "OGC 14-005r5, A.2.2.2")
    public void verifyIndoorGMLIndoorNavigationValidIndoorGMLInstanceDocument() throws MalformedURLException, IOException {
    	
    	
		StreamSource[] schemaDocuments = new StreamSource[1];
		schemaDocuments[0] = new StreamSource(
				new URL("http://schemas.opengis.net/indoorgml/1.0/indoorgmlnavi.xsd").openStream());
		
		Source instanceDocument = new StreamSource(indoorGMLFile.openStream());

		SchemaFactory sf = null;
		Schema s = null;
		boolean schemaValid = false;

		try {
			
			sf = SchemaFactory.newInstance("http://www.w3.org/XML/XMLSchema/v1.0");
			
			s = sf.newSchema(schemaDocuments);
		
			Validator v = s.newValidator();
			v.validate(instanceDocument);
			schemaValid = true;
			
		} catch (IllegalArgumentException iae) {
			System.out.println(iae.getMessage());
			schemaValid = false;

		} catch (Exception ee) {
			System.out.println(ee.getMessage());
			schemaValid = false;

		}

		Assert.assertTrue(schemaValid, ErrorMessage.format(ErrorMessageKeys.NOT_SCHEMA_VALID));		
        
    	
    }         

}
