/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.service.ngo;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.owb.playhelp.shared.exceptions.NoUserException;
import com.owb.playhelp.shared.ngo.NgoInfo;
import com.owb.playhelp.shared.ngo.NgoItemInfo;

public interface NgoServiceAsync {
	void requestMemberNgo(NgoInfo ngoInfo, AsyncCallback<NgoInfo> callback);
	void updateNgo(NgoInfo ngo, AsyncCallback<NgoInfo> callback);	
	void confirmNgo(NgoInfo ngoInfo, AsyncCallback<NgoInfo> callback);
	void reportAbuseNgo(NgoInfo ngoInfo, String report, AsyncCallback<NgoInfo> callback);
	void getNgoList(AsyncCallback<ArrayList<NgoInfo>> callback);
	void removeNgo(NgoInfo ngo, AsyncCallback<Void> callback);	
	/*
	void getUserNgoList(AsyncCallback<ArrayList<NgoItemInfo>> callback);
	void getNgo(String id, AsyncCallback<NgoInfo> callback);
	void deleteNgo(String id, AsyncCallback<String> callback) throws NoUserException;
	*/
}
