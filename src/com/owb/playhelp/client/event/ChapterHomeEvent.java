/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event;


import com.google.gwt.event.shared.GwtEvent;

public class ChapterHomeEvent extends GwtEvent<ChapterHomeEventHandler>{
	public static Type<ChapterHomeEventHandler> TYPE = new Type<ChapterHomeEventHandler>();
	public ChapterHomeEvent(){};
	@Override public Type<ChapterHomeEventHandler> getAssociatedType(){
		return TYPE;
	}
	@Override protected void dispatch(ChapterHomeEventHandler handler){
		handler.onChapterHomeRequest(this);
	}
	
}

