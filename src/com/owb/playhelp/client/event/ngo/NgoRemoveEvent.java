/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.ngo;

import com.google.gwt.event.shared.GwtEvent;
import com.owb.playhelp.shared.ngo.NgoInfo;

public class NgoRemoveEvent extends GwtEvent<NgoRemoveEventHandler>{
	public static Type<NgoRemoveEventHandler> TYPE = new Type<NgoRemoveEventHandler>();
	
	private final NgoInfo ngo;
	
	public NgoRemoveEvent(NgoInfo ngo){
		this.ngo = ngo;
	};
	
	public NgoInfo getNgo(){
		return ngo;
	}
	
	@Override public Type<NgoRemoveEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(NgoRemoveEventHandler handler){
		handler.onNgoRemove(this);
	}
	
}
