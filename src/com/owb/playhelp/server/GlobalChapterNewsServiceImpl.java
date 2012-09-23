/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.server;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.owb.playhelp.server.domain.UserProfile;
import com.owb.playhelp.client.service.GlobalChapterNewsService;
import com.owb.playhelp.shared.GlobalChapterNewsInfo;
import com.owb.playhelp.shared.exceptions.NoUserException;

public class GlobalChapterNewsServiceImpl extends RemoteServiceServlet implements GlobalChapterNewsService {
	
	public final static String CHANNEL_ID = "channel_id";
	
	@Override
	public GlobalChapterNewsInfo getCurrentGlobalChapterNewsInfo(){
		return new GlobalChapterNewsInfo();
	}
	
}

