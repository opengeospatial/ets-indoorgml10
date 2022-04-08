package org.opengis.cite.indoorgml10;

/**
 * Defines keys used to access localized messages for assertion errors. The
 * messages are stored in Properties files that are encoded in ISO-8859-1
 * (Latin-1). For some languages the {@code native2ascii} tool must be used to
 * process the files and produce escaped Unicode characters.
 */
public class ErrorMessageKeys {

    public static final String NOT_SCHEMA_VALID = "NotSchemaValid";
    public static final String EMPTY_STRING = "EmptyString";
    public static final String XPATH_RESULT = "XPathResult";
    public static final String NAMESPACE_NAME = "NamespaceName";
    public static final String LOCAL_NAME = "LocalName";
    public static final String XML_ERROR = "XMLError";
    public static final String XPATH_ERROR = "XPathError";
    public static final String MISSING_INFOSET_ITEM = "MissingInfosetItem";
    public static final String UNEXPECTED_STATUS = "UnexpectedStatus";
    public static final String UNEXPECTED_MEDIA_TYPE = "UnexpectedMediaType";
    public static final String MISSING_ENTITY = "MissingEntity";
    public static final String MISSING_CORRECT_ROOT_ELEMENT = "The root element must be {http://www.opengis.net/indoorgml/1.0/core}IndoorFeatures";
    public static final String MISSING_CONTENT_IN_INDOORFEATURES_ELEMENT = "The IndoorFeatures element is an aggregated element with PrimalSpaceFeatures and MultiLayeredGraph. It must have at least one of either primalSpaceFeatures or multiLayeredGraph child elements";
    public static final String NO_NAVIGATION_MODULE_ELEMENTS_FOUND = "The document must satisfy the rules of the IndoorGML Indoor Navigation module described in clause 9. No Navigation Module elements were found in the document.";
    public static final String THICK_AND_THIN_DOOR_MODELS_FOUND = "Thick door model and thin door models shall not be defined in a same IndoorGML encoding.";
    public static final String THICK_DOOR_MODEL_EITHER_CONNECTIONSPACE_OR_ANCHORSPACE = "Every thick door shall be encoded as an instance of either ConnectionSpace or AnchorSpace.";
    public static final String THIN_DOOR_MODEL_EITHER_CONNECTIONBOUNDARY_OR_ANCHORBOUNDARY = "Every thin door shall be encoded as an instance of either ConnectionBoundary or AnchorBoundary.";
    
}
