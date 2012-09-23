/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter.project;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.helper.RPCCall;
import com.owb.playhelp.client.service.LoginServiceAsync;
import com.owb.playhelp.shared.ResourceInfo;
import com.owb.playhelp.shared.UserProfileInfo;
import com.owb.playhelp.client.presenter.Presenter;
import com.owb.playhelp.client.presenter.project.ProjectNeedMoneyPresenter;
import com.owb.playhelp.client.presenter.project.ProjectNeedVolunteeringPresenter;
import com.owb.playhelp.client.event.project.ProjectNeedAddEvent;
import com.owb.playhelp.client.view.project.ProjectNeedMoneyView;
import com.owb.playhelp.client.view.project.ProjectNeedVolunteeringView;


public class ProjectNeedInputPresenter implements Presenter {
	public interface Display {
		Widget asWidget();
		HasClickHandlers getAddLink();
		HasClickHandlers getCancelLink();
		VerticalPanel getContribPanel();
		ListBox getResourceList();
		void hide();
		void update();
	}

	private final SimpleEventBus eventBus;
	private final Display display;

	private UserProfileInfo currentUser;
	private ResourceInfo resource = null;

	public ProjectNeedInputPresenter(UserProfileInfo currentUser,
			SimpleEventBus eventBus, Display display) {
		this.currentUser = currentUser;
		this.eventBus = eventBus;
		this.display = display;
		bind();
	}

	public void bind() {
		this.display.getResourceList().addItem("Money");
		this.display.getResourceList().addItem("Volunteering");
		
		this.display.getAddLink().addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				display.hide();
				eventBus.fireEvent(new ProjectNeedAddEvent(resource));
			}
		});
		this.display.getCancelLink().addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event){
				display.hide();
			}
		});
		this.display.getResourceList().addChangeHandler(new ChangeHandler(){
			public void onChange(ChangeEvent event){
				changeNeedType(display.getResourceList().getSelectedIndex());
			}
		});
		changeNeedType(display.getResourceList().getSelectedIndex());
	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		bind();
	}
	
	public VerticalPanel getResourcePanel(){
		return display.getContribPanel();
	}
	
	private void changeNeedType(int selIndex){// indexes are described at the beginning of this function
		// 0 = Money; 1 = Volunteering
		switch (selIndex){
		case 0: // Money
			ProjectNeedMoneyPresenter projectNeedMoneyPresenter = new ProjectNeedMoneyPresenter(currentUser, eventBus, new ProjectNeedMoneyView());
			projectNeedMoneyPresenter.go(ProjectNeedInputPresenter.this.display.getContribPanel());
			break;
		case 1: // Volunteering
			ProjectNeedVolunteeringPresenter projectNeedVolunteeringPresenter = new ProjectNeedVolunteeringPresenter(currentUser, eventBus, new ProjectNeedVolunteeringView());
			projectNeedVolunteeringPresenter.go(ProjectNeedInputPresenter.this.display.getContribPanel());
			break;
		default: // do nothing. this should never happen
			break;
		}		
		//ProjectNeedInputPresenter.this.display.update();
	}

}
