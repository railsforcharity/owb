/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.shared;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial") public class ResourceInfo implements Serializable {
	
	private String id;
	private Date startDate;
	private Date endDate;
	private String description;
	private String name;

	public ResourceInfo(){
		
	}
	public ResourceInfo(String name){
		this();
		this.name = name;
	}
	public ResourceInfo(String name, String description){
		this(name);
		this.description = description;
	}
	public ResourceInfo(String name, Date startDate){
		this(name);
		this.startDate = startDate;
	}
	public ResourceInfo(String name, String description, Date startDate){
		this(name,description);
		this.startDate = startDate;
	}
	public ResourceInfo(String name, Date startDate, Date endDate){
		this(name,startDate);
		this.endDate = endDate;
	}
	public ResourceInfo(String name, String description, Date startDate, Date endDate){
		this(name,description,startDate);
		this.endDate = endDate;
	}

	public String getId(){
		return this.id;
	}
	public String getName(){
		return this.name;
	}
	public String getDescription(){
		return this.description;
	}
	public Date getStartDate(){
		return this.startDate;
	}
	public Date getEndDate(){
		return this.endDate;
	}

	public void setId(String id){
		this.id = id;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setDescription(String description){
		this.description = description;
	}
	public void setStartDate(Date starDate){
		this.startDate = startDate;
	}
	public void setEndDate(Date endDate){
		this.endDate = endDate;
	}
	public String[] toStringArray(){
		String[] strResource = new String[4];

		strResource[0] = this.name;
		strResource[1] = this.description;
		strResource[2] = this.startDate.toString()+"-"+this.endDate.toString();
		strResource[3] = "";
		
		return strResource;
	}
}
