/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.server.access;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.owb.playhelp.server.utils.cache.CacheSupport;
import com.owb.playhelp.server.utils.cache.Cacheable;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class AccessRightList implements Serializable, Cacheable {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String name;

	  @Persistent(mappedBy = "accessR")
	  @Element(dependent = "true")
	  private Set<AccessRight> accessRight = new HashSet<AccessRight>();

	@Persistent
	private String uniqueId;

	//@Persistent(mappedBy = "userPrefs")
	//@Element(dependent = "true")
	//private Set<FriendItem> friends = new HashSet<FriendItem>();

	public AccessRightList() {
	}

	public AccessRightList(Long id) {
		// If it does exist fill the information with the current profile
		this.id = id;
	}


	public void addToCache() {
		CacheSupport.cachePut(this.getClass().getName(), id, this);
	}

	public void removeFromCache() {
		CacheSupport.cacheDelete(this.getClass().getName(), id);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
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
}