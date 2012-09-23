/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.server.domain.project;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

import javax.jdo.JDOCanRetryException;
import javax.jdo.JDOUserException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.owb.playhelp.server.PMFactory;
import com.owb.playhelp.server.domain.ConfirmationBadge;
import com.owb.playhelp.server.domain.Standard;
import com.owb.playhelp.server.domain.orphanage.Orphanage;
import com.owb.playhelp.server.domain.orphanage.OrphanageStandard;
import com.owb.playhelp.server.utils.Utils;
import com.owb.playhelp.server.utils.cache.CacheSupport;
import com.owb.playhelp.server.utils.cache.Cacheable;
import com.owb.playhelp.shared.project.ProjectInfo;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class Project implements Serializable, Cacheable {

	private static final Logger log = Logger.getLogger(Utils.class.getName());
	private static final int NUM_RETRIES = 5; 
	  
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String name;

	@Persistent
	private String description;

	@Persistent
	private String website;
	
	@Persistent(dependent = "true")
	private ProjectStandard status;

	@Persistent
	private Date creationDate;

	@Persistent
	private String uniqueId;
	
	@Persistent
	private String confirmationBadge;

	@Persistent
	private Set<String> members = new HashSet<String>();

	@Persistent
	private Set<String> followers = new HashSet<String>();

	@Persistent
	private Set<String> ngos = new HashSet<String>();

	//@Persistent(mappedBy = "pNews")
	//@Element(dependent = "true")
	//private Set<ProjectNews> news = new HashSet<ProjectNews>();

	//@Persistent(mappedBy = "pNeeds")
	//@Element(dependent = "true")
	//private Set<ProjectNeed> needs = new HashSet<ProjectNeed>();


	public Project(){
		this.status = new ProjectStandard();
		if (this.getUniqueId() == null) {
			UUID uuid = UUID.randomUUID();
			this.uniqueId = uuid.toString();  //this.getEmail();
		} 
	}
	public Project(ProjectInfo projectInfo) {
		this();
		this.name = projectInfo.getName();
		this.description = projectInfo.getDescription();
		// we may need to check if the uniqueId was not empty.
		this.uniqueId = projectInfo.getUniqueId();
	}
	
	public void reEdit(ProjectInfo projectInfo) {
		this.name = projectInfo.getName();
		this.description = projectInfo.getDescription();
	}
	
	public static ProjectInfo toInfo(Project o) {
		if (o == null)
			return null;

		ProjectInfo projectInfo = new ProjectInfo();
		projectInfo.setName(o.getName());
		projectInfo.setDescription(o.getDescription());
		
		return projectInfo;
	}


	  // Retrieve the user from the database if it already exist or
	  // create a new account if it is the first loggin
	  public static Project findOrCreateOrphanage(Project project) {
	
	    PersistenceManager pm = PMFactory.getTxnPm();
	    Transaction tx = null;
	    Project oneResult = null, detached = null;
	
	    String uniqueId = project.getUniqueId();
	
	    Query q = pm.newQuery(Project.class, "uniqueId == :uniqueId");
	    q.setUnique(true);
	
	    // perform the query and creation under transactional control,
	    // to prevent another process from creating an acct with the same id.
	    try {
	      for (int i = 0; i < NUM_RETRIES; i++) {
	        tx = pm.currentTransaction();
	        tx.begin();
	        oneResult = (Project) q.execute(uniqueId);
	        if (oneResult != null) {
	          log.info("User uniqueId already exists: " + uniqueId);
	          detached = pm.detachCopy(oneResult);
	        } else {
	          log.info("Project " + uniqueId + " does not exist, creating...");
	          pm.makePersistent(project);
	          detached = pm.detachCopy(project);
	        }
	        try {
	          tx.commit();
	          break;
	        }
	        catch (JDOCanRetryException e1) {
	          if (i == (NUM_RETRIES - 1)) { 
	            throw e1;
	          }
	        }
	      } // end for
	    } catch (JDOUserException e){
	          log.info("JDOUserException: UserProfile table is empty");
	          // Create friends from Google+
	          pm.makePersistent(project);
	          detached = pm.detachCopy(project);	    	
		        try {
			          tx.commit();
			        }
			        catch (JDOCanRetryException e1) {
			        }
	    } catch (Exception e) {
	      e.printStackTrace();
	    } 
	    finally {
	      if (tx.isActive()) {
	        tx.rollback();
	      }
	      pm.close();
	      q.closeAll();
	    }
	    
	    return detached;
	  }
	
	public void addToCache() {
		CacheSupport.cachePut(this.getClass().getName(), id, this);
	}

	public void removeFromCache() {
		CacheSupport.cacheDelete(this.getClass().getName(), id);
	}

	
	public boolean isMember(String userUniqueId){
		return members.contains(userUniqueId);
	}
	
	public boolean isFollower(String userUniqueId){
		return followers.contains(userUniqueId);
	}
	
	public boolean isNgo(String ngoUniqueId){
		return ngos.contains(ngoUniqueId);
	}
		
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}
	public Date getCreationDate() {
		return this.creationDate;
	}
	
	public String getUniqueId(){
		return this.uniqueId;
	}
	
	public void setUniqueId(String uniqueId){
		this.uniqueId = uniqueId;
	}
	
	public ConfirmationBadge getConfirmationBadge(){
		// Search for Confirmation Badge with confirmationBadge uniqueId
		
		return new ConfirmationBadge(this);
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}


	public ProjectStandard getStandard() {
		return this.status;
	}

	public void setStandard(ProjectStandard status) {
		this.status = status;
	}
	public void setStandard(Standard status) {
		if (this.status == null) {
			this.status = new ProjectStandard();
		}
		this.status.setHealth(status.getHealth());
		this.status.setEducation(status.getEducation());
		this.status.setNutrition(status.getNutrition());
	}
	
	public void addMember(String member){
		// Check if the member exist
		if (members.contains(member)) return;
		
		// Add it if it does not
		members.add(member);
	}
	
	public Set<String> getMembers(){
		return members;
	}
	
	public void addFollowers(String follower){
		// Check if the member exist
		if (followers.contains(follower)) return;
		
		// Add it if it does not
		followers.add(follower);
	}
	
	public Set<String> getFollowers(){
		return followers;
	}
	
	public void addNgos(String ngo){
		// Check if the member exist
		if (ngos.contains(ngo)) return;
		
		// Add it if it does not
		ngos.add(ngo);
	}
	
	public Set<String> getNgos(){
		return ngos;
	}
	
	
}