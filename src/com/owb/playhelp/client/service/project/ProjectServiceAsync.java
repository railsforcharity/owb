/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.service.project;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.owb.playhelp.shared.exceptions.NoUserException;
import com.owb.playhelp.shared.project.ProjectInfo;
import com.owb.playhelp.shared.project.ProjectItemInfo;

public interface ProjectServiceAsync {
	void getUserProjectList(AsyncCallback<ArrayList<ProjectItemInfo>> callback);
	void updateProject(ProjectInfo project, AsyncCallback<ProjectInfo> callback);	
	void getProjectList(AsyncCallback<ArrayList<ProjectInfo>> callback);
	void getProject(String id, AsyncCallback<ProjectInfo> callback);
	void deleteProject(String id, AsyncCallback<String> callback) throws NoUserException;
}
