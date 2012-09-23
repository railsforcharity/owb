/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.orphanage;

import com.google.gwt.event.shared.GwtEvent;
import com.owb.playhelp.client.helper.ClickPoint;
import com.owb.playhelp.shared.orphanage.OrphanageInfo;

public class ShowPopupAddOrphanageStatusEvent extends GwtEvent<ShowPopupAddOrphanageStatusEventHandler>{
	public static Type<ShowPopupAddOrphanageStatusEventHandler> TYPE = new Type<ShowPopupAddOrphanageStatusEventHandler>();
	
	private final ClickPoint point;
	private OrphanageInfo orphanage;
	
	public ShowPopupAddOrphanageStatusEvent(ClickPoint location){
		this.point = location;
		this.orphanage = null;
	};
	public ShowPopupAddOrphanageStatusEvent(ClickPoint location, OrphanageInfo orphanage){
		this.point = location;
		this.orphanage = orphanage;
	};
	
	public ClickPoint getClickPoint(){
		return point;
	}
	
	public OrphanageInfo getOrphanage(){
		return orphanage;
	}
	@Override public Type<ShowPopupAddOrphanageStatusEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(ShowPopupAddOrphanageStatusEventHandler handler){
		handler.onShowPopupAddOrphanageStatus(this);
	}
	
}
