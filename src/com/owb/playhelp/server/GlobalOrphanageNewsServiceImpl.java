/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.server;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.owb.playhelp.server.domain.UserProfile;
import com.owb.playhelp.client.service.GlobalOrphanageNewsService;
import com.owb.playhelp.shared.GlobalOrphanageNewsInfo;
import com.owb.playhelp.shared.exceptions.NoUserException;

public class GlobalOrphanageNewsServiceImpl extends RemoteServiceServlet implements GlobalOrphanageNewsService {
	
	public final static String CHANNEL_ID = "channel_id";
	
	@Override
	public GlobalOrphanageNewsInfo getCurrentGlobalOrphanageNewsInfo(){
		return new GlobalOrphanageNewsInfo();
	}
	
}

