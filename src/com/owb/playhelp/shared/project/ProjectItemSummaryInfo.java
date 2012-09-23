/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.shared.project;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ProjectItemSummaryInfo implements Serializable  {
	  private String id;
	  private String title;
	  private String status;
	  private Date date;

	  public ProjectItemSummaryInfo(){
		  
	  }
	  public ProjectItemSummaryInfo(String id,String title, Date date){
		  this();
		  this.id = id;
		  this.date = date;
		  this.title = title;
	  }
	  
	  public String getId(){
		  return this.id;
	  }
	  
	  public String getTitle(){
		  return this.title;
	  }
	  
	  public String getStatus(){
		  return this.status;
	  }
	  
	  public Date getDate(){
		  return this.date;
	  }

	  public void setId(String id){
		  this.id = id;
	  }
	  public void setTitle(String title){
		  this.title = title;
	  }
	  public void setStatus(String status){
		  this.status = status;
	  }
	  public void setDate(Date date){
		  this.date = date;
	  }
}
