/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ContactHomeEvent extends GwtEvent<ContactHomeEventHandler>{
	public static Type<ContactHomeEventHandler> TYPE = new Type<ContactHomeEventHandler>();
	public ContactHomeEvent(){};
	@Override public Type<ContactHomeEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(ContactHomeEventHandler handler){
		handler.onContactHome(this);
	}
	
}
