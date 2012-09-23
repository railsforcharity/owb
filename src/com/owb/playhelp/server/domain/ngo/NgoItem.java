/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.server.domain.ngo;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.owb.playhelp.server.domain.UserProfile;
import com.owb.playhelp.server.utils.cache.CacheSupport;
import com.owb.playhelp.server.utils.cache.Cacheable;
import com.owb.playhelp.shared.ngo.NgoItemInfo;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class NgoItem implements Serializable, Cacheable {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	private String id;

	@Persistent
	private String name;

	@Persistent
	private String uniqueId;

	//@Persistent(mappedBy = "userPrefs")
	//@Element(dependent = "true")
	//private Set<FriendItem> friends = new HashSet<FriendItem>();


	@Persistent
	private UserProfile userNgo;
	
	public NgoItem() {
	}

	public NgoItem(String id) {
		// If it does exist fill the information with the current profile
		this.id = id;
	}

	public static NgoItemInfo toInfo(NgoItem o) {
		if (o == null)
			return null;

		NgoItemInfo oInfo = new NgoItemInfo();


		return oInfo;
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

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getUniqueId() {
		return uniqueId;
	}
}