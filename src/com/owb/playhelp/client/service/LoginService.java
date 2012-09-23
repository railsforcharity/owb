/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.owb.playhelp.shared.UserProfileInfo;
import com.owb.playhelp.shared.exceptions.NotLoggedInException;

@RemoteServiceRelativePath("loginService")
public interface LoginService extends RemoteService {
	UserProfileInfo getCurrentUserInfo();	
	void doSuperUser();	
	void logout() throws NotLoggedInException;
}
