package org.opengis.cite.indoorgml10.annexa1;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import org.opengis.cite.indoorgml10.CommonFixture;
import org.opengis.cite.indoorgml10.ErrorMessage;
import org.opengis.cite.indoorgml10.ErrorMessageKeys;
import org.opengis.cite.indoorgml10.SuiteAttribute;
import org.opengis.cite.validation.RelaxNGValidator;
import org.opengis.cite.validation.ValidationErrorHandler;
import org.testng.Assert;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.xml.XmlSuite;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

/**
 * Includes various tests of Annex A.1.
 */
public class AnnexA1Tests extends CommonFixture {

	private Document testSubject;
	


	/**
	 * Sets the test subject. This method is intended to facilitate unit testing.
	 *
	 * @param testSubject A Document node representing the test subject or metadata
	 *                    about it.
	 */
	public void setTestSubject(Document testSubject) {
		this.testSubject = testSubject;
	}

	/**
	 * Verify the validity of the IndoorGML instance document against the XML Schema
	 * definition of each IndoorGML module that is part of the IndoorGML profile
	 * employed by the instance document. This may be any combination of IndoorGML
	 * extension modules in conjunction with the IndoorGML core module. A.1.1 Valid
	 * IndoorGML instance document
	 * 
	 * @throws SAXException If the resource cannot be parsed.
	 * @throws IOException  If the resource is not accessible. 
	 * @throws ParserConfigurationException If the resource cannot be parsed.
	 */
	@Test(description = "OGC 14-005r5, A.1.1")
	public void verifyValidIndoorGMLInstanceDocument() throws SAXException, IOException, ParserConfigurationException {

		
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
        DocumentBuilder db;
	    db = dbf.newDocumentBuilder();	          
        Document document = db.parse(indoorGMLFile.openStream());
        Element root = document.getDocumentElement();
		
       
		
		if(!root.getNodeName().endsWith("IndoorFeatures"))
		{
			Assert.assertTrue(root.getNodeName().endsWith("IndoorFeatures"), ErrorMessageKeys.MISSING_CORRECT_ROOT_ELEMENT);
		}
		
		//====validate
		
		StreamSource[] schemaDocuments = new StreamSource[1];
		schemaDocuments[0] = new StreamSource(
				new URL("https://schemas.opengis.net/indoorgml/1.0/indoorgmlnavi.xsd").openStream());  //indoorgmlnavi.xsd imports indoorgmlcore.xsd
		
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

	/**
	 * Validate the IndoorGML XML instance document against the XML Schema
	 * definitions of all employed IndoorGML modules. The process may be using an
	 * appropriate software tool for validation or be a manual process that checks
	 * all relevant definitions from the respective XML Schema specification of the
	 * employed IndoorGML modules A.1.2 Conformance classes related to IndoorGML
	 * modules
	 * @throws Exception 
	 */
	@Test(description = "OGC 14-005r5, A.1.2")
	public void validateConformanceClassesRelatedToIndoorGMLModules() throws Exception{

		
		StreamSource[] schemaDocuments = getStreamSourceArray(indoorGMLFile);
		
		
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
	
		} catch (Exception ee) {
	
			schemaValid = false;
			validationErrorMessage.append(". "+ ee.getMessage()+"\n");

		}

		Assert.assertTrue(schemaValid, validationErrorMessage.toString());	
		
	}

	/**
	 * Verify that all spatial geometry objects within an IndoorGML instance
	 * document adhere to the XML Schema definition of the Geography Markup Language
	 * version 3.2.1 and to the IndoorGML spatial model A.1.3 Spatial geometry
	 * objects
	 * 
	 * Requirement 1 - The dimensions of CellSpace and CellSpaceBoundary should be consistent. 
	 * If the geometry type of CellSpace is gml:Surface, then that of CellSpaceBoundary shall 
	 * be gml:Curve. And if the geometry type of CellSpace is gml:Solid, then that of 
	 * CellSpaceBoundary must be gml:Surface.
	 */
	@Test(description = "OGC 14-005r5, A.1.3 - Requirement 1")
	public void verifySpatialGeometryObjectsRequirement1() {
		
	
		
		boolean eachCellSpaceSolidHasSurfaceGeometry = false;
		
		CoreRequirementChecker rc = new CoreRequirementChecker();
		try {
			eachCellSpaceSolidHasSurfaceGeometry = rc.checkRequirement1(indoorGMLFile);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertTrue(eachCellSpaceSolidHasSurfaceGeometry,"Not every Solid CellSpace has a Surface Geometry");
		
	}
	
	/**
	 * Requirement 2 - The instances of CellSpace belonging to the same instance of SpaceLayer 
	 * shall not overlap.
	 */
	
	@Test(description = "OGC 14-005r5, A.1.3 - Requirement 2")
	public void verifySpatialGeometryObjectsRequirement2() {
		
	
		
		boolean foundOverlapsBetweenCellSpacesOfSameSpaceLayer = false;
		
		CoreRequirementChecker rc = new CoreRequirementChecker();
		try {
			foundOverlapsBetweenCellSpacesOfSameSpaceLayer = rc.checkRequirement2(indoorGMLFile);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertFalse(foundOverlapsBetweenCellSpacesOfSameSpaceLayer,"The instances of CellSpace belonging to the same instance of SpaceLayer shall not overlap.");
		
	}	

	
	/**
	 * Requirement 4 - Every instance of InterLayerConnection shall connect two State instances, each of which belongs to different space layers.
	 */
	
	@Test(description = "OGC 14-005r5, A.1.3 - Requirement 4")
	public void verifySpatialGeometryObjectsRequirement4() {
		
	
		
		boolean passesRequirement4Checks = true;
		
		CoreRequirementChecker rc = new CoreRequirementChecker();
		try {
			passesRequirement4Checks = rc.checkRequirement4(indoorGMLFile);
			
		} catch (Exception e) {
			passesRequirement4Checks = false;
			e.printStackTrace();
		}
		Assert.assertTrue(passesRequirement4Checks,"Failed Requirement 4 check that every instance of InterLayerConnection shall connect two State instances, each of which belongs to different space layers.");
		
	}	
	

	
}
