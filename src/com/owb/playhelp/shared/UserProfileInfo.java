/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.shared;

import java.io.Serializable;

public class UserProfileInfo implements Serializable  {
	
	private String id;
	private String name;
	private String emailAddress;
	private String userType;
	private String channelId;
	private String uniqueId;

    public UserProfileInfo() {
	  
	}	  
    public String getId(){
    	return this.id;
    }
    public String getName(){
    	return this.name;
    }
    public String getEmailAddress(){
    	return this.emailAddress;
    }
    public String getUserType(){
    	return this.userType;
    }
    public String getChannel(){
    	return this.channelId;
    }
    public String getUniqueId(){
    	return this.uniqueId;
    }	  
    
    public void setId(String id){
    	this.id = id;
    }
    public void setName(String name){
    	this.name = name;
    }
    public void setEmailAddress(String emailAddress){
    	this.emailAddress = emailAddress;
    }
    public void setUserType(String userType){
    	this.userType = userType;
    }
    public void setChannelId(String channelId){
    	this.channelId = channelId;
    }
    public void setUniqueId(String uniqueId){
    	this.uniqueId = uniqueId;
    }
}
