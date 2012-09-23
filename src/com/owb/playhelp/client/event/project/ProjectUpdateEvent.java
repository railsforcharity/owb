/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.project;

import com.google.gwt.event.shared.GwtEvent;
import com.owb.playhelp.shared.project.ProjectInfo;

public class ProjectUpdateEvent extends GwtEvent<ProjectUpdateEventHandler>{
	public static Type<ProjectUpdateEventHandler> TYPE = new Type<ProjectUpdateEventHandler>();
	private final ProjectInfo updatedProject;
	
	public ProjectUpdateEvent(ProjectInfo updatedProject){
		this.updatedProject = updatedProject;
	}
	public ProjectInfo getUpdatedProject() {
		return this.updatedProject;
	}
	
	@Override public Type<ProjectUpdateEventHandler> getAssociatedType() {
		return TYPE;
	}
	@Override protected void dispatch(ProjectUpdateEventHandler handler){
		handler.onProjectUpdated(this);
	}
}
