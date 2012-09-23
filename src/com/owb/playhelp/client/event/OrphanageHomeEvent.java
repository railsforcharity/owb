/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class OrphanageHomeEvent extends GwtEvent<OrphanageHomeEventHandler>{
	public static Type<OrphanageHomeEventHandler> TYPE = new Type<OrphanageHomeEventHandler>();
	public OrphanageHomeEvent(){};
	@Override public Type<OrphanageHomeEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(OrphanageHomeEventHandler handler){
		handler.onOrphanageHomeRequest(this);
	}
	
}

