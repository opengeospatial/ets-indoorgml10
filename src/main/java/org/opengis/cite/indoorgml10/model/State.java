package org.opengis.cite.indoorgml10.model;

/**
 * <p>
 * State class.
 * </p>
 */
public class State {

	private String gmlid = "";

	/**
	 * <p>
	 * Constructor for State.
	 * </p>
	 * @param gmlid a {@link java.lang.String} object
	 */
	public State(String gmlid) {
		this.gmlid = gmlid;
	}

	/**
	 * <p>
	 * Getter for the field <code>gmlid</code>.
	 * </p>
	 * @return a {@link java.lang.String} object
	 */
	public String getGmlid() {
		return gmlid;
	}

	/**
	 * <p>
	 * Setter for the field <code>gmlid</code>.
	 * </p>
	 * @param gmlid a {@link java.lang.String} object
	 */
	public void setGmlid(String gmlid) {
		this.gmlid = gmlid;
	}

}
