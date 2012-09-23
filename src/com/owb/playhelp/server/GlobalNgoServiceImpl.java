/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.server;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.owb.playhelp.server.domain.UserProfile;
import com.owb.playhelp.client.service.GlobalNgoService;
import com.owb.playhelp.shared.GlobalNgoInfo;
import com.owb.playhelp.shared.exceptions.NoUserException;

public class GlobalNgoServiceImpl extends RemoteServiceServlet implements GlobalNgoService {
	
	public final static String CHANNEL_ID = "channel_id";
	
	@Override
	public GlobalNgoInfo getCurrentGlobalNgoInfo(){
		return new GlobalNgoInfo();
	}
	
}

