/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.server;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.owb.playhelp.server.domain.UserProfile;
import com.owb.playhelp.client.service.FriendNewsService;
import com.owb.playhelp.shared.FriendNewsInfo;
import com.owb.playhelp.shared.exceptions.NoUserException;

public class FriendNewsServiceImpl extends RemoteServiceServlet implements FriendNewsService {
	
	public final static String CHANNEL_ID = "channel_id";
	
	@Override
	public FriendNewsInfo getCurrentFriendNewsInfo(){
		return new FriendNewsInfo();
	}
	
}

