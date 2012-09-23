/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.ngo;

import com.google.gwt.event.shared.GwtEvent;
import com.owb.playhelp.shared.ngo.NgoInfo;

public class JoinNgoEvent extends GwtEvent<JoinNgoEventHandler>{
	public static Type<JoinNgoEventHandler> TYPE = new Type<JoinNgoEventHandler>();

	private final NgoInfo ngo;
	
	public JoinNgoEvent(NgoInfo ngo){this.ngo = ngo;};

	public NgoInfo getNgo(){
		return ngo;
	}
	
	@Override public Type<JoinNgoEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(JoinNgoEventHandler handler){
		handler.onJoinNgo(this);
	}
	
}

