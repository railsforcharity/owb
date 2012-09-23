/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.project;

import com.google.gwt.event.shared.GwtEvent;
import com.owb.playhelp.client.helper.ClickPoint;
import com.owb.playhelp.shared.project.ProjectInfo;

public class ShowPopupAddProjectEvent extends GwtEvent<ShowPopupAddProjectEventHandler>{
	public static Type<ShowPopupAddProjectEventHandler> TYPE = new Type<ShowPopupAddProjectEventHandler>();
	
	private final ClickPoint point;
	private ProjectInfo project;
	
	public ShowPopupAddProjectEvent(ClickPoint location){
		this.point = location;
		this.project = null;
	};
	public ShowPopupAddProjectEvent(ClickPoint location, ProjectInfo project){
		this.point = location;
		this.project = project;
	};
	
	public ClickPoint getClickPoint(){
		return point;
	}
	
	public ProjectInfo getProject(){
		return project;
	}
	@Override public Type<ShowPopupAddProjectEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(ShowPopupAddProjectEventHandler handler){
		handler.onShowPopupAddProject(this);
	}
	
}
