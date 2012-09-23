/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.project;

import com.google.gwt.event.shared.GwtEvent;

public class ProjectAddCancelledEvent extends GwtEvent<ProjectAddCancelledEventHandler>{
	public static Type<ProjectAddCancelledEventHandler> TYPE = new Type<ProjectAddCancelledEventHandler>();
	public ProjectAddCancelledEvent(){};
	@Override public Type<ProjectAddCancelledEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(ProjectAddCancelledEventHandler handler){
		handler.onProjectAddCancelled(this);
	}
	
}

