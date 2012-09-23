/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.project;

import com.google.gwt.event.shared.GwtEvent;
import com.owb.playhelp.client.helper.ClickPoint;
import com.owb.playhelp.shared.project.ProjectInfo;

public class ShowPopupAddProjectStatusEvent extends GwtEvent<ShowPopupAddProjectStatusEventHandler>{
	public static Type<ShowPopupAddProjectStatusEventHandler> TYPE = new Type<ShowPopupAddProjectStatusEventHandler>();
	
	private final ClickPoint point;
	private ProjectInfo project;
	
	public ShowPopupAddProjectStatusEvent(ClickPoint location){
		this.point = location;
		this.project = null;
	};
	public ShowPopupAddProjectStatusEvent(ClickPoint location, ProjectInfo project){
		this.point = location;
		this.project = project;
	};
	
	public ClickPoint getClickPoint(){
		return point;
	}
	
	public ProjectInfo getProject(){
		return project;
	}
	@Override public Type<ShowPopupAddProjectStatusEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(ShowPopupAddProjectStatusEventHandler handler){
		handler.onShowPopupAddProjectStatus(this);
	}
	
}
