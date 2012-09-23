/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.server;

import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.owb.playhelp.server.domain.UserProfile;
import com.owb.playhelp.client.service.ChapterNewsService;
import com.owb.playhelp.shared.ChapterNewsInfo;
import com.owb.playhelp.shared.exceptions.NoUserException;

public class ChapterNewsServiceImpl extends RemoteServiceServlet implements ChapterNewsService {
	
	public final static String CHANNEL_ID = "channel_id";
	
	@Override
	public ChapterNewsInfo getCurrentChapterNewsInfo(){
		return new ChapterNewsInfo();
	}
	
}

