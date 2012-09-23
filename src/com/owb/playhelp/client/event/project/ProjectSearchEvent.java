/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.project;

import com.google.gwt.event.shared.GwtEvent;

public class ProjectSearchEvent extends GwtEvent<ProjectSearchEventHandler>{
	public static Type<ProjectSearchEventHandler> TYPE = new Type<ProjectSearchEventHandler>();
	public ProjectSearchEvent(){};
	@Override public Type<ProjectSearchEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(ProjectSearchEventHandler handler){
		handler.onProjectSearch(this);
	}
	
}

