/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class MainViewEvent extends GwtEvent<MainViewEventHandler>{
	public static Type<MainViewEventHandler> TYPE = new Type<MainViewEventHandler>();
	public MainViewEvent(){};
	@Override public Type<MainViewEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(MainViewEventHandler handler){
		handler.onMainView(this);
	}
	
}

