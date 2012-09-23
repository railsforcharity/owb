/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.ngo;

import com.google.gwt.event.shared.EventHandler;
import com.owb.playhelp.shared.ngo.NgoInfo;

public interface JoinNgoEventHandler extends EventHandler {
	void onJoinNgo(JoinNgoEvent event);
	
}
