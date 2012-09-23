/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.server.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
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
import javax.jdo.annotations.Element;

import com.owb.playhelp.server.PMFactory;
import com.owb.playhelp.server.utils.Utils;
import com.owb.playhelp.server.utils.cache.CacheSupport;
import com.owb.playhelp.server.utils.cache.Cacheable;
import com.owb.playhelp.server.domain.ngo.Ngo;
import com.owb.playhelp.server.domain.ngo.NgoItem;
import com.owb.playhelp.server.domain.project.Project;
import com.owb.playhelp.shared.project.ProjectInfo;
import com.owb.playhelp.shared.UserProfileInfo;
import com.owb.playhelp.shared.ngo.NgoInfo;
import com.owb.playhelp.shared.orphanage.OrphanageInfo;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class UserProfile implements Serializable, Cacheable  {

	  private static final Logger log = Logger.getLogger(Utils.class.getName());
	  private static final int NUM_RETRIES = 5; 

	  @PrimaryKey
	  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	  private Long id;

	  @Persistent
	  private String name;

	  // This should be more than a string allowing to store the picture 
	  // in the data base. Or it may be an url picture. Not sure how to handle it
	  @Persistent
	  private String picture;
	  
	  @Persistent
	  private String type;

	  @Persistent
	  private String emailAddress;

	  @Persistent
	  private Date lastLoginOn;

	  @Persistent
	  private Date lastActive;
	  
	  @Persistent
	  private Date lastReported;
	  
	  @Persistent
	  private String channelId;

	  /*
	  @Persistent(mappedBy = "userKarma")
	  @Element(dependent = "true")
	  private Karma karma;*/

	  /**
	   * loginId and loginProvider form a unique key. 
	   * E.g.: loginId = supercobra, loginProvider = LoginProvider.TWITTER
	   */
	  @Persistent
	  private String uniqueId;

      @Persistent
      private boolean isAdmin;
	  
      /*
	  @Persistent(mappedBy = "userChap")
	  //@Element(dependent = "true")
	  private Set<ChapterItem> chapters = new HashSet<ChapterItem>();
	  
	  @Persistent(mappedBy = "userProj")
	  //@Element(dependent = "true")
	  private Set<ProjectItem> projects = new HashSet<ProjectItem>();
	  
	  @Persistent(mappedBy = "userNgo")
	  //@Element(dependent = "true")
	  private Set<NgoItem> ngos = new HashSet<NgoItem>();
	  
	  @Persistent(mappedBy = "userOrph")
	  //@Element(dependent = "true")
	  private Set<OrphanageItem> orphanages = new HashSet<OrphanageItem>();
	  
	  @Persistent(mappedBy = "userFriends")
	  //@Element(dependent = "true")
	  private Set<FriendItem> friends = new HashSet<FriendItem>();
	  */

	  @Persistent
	  private Set<String> chapters = new HashSet<String>();
	  
	  @Persistent
	  private Set<String> projects = new HashSet<String>();
	  
	  @Persistent
	  private Set<String> ngos = new HashSet<String>();
	  
	  @Persistent
	  private Set<String> orphanages = new HashSet<String>();
	  
	  @Persistent
	  private Set<String> friends = new HashSet<String>();
	  
	  public UserProfile(){
		  this.isAdmin = true;
	  }

	  public UserProfile(String loginId, Integer loginProvider){
		  this();
		  this.setUniqueId(loginId + "-" + loginProvider);
		  
		  // We should may be retrieve the Name from Google
		  this.setName(loginId);
		  this.emailAddress = loginId;
			  this.picture = "profile_icon.png";
		  if (loginId=="mcharcos@example.com") this.isAdmin = true;
	  }
	  
	  public UserProfile(String uniqueId){
		  this();
		  
		  // Search info in Database and fill it if the info exist
		  this.name = "TBC";
		  this.emailAddress = "TBC";
		  this.uniqueId = uniqueId;
		  this.picture = "profile_icon.png";
	  }
	  
	  public static UserProfileInfo toInfo(UserProfile user){
		  if (user == null) return null;
		  
		  UserProfileInfo userInfo = new UserProfileInfo();

		  userInfo.setName(user.getName());
		  userInfo.setEmailAddress(user.getEmail());
		  userInfo.setUserType(user.getType());
		  userInfo.setUniqueId(user.getUniqueId());
		  userInfo.setChannelId(user.getChannelId());
		  
		  return userInfo;
	  }
	  
	  public void addToCache() {
	    CacheSupport.cachePut(this.getClass().getName(), id, this);
	  }

	  public void removeFromCache() {
	    CacheSupport.cacheDelete(this.getClass().getName(), id);
	  }
	  
	// Retrieve the user from the database if it already exist or
	  // create a new account if it is the first loggin
	  public static UserProfile findUserProfile(UserProfile user) {

	    PersistenceManager pm = PMFactory.getTxnPm();
	    Transaction tx = null;
	    UserProfile oneResult = null, detached = null;

	    String uniqueId = user.getUniqueId();

	    Query q = pm.newQuery(UserProfile.class, "uniqueId == :uniqueId");
	    q.setUnique(true);

	    // perform the query and creation under transactional control,
	    // to prevent another process from creating an acct with the same id.
	    try {
	      for (int i = 0; i < NUM_RETRIES; i++) {
	        tx = pm.currentTransaction();
	        tx.begin();
	        oneResult = (UserProfile) q.execute(uniqueId);
	        if (oneResult != null) {
	          log.info("User uniqueId already exists: " + uniqueId);
	          detached = pm.detachCopy(oneResult);
	        } else {
	          log.info("UserProfile " + uniqueId + " does not exist, creating...");
	          // Create friends from Google+
	          //user.setKarma(new Karma());
	          pm.makePersistent(user);
	          detached = pm.detachCopy(user);
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
	          pm.makePersistent(user);
	          detached = pm.detachCopy(user);	    	
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
	// Retrieve the user from the database if it already exist or
	  // create a new account if it is the first loggin
	  public static void createSuperUser() {

	    PersistenceManager pm = PMFactory.getTxnPm();
	    Transaction tx = null;
	    UserProfile oneResult = null, detached = null;

	    String uniqueId = "test100@gmail.com";

	    Query q = pm.newQuery(UserProfile.class, "uniqueId == :uniqueId");
	    q.setUnique(true);
	    

        UserProfile superUser = new UserProfile();
        OrphanageItem orphanage = new OrphanageItem();
        NgoItem ngo = new NgoItem();
        ProjectInfo project = new ProjectInfo();
        ChapterItem chapter = new ChapterItem();
        FriendItem friend = new FriendItem();
        /*
        superUser.addOrphanage(orphanage);
        superUser.addNgo(ngo);
        superUser.addProject(project);
        superUser.addChapter(chapter);
        superUser.addFriend(friend);*/

	    // perform the query and creation under transactional control,
	    // to prevent another process from creating an acct with the same id.
	    try {
	      for (int i = 0; i < NUM_RETRIES; i++) {
	        tx = pm.currentTransaction();
	        tx.begin();
	          // Create friends from Google+
	          
	          pm.makePersistent(superUser);
	          //detached = pm.detachCopy(superUser);
	          tx.commit();
	          break;
	      } // end for
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
	  }
	  
	  public void setName(String name){
		  this.name = name;
	  }
	  
	  public String getName(){
		  return this.name;
	  }

	  public void setType(String type){
		  this.type = type;
	  }
	  
	  public String getType(){
		  return this.type;
	  }

	  public void setId(Long id){
		  this.id = id;
	  }
	  
	  public Long getId(){
		  return this.id;
	  }

	  public void setEmail(String email){
		  this.emailAddress = email;
	  }
	  
	  public String getEmail(){
		  return this.emailAddress;
	  }

	  public void setChannelId(String channelId){
		  this.channelId = channelId;
	  }
	  
	  public String getChannelId(){
		  return this.channelId;
	  }

	  public void setUniqueId(String uniqueId) {
	    this.uniqueId = uniqueId;
	  }

	  public String getUniqueId() {
	    return uniqueId;
	  }
	  
	  /*
	  public void addChapter(ChapterItem chapter){
		  chapters.add(chapter);
	  }
	  public Set<ChapterItem> getChapters(){
		  return chapters;
	  }
	  
	  public void addProject(ProjectItem project){
		  projects.add(project);
	  }
	  public Set<ProjectItem> getProjects(){
		  return projects;
	  }
	  
	  public void addOrphanage(OrphanageItem orphanage){
		  orphanages.add(orphanage);
	  }
	  public Set<OrphanageItem> getOrphanagess(){
		  return orphanages;
	  }
	  
	  public void addNgo(NgoItem ngo){
		  ngos.add(ngo);
	  }
	  public Set<NgoItem> getNgos(){
		  return ngos;
	  }
	  
	  public void addFriend(FriendItem friend){
		  friends.add(friend);
	  }
	  public Set<FriendItem> getFriends(){
		  return friends;
	  }
	  */

	  public void addChapter(ChapterItem chapter){
		  chapters.add(chapter.getUniqueId());
	  }
	  public Set<String> getChapters(){
		  // retrieve the chapters with the corresponding UniqueIds
		  return chapters;
	  }
	  
	  public void addProject(ProjectInfo project){
		  projects.add(project.getUniqueId());
	  }
	  public Set<String> getProjects(){
		  return projects;
	  }
	  
	  public void addOrphanage(OrphanageInfo orphanage){
		  orphanages.add(orphanage.getUniqueId());
	  }
	  public Set<String> getOrphanagess(){
		  return orphanages;
	  }
	  
	  public void addNgo(NgoInfo ngo){
		  ngos.add(ngo.getUniqueId());
	  }
	  public Set<String> getNgos(){
		  return ngos;
	  }
	  
	  

	  public void setLastLoginOn(Date lastLoginOn) {
	    this.lastLoginOn = lastLoginOn;
	  }

	  public Date getLastLoginOn() {
	    return lastLoginOn;
	  }

	  public void setLastActive(Date lastActive) {
	    this.lastActive = lastActive;
	  }

	  public Date getLastActive() {
	    return lastActive;
	  }
	  
	  public void setLastReported(Date lastReported) {
	    this.lastReported = lastReported;
	  }

	  public Date getLastReported() {
	    return lastReported;
	  }
	  
	  public boolean isAdmin(){
		  return this.isAdmin;
	  }
	  
	  public boolean isMember(Ngo ngo){
		  return ngos.contains(ngo.getUniqueId());
	  }
	  
	  /*
	  public void setKarma(Karma karma){
		  this.karma = karma;
	  }
	  
	  public Karma getKarma(){
		  return this.karma;
	  }*/
	  
	  public void reEdit(UserProfileInfo userInfo){
		  this.name = userInfo.getName();
		  this.emailAddress = userInfo.getEmailAddress();
	  }
	  
}
