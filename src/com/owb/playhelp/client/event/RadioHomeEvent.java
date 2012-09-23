/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class RadioHomeEvent extends GwtEvent<RadioHomeEventHandler>{
	public static Type<RadioHomeEventHandler> TYPE = new Type<RadioHomeEventHandler>();
	public RadioHomeEvent(){};
	@Override public Type<RadioHomeEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(RadioHomeEventHandler handler){
		handler.onRadioHome(this);
	}
	
}

