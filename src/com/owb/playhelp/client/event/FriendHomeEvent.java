/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class FriendHomeEvent extends GwtEvent<FriendHomeEventHandler>{
	public static Type<FriendHomeEventHandler> TYPE = new Type<FriendHomeEventHandler>();
	public FriendHomeEvent(){};
	@Override public Type<FriendHomeEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(FriendHomeEventHandler handler){
		handler.onFriendHomeRequest(this);
	}
	
}

