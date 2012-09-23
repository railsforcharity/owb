/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.ngo;

import com.google.gwt.event.shared.GwtEvent;
import com.owb.playhelp.client.helper.ClickPoint;
import com.owb.playhelp.shared.ngo.NgoInfo;

public class ShowPopupReportAbuseNgoEvent extends GwtEvent<ShowPopupReportAbuseNgoEventHandler>{
	public static Type<ShowPopupReportAbuseNgoEventHandler> TYPE = new Type<ShowPopupReportAbuseNgoEventHandler>();

	private final ClickPoint point;
	private NgoInfo ngo;
	
	public ShowPopupReportAbuseNgoEvent(ClickPoint location){
		this.point = location;
		this.ngo = null;
	};
	public ShowPopupReportAbuseNgoEvent(ClickPoint location, NgoInfo ngo){
		this.point = location;
		this.ngo = ngo;
	};

	public ClickPoint getClickPoint(){
		return point;
	}
	
	public NgoInfo getNgo(){
		return ngo;
	}
	@Override public Type<ShowPopupReportAbuseNgoEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(ShowPopupReportAbuseNgoEventHandler handler){
		handler.onShowPopupReportAbuseNgo(this);
	}
	
}

