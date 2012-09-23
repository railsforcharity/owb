package com.owb.playhelp.shared;

import java.util.HashSet;
import java.util.Set;

public class VolunteeringInfo extends ResourceInfo{
	
	private int numVolunteers = 0;
	
	public void setNumVolunteers(int numVolunteers){
		this.numVolunteers = numVolunteers;
	}
	public int getNumvolunteers(){
		return this.numVolunteers;
	}

	@Override
	public String[] toStringArray(){
		String[] strResource = new String[4];

		strResource[0] = this.getName();
		strResource[1] = this.getDescription();
		strResource[2] = this.getStartDate().toString()+"-"+this.getEndDate().toString();
		switch (this.getNumvolunteers()){
		case 0: strResource[3] = "none";
			break;
		case 1: strResource[3] = "1 person";
			break;
		default: strResource[3] = Double.toString(this.getNumvolunteers())+" people";
			break;
		}
				
		return strResource;
	}
	
}
