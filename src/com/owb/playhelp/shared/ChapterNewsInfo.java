/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.shared;

import java.io.Serializable;

public class ChapterNewsInfo implements Serializable {

	private String id;
	private String name;
	private String uniqueId;

	public ChapterNewsInfo() {

	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
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

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
}
