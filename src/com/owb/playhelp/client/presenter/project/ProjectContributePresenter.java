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
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.helper.RPCCall;
import com.owb.playhelp.client.service.LoginServiceAsync;
import com.owb.playhelp.client.service.ContributionServiceAsync;
import com.owb.playhelp.shared.UserProfileInfo;
import com.owb.playhelp.shared.ContributionInfo;
import com.owb.playhelp.shared.project.ProjectInfo;
import com.owb.playhelp.client.presenter.Presenter;
import com.owb.playhelp.client.event.project.ProjectContributeEvent;
import com.owb.playhelp.client.event.project.ProjectContributeCancelledEvent;

public class ProjectContributePresenter implements Presenter {
	public interface Display {
		Widget asWidget();
		HasClickHandlers getGoLink();
		HasClickHandlers getCancelLink();
		HasValue<String> getContribField();
		void hide();
	}

	private final SimpleEventBus eventBus;
	private final Display display;
	private final ContributionServiceAsync contributeService;
	
	private UserProfileInfo currentUser;
	private ProjectInfo currentProject;
	private ContributionInfo currentContribution;

	public ProjectContributePresenter(UserProfileInfo currentUser, ProjectInfo currentProject,
			ContributionServiceAsync contributeService, SimpleEventBus eventBus, Display display) {
		this.currentUser = currentUser;
		this.currentProject = currentProject;
		this.contributeService = contributeService;
		this.eventBus = eventBus;
		this.display = display;
		bind();
	}

	public void bind() {
	    this.display.getGoLink().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	          display.hide();
	          double contribute = Double.valueOf(ProjectContributePresenter.this.display.getContribField().getValue());
	          currentContribution = new ContributionInfo(currentUser,currentProject,contribute);
	          doContribution();	
	        }
	      });
	    this.display.getCancelLink().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	          display.hide();
	          //eventBus.fireEvent(new ProjectContributeCancelledEvent());
	        }
	      });
	}
	
	private void doContribution(){
		new RPCCall<ContributionInfo>(){
			@Override
			protected void callService(AsyncCallback<ContributionInfo> cb){
				contributeService.addContribution(currentContribution, cb);
			}
			
			public void onSuccess(ContributionInfo contribution){
				eventBus.fireEvent(new ProjectContributeEvent(contribution));
			}
			
			@Override
			public void onFailure(Throwable caught){
				if (caught instanceof com.owb.playhelp.shared.exceptions.NoUserException) {
			          Window.alert("You need to login first");
			        } else {
			          Window.alert("An error occurred: " + caught.toString());
			        }
			}
		}.retry(3);
	}
	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		bind();
	}

}
