/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.project;

import com.google.gwt.event.shared.GwtEvent;

public class ProjectAvailableEvent extends GwtEvent<ProjectAvailableEventHandler>{
	public static Type<ProjectAvailableEventHandler> TYPE = new Type<ProjectAvailableEventHandler>();
	public ProjectAvailableEvent(){};
	@Override public Type<ProjectAvailableEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(ProjectAvailableEventHandler handler){
		handler.onContentAvailable(this);
	}
	
}
