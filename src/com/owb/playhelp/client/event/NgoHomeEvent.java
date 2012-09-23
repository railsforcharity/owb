/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class NgoHomeEvent extends GwtEvent<NgoHomeEventHandler>{
	public static Type<NgoHomeEventHandler> TYPE = new Type<NgoHomeEventHandler>();
	public NgoHomeEvent(){};
	@Override public Type<NgoHomeEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(NgoHomeEventHandler handler){
		handler.onNgoHomeRequest(this);
	}
	
}

