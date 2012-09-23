/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.server;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.owb.playhelp.server.domain.UserProfile;
import com.owb.playhelp.client.service.GlobalProjectNewsService;
import com.owb.playhelp.shared.GlobalProjectNewsInfo;
import com.owb.playhelp.shared.exceptions.NoUserException;

public class GlobalProjectNewsServiceImpl extends RemoteServiceServlet implements GlobalProjectNewsService {
	
	public final static String CHANNEL_ID = "channel_id";
	
	@Override
	public GlobalProjectNewsInfo getCurrentGlobalProjectNewsInfo(){
		return new GlobalProjectNewsInfo();
	}
	
}

