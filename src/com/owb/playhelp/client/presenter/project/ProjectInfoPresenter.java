/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter.project;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.Owb;
import com.owb.playhelp.client.event.LoginEvent;
import com.owb.playhelp.client.event.LoginEventHandler;
import com.owb.playhelp.client.event.project.ProjectHomeEvent;
import com.owb.playhelp.client.event.project.ProjectContributeEvent;
import com.owb.playhelp.client.helper.RPCCall;
import com.owb.playhelp.client.view.project.ProjectContributeView;
import com.owb.playhelp.client.view.project.ProjectContributeNotLoggedView;
import com.owb.playhelp.client.service.LoginServiceAsync;
import com.owb.playhelp.client.service.project.ProjectServiceAsync;
import com.owb.playhelp.client.service.ContributionServiceAsync;
import com.owb.playhelp.client.presenter.Presenter;
import com.owb.playhelp.client.presenter.project.ProjectContributePresenter;
import com.owb.playhelp.client.presenter.project.ProjectContributeNotLoggedPresenter;
import com.owb.playhelp.shared.UserProfileInfo;
import com.owb.playhelp.shared.project.ProjectInfo;

public class ProjectInfoPresenter implements Presenter {
	public interface Display {
		Widget asWidget();
		HorizontalPanel getContainer();
		public Label getNameLabel();
		public Label getDescriptionLabel();
		public Anchor getContributeLink();
	}

	private final SimpleEventBus eventBus;
	private final ProjectServiceAsync projectService;
	private final ContributionServiceAsync contributionService;
	private final Display display;

	private UserProfileInfo currentUser;
	private ProjectInfo currentProject;

	public ProjectInfoPresenter(ProjectInfo currentProject, UserProfileInfo currentUser, 
			ProjectServiceAsync projectService, ContributionServiceAsync contributionService,
			SimpleEventBus eventBus, Display display) {
		this.currentProject = currentProject;
		this.currentUser = currentUser;
		this.projectService = projectService;
		this.contributionService = contributionService;
		this.eventBus = eventBus;
		this.display = display;
	}

	public void bind() {
		eventBus.addHandler(LoginEvent.TYPE, new LoginEventHandler(){
			  @Override public void onLogin(LoginEvent event){
				  currentUser = event.getUser();
				  //updateInfo();
			  }
		  });
		  this.display.getContributeLink().addClickHandler(new ClickHandler(){
			  public void onClick(ClickEvent event){
				  if (currentUser != null){
					  ProjectContributePresenter projectContributePresenter = new ProjectContributePresenter(currentUser, currentProject, contributionService,  eventBus, new ProjectContributeView());
					  //projectContributePresenter.go();  
				  } else {
					  ProjectContributeNotLoggedPresenter projectContributeNotLoggedPresenter = new ProjectContributeNotLoggedPresenter(eventBus, new ProjectContributeNotLoggedView());
					  //projectContributePresenter.go();
				  }
			  }
		  });
	}
	
	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		
		// Fill the information of the project
		ProjectInfoPresenter.this.display.getNameLabel().setText(currentProject.getName());
		ProjectInfoPresenter.this.display.getDescriptionLabel().setText(currentProject.getDescription());
		
		bind();
	}

}
