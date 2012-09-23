/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.orphanage;

import com.google.gwt.event.shared.GwtEvent;
import com.owb.playhelp.shared.orphanage.OrphanageInfo;

public class OrphanageUpdateEvent extends GwtEvent<OrphanageUpdateEventHandler>{
	public static Type<OrphanageUpdateEventHandler> TYPE = new Type<OrphanageUpdateEventHandler>();
	
	private final OrphanageInfo updatedOrphanage;
	
	public OrphanageUpdateEvent(OrphanageInfo updatedOrphanage){
		this.updatedOrphanage = updatedOrphanage;
	}
	
	public OrphanageInfo getUpdatedOrphanage(){
		return updatedOrphanage;
	}
	
	@Override public Type<OrphanageUpdateEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(OrphanageUpdateEventHandler handler){
		handler.onOrphanageUpdate(this);
	}
	
}

