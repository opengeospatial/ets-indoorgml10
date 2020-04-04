package org.opengis.cite.indoorgml10;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import javax.ws.rs.core.MediaType;
import org.opengis.cite.indoorgml10.util.ClientUtils;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.xml.XmlSuite;
import org.w3c.dom.Document;

/**
 * A supporting base class that sets up a common test fixture. These
 * configuration methods are invoked before those defined in a subclass.
 */
public class CommonFixture {

    /**
     * Root test suite package (absolute path).
     */
    public static final String ROOT_PKG_PATH = "/org/opengis/cite/indoorgml10/";
    /**
     * HTTP client component (JAX-RS Client API).
     */
    protected Client client;
    /**
     * An HTTP request message.
     */
    protected ClientRequest request;
    /**
     * An HTTP response message.
     */
    protected ClientResponse response;

    
	protected URL indoorGMLFile;

	/**
	 * Obtains the test subject from the ISuite context. The suite attribute
	 * {@link org.opengis.cite.indoorgml10.SuiteAttribute#TEST_SUBJECT} should
	 * evaluate to a DOM Document node.
	 * 
	 * @param testContext The test (group) context.
	 */
	@BeforeClass
	public void obtainTestSubject(ITestContext testContext) {

		XmlSuite suite = testContext.getSuite().getXmlSuite();

		Map<String, String> map = suite.getAllParameters();
		
		try {
		   this.indoorGMLFile = new URL(map.get("iut"));
		}
        catch(Exception ex){
			throw new SkipException("Failed to retrieve iut at obtainTestSubject " + ex.getMessage());
		}
		

	}    
    /**
     * Initializes the common test fixture with a client component for 
     * interacting with HTTP endpoints.
     *
     * @param testContext The test context that contains all the information for
     * a test run, including suite attributes.
     */
    @BeforeClass
    public void initCommonFixture(ITestContext testContext) {
        Object obj = testContext.getSuite().getAttribute(SuiteAttribute.CLIENT.getName());
        if (null != obj) {
            this.client = Client.class.cast(obj);
        }
        obj = testContext.getSuite().getAttribute(SuiteAttribute.TEST_SUBJECT.getName());
        if (null == obj) {
            throw new SkipException("Test subject not found in ITestContext.");
        }
    }

    @BeforeMethod
    public void clearMessages() {
        this.request = null;
        this.response = null;
    }

    /**
     * Obtains the (XML) response entity as a DOM Document. This convenience
     * method wraps a static method call to facilitate unit testing (Mockito
     * workaround).
     *
     * @param response A representation of an HTTP response message.
     * @param targetURI The target URI from which the entity was retrieved (may
     * be null).
     * @return A Document representing the entity.
     *
     * @see
     * ClientUtils#getResponseEntityAsDocument(com.sun.jersey.api.client.ClientResponse,
     * java.lang.String)
     */
    public Document getResponseEntityAsDocument(ClientResponse response,
            String targetURI) {
        return ClientUtils.getResponseEntityAsDocument(response, targetURI);
    }

    /**
     * Builds an HTTP request message that uses the GET method. This convenience
     * method wraps a static method call to facilitate unit testing (Mockito
     * workaround).
     *
     * @param endpoint A URI indicating the target resource.
     * @param qryParams A Map containing query parameters (may be null);
     * @param mediaTypes A list of acceptable media types; if not specified,
     * generic XML ("application/xml") is preferred.
     * @return A ClientRequest object.
     *
     * @see ClientUtils#buildGetRequest(java.net.URI, java.util.Map,
     * javax.ws.rs.core.MediaType...)
     */
    public ClientRequest buildGetRequest(URI endpoint,
            Map<String, String> qryParams, MediaType... mediaTypes) {
        return ClientUtils.buildGetRequest(endpoint, qryParams, mediaTypes);
    }

}
