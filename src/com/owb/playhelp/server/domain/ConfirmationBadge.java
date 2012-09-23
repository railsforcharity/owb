package com.owb.playhelp.server.domain;

import java.io.Serializable;
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

import com.owb.playhelp.server.PMFactory;
import com.owb.playhelp.server.domain.ngo.Ngo;
import com.owb.playhelp.server.domain.orphanage.Orphanage;
import com.owb.playhelp.server.domain.project.Project;
import com.owb.playhelp.server.utils.Utils;
import com.owb.playhelp.server.utils.cache.CacheSupport;
import com.owb.playhelp.server.utils.cache.Cacheable;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class ConfirmationBadge implements Serializable, Cacheable  {

	private static final Logger log = Logger.getLogger(Utils.class.getName());
	private static final int NUM_RETRIES = 5; 
	public static Integer USER=0, NGO=1, ORPHANAGE=2, PROJECT=3;
	  
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String uniqueId;
	
	// uniqueId of element to be confirmed
	@Persistent
	private String element;
	
	@Persistent
	private Integer elementType;
	
	@Persistent
	private Set<String> individualAbuse = new HashSet<String>();
	
	@Persistent
	private Set<String> individualUserAbuse = new HashSet<String>();

	@Persistent
	private Set<String> ngoConf = new HashSet<String>();

	// I added this to be able to track what user confirmed the badge on behalf an ngo
	@Persistent
	private Set<String> ngoUserConf = new HashSet<String>();
	
	@Persistent
	private Set<String> ngoConfDecision = new HashSet<String>();

	@Persistent
	private Set<String> owbConf = new HashSet<String>();

	// I added this to be able to track what user confirmed the badge on behalf owb
	@Persistent
	private Set<String> owbUserConf = new HashSet<String>();
	
	@Persistent
	private Set<String> owbConfDecision = new HashSet<String>();

	@Persistent
	private Set<String> adminConf = new HashSet<String>();
	
	@Persistent
	private Set<Integer> adminConfDecision = new HashSet<Integer>();
	
	@Persistent
	private Set<String> adminConfComment = new HashSet<String>();
    
	private ConfirmationBadge(){
		// Generate random uniqueId key
		UUID uuid = UUID.randomUUID();
		this.uniqueId = uuid.toString();		
	}
	
	// Not sure why I would like to confirm users.. but I though it was a good idea for some reason.
	public ConfirmationBadge(UserProfile user){
		this();
		this.elementType = ConfirmationBadge.USER;
		this.element = user.getUniqueId();
		//user.setConfirmationBadge(this.uniqueId);
	}
	
	public ConfirmationBadge(Ngo ngo){
		this();
		this.elementType = ConfirmationBadge.NGO;
		this.element = ngo.getUniqueId();
		if (ngo.getConfirmationBadgeId() == null){
			ngo.setConfirmationBadge(this.uniqueId);
		} else {
			this.uniqueId = ngo.getConfirmationBadgeId();
		}
			
	}
	
	public ConfirmationBadge(Orphanage orphanage){
		this();
		this.elementType = ConfirmationBadge.ORPHANAGE;
		this.element = orphanage.getUniqueId();
		//orphanage.setConfirmationBadge(this.uniqueId);
	}
	
	public ConfirmationBadge(Project project){
		this();
		this.elementType = ConfirmationBadge.PROJECT;
		this.element = project.getUniqueId();
		//project.setConfirmationBadge(this.uniqueId);
	}

	public void addConfirmation(UserProfile user, Ngo ngo){
		// if ngo is owb then add to owbConf else add to ngoConf
		// for now I will add all of them to ngoConf
		
		// First check if this specific NGO is confirmed
		ConfirmationBadge confB = ngo.getConfirmationBadge();
		if (!confB.isValid()){
			log.info("Ngo cannot confirm because it is itself invalid");
			return;
		}
		this.ngoConf.add(ngo.getUniqueId());
		this.ngoUserConf.add(user.getUniqueId());
		this.owbConf.add(ngo.getUniqueId());  // I just added this to the list because I don't want to deal now with searching if it is owb
		this.owbUserConf.add(user.getUniqueId());
	}
	
	public void delConfirmation(UserProfile user, Ngo ngo){
		// remove confirmation of a specific user representing the ngo.
		
	}
	
	public void addConfirmation(UserProfile user){
		// First check if the user is an admin
		if (!user.isAdmin()){
			log.info("User is not an administrator");
			return;
		}
		
		this.adminConf.add(user.getUniqueId());
	}

	public String getUniqueId(){
		return this.uniqueId;
	}
	public String getElement(){
		return element;
	}
	public Integer getElementType(){
		return elementType;
	}
	public Set<String> getNgoConfList(){
		return ngoConf;
	}
	public Set<String> getOwbConfList(){
		return owbConf;
	}
	public Set<String> getAdminConfList(){
		return adminConf;
	}
	
	public void setUniqueId(String uniqueId){
		this.uniqueId = uniqueId;
	}
	
	public boolean isValid(){
		if (adminConf.isEmpty()){
			log.info("No Admin has confirmed this element");
			return false;
		}
		
		// check the adminConfDecision to see if there is not deny
		
		return true;
	}

	public boolean isConfirmed(String userId, Ngo ngo){
		// Check if the userId has confirmed this badge within the specific NGO
		
		return false;
	}
	
	public boolean isSupported(){
		if (ngoConf.isEmpty()){
			log.info("No Ngo has confirmed this element");
			return false;
		}
		if (owbConf.isEmpty()){
			log.info("No Owb has confirmed this element");
			return false;
		}
		return true;
	}
	public static ConfirmationBadge findNgo(Ngo ngo) {
	    PersistenceManager pm = PMFactory.getTxnPm();
	    Transaction tx = null;
	    ConfirmationBadge oneResult = null, detached = null;
	
	    String uniqueId = ngo.getConfirmationBadgeId();
	
	    Query q = pm.newQuery(ConfirmationBadge.class, "uniqueId == :uniqueId");
	    q.setUnique(true);
	
	    // perform the query and creation under transactional control,
	    // to prevent another process from creating an acct with the same id.
	    try {
	      for (int i = 0; i < NUM_RETRIES; i++) {
	        tx = pm.currentTransaction();
	        tx.begin();
	        oneResult = (ConfirmationBadge) q.execute(uniqueId);
	        if (oneResult != null) {
	          log.info("User uniqueId already exists: " + uniqueId);
	          detached = pm.detachCopy(oneResult);
	        } else {
	          log.info("UserProfile " + uniqueId + " does not exist, creating...");
	          // Something is really wrong here because that means that we got a NGO without a badge
	          //pm.makePersistent(confB);
	          //detached = pm.detachCopy(confB);
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
	public static ConfirmationBadge findOrCreateNgo(ConfirmationBadge confB) {
		
	    PersistenceManager pm = PMFactory.getTxnPm();
	    Transaction tx = null;
	    ConfirmationBadge oneResult = null, detached = null;
	
	    String uniqueId = confB.getUniqueId();
	
	    Query q = pm.newQuery(ConfirmationBadge.class, "uniqueId == :uniqueId");
	    q.setUnique(true);
	
	    // perform the query and creation under transactional control,
	    // to prevent another process from creating an acct with the same id.
	    try {
	      for (int i = 0; i < NUM_RETRIES; i++) {
	        tx = pm.currentTransaction();
	        tx.begin();
	        oneResult = (ConfirmationBadge) q.execute(uniqueId);
	        if (oneResult != null) {
	          log.info("User uniqueId already exists: " + uniqueId);
	          detached = pm.detachCopy(oneResult);
	        } else {
	          log.info("UserProfile " + uniqueId + " does not exist, creating...");
	          // Create friends from Google+
	          //user.setKarma(new Karma());
	          pm.makePersistent(confB);
	          detached = pm.detachCopy(confB);
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
	          pm.makePersistent(confB);
	          detached = pm.detachCopy(confB);	    	
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
	
	public void addAbuse(String userId, String report){
		individualAbuse.add(report);
		individualUserAbuse.add(userId);
	}
	
	public void addToCache() {
		CacheSupport.cachePut(this.getClass().getName(), id, this);
	}

	public void removeFromCache() {
		CacheSupport.cacheDelete(this.getClass().getName(), id);
	}
}
