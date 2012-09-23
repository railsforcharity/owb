/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.orphanage;

import com.google.gwt.event.shared.GwtEvent;
import com.owb.playhelp.client.helper.ClickPoint;
import com.owb.playhelp.shared.orphanage.OrphanageInfo;

public class ShowPopupAddOrphanageEvent extends GwtEvent<ShowPopupAddOrphanageEventHandler>{
	public static Type<ShowPopupAddOrphanageEventHandler> TYPE = new Type<ShowPopupAddOrphanageEventHandler>();
	
	private final ClickPoint point;
	private OrphanageInfo Orphanage;
	
	public ShowPopupAddOrphanageEvent(ClickPoint location){
		this.point = location;
		this.Orphanage = null;
	};
	public ShowPopupAddOrphanageEvent(ClickPoint location, OrphanageInfo Orphanage){
		this.point = location;
		this.Orphanage = Orphanage;
	};
	
	public ClickPoint getClickPoint(){
		return point;
	}
	
	public OrphanageInfo getOrphanage(){
		return Orphanage;
	}
	@Override public Type<ShowPopupAddOrphanageEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(ShowPopupAddOrphanageEventHandler handler){
		handler.onShowPopupAddOrphanage(this);
	}
	
}
