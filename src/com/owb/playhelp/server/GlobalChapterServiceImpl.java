/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.server;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.owb.playhelp.server.domain.UserProfile;
import com.owb.playhelp.client.service.GlobalChapterService;
import com.owb.playhelp.shared.GlobalChapterInfo;
import com.owb.playhelp.shared.exceptions.NoUserException;

public class GlobalChapterServiceImpl extends RemoteServiceServlet implements GlobalChapterService {
	
	public final static String CHANNEL_ID = "channel_id";
	
	@Override
	public GlobalChapterInfo getCurrentGlobalChapterInfo(){
		return new GlobalChapterInfo();
	}
	
}

