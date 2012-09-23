/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface LoginEventHandler extends EventHandler {
	void onLogin(LoginEvent event);
}
