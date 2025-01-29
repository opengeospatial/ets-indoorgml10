package org.opengis.cite.indoorgml10.model;

import java.util.ArrayList;

/**
 * <p>
 * SpaceLayer class.
 * </p>
 */
public class SpaceLayer {

	String gmlid = "";

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

	public ArrayList<State> stateList = new ArrayList<State>();

	/**
	 * <p>
	 * hasState.
	 * </p>
	 * @param stateId a {@link java.lang.String} object
	 * @return a boolean
	 */
	public boolean hasState(String stateId) {

		boolean result = false;
		String trimmedStateId = null;
		if (stateId.startsWith("#"))
			trimmedStateId = stateId.substring(1);
		else
			trimmedStateId = stateId;

		for (int z = 0; z < stateList.size(); z++) {
			if (stateList.get(z).getGmlid().equals(trimmedStateId)) {
				result = true;
			}
		}

		return result;
	}

}
