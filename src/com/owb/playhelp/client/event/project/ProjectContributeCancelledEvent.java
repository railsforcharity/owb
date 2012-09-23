/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.project;

import com.google.gwt.event.shared.GwtEvent;

public class ProjectContributeCancelledEvent extends GwtEvent<ProjectContributeCancelledEventHandler>{
	public static Type<ProjectContributeCancelledEventHandler> TYPE = new Type<ProjectContributeCancelledEventHandler>();
	public ProjectContributeCancelledEvent(){};
	@Override public Type<ProjectContributeCancelledEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(ProjectContributeCancelledEventHandler handler){
		handler.onProjectContributeCancelled(this);
	}
	
}


