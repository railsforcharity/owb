/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class PreferencesEditEvent extends GwtEvent<PreferencesEditEventHandler>{
	public static Type<PreferencesEditEventHandler> TYPE = new Type<PreferencesEditEventHandler>();
	public PreferencesEditEvent(){};
	@Override public Type<PreferencesEditEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(PreferencesEditEventHandler handler){
		handler.onEditPreference(this);
	}

}
