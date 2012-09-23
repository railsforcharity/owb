/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.server.domain.project;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.owb.playhelp.server.domain.UserProfile;
import com.owb.playhelp.server.utils.cache.Cacheable;
import com.owb.playhelp.server.utils.cache.CacheSupport;
import com.owb.playhelp.shared.project.ProjectItemInfo;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class ProjectItem implements Serializable, Cacheable {

	  private static final int CACHE_EXPIR = 1440 * 60;  // expire items after 24 hours, in seconds
	  
	  @PrimaryKey
	  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	  @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	  private String id;

	  @Persistent
	  private String title;

	  @Persistent
	  private String description;

	  @Persistent
	  private Date date;
	  
	  // Resources
	  @Persistent
	  Set<Long> ukeys;
	  
	  @Persistent
	  private UserProfile userProj;
	  
	  public ProjectItem(){
	  }
	  
	  public ProjectItem(ProjectItemInfo projectItemInfo){
		  this();
		  this.title = projectItemInfo.getName();
		  this.description = projectItemInfo.getDescription();
	  }
	  
	  public ProjectItem(String title, Date date, Set<Long> ukeys){
		  this();
		  this.title = title;
		  this.date = date;
		  this.ukeys = ukeys;
	  }
	  
	  public ProjectItemInfo toInfo(){
		  ProjectItemInfo projectInfo = new ProjectItemInfo();
		  projectInfo.setId(id);
		  projectInfo.setDescription(description);
		  projectInfo.setName(title);
		  
		  return projectInfo;
	  }
	  
	  public void setTitle(String title){
		  this.title = title;
	  }
	  public String getTitle(){
		  return this.title;
	  }

	  public void setDate(Date date){
		  this.date = date;
	  }
	  public Date getDate(){
		  return this.date;
	  }

	  public void setDescription(String description){
		  this.description = description;
	  }
	  public String getDescription(){
		  return this.description;
	  }

	  public void setId(String id){
		  this.id = id;
	  }
	  public String getId(){
		  return this.id;
	  }
	  
	  public ProjectItemInfo buildItem(){
		  ProjectItemInfo summary = new ProjectItemInfo(title, description);
		  return summary;
	  }
	  
	  public ProjectItemInfo addToCacheGetSumm(){
		  CacheSupport.cachePutExp(this.getClass().getName(), id, this, CACHE_EXPIR);
		  return addSummaryToCache();
	  }
	  public ProjectItemInfo addSummaryToCache(){
		  ProjectItemInfo summary = buildItem();
		  CacheSupport.cachePut(ProjectItemInfo.class.getName(),summary.getId(), summary);
		  return summary;
	  }
	  public void addToCache() {
	      CacheSupport.cachePutExp(this.getClass().getName(), id, this, CACHE_EXPIR);
	      addSummaryToCache();
	  }
	  public void removeFromCache() {
		  CacheSupport.cacheDelete(this.getClass().getName(), id);
	  }
}
