package org.opengis.cite.indoorgml10.model;

import java.util.ArrayList;

public class SpaceLayer {
	String gmlid = "";
	public String getGmlid() {
		return gmlid;
	}
	public void setGmlid(String gmlid) {
		this.gmlid = gmlid;
	}
	public ArrayList<State> stateList = new ArrayList<State>();
	public boolean hasState(String stateId) {
		
		boolean result = false;
		String trimmedStateId = null;
		if(stateId.startsWith("#")) trimmedStateId = stateId.substring(1);
		else trimmedStateId = stateId;
		
		for(int z=0; z < stateList.size(); z++) {
	       if(stateList.get(z).getGmlid().equals(trimmedStateId) ){
	    	   result = true;
	       }
		}
		
		return result;
	}
}
