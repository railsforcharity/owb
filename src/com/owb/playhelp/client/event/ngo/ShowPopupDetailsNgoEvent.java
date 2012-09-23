/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.ngo;

import com.google.gwt.event.shared.GwtEvent;
import com.owb.playhelp.client.helper.ClickPoint;
import com.owb.playhelp.shared.ngo.NgoInfo;

public class ShowPopupDetailsNgoEvent extends GwtEvent<ShowPopupDetailsNgoEventHandler>{
	public static Type<ShowPopupDetailsNgoEventHandler> TYPE = new Type<ShowPopupDetailsNgoEventHandler>();
	
	private final ClickPoint point;
	private NgoInfo ngo;
	
	public ShowPopupDetailsNgoEvent(ClickPoint location){
		this.point = location;
		this.ngo = null;
	};
	public ShowPopupDetailsNgoEvent(ClickPoint location, NgoInfo ngo){
		this.point = location;
		this.ngo = ngo;
	};
	
	public ClickPoint getClickPoint(){
		return point;
	}
	
	public NgoInfo getNgo(){
		return ngo;
	}
	@Override public Type<ShowPopupDetailsNgoEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(ShowPopupDetailsNgoEventHandler handler){
		handler.onShowPopupDetailsNgo(this);
	}
	
}
