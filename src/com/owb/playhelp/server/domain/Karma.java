package com.owb.playhelp.server.domain;

import java.io.Serializable;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.listener.StoreCallback;

import com.owb.playhelp.server.utils.cache.CacheSupport;
import com.owb.playhelp.server.utils.cache.Cacheable;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class Karma implements StoreCallback, Serializable, Cacheable {

	  private static final int CACHE_EXPIR = 1440 * 60;  // expire items after 24 hours, in seconds

	  @PrimaryKey
	  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	  @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	  private String id;
	  
	  @Persistent
	  private double karmaLevel;
	  
	  @Persistent
	  private UserProfile userKarma;

	  public Karma(){
		  this.karmaLevel = 0.0;
	  }
	  public void addToCache() {
	      CacheSupport.cachePutExp(this.getClass().getName(), id, this, CACHE_EXPIR);
	      addSummaryToCache();
	  }
	  public void removeFromCache() {
		  CacheSupport.cacheDelete(this.getClass().getName(), id);
	  }
	  public void addSummaryToCache(){
	  }

	  public void jdoPreStore() {
	  }
	  
	  public void addKarma(double karmaLevel){
		  this.karmaLevel = this.karmaLevel + karmaLevel;
	  }
	  
	  public void subtractKarma(double karmaLevel){
		  this.karmaLevel = this.karmaLevel - karmaLevel;
	  }
	  
	  public double getKarma(){
		  return this.karmaLevel;
	  }
	  
	  public UserProfile getUser(){
		  return userKarma;
	  }
}
