/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.ngo;

import com.google.gwt.event.shared.GwtEvent;
import com.owb.playhelp.shared.ngo.NgoInfo;

public class ReportAbuseNgoEvent extends GwtEvent<ReportAbuseNgoEventHandler>{
	public static Type<ReportAbuseNgoEventHandler> TYPE = new Type<ReportAbuseNgoEventHandler>();

	private final NgoInfo ngo;
	
	public ReportAbuseNgoEvent(NgoInfo ngo){
		this.ngo = ngo;
	};

	public NgoInfo getNgo(){
		return ngo;
	}
	@Override public Type<ReportAbuseNgoEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(ReportAbuseNgoEventHandler handler){
		handler.onReportAbuseNgo(this);
	}
	
}

