/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.server;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.owb.playhelp.server.LoginHelper;
import com.owb.playhelp.server.domain.UserProfile;
import com.owb.playhelp.client.service.LoginService;
import com.owb.playhelp.shared.UserProfileInfo;
import com.owb.playhelp.shared.exceptions.NotLoggedInException;

public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {

	public final static String CHANNEL_ID = "channel_id";
	  
	@Override
	public UserProfileInfo getCurrentUserInfo(){
		HttpSession session = getThreadLocalRequest().getSession();
		
		UserProfile user = LoginHelper.getLoggedUser(session, null);
		
		if (user == null) return null;
		
		return UserProfile.toInfo(user);
	}
	
	@Override
	public void logout() throws NotLoggedInException{
		getThreadLocalRequest().getSession().invalidate();
	    throw new NotLoggedInException("Logged out");
	}
	
	@Override
	public void doSuperUser(){
		UserProfile.createSuperUser();
	}
	
}
