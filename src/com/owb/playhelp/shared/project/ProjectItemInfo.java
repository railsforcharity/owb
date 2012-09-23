/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.shared.project;

import java.io.Serializable;

@SuppressWarnings("serial") public class ProjectItemInfo implements Serializable {

	private String id;
	private String name;
	private String description;
	private String status;
	private String uniqueId;

	public ProjectItemInfo() {
	}
	
	public ProjectItemInfo(String name) {
		this.name = name;
	}
	
	public ProjectItemInfo(String name, String description) {
		this(name);
		this.description = description;
	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public String getStatus() {
		return this.status;
	}

	public String getUniqueId() {
		return this.uniqueId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String desc) {
		this.description = desc;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
}
