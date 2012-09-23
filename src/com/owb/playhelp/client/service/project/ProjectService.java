/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.service.project;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.owb.playhelp.shared.exceptions.NoUserException;
import com.owb.playhelp.shared.project.ProjectInfo;
import com.owb.playhelp.shared.project.ProjectItemInfo;

@RemoteServiceRelativePath("projectService")
public interface ProjectService extends RemoteService {
	ProjectInfo updateProject(ProjectInfo project);	
	ArrayList<ProjectItemInfo> getUserProjectList();
	ArrayList<ProjectInfo> getProjectList();
	  ProjectInfo getProject(String id);
	  String deleteProject(String id) throws NoUserException;
}


