package com.owb.playhelp.shared.orphanage;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.geocode.Geocoder;
import com.google.gwt.maps.client.geocode.LatLngCallback;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.user.client.Timer;
import com.owb.playhelp.client.helper.MapHelper;
import com.owb.playhelp.shared.StandardInfo;

@SuppressWarnings("serial") public class OrphanageInfo implements Serializable {

	private String id;
	private String name;
	private String description;
	private String address;
	private double lat;
	private double lng;
	private String phone;
	private String email;
	private String website;
	private String uniqueId;
	private StandardInfo status;
	private boolean member;
	private boolean follower;
	private Set<String> ngoNames = new HashSet<String>();

	private boolean isApiLoaded;
	
	public OrphanageInfo() {

	}
	public OrphanageInfo(String name) {
		this();
		this.name = name;
	}
	public OrphanageInfo(String name, String description) {
		this(name);
		this.description = description;
	}
	public OrphanageInfo(String name, String description,String addr, double lat, double lng) {
		this(name,description);
		this.address = addr;
		
		isApiLoaded = false;

		this.lat = lat;
		this.lng = lng;
	}
	public OrphanageInfo(String name, String description,String address, double lat, double lng, String phone, String email, String website) {
		this(name,description,address,lat,lng);
		this.phone = phone;
		this.email = email;
		this.website = website;
	}

	public String getId() {
		return this.id;
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

	public String getUniqueId() {
		return this.uniqueId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public StandardInfo getStandard() {
		return this.status;
	}

	public void setStandard(StandardInfo status) {
		this.status = status;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setAddress(String address,double lat, double lng) {
		this.address = address;
		this.lat = lat;
		this.lng = lng;
	}
	public void setPoint(double lat, double lng) {
		this.lat = lat;
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

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	
	public boolean getMember(){
		return member;
	}
	
	public boolean getFollower(){
		return follower;
	}
	
	public Set<String> getNgoNames(){
		return ngoNames;
	}
	
	public void activateMember(){
		this.member = true;
	}
	public void deactivateMember(){
		this.member = false;
	}
	public void activateFollower(){
		this.follower = true;
	}
	public void deactivateFollower(){
		this.follower = false;
	}
	
}

