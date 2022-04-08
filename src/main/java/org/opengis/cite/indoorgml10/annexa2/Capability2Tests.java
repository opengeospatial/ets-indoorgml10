package org.opengis.cite.indoorgml10.annexa2;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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

/**
 * Includes various tests of capability 2.
 */
public class Capability2Tests extends CommonFixture {

	/**
     * Run conformance level 2 tests only if the preconditions are satisfied.
     */
    /*@BeforeTest
    public void checkPreconditions() {
        Assert.assertTrue(2 > 1,
                "Preconditions for Conformance Level 2 were not satisfied.");
    }*/

    /**
     * Verify that the IndoorGML instance document follows the IndoorGML Core module’s rules for encoding of 
     * objects and properties and adheres to all its conformance requirements. This test case is mandatory 
     * for all IndoorGML instance documents
     * A.2.1.1 IndoorGML Core Module - Mandatory conformace requirements
     */
    @Test(description = "OGC 14-005r5, A.2.1.1")
    public void verifyIndoorGMLCoreModuleMandatoryConformaceRequirements() {
    	throw new SkipException("not yet implemented");
    }
    
    /**
     * Verify the validity of the IndoorGML instance document against the XML Schema definition of the IndoorGML 
     * Core module. This test case is mandatory for all IndoorGML instance documents.
     * A.2.1.2 IndoorGML Core Module - Valid IndoorGML instance document
     */
    @Test(description = "OGC 14-005r5, A.2.1.2")
    public void verifyIndoorGMLCoreModuleValidIndoorGMLInstanceDocument() {
    	throw new SkipException("not yet implemented");
    }    
    
    /**
     * Verify that the IndoorGML instance document follows the IndoorGML Indoor Navigation module’s rules for 
     * encoding of objects and properties and adheres to all its conformance requirements. This test case is 
     * mandatory for all IndoorGML instance documents which employ elements defined within the IndoorNavigation module
     * A.2.2.1 IndoorGML Indoor Navigation - Mandatory conformace requirements
     */
    @Test(description = "OGC 14-005r5, A.2.2.1")
    public void verifyIndoorGMLIndoorNavigationMandatoryConformaceRequirements() {
    	throw new SkipException("not yet implemented");
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
    	
    	if(1<2)throw new SkipException("not yet implemented");	
    	
		StreamSource[] schemaDocuments = new StreamSource[1];
		schemaDocuments[0] = new StreamSource(
				new URL("http://schemas.opengis.net/indoorgml/1.0/indoorgmlnavi.xsd").openStream());
		
		Source instanceDocument = new StreamSource(indoorGMLFile.openStream());

		SchemaFactory sf = null;
		Schema s = null;
		boolean schemaValid = false;

		try {
			System.out.println("Starting verifyIndoorGMLIndoorNavigationValidIndoorGMLInstanceDocument");
			sf = SchemaFactory.newInstance("http://www.w3.org/XML/XMLSchema/v1.0");
			
			s = sf.newSchema(schemaDocuments);
			
		

			Validator v = s.newValidator();
			v.validate(instanceDocument);
			schemaValid = true;
			System.out.println("Completed verifyIndoorGMLIndoorNavigationValidIndoorGMLInstanceDocument");
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
