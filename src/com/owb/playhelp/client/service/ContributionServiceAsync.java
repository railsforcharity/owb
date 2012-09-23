/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.service;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.owb.playhelp.shared.UserProfileInfo;
import com.owb.playhelp.shared.ContributionInfo;

public interface ContributionServiceAsync {
	void addContribution(ContributionInfo contributionInfo, AsyncCallback<ContributionInfo> callback);
	void getUserContribution(AsyncCallback<ArrayList<ContributionInfo>> callback);
}