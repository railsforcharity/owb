/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.owb.playhelp.shared.GlobalChapterNgoNewsInfo;

@RemoteServiceRelativePath("GlobalChapterNgoNewsService")
public interface GlobalChapterNgoNewsService extends RemoteService {
	GlobalChapterNgoNewsInfo getCurrentGlobalChapterNgoNewsInfo();	
}


