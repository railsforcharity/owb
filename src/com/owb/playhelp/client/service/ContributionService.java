/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.service;

import java.util.ArrayList;

import javax.jdo.JDOCanRetryException;
import javax.jdo.PersistenceManager;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.owb.playhelp.server.LoginHelper;
import com.owb.playhelp.server.PMFactory;
import com.owb.playhelp.shared.UserProfileInfo;
import com.owb.playhelp.server.domain.project.Project;
import com.owb.playhelp.server.domain.project.ProjectItem;
import com.owb.playhelp.server.domain.UserProfile;
import com.owb.playhelp.shared.ContributionInfo;

@RemoteServiceRelativePath("contributionService")
public interface ContributionService extends RemoteService {
			
	ContributionInfo addContribution(ContributionInfo contributionInfo);
	public ArrayList<ContributionInfo> getUserContribution();
}






