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

import com.owb.playhelp.client.helper.RPCCall;
import com.owb.playhelp.client.service.LoginServiceAsync;
import com.owb.playhelp.shared.UserProfileInfo;

public class UserKarmaPresenter implements Presenter {
	public interface Display {
		Widget asWidget();
		//HasText getUserKarmaLabel();
	}

	private final SimpleEventBus eventBus;
	private final Display display;

	private UserProfileInfo currentUser;

	public UserKarmaPresenter(UserProfileInfo currentUser,
			SimpleEventBus eventBus, Display display) {
		this.currentUser = currentUser;
		this.eventBus = eventBus;
		this.display = display;
	}

	public void bind() {
		// initialize the fields with the 
		createUI();
		// listen to project or other things change that may affect the karma
	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		bind();
	}
	
	private void createUI(){
		/*
		if (currentUser != null){
			// read karma from user
			UserKarmaPresenter.this.display.getUserKarmaLabel().setText("karma user");
		} else {
			UserKarmaPresenter.this.display.getUserKarmaLabel().setText("no user");			
		}*/
	}

}
