/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.server;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.owb.playhelp.server.domain.UserProfile;
import com.owb.playhelp.client.service.GlobalOrphanageService;
import com.owb.playhelp.shared.GlobalOrphanageInfo;
import com.owb.playhelp.shared.exceptions.NoUserException;

public class GlobalOrphanageServiceImpl extends RemoteServiceServlet implements GlobalOrphanageService {
	
	public final static String CHANNEL_ID = "channel_id";
	
	@Override
	public GlobalOrphanageInfo getCurrentGlobalOrphanageInfo(){
		return new GlobalOrphanageInfo();
	}
	
}

