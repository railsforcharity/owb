/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

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

public class SelectionMenuPresenter implements Presenter {
	public interface Display {
		Widget asWidget();
		public HasClickHandlers getHomeLink();
		public HasClickHandlers getFriendLink();
		public HasClickHandlers getProjectLink();
		public HasClickHandlers getChapterLink();
		public HasClickHandlers getNgoLink();
		public HasClickHandlers getOrphanageLink();
		public HasClickHandlers getMoreLink();
	}

	private final SimpleEventBus eventBus;
	private final Display display;

	private UserProfileInfo currentUser;

	public SelectionMenuPresenter(UserProfileInfo currentUser,SimpleEventBus eventBus, Display display) {
		this.currentUser = currentUser;
		this.eventBus = eventBus;
		this.display = display;
	}

	public void bind() {
		  this.display.getHomeLink().addClickHandler(new ClickHandler(){
			  public void onClick(ClickEvent event){
				  eventBus.fireEvent(new MainHomeEvent());
			  }
		  });
		  this.display.getFriendLink().addClickHandler(new ClickHandler(){
			  public void onClick(ClickEvent event){
				  eventBus.fireEvent(new FriendHomeEvent());
			  }
		  });
		  this.display.getProjectLink().addClickHandler(new ClickHandler(){
			  public void onClick(ClickEvent event){
				  eventBus.fireEvent(new ProjectMainEvent());
			  }
		  });
		  this.display.getChapterLink().addClickHandler(new ClickHandler(){
			  public void onClick(ClickEvent event){
				  eventBus.fireEvent(new ChapterHomeEvent());
			  }
		  });
		  this.display.getNgoLink().addClickHandler(new ClickHandler(){
			  public void onClick(ClickEvent event){
				  eventBus.fireEvent(new NgoHomeEvent());
			  }
		  });
		  this.display.getOrphanageLink().addClickHandler(new ClickHandler(){
			  public void onClick(ClickEvent event){
				  eventBus.fireEvent(new OrphanageHomeEvent());
			  }
		  });
		  this.display.getMoreLink().addClickHandler(new ClickHandler(){
			  public void onClick(ClickEvent event){
				  eventBus.fireEvent(new MoreMenuPopUpEvent());
			  }
		  });
	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		bind();
	}

}
