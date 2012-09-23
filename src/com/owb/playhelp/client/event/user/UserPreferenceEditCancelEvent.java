/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.user;

import com.google.gwt.event.shared.GwtEvent;

public class UserPreferenceEditCancelEvent extends GwtEvent<UserPreferenceEditCancelEventHandler>{
	public static Type<UserPreferenceEditCancelEventHandler> TYPE = new Type<UserPreferenceEditCancelEventHandler>();
	public UserPreferenceEditCancelEvent(){};
	@Override public Type<UserPreferenceEditCancelEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(UserPreferenceEditCancelEventHandler handler){
		handler.onUserPreferenceEditCancel(this);
	}
	
}

