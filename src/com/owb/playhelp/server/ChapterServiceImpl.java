/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.server;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.owb.playhelp.server.domain.UserProfile;
import com.owb.playhelp.client.service.ChapterService;
import com.owb.playhelp.shared.ChapterInfo;

public class ChapterServiceImpl extends RemoteServiceServlet implements ChapterService {
	
	public final static String CHANNEL_ID = "channel_id";
	
	@Override
	public ChapterInfo getCurrentChapterInfo(){
		return new ChapterInfo();
	}
	
}

