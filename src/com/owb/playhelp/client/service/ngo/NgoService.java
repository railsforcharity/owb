/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.service.ngo;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.owb.playhelp.shared.exceptions.NoUserException;
import com.owb.playhelp.shared.ngo.NgoInfo;
import com.owb.playhelp.shared.ngo.NgoItemInfo;

@RemoteServiceRelativePath("ngoService")
public interface NgoService extends RemoteService {
	NgoInfo requestMemberNgo(NgoInfo ngoInfo);
	NgoInfo updateNgo(NgoInfo ngo);	
	NgoInfo confirmNgo(NgoInfo ngoInfo);
	NgoInfo reportAbuseNgo(NgoInfo ngoInfo, String report);
	ArrayList<NgoInfo> getNgoList();
	void removeNgo(NgoInfo ngoInfo);
	/*
	ArrayList<NgoItemInfo> getUserNgoList();
	NgoInfo getNgo(String id);
	String deleteNgo(String id) throws NoUserException;
	*/
}


