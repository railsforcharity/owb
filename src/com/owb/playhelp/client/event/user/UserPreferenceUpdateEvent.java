/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.user;

import com.google.gwt.event.shared.GwtEvent;
import com.owb.playhelp.shared.UserProfileInfo;

public class UserPreferenceUpdateEvent extends GwtEvent<UserPreferenceUpdateEventHandler>{
	public static Type<UserPreferenceUpdateEventHandler> TYPE = new Type<UserPreferenceUpdateEventHandler>();
	private final UserProfileInfo updatedUser;
	
	public UserPreferenceUpdateEvent(UserProfileInfo updatedUser){
		this.updatedUser = updatedUser;
	}
	
	public UserProfileInfo getUpdatedUser(){
		return updatedUser;
	}
	
	@Override public Type<UserPreferenceUpdateEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(UserPreferenceUpdateEventHandler handler){
		handler.onUserPreferenceUpdate(this);
	}
	
}

