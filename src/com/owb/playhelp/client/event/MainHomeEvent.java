/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class MainHomeEvent extends GwtEvent<MainHomeEventHandler>{
	public static Type<MainHomeEventHandler> TYPE = new Type<MainHomeEventHandler>();
	public MainHomeEvent(){};
	@Override public Type<MainHomeEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(MainHomeEventHandler handler){
		handler.onMainHomeRequest(this);
	}
	
}

