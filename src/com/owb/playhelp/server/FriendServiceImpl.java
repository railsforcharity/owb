/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.server;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.owb.playhelp.server.domain.UserProfile;
import com.owb.playhelp.client.service.FriendService;
import com.owb.playhelp.shared.FriendInfo;
import com.owb.playhelp.shared.exceptions.NoUserException;

public class FriendServiceImpl extends RemoteServiceServlet implements FriendService {
	
	public final static String CHANNEL_ID = "channel_id";
	
	@Override
	public FriendInfo getCurrentFriendInfo(){
		return new FriendInfo();
	}
	
}

