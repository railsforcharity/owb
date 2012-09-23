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
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.Command;

import com.owb.playhelp.client.event.project.ProjectHomeEvent;
import com.owb.playhelp.client.event.project.ProjectListEvent;
import com.owb.playhelp.client.event.project.ProjectSearchEvent;
import com.owb.playhelp.client.event.project.ProjectAddEvent;
import com.owb.playhelp.client.helper.RPCCall;
import com.owb.playhelp.client.presenter.Presenter;
import com.owb.playhelp.client.service.LoginServiceAsync;
import com.owb.playhelp.shared.UserProfileInfo;

public class ProjectTabPresenter implements Presenter {
	public interface Display {
		Widget asWidget();
		MenuItem getHomeLink();
		MenuItem getListLink();
		MenuItem getSearchLink();
	}

	private final SimpleEventBus eventBus;
	private final Display display;

	private UserProfileInfo currentUser;

	public ProjectTabPresenter(UserProfileInfo currentUser,
			SimpleEventBus eventBus, Display display) {
		this.currentUser = currentUser;
		this.eventBus = eventBus;
		this.display = display;
	}

	public void bind() {
		  this.display.getHomeLink().setCommand(new Command(){
			  public void execute(){
				  eventBus.fireEvent(new ProjectHomeEvent());
			  }
		  });
		  this.display.getListLink().setCommand(new Command(){
			  public void execute(){
				  eventBus.fireEvent(new ProjectListEvent());
			  }
		  });
		  this.display.getSearchLink().setCommand(new Command(){
			  public void execute(){
				  eventBus.fireEvent(new ProjectSearchEvent());
			  }
		  });
	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		bind();
	}

}
