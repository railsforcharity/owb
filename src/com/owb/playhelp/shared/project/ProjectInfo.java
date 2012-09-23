/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.shared.project;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;

import com.owb.playhelp.shared.StandardInfo;
import com.owb.playhelp.shared.ResourceInfo;
import com.owb.playhelp.shared.ChapterInfo;
import com.owb.playhelp.shared.orphanage.OrphanageInfo;
import com.owb.playhelp.shared.ngo.NgoInfo;

@SuppressWarnings("serial") public class ProjectInfo implements Serializable {

	private String id;
	private String name;
	private String description;
	private String website;
	private String uniqueId;
	private StandardInfo status;
	private boolean member;
	private boolean follower;
	private Set<String> ngoList = new HashSet<String>();
	private String orphanage = "";
	
	// projects and needs are managed through their uniqueId key.
	private Set<String> needs = new HashSet<String>();
	private Set<String> contributions = new HashSet<String>();

	public ProjectInfo() {
	}
	
	public ProjectInfo(String name) {
		this.name = name;
	}
	
	public ProjectInfo(String name, String description) {
		this(name);
		this.description = description;
	}

	public ProjectInfo(String name, String description, String website) {
		this(name,description);
		this.website = website;
	}
	
	public String getWebsite() {
		return this.website;
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

	public StandardInfo getStandard() {
		return this.status;
	}

	public String getUniqueId() {
		return this.uniqueId;
	}

	public boolean getMember(){
		return member;
	}
	
	public boolean getFollower(){
		return follower;
	}
	
	public Set<String> getNgoList(){
		return ngoList;
	}
	
	public String getOrphanage(){
		return orphanage;
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

	public void setWebsite(String website) {
		this.website = website;
	}

	public void setStatus(StandardInfo status) {
		this.status = status;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	
	public void setOrphanage(String orphanage){
		this.orphanage = orphanage;
	}
		
	public void activateMember(){
		this.member = true;
	}
	public void deactivateMember(){
		this.member = false;
	}
	public void activateFollower(){
		this.follower = true;
	}
	public void deactivateFollower(){
		this.follower = false;
	}
}
