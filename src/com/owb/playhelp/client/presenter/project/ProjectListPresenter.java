/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter.project;

import java.util.List;
import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.event.LoginEvent;
import com.owb.playhelp.client.event.LoginEventHandler;
import com.owb.playhelp.client.event.project.ProjectUpdateEvent;
import com.owb.playhelp.client.helper.RPCCall;
import com.owb.playhelp.client.presenter.Presenter;
import com.owb.playhelp.client.service.ContributionServiceAsync;
import com.owb.playhelp.client.service.LoginServiceAsync;
import com.owb.playhelp.client.service.project.ProjectServiceAsync;
import com.owb.playhelp.client.view.contribution.ContributionUserInfoView;
import com.owb.playhelp.shared.UserProfileInfo;
import com.owb.playhelp.shared.ContributionInfo;
import com.owb.playhelp.shared.project.ProjectInfo;
import com.owb.playhelp.client.presenter.contribution.ContributionUserInfoPresenter;

public class ProjectListPresenter implements Presenter {
	public interface Display {
		Widget asWidget();
		HasClickHandlers getList();
		void setData(List<HorizontalPanel> data);
	}

	private final SimpleEventBus eventBus;
	private final ProjectServiceAsync projectService;
	private ContributionServiceAsync contributionService;
	private final Display display;

	private UserProfileInfo currentUser;

	public ProjectListPresenter(UserProfileInfo currentUser, ProjectServiceAsync projectService,
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
	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		bind();
	}
	
	private void doRetrieve(){
	    new RPCCall<ArrayList<ContributionInfo>>() {
	      @Override
	      protected void callService(AsyncCallback<ArrayList<ContributionInfo>> cb) {
	        contributionService.getUserContribution(cb);
	      }

	      @Override
	      public void onSuccess(ArrayList<ContributionInfo> result) {
	    	  display.setData(toPanelList(result));
	      }

	      @Override
	      public void onFailure(Throwable caught) {
	        Window.alert("Error retrieving project...");
	      }
	    }.retry(3);
	}

	  private List<String> toStringList(ArrayList<ContributionInfo> contributionList) {
	     if (contributionList == null) return null;
		  
		  List<String> list = new ArrayList<String>();
	    for (ContributionInfo contrib : contributionList) {
	      list.add(contrib.getProject().getName());
	    }

	    return list;
	  }

	  private List<HorizontalPanel> toPanelList(ArrayList<ContributionInfo> contributionList) {
	    List<HorizontalPanel> list = new ArrayList<HorizontalPanel>();
	    ContributionUserInfoPresenter contributionInfoPresenter = null;
	    HorizontalPanel pContainer = null;
	    for (ContributionInfo p : contributionList) {
	    	contributionInfoPresenter = new ContributionUserInfoPresenter(p, currentUser, projectService, contributionService,
	    			eventBus, new ContributionUserInfoView());
	    	pContainer = new HorizontalPanel();
	    	list.add(pContainer);
	    	contributionInfoPresenter.go(pContainer);
	    }
	    return list;
	  }

}
