/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class MoreMenuPopUpEvent extends GwtEvent<MoreMenuPopUpEventHandler>{
	public static Type<MoreMenuPopUpEventHandler> TYPE = new Type<MoreMenuPopUpEventHandler>();
	public MoreMenuPopUpEvent(){};
	@Override public Type<MoreMenuPopUpEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(MoreMenuPopUpEventHandler handler){
		handler.onMoreMenuPopUpEvent(this);
	}
	
}
