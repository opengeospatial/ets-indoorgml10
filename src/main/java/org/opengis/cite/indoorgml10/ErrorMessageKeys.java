package org.opengis.cite.indoorgml10;

/**
 * Defines keys used to access localized messages for assertion errors. The messages are
 * stored in Properties files that are encoded in ISO-8859-1 (Latin-1). For some languages
 * the {@code native2ascii} tool must be used to process the files and produce escaped
 * Unicode characters.
 */
public class ErrorMessageKeys {

	/** Constant <code>NOT_SCHEMA_VALID="NotSchemaValid"</code> */
	public static final String NOT_SCHEMA_VALID = "NotSchemaValid";

	/** Constant <code>EMPTY_STRING="EmptyString"</code> */
	public static final String EMPTY_STRING = "EmptyString";

	/** Constant <code>XPATH_RESULT="XPathResult"</code> */
	public static final String XPATH_RESULT = "XPathResult";

	/** Constant <code>NAMESPACE_NAME="NamespaceName"</code> */
	public static final String NAMESPACE_NAME = "NamespaceName";

	/** Constant <code>LOCAL_NAME="LocalName"</code> */
	public static final String LOCAL_NAME = "LocalName";

	/** Constant <code>XML_ERROR="XMLError"</code> */
	public static final String XML_ERROR = "XMLError";

	/** Constant <code>XPATH_ERROR="XPathError"</code> */
	public static final String XPATH_ERROR = "XPathError";

	/** Constant <code>MISSING_INFOSET_ITEM="MissingInfosetItem"</code> */
	public static final String MISSING_INFOSET_ITEM = "MissingInfosetItem";

	/** Constant <code>UNEXPECTED_STATUS="UnexpectedStatus"</code> */
	public static final String UNEXPECTED_STATUS = "UnexpectedStatus";

	/** Constant <code>UNEXPECTED_MEDIA_TYPE="UnexpectedMediaType"</code> */
	public static final String UNEXPECTED_MEDIA_TYPE = "UnexpectedMediaType";

	/** Constant <code>MISSING_ENTITY="MissingEntity"</code> */
	public static final String MISSING_ENTITY = "MissingEntity";

	/**
	 * Constant
	 * <code>MISSING_CORRECT_ROOT_ELEMENT=". The root element must be {http://www."{trunked}</code>
	 */
	public static final String MISSING_CORRECT_ROOT_ELEMENT = ". The root element must be {http://www.opengis.net/indoorgml/1.0/core}IndoorFeatures";

	/**
	 * Constant
	 * <code>MISSING_CONTENT_IN_INDOORFEATURES_ELEMENT=". The IndoorFeatures element is an aggr"{trunked}</code>
	 */
	public static final String MISSING_CONTENT_IN_INDOORFEATURES_ELEMENT = ". The IndoorFeatures element is an aggregated element with PrimalSpaceFeatures and MultiLayeredGraph. It must have at least one of either primalSpaceFeatures or multiLayeredGraph child elements";

	/**
	 * Constant
	 * <code>NO_NAVIGATION_MODULE_ELEMENTS_FOUND=". The document must satisfy the rules o"{trunked}</code>
	 */
	public static final String NO_NAVIGATION_MODULE_ELEMENTS_FOUND = ". The document must satisfy the rules of the IndoorGML Indoor Navigation module described in clause 9. No Navigation Module elements were found in the document.";

	/**
	 * Constant
	 * <code>THICK_AND_THIN_DOOR_MODELS_FOUND=". Failed Requirement 5. Thick door mode"{trunked}</code>
	 */
	public static final String THICK_AND_THIN_DOOR_MODELS_FOUND = ". Failed Requirement 5. Thick door model and thin door models shall not be defined in a same IndoorGML encoding.";

	/**
	 * Constant
	 * <code>THICK_DOOR_MODEL_EITHER_CONNECTIONSPACE_OR_ANCHORSPACE="Failed Requirement 6. Every thick door "{trunked}</code>
	 */
	public static final String THICK_DOOR_MODEL_EITHER_CONNECTIONSPACE_OR_ANCHORSPACE = "Failed Requirement 6. Every thick door shall be encoded as an instance of either ConnectionSpace or AnchorSpace.";

	/**
	 * Constant
	 * <code>THIN_DOOR_MODEL_EITHER_CONNECTIONBOUNDARY_OR_ANCHORBOUNDARY=". Failed Requirement 7. Every thin door"{trunked}</code>
	 */
	public static final String THIN_DOOR_MODEL_EITHER_CONNECTIONBOUNDARY_OR_ANCHORBOUNDARY = ". Failed Requirement 7. Every thin door shall be encoded as an instance of either ConnectionBoundary or AnchorBoundary.";

}
