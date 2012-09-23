/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.owb.playhelp.shared.GlobalOrphanageInfo;

@RemoteServiceRelativePath("GlobalOrphanageService")
public interface GlobalOrphanageService extends RemoteService {
	GlobalOrphanageInfo getCurrentGlobalOrphanageInfo();	
}


