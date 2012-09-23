/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class FriendsHomeEvent extends GwtEvent<FriendsHomeEventHandler>{
	public static Type<FriendsHomeEventHandler> TYPE = new Type<FriendsHomeEventHandler>();
	public FriendsHomeEvent(){};
	@Override public Type<FriendsHomeEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(FriendsHomeEventHandler handler){
		handler.onFriendsHome(this);
	}
	
}
