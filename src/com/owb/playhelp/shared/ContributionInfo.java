package com.owb.playhelp.shared;

import java.io.Serializable;
import java.util.Date;
import com.owb.playhelp.shared.project.ProjectInfo;

@SuppressWarnings("serial")
public class ContributionInfo implements Serializable {

	private String id;
	private ProjectInfo project;
	private UserProfileInfo contribUser;
	private Date creationDate;
	private double resource;

	public ContributionInfo(){
		// the creation date is performed at the server side
		//this.creationDate = new Date();		
	}
	
	public ContributionInfo(UserProfileInfo contribUser, ProjectInfo project, double resource){
		this.project = project;
		this.contribUser = contribUser;
		this.resource = resource;
	}
	
	public ProjectInfo getProject(){
		return project;
	}
	
	public UserProfileInfo getUser(){
		return contribUser;
	}
	
	public double getResource(){
		return resource;
	}
	
	public Date getCreationDate(){
		return creationDate;
	}
	
	public void setProject(ProjectInfo project){
		this.project = project;
	}
	
	public void setUser(UserProfileInfo user){
		this.contribUser = user;
	}
	
	public void setResource(double resource){
		this.resource = resource;
	}
	
	public void setCreationDate(Date date){
		this.creationDate = date;
	}	
}
