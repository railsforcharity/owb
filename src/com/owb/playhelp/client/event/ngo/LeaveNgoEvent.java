/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.ngo;

import com.google.gwt.event.shared.GwtEvent;
import com.owb.playhelp.shared.ngo.NgoInfo;

public class LeaveNgoEvent extends GwtEvent<LeaveNgoEventHandler>{
	public static Type<LeaveNgoEventHandler> TYPE = new Type<LeaveNgoEventHandler>();

	private final NgoInfo ngo;
	
	public LeaveNgoEvent(NgoInfo ngo){this.ngo = ngo;};

	public NgoInfo getNgo(){
		return ngo;
	}
	
	@Override public Type<LeaveNgoEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(LeaveNgoEventHandler handler){
		handler.onLeaveNgo(this);
	}
	
}

