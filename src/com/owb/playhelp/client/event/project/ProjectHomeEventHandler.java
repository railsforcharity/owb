/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event.project;

import com.google.gwt.event.shared.EventHandler;

public interface ProjectHomeEventHandler extends EventHandler {
	void onProjectHomeRequest(ProjectHomeEvent event);
}
