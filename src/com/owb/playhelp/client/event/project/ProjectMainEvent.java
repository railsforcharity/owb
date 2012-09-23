/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.project;

import com.google.gwt.event.shared.GwtEvent;

public class ProjectMainEvent extends GwtEvent<ProjectMainEventHandler>{
	public static Type<ProjectMainEventHandler> TYPE = new Type<ProjectMainEventHandler>();
	public ProjectMainEvent(){};
	@Override public Type<ProjectMainEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(ProjectMainEventHandler handler){
		handler.onProjectMainRequest(this);
	}
	
}


