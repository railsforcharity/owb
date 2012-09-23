/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.service.orphanage;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.owb.playhelp.shared.exceptions.NoUserException;
import com.owb.playhelp.shared.orphanage.OrphanageInfo;

public interface OrphanageServiceAsync {
	void updateOrphanage(OrphanageInfo Orphanage, AsyncCallback<OrphanageInfo> callback);	
	void getOrphanageList(AsyncCallback<ArrayList<OrphanageInfo>> callback);
	void removeOrphanage(OrphanageInfo Orphanage, AsyncCallback<Void> callback);	
	/*
	void getUserOrphanageList(AsyncCallback<ArrayList<OrphanageItemInfo>> callback);
	void getOrphanage(String id, AsyncCallback<OrphanageInfo> callback);
	void deleteOrphanage(String id, AsyncCallback<String> callback) throws NoUserException;
	*/
}
