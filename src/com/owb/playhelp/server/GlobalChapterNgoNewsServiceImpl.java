/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.server;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.owb.playhelp.server.domain.UserProfile;
import com.owb.playhelp.client.service.GlobalChapterNgoNewsService;
import com.owb.playhelp.shared.GlobalChapterNgoNewsInfo;
import com.owb.playhelp.shared.exceptions.NoUserException;

public class GlobalChapterNgoNewsServiceImpl extends RemoteServiceServlet implements GlobalChapterNgoNewsService {
	
	public final static String CHANNEL_ID = "channel_id";
	
	@Override
	public GlobalChapterNgoNewsInfo getCurrentGlobalChapterNgoNewsInfo(){
		return new GlobalChapterNgoNewsInfo();
	}
	
}

