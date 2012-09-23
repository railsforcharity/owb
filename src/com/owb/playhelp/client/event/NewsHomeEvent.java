/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class NewsHomeEvent extends GwtEvent<NewsHomeEventHandler>{
	public static Type<NewsHomeEventHandler> TYPE = new Type<NewsHomeEventHandler>();
	public NewsHomeEvent(){};
	@Override public Type<NewsHomeEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(NewsHomeEventHandler handler){
		handler.onNewsHome(this);
	}
	
}
