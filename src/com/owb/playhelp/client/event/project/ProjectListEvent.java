/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.project;

import com.google.gwt.event.shared.GwtEvent;

public class ProjectListEvent extends GwtEvent<ProjectListEventHandler>{
	public static Type<ProjectListEventHandler> TYPE = new Type<ProjectListEventHandler>();
	public ProjectListEvent(){};
	@Override public Type<ProjectListEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(ProjectListEventHandler handler){
		handler.onProjectList(this);
	}
	
}

