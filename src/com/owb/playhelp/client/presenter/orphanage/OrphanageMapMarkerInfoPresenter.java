/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter.orphanage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.helper.RPCCall;
import com.owb.playhelp.client.service.LoginServiceAsync;
import com.owb.playhelp.shared.UserProfileInfo;
import com.owb.playhelp.shared.orphanage.OrphanageInfo;
import com.owb.playhelp.shared.project.ProjectInfo;
import com.owb.playhelp.client.presenter.Presenter;
import com.owb.playhelp.client.event.orphanage.ShowPopupAddOrphanageEvent;
import com.owb.playhelp.client.event.orphanage.OrphanageRemoveEvent;
import com.owb.playhelp.client.event.project.ShowPopupAddProjectEvent;
import com.owb.playhelp.client.helper.ClickPoint;

public class OrphanageMapMarkerInfoPresenter implements Presenter {
	public interface Display {
		Widget asWidget();
		public HTMLPanel getMainPanel();
		public HasText getOrphanageName();
		public HasText getOrphanageDescription();
		public HasText getOrphanageAddress();
		public HasText getOrphanagePhone();
		public HasText getOrphanageEmail();
		public Anchor getEditBut();
		public Anchor getRemoveBut();
		public HasClickHandlers getReportBut();
		public HasClickHandlers getFollowBut();
		public HasClickHandlers getFulldescBut();
		public HasClickHandlers getAddprojBut();
	}

	private final SimpleEventBus eventBus;
	public final Display display;

	private UserProfileInfo currentUser;
	private final OrphanageInfo Orphanage;

	public OrphanageMapMarkerInfoPresenter(UserProfileInfo currentUser,
			SimpleEventBus eventBus, OrphanageInfo Orphanage, Display display) {
		this.currentUser = currentUser;
		this.eventBus = eventBus;
		this.display = display;
		this.Orphanage = Orphanage;
	}

	public void bind() {
	    this.display.getEditBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	eventBus.fireEvent(new ShowPopupAddOrphanageEvent(new ClickPoint(100,100),Orphanage));
	        }
	      });
	    this.display.getRemoveBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	eventBus.fireEvent(new OrphanageRemoveEvent(Orphanage));
	        }
	      });
	    this.display.getReportBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        }
	      });
	    this.display.getFollowBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        }
	      });
	    this.display.getFulldescBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        }
	      });
	    this.display.getAddprojBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	eventBus.fireEvent(new ShowPopupAddProjectEvent(new ClickPoint(100,100),new ProjectInfo()));
	        }
	      });
	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		display.getOrphanageName().setText(Orphanage.getName());
		display.getOrphanageAddress().setText(Orphanage.getAddress());
		display.getOrphanageDescription().setText(Orphanage.getDescription());
		display.getOrphanagePhone().setText(Orphanage.getPhone());
		display.getOrphanageEmail().setText(Orphanage.getEmail());
		
		if (!Orphanage.getMember()) {
			display.getEditBut().setVisible(false);
			display.getRemoveBut().setVisible(false);
		}
		bind();
	}

}
