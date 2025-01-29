package org.opengis.cite.indoorgml10;

/**
 * <p>
 * CellSpacePairing class.
 * </p>
 *
 */
public class CellSpacePairing {

	public CellSpace cellSpace1 = null;

	public CellSpace cellSpace2 = null;

	/**
	 * <p>
	 * Constructor for CellSpacePairing.
	 * </p>
	 * @param cellSpace1 a {@link org.opengis.cite.indoorgml10.CellSpace} object
	 * @param cellSpace2 a {@link org.opengis.cite.indoorgml10.CellSpace} object
	 */
	public CellSpacePairing(CellSpace cellSpace1, CellSpace cellSpace2) {
		this.cellSpace1 = cellSpace1;
		this.cellSpace2 = cellSpace2;
	}

}
