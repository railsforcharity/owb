/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.project;

import com.google.gwt.event.shared.GwtEvent;
import com.owb.playhelp.client.helper.MouseClick;
import com.owb.playhelp.shared.ResourceInfo;
import com.owb.playhelp.shared.VolunteeringInfo;

public class ProjectNeedAddEvent extends GwtEvent<ProjectNeedAddEventHandler>{
	public static Type<ProjectNeedAddEventHandler> TYPE = new Type<ProjectNeedAddEventHandler>();
	private ResourceInfo resource = null;
	
	public ProjectNeedAddEvent(ResourceInfo resource){
		this.resource = resource;
	}
	
	public ResourceInfo getResource(){
		return resource;
	}
	
	@Override public Type<ProjectNeedAddEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(ProjectNeedAddEventHandler handler){
		handler.onProjectNeedAdd(this);
	}
}

