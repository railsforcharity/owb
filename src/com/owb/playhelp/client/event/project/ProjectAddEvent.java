/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.project;

import com.google.gwt.event.shared.GwtEvent;

public class ProjectAddEvent extends GwtEvent<ProjectAddEventHandler>{
	public static Type<ProjectAddEventHandler> TYPE = new Type<ProjectAddEventHandler>();
	public ProjectAddEvent(){};
	@Override public Type<ProjectAddEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(ProjectAddEventHandler handler){
		handler.onProjectAdd(this);
	}
	
}

