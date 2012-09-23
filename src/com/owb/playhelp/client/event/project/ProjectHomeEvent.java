/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.project;

import com.google.gwt.event.shared.GwtEvent;

public class ProjectHomeEvent extends GwtEvent<ProjectHomeEventHandler>{
	public static Type<ProjectHomeEventHandler> TYPE = new Type<ProjectHomeEventHandler>();
	public ProjectHomeEvent(){};
	@Override public Type<ProjectHomeEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(ProjectHomeEventHandler handler){
		handler.onProjectHomeRequest(this);
	}
	
}

