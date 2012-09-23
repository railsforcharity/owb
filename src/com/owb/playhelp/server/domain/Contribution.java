package com.owb.playhelp.server.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Date;
import java.util.logging.Logger;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.JDOCanRetryException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.owb.playhelp.server.utils.Utils;
import com.owb.playhelp.server.utils.cache.CacheSupport;
import com.owb.playhelp.server.utils.cache.Cacheable;
import com.owb.playhelp.shared.UserProfileInfo;
import com.owb.playhelp.shared.ContributionInfo;
import com.owb.playhelp.shared.project.ProjectInfo;
import com.owb.playhelp.server.LoginHelper;
import com.owb.playhelp.server.PMFactory;
import com.owb.playhelp.server.domain.project.Project;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class Contribution implements Serializable, Cacheable {

	  private static final Logger log = Logger.getLogger(Utils.class.getName());
	  private static final int NUM_RETRIES = 5;
	  
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	@Persistent
	private String projectUser;
	
	@Persistent
	private Date projectDate;
	
	@Persistent
	private String contributorId;
	
	@Persistent
	private Date creationDate;
	
	@Persistent
	private double resource;
	
	public Contribution(ProjectInfo projectInfo, UserProfileInfo userInfo, double resource){
		this.creationDate = new Date();
		
		//this.projectUser = projectInfo.getUserId();
		//this.projectDate = projectInfo.getCreationDate();
		this.contributorId = userInfo.getUniqueId();
		this.resource = resource;
	}
	public Contribution(ContributionInfo contribInfo){
		this.creationDate = new Date();
		this.contributorId = contribInfo.getUser().getUniqueId();
		//this.projectDate = contribInfo.getProject().getCreationDate();
		//this.projectUser = contribInfo.getProject().getUserId();
		
		this.resource = contribInfo.getResource();
	}

	public void addToCache() {
		CacheSupport.cachePut(this.getClass().getName(), id, this);
	}

	public void removeFromCache() {
		CacheSupport.cacheDelete(this.getClass().getName(), id);
	}
	
	public String getUserId(){
		return contributorId;
	}
	
	public String getProjectUser(){
		return projectUser;
	}
	
	public Date getProjectDate(){
		return projectDate;
	}
	
	public double getResource(){
		return resource;
	}
	
	public ContributionInfo toInfo(){	
		ContributionInfo contributionInfo = new ContributionInfo();
		
		UserProfileInfo projUser = this.getUserInfo();
		if (projUser == null) {
			return null;
		}
		ProjectInfo contribProject = this.getProjectInfo();
		if (contribProject == null){
			return null;
		}
		
		contributionInfo.setCreationDate(this.getProjectDate());
		contributionInfo.setProject(contribProject);
		contributionInfo.setUser(projUser);
		contributionInfo.setResource(this.getResource());
		
		return contributionInfo;
	}

	public UserProfileInfo getUserInfo(){	
		UserProfileInfo userInfo = null;
		
		PersistenceManager pm = PMFactory.getTxnPm();
		
		UserProfile uniqueUser = null;
		Query q = pm.newQuery(UserProfile.class,"uniqueId == :userId");
		q.setUnique(true); 
		try{			
			for (int i = 0; i < NUM_RETRIES; i++){
				pm.currentTransaction().begin();
				uniqueUser = (UserProfile) q.execute(this.getUserId());
				if (uniqueUser == null){
					return null;
				}
				userInfo = UserProfile.toInfo(uniqueUser);
				
				try{
					log.fine("commiting...");
					pm.currentTransaction().commit();
					log.fine("commited!");
					break;
				} catch (JDOCanRetryException e1){
					if (i == (NUM_RETRIES - 1)) {
			            throw e1;
			          }
				}
			} // endfor 
			
		}catch (Exception e){
			log.warning(e.getMessage());
			userInfo = null;
		} finally {
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
				userInfo = null;
				log.warning("did transaction rollback");
			}
			pm.close();
		}
		
		return userInfo;
	}

	public ProjectInfo getProjectInfo(){	
		ProjectInfo projectInfo = null;
		
		PersistenceManager pm = PMFactory.getTxnPm();
		
		Project uniqueProject = null;
		Query q = pm.newQuery(Project.class,"userId == :userId && creationDate == :creationDate");
		q.setUnique(true); 
		try{			
			for (int i = 0; i < NUM_RETRIES; i++){
				pm.currentTransaction().begin();
				uniqueProject = (Project) q.execute(this.getUserId(),this.getProjectDate());
				if (uniqueProject == null){
					return null;
				}
				projectInfo = Project.toInfo(uniqueProject);
				
				try{
					log.fine("commiting...");
					pm.currentTransaction().commit();
					log.fine("commited!");
					break;
				} catch (JDOCanRetryException e1){
					if (i == (NUM_RETRIES - 1)) {
			            throw e1;
			          }
				}
			} // endfor 
			
		}catch (Exception e){
			log.warning(e.getMessage());
			projectInfo = null;
		} finally {
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
				projectInfo = null;
				log.warning("did transaction rollback");
			}
			pm.close();
		}
		
		return projectInfo;
	}
}
