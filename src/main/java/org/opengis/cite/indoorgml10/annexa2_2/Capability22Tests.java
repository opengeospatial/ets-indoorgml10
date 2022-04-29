package org.opengis.cite.indoorgml10.annexa2_2;

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
 * Includes various tests of Annex A.2.2.
 */
public class Capability22Tests extends CommonFixture {

	boolean skipIncompleteTests = true;

    
    /**
     * Verify that the IndoorGML instance document follows the IndoorGML Indoor Navigation module’s rules for 
     * encoding of objects and properties and adheres to all its conformance requirements. This test case is 
     * mandatory for all IndoorGML instance documents which employ elements defined within the IndoorNavigation module
     * A.2.2.1 IndoorGML Indoor Navigation - Mandatory conformace requirements
     * 
     * @throws Exception 
     *            If something goes wrong
     * 
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
                    	if(qName.endsWith(navigationModuleElement)) {
                    	atLeastOneNavigationElementFound = true;
                    	}
                    }
      
                    break;

                }
            }
        
           
            Assert.assertTrue(atLeastOneNavigationElementFound, ErrorMessageKeys.NO_NAVIGATION_MODULE_ELEMENTS_FOUND);  
         
            
    
            
 
            
           
            
    }
    
    /**
     * Verify that the IndoorGML instance document follows the IndoorGML Indoor Navigation module’s rules for 
     * encoding of objects and properties and adheres to all its conformance requirements. This test case is 
     * mandatory for all IndoorGML instance documents which employ elements defined within the IndoorNavigation module
     * A.2.2.1 IndoorGML Indoor Navigation - Mandatory conformace Requirement 5
     * 
     * @throws Exception 
     *            If something goes wrong
     * 
     */    
    @Test(description = "OGC 14-005r5, A.2.2.1 - Requirement 5", dependsOnMethods = "verifyIndoorGMLIndoorNavigationMandatoryConformaceRequirements")
    public void verifyIndoorGMLIndoorNavigationMandatoryConformaceRequirement5() throws Exception{        
        NavigationRequirementChecker checker = new NavigationRequirementChecker();
        boolean passesRequirement5 = checker.checkRequirement5(indoorGMLFile);        
        Assert.assertTrue(passesRequirement5, ErrorMessageKeys.THICK_AND_THIN_DOOR_MODELS_FOUND);     
    }
    /**
     * Verify that the IndoorGML instance document follows the IndoorGML Indoor Navigation module’s rules for 
     * encoding of objects and properties and adheres to all its conformance requirements. This test case is 
     * mandatory for all IndoorGML instance documents which employ elements defined within the IndoorNavigation module
     * A.2.2.1 IndoorGML Indoor Navigation - Mandatory conformace Requirement 6
     * 
     * @throws Exception 
     *            If something goes wrong
     * 
     */     
    @Test(description = "OGC 14-005r5, A.2.2.1 - Requirement 6", dependsOnMethods = "verifyIndoorGMLIndoorNavigationMandatoryConformaceRequirements")
    public void verifyIndoorGMLIndoorNavigationMandatoryConformaceRequirement6() throws Exception{
    	
    	if(skipIncompleteTests) throw new SkipException("Test not yet implemented.");
    	
    	NavigationRequirementChecker checker = new NavigationRequirementChecker();
        boolean passesRequirement6 = checker.checkRequirement6(indoorGMLFile);        
        Assert.assertTrue(passesRequirement6, ErrorMessageKeys.THICK_DOOR_MODEL_EITHER_CONNECTIONSPACE_OR_ANCHORSPACE);  
          	
    }
    /**
     * Verify that the IndoorGML instance document follows the IndoorGML Indoor Navigation module’s rules for 
     * encoding of objects and properties and adheres to all its conformance requirements. This test case is 
     * mandatory for all IndoorGML instance documents which employ elements defined within the IndoorNavigation module
     * A.2.2.1 IndoorGML Indoor Navigation - Mandatory conformace Requirement 7
     * 
     * @throws Exception 
     *            If something goes wrong
     * 
     */     
    @Test(description = "OGC 14-005r5, A.2.2.1 - Requirement 7", dependsOnMethods = "verifyIndoorGMLIndoorNavigationMandatoryConformaceRequirements")
    public void verifyIndoorGMLIndoorNavigationMandatoryConformaceRequirement7() throws Exception{
    	
    	if(skipIncompleteTests) throw new SkipException("Test not yet implemented.");
    	
    	NavigationRequirementChecker checker = new NavigationRequirementChecker();        
        boolean passesRequirement7 = checker.checkRequirement7(indoorGMLFile);        
        if(passesRequirement7==false) {
            Assert.assertTrue(passesRequirement7, ErrorMessageKeys.THIN_DOOR_MODEL_EITHER_CONNECTIONBOUNDARY_OR_ANCHORBOUNDARY);  
         }      	
    }
    /**
     * Verify that the IndoorGML instance document follows the IndoorGML Indoor Navigation module’s rules for 
     * encoding of objects and properties and adheres to all its conformance requirements. This test case is 
     * mandatory for all IndoorGML instance documents which employ elements defined within the IndoorNavigation module
     * A.2.2.1 IndoorGML Indoor Navigation - Mandatory conformace Requirement 8
     * 
     * @throws Exception 
     *            If something goes wrong
     * 
     */     
    @Test(description = "OGC 14-005r5, A.2.2.1 - Requirement 8", dependsOnMethods = "verifyIndoorGMLIndoorNavigationMandatoryConformaceRequirements")
    public void verifyIndoorGMLIndoorNavigationMandatoryConformaceRequirement8() throws Exception{
    	
    	if(skipIncompleteTests) throw new SkipException("Test not yet implemented.");
    	
    	NavigationRequirementChecker checker = new NavigationRequirementChecker();
    }    
    
    /**
     * Verify the validity of the IndoorGML instance document against the XML Schema definition of the IndoorGML Indoor 
     * Navigation module. This test case is mandatory for all IndoorGML instance documents.
     * A.2.2.2 IndoorGML Indoor Navigation - Valid IndoorGML instance document
     * @throws IOException
     * 			If something goes wrong with input 
     * @throws MalformedURLException 
     * 			If a URL is malformed
     * 
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
		StringBuffer validationErrorMessage = new StringBuffer();

		try {
			
			sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			
			s = sf.newSchema(schemaDocuments);
		
			Validator v = s.newValidator();
			v.validate(instanceDocument);
			schemaValid = true;
			
		} catch (IllegalArgumentException iae) {
			validationErrorMessage.append(". "+ iae.getMessage()+"\n");
			schemaValid = false;

		} catch (Exception ee) {
			validationErrorMessage.append(". "+ ee.getMessage()+"\n");
			schemaValid = false;

		}

		Assert.assertTrue(schemaValid, validationErrorMessage.toString());		
        
    	
    }         

}
