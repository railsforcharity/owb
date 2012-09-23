/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.server.domain.orphanage;

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
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.owb.playhelp.server.LoginHelper;
import com.owb.playhelp.server.domain.ConfirmationBadge;
import com.owb.playhelp.server.domain.Standard;
import com.owb.playhelp.server.domain.UserProfile;
import com.owb.playhelp.server.PMFactory;
import com.owb.playhelp.server.domain.UserProfile;
import com.owb.playhelp.shared.StandardInfo;
import com.owb.playhelp.server.utils.Utils;
import com.owb.playhelp.server.utils.cache.CacheSupport;
import com.owb.playhelp.server.utils.cache.Cacheable;
import com.owb.playhelp.shared.orphanage.OrphanageInfo;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class Orphanage implements Serializable, Cacheable {

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
	private String address;

	@Persistent
	private double lat, lng;
	
	@Persistent
	private String phone;

	@Persistent
	private String email;

	@Persistent
	private String website;
	
	@Persistent(dependent = "true")
	private Standard status;

	@Persistent
	private String uniqueId;
	
	@Persistent
	private String confirmationBadge;

	// Members and followers must be individuals 
	// Individuals could add organizations that they are register to
	@Persistent
	private Set<String> members = new HashSet<String>();

	@Persistent
	private Set<String> followers = new HashSet<String>();

	@Persistent
	private Set<String> ngos = new HashSet<String>();

	public Orphanage() {
		this.status = new Standard();
		if (this.getUniqueId() == null) {
			UUID uuid = UUID.randomUUID();
			this.uniqueId = uuid.toString();  //this.getEmail();
		} 
	}

	public Orphanage(OrphanageInfo OrphanageInfo) {
		this();
		this.setName(OrphanageInfo.getName());
		this.setDescription(OrphanageInfo.getDescription());
		this.setAddress(OrphanageInfo.getAddress());
		this.setLatitude(OrphanageInfo.getLatitude());
		this.setLongitude(OrphanageInfo.getLongitude());
		this.setPhone(OrphanageInfo.getPhone());
		this.setEmail(OrphanageInfo.getEmail());
		this.setWebsite(OrphanageInfo.getWebsite());
		this.setUniqueId(OrphanageInfo.getUniqueId());
		this.status.fromInfo(OrphanageInfo.getStandard());
		
		if (this.getUniqueId() == null) {
			this.uniqueId = this.getEmail();
		} 
	}

	public void reEdit(OrphanageInfo OrphanageInfo) {
		
		/*
		if (!this.getUniqueId().equals(OrphanageInfo.getUniqueId())){
			return;
		}*/
		
		this.setName(OrphanageInfo.getName());
		this.setDescription(OrphanageInfo.getDescription());
		this.setName(OrphanageInfo.getName());
		this.setAddress(OrphanageInfo.getAddress());
		this.setLatitude(OrphanageInfo.getLatitude());
		this.setLongitude(OrphanageInfo.getLongitude());
		this.setPhone(OrphanageInfo.getPhone());
		this.setEmail(OrphanageInfo.getEmail());
		this.setWebsite(OrphanageInfo.getWebsite());	
	}
	
	public static OrphanageInfo toInfo(Orphanage o) {
		if (o == null)
			return null;

		OrphanageInfo oInfo = new OrphanageInfo(o.getName(), o.getDescription(), o.getAddress(),o.getLatitude(),o.getLongitude(),o.getPhone(), o.getEmail(), o.getWebsite());
		oInfo.setUniqueId(o.getUniqueId());
		
		Standard status = new Standard();
		status.setHealth(o.getStandard().getHealth());
		status.setEducation(o.getStandard().getEducation());
		status.setNutrition(o.getStandard().getNutrition());
		oInfo.setStandard(status.toInfo());
		//oInfo.setPoint(o.getLatitude(),o.getLongitude());
		oInfo.deactivateMember();
		oInfo.deactivateFollower();
		
		// find and add list of Ngos associated to orphanage
		// TBD
		
		return oInfo;
	}

	public static OrphanageInfo toInfo(Orphanage o, String userUniqueId) {
		if (o == null)
			return null;

		OrphanageInfo oInfo = Orphanage.toInfo(o);
		
		//oInfo.setPoint(o.getLatitude(),o.getLongitude());
		if (o.isMember(userUniqueId)) oInfo.activateMember();
		if (o.isFollower(userUniqueId)) oInfo.activateFollower();
		
		return oInfo;
	}
	
	  // Retrieve the user from the database if it already exist or
	  // create a new account if it is the first loggin
	  public static Orphanage findOrCreateOrphanage(Orphanage Orphanage) {
	
	    PersistenceManager pm = PMFactory.getTxnPm();
	    Transaction tx = null;
	    Orphanage oneResult = null, detached = null;
	
	    String uniqueId = Orphanage.getUniqueId();
	
	    Query q = pm.newQuery(Orphanage.class, "uniqueId == :uniqueId");
	    q.setUnique(true);
	
	    // perform the query and creation under transactional control,
	    // to prevent another process from creating an acct with the same id.
	    try {
	      for (int i = 0; i < NUM_RETRIES; i++) {
	        tx = pm.currentTransaction();
	        tx.begin();
	        oneResult = (Orphanage) q.execute(uniqueId);
	        if (oneResult != null) {
	          log.info("User uniqueId already exists: " + uniqueId);
	          detached = pm.detachCopy(oneResult);
	        } else {
	          log.info("UserProfile " + uniqueId + " does not exist, creating...");
	          // Create friends from Google+
	          //user.setKarma(new Karma());
	          pm.makePersistent(Orphanage);
	          detached = pm.detachCopy(Orphanage);
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
	          pm.makePersistent(Orphanage);
	          detached = pm.detachCopy(Orphanage);	    	
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
	
	public boolean isMember(String userUniqueId){
		return members.contains(userUniqueId);
	}
	
	public boolean isFollower(String userUniqueId){
		return followers.contains(userUniqueId);
	}
	
	public boolean isNgo(String ngoUniqueId){
		return ngos.contains(ngoUniqueId);
	}
	  
	public void addToCache() {
		CacheSupport.cachePut(this.getClass().getName(), id, this);
	}

	public void removeFromCache() {
		CacheSupport.cacheDelete(this.getClass().getName(), id);
	}

	public String getName() {
		return this.name;
	}
	public String getDescription() {
		return this.description;
	}
	public String getAddress() {
		return this.address;
	}
	public double getLatitude() {
		return this.lat;
	}
	public double getLongitude() {
		return this.lng;
	}
	public String getPhone() {
		return this.phone;
	}
	public String getEmail() {
		return this.email;
	}
	public String getWebsite() {
		return this.website;
	}
	
	public ConfirmationBadge getConfirmationBadge(){
		// Search for Confirmation Badge with confirmationBadge uniqueId
		
		return new ConfirmationBadge(this);
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setLatitude(double lat) {
		this.lat = lat;
	}
	public void setLongitude(double lng) {
		this.lng = lng;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getUniqueId() {
		return uniqueId;
	}
	
	public Standard getStandard() {
		return this.status;
	}

	public void setStandard(StandardInfo status) {
		this.status.fromInfo(status);
	}
	public void setStandard(Standard status) {
		if (this.status == null) {
			this.status = new Standard();
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