/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.server.domain.ngo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.UUID;

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
import com.owb.playhelp.server.domain.UserProfile;
import com.owb.playhelp.server.PMFactory;
import com.owb.playhelp.server.domain.ConfirmationBadge;
import com.owb.playhelp.server.domain.Standard;
import com.owb.playhelp.server.utils.Utils;
import com.owb.playhelp.server.utils.cache.CacheSupport;
import com.owb.playhelp.server.utils.cache.Cacheable;
import com.owb.playhelp.shared.ngo.NgoInfo;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class Ngo implements Serializable, Cacheable {

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

	/*
	@Persistent(dependent = "true")
	private Standard status;
	*/
	
	@Persistent
	private String uniqueId;
	
	@Persistent
	private String confirmationBadge;

	@Persistent
	private Set<String> memberRequests = new HashSet<String>();

	@Persistent
	private Set<String> members = new HashSet<String>();

	@Persistent
	private Set<String> followers = new HashSet<String>();

	//@Persistent(mappedBy = "userPrefs")
	//@Element(dependent = "true")
	//private Set<FriendItem> friends = new HashSet<FriendItem>();

	public Ngo() {
		if (this.getUniqueId() == null) {
			UUID uuid = UUID.randomUUID();
			this.uniqueId = uuid.toString();  //this.getEmail();
		} 
		
		//ConfirmationBadge confB = ConfirmationBadge.findOrCreateNgo(new ConfirmationBadge(this));
        
		// Don't need to update the uniqueId of the confirmationBadge because
		// the class ConfirmationBadge takes care of this
		//this.confirmationBadge = confB.getUniqueId();
	}

	public Ngo(NgoInfo ngoInfo) {
		this();
		this.setName(ngoInfo.getName());
		this.setDescription(ngoInfo.getDescription());
		this.setAddress(ngoInfo.getAddress());
		this.setLatitude(ngoInfo.getLatitude());
		this.setLongitude(ngoInfo.getLongitude());
		this.setPhone(ngoInfo.getPhone());
		this.setEmail(ngoInfo.getEmail());
		this.setWebsite(ngoInfo.getWebsite());
		if (ngoInfo.getUniqueId() != null) this.setUniqueId(ngoInfo.getUniqueId());
		
	}

	public void reEdit(NgoInfo ngoInfo) {
		this.setName(ngoInfo.getName());
		this.setDescription(ngoInfo.getDescription());
		this.setName(ngoInfo.getName());
		this.setAddress(ngoInfo.getAddress());
		this.setLatitude(ngoInfo.getLatitude());
		this.setLongitude(ngoInfo.getLongitude());
		this.setPhone(ngoInfo.getPhone());
		this.setEmail(ngoInfo.getEmail());
		this.setWebsite(ngoInfo.getWebsite());	
	}
	
	public static NgoInfo toInfo(Ngo o) {
		if (o == null)
			return null;

		NgoInfo oInfo = new NgoInfo(o.getName(), o.getDescription(), o.getAddress(),o.getLatitude(),o.getLongitude(),o.getPhone(), o.getEmail(), o.getWebsite());
		oInfo.setUniqueId(o.getUniqueId());
		//oInfo.setPoint(o.getLatitude(),o.getLongitude());
		oInfo.deactivateMember();
		oInfo.deactivateFollower();
		oInfo.setValid(o.isValid());
		oInfo.setConfirm(false);
		
		// Fill the information about members, followers,...
		List<String> nameList = new ArrayList<String>();
		if (o.getMembers() != null){
			for(String m:o.getMembers()){
				nameList.add(UserProfile.findUserProfile(new UserProfile(m)).getName());
			}
		} 
		oInfo.setMemberList(nameList);

		nameList = new ArrayList<String>();
		if (o.getMemberRequests() != null){
			for(String m:o.getMemberRequests()){
				nameList.add(UserProfile.findUserProfile(new UserProfile(m)).getName());
			}
		} 
		oInfo.setMemberReqList(nameList);

		nameList = new ArrayList<String>();
		if (o.getFollowers() != null){
			for(String m:o.getFollowers()){
				nameList.add(UserProfile.findUserProfile(new UserProfile(m)).getName());
			}
		} 
		oInfo.setFollowerList(nameList);
		
		return oInfo;
	}

	public static NgoInfo toInfo(Ngo o, String userUniqueId) {
		if (o == null)
			return null;

		NgoInfo oInfo = Ngo.toInfo(o);
		
		//oInfo.setPoint(o.getLatitude(),o.getLongitude());
		if (o.isMember(userUniqueId)) oInfo.activateMember();
		if (o.isFollower(userUniqueId)) oInfo.activateFollower();

		//oInfo.setValid(o.isValid());
		oInfo.setConfirm(o.isConfirmed(userUniqueId,o));
		
		return oInfo;
	}
	
	  // Retrieve the user from the database if it already exist or
	  // create a new account if it is the first loggin
	  public static Ngo findOrCreateNgo(Ngo ngo) {
	
	    PersistenceManager pm = PMFactory.getTxnPm();
	    Transaction tx = null;
	    Ngo oneResult = null, detached = null;
	
	    String uniqueId = ngo.getUniqueId();
	
	    Query q = pm.newQuery(Ngo.class, "uniqueId == :uniqueId");
	    q.setUnique(true);
	
	    boolean persistConfirmationBadge = false;
	    ConfirmationBadge confB = null;
	    // perform the query and creation under transactional control,
	    // to prevent another process from creating an acct with the same id.
	    try {
	      for (int i = 0; i < NUM_RETRIES; i++) {
	        tx = pm.currentTransaction();
	        tx.begin();
	        if (uniqueId != null) oneResult = (Ngo) q.execute(uniqueId);
	        if (oneResult != null) {
	          log.info("User uniqueId already exists: " + uniqueId);
	          detached = pm.detachCopy(oneResult);
	        } else {
	          log.info("Ngo " + uniqueId + " does not exist, creating...");
	          confB = new ConfirmationBadge(ngo);
	          persistConfirmationBadge = true;
	          pm.makePersistent(ngo);
	          detached = pm.detachCopy(ngo);
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
	          pm.makePersistent(ngo);
	          detached = pm.detachCopy(ngo);	    	
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
	    
	    if (persistConfirmationBadge) {
	    	ConfirmationBadge newConfB = ConfirmationBadge.findOrCreateNgo(confB);
	    }
	    return detached;
	  }
	

	public void addConfirmation(UserProfile user, Ngo ngo){
		if (!user.isMember(ngo)){
			log.info("User is not a member of the NGO");
			return;
		}
		this.getConfirmationBadge().addConfirmation(user, ngo);
	}
	
	public void addConfirmation(UserProfile user){
		this.getConfirmationBadge().addConfirmation(user);
	}
	  
	public boolean isMember(String userUniqueId){
		return members.contains(userUniqueId);
	}
	
	public boolean isFollower(String userUniqueId){
		return followers.contains(userUniqueId);
	}

	public boolean isConfirmed(String userId, Ngo ngo){
		return this.getConfirmationBadge().isConfirmed(userId, ngo);
	}
	
	public boolean isValid(){
		return this.getConfirmationBadge().isValid();
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
		return ConfirmationBadge.findNgo(this);
	}
	
	public String getConfirmationBadgeId(){
		return confirmationBadge;
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
	
	public void setConfirmationBadge(String uniqueId){
		this.confirmationBadge = uniqueId;
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
	
	public void requestMember(String member){
		// Check if the member exist
		if (memberRequests.contains(member)) return;
		
		// Add it if it does not
		memberRequests.add(member);
	}
	
	public void addMember(String member){
		// Check if the member exist
		if (members.contains(member)) return;
		
		// Add it if it does not
		members.add(member);
	}
	
	public Set<String> getMemberRequests(){
		return memberRequests;
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
}