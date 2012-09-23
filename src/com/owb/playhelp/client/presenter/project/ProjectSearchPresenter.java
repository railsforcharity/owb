/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter.project;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.event.LoginEvent;
import com.owb.playhelp.client.event.LoginEventHandler;
import com.owb.playhelp.client.event.project.ProjectAddEvent;
import com.owb.playhelp.client.helper.RPCCall;
import com.owb.playhelp.client.presenter.Presenter;
import com.owb.playhelp.client.presenter.project.ProjectInfoPresenter;
import com.owb.playhelp.client.view.project.ProjectInfoView;
import com.owb.playhelp.client.service.ContributionServiceAsync;
import com.owb.playhelp.client.service.LoginServiceAsync;
import com.owb.playhelp.client.service.project.ProjectServiceAsync;
import com.owb.playhelp.shared.UserProfileInfo;
import com.owb.playhelp.shared.project.ProjectInfo;

public class ProjectSearchPresenter implements Presenter {
	public interface Display {
		Widget asWidget();
		void setData(List<HorizontalPanel> data);
		HasClickHandlers getList();
		HasClickHandlers getAddProjectButton();
	}

	private final SimpleEventBus eventBus;
	private final ProjectServiceAsync projectService;
	private ContributionServiceAsync contributionService;
	private final Display display;

	private UserProfileInfo currentUser;

	public ProjectSearchPresenter(UserProfileInfo currentUser, ProjectServiceAsync projectService,
			ContributionServiceAsync contributionService, SimpleEventBus eventBus, Display display) {
		this.currentUser = currentUser;
		this.projectService = projectService;
		this.contributionService = contributionService;
		this.eventBus = eventBus;
		this.display = display;
	}

	public void bind() {
		doRetrieve();  
		eventBus.addHandler(LoginEvent.TYPE, new LoginEventHandler(){
			  @Override public void onLogin(LoginEvent event){
				  currentUser = event.getUser();
				  doRetrieve();
			  }
		  });
		this.display.getAddProjectButton().addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				eventBus.fireEvent(new ProjectAddEvent());
			}
		});
	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		bind();
	}
	
	private void doRetrieve(){
	    new RPCCall<ArrayList<ProjectInfo>>() {
	      @Override
	      protected void callService(AsyncCallback<ArrayList<ProjectInfo>> cb) {
	        projectService.getProjectList(cb);
	      }

	      @Override
	      public void onSuccess(ArrayList<ProjectInfo> result) {
	    	  display.setData(toPanelList(result));
	      }

	      @Override
	      public void onFailure(Throwable caught) {
	        Window.alert("Error retrieving project...");
	      }
	    }.retry(3);
	}

	  private List<HorizontalPanel> toPanelList(ArrayList<ProjectInfo> projectList) {
	    List<HorizontalPanel> list = new ArrayList<HorizontalPanel>();
	    ProjectInfoPresenter projectInfoPresenter = null;
	    HorizontalPanel pContainer = null;
	    for (ProjectInfo p : projectList) {
	    	projectInfoPresenter = new ProjectInfoPresenter(p, currentUser, projectService, contributionService,
	    			eventBus, new ProjectInfoView());
	    	pContainer = new HorizontalPanel();
	    	list.add(pContainer);
	    	projectInfoPresenter.go(pContainer);
	    }
	    return list;
	  }


}
