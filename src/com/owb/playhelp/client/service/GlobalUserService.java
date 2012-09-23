/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.owb.playhelp.shared.GlobalUserInfo;

@RemoteServiceRelativePath("GlobalUserService")
public interface GlobalUserService extends RemoteService {
	GlobalUserInfo getCurrentGlobalUserInfo();	
}


