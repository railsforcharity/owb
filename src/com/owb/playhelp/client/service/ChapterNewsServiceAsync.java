/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.service;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.owb.playhelp.shared.ChapterNewsInfo;

public interface ChapterNewsServiceAsync {
	void getCurrentChapterNewsInfo(AsyncCallback<ChapterNewsInfo> callback);
}

