/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.service;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.owb.playhelp.shared.UserProfileInfo;

public interface LoginServiceAsync {
	void getCurrentUserInfo(AsyncCallback<UserProfileInfo> callback);
	void doSuperUser(AsyncCallback<Void> callback);
	void logout(AsyncCallback<Void> callback);
}
