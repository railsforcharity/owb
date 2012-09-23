/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.server;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.owb.playhelp.server.domain.UserProfile;
import com.owb.playhelp.client.service.GlobalProjectService;
import com.owb.playhelp.shared.GlobalProjectInfo;
import com.owb.playhelp.shared.exceptions.NoUserException;

public class GlobalProjectServiceImpl extends RemoteServiceServlet implements GlobalProjectService {
	
	public final static String CHANNEL_ID = "channel_id";
	
	@Override
	public GlobalProjectInfo getCurrentGlobalProjectInfo(){
		return new GlobalProjectInfo();
	}
	
}

