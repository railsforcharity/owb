/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.project;

import com.google.gwt.event.shared.GwtEvent;
import com.owb.playhelp.shared.ContributionInfo;

public class ProjectContributeEvent extends GwtEvent<ProjectContributeEventHandler>{
	public static Type<ProjectContributeEventHandler> TYPE = new Type<ProjectContributeEventHandler>();
	private final ContributionInfo currentContribution;
	
	public ProjectContributeEvent(ContributionInfo contribution){
		this.currentContribution = contribution;
	};
	
	public ContributionInfo getProject() {
		return currentContribution;
	}
	@Override public Type<ProjectContributeEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(ProjectContributeEventHandler handler){
		handler.onProjectContribute(this);
	}
	
}

