/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.server;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.owb.playhelp.server.domain.UserProfile;
import com.owb.playhelp.client.service.GlobalUserService;
import com.owb.playhelp.shared.GlobalUserInfo;
import com.owb.playhelp.shared.exceptions.NoUserException;

public class GlobalUserServiceImpl extends RemoteServiceServlet implements GlobalUserService {
	
	public final static String CHANNEL_ID = "channel_id";
	
	@Override
	public GlobalUserInfo getCurrentGlobalUserInfo(){
		return new GlobalUserInfo();
	}
	
}

