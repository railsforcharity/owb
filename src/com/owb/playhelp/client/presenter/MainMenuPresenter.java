/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.Command;

import com.owb.playhelp.client.event.MainHomeEvent;
import com.owb.playhelp.client.event.FriendHomeEvent;
import com.owb.playhelp.client.event.ChapterHomeEvent;
import com.owb.playhelp.client.event.NgoHomeEvent;
import com.owb.playhelp.client.event.OrphanageHomeEvent;
import com.owb.playhelp.client.event.MoreMenuPopUpEvent;
import com.owb.playhelp.client.event.project.ProjectHomeEvent;
import com.owb.playhelp.client.event.project.ProjectMainEvent;
import com.owb.playhelp.client.helper.RPCCall;
import com.owb.playhelp.client.service.LoginServiceAsync;
import com.owb.playhelp.shared.UserProfileInfo;

public class MainMenuPresenter implements Presenter {
	public interface Display {
		Widget asWidget();
		public MenuItem getHomeLink();
		public MenuItem getFriendLink();
		public MenuItem getProjectLink();
		public MenuItem getChapterLink();
		public MenuItem getNgoLink();
		public MenuItem getOrphanageLink();
		public MenuItem getMoreLink();
		public MenuBar getMainMenu();
	}

	private final SimpleEventBus eventBus;
	private final Display display;

	private UserProfileInfo currentUser;

	public MainMenuPresenter(UserProfileInfo currentUser,SimpleEventBus eventBus, Display display) {
		this.currentUser = currentUser;
		this.eventBus = eventBus;
		this.display = display;
	}

	public void bind() {
		this.display.getHomeLink().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new MainHomeEvent());
			}
		});
		this.display.getFriendLink().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new FriendHomeEvent());
			}
		});
		this.display.getProjectLink().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ProjectMainEvent());
			}
		});
		this.display.getChapterLink().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ChapterHomeEvent());
			}
		});
		this.display.getNgoLink().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new NgoHomeEvent());
			}
		});
		this.display.getOrphanageLink().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new OrphanageHomeEvent());
			}
		});
		this.display.getMoreLink().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new MoreMenuPopUpEvent());
			}
		});
		
		//this.display.getMainMenu().addHandler(new ClickHandler(){
		//	  public void onClick(ClickEvent event){
		//		  Window.alert("Yep"+display.getMainMenu());
		//	  }
		// }, ClickEvent.getType());
	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		bind();
	}

}
