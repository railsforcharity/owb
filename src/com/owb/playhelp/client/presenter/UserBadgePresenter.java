/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.Owb;
import com.owb.playhelp.client.event.PreferencesEditEvent;
import com.owb.playhelp.client.event.LoginEvent;
import com.owb.playhelp.client.event.LoginEventHandler;
import com.owb.playhelp.client.event.LogoutEvent;
import com.owb.playhelp.client.event.LogoutEventHandler;
import com.owb.playhelp.client.event.project.ProjectUpdateEvent;
import com.owb.playhelp.client.event.user.UserPreferenceUpdateEvent;
import com.owb.playhelp.client.event.user.UserPreferenceUpdateEventHandler;
import com.owb.playhelp.client.helper.RPCCall;
import com.owb.playhelp.client.presenter.UserBadgePresenter.Display;
import com.owb.playhelp.client.service.LoginServiceAsync;
import com.owb.playhelp.shared.UserProfileInfo;
//import com.owb.playhelp.shared.exceptions.NoUserException;
import com.owb.playhelp.shared.project.ProjectItemSummaryInfo;
import com.owb.playhelp.client.resources.Resources;


public class UserBadgePresenter implements Presenter{
	  public interface Display {
			VerticalPanel getKarmaPanel();
			HasClickHandlers getPreferencesLink();
			Anchor getPreferencesText();
			HasClickHandlers getLogoutLink();
			Anchor getLogoutText();
			Image getProfilePictureFrame();
			HasText getUserNameLabel();
			HasText getUserEmailLabel();
			HasText getUserTypeLabel();
		    Widget asWidget();
		  }

	  private final LoginServiceAsync loginService;
	  private final SimpleEventBus eventBus;
	  private final Display display;

	  private UserProfileInfo currentUser;

	  public UserBadgePresenter(LoginServiceAsync loginService, SimpleEventBus eventBus,
	      Display display) {
	    this.loginService = loginService;
	    this.eventBus = eventBus;
	    this.display = display;
	  }
	  
	  public void bind(){		  
		  createUI();
		  this.display.getLogoutLink().addClickHandler(new ClickHandler(){
			  public void onClick(ClickEvent event){
				  doLoginout();
			  }
		  });
		  
		  this.display.getPreferencesLink().addClickHandler(new ClickHandler(){
			  public void onClick(ClickEvent event){
				  eventBus.fireEvent(new PreferencesEditEvent());
			  }
		  });
		  
		  eventBus.addHandler(LoginEvent.TYPE, new LoginEventHandler(){
			  @Override public void onLogin(LoginEvent event){
				  currentUser = event.getUser();
				  createUI();
			  }
		  });

		  eventBus.addHandler(LogoutEvent.TYPE, new LogoutEventHandler(){
			  @Override public void onLogout(LogoutEvent event){
				  GWT.log("UserBadgePresenter:: logout");
				  currentUser = null;
				  createUI();
			  }
		  });
			
		  eventBus.addHandler(UserPreferenceUpdateEvent.TYPE, new UserPreferenceUpdateEventHandler(){
				public void onUserPreferenceUpdate(UserPreferenceUpdateEvent event){
					currentUser = event.getUpdatedUser();
					createUI();
				}
			});
		  
		  // Listen to login events so we update the info of the user in this panel
	  }
	  
	  private void createUI(){
		  if (currentUser != null){
			  userLoggedin();
		  } else {
			  userLoggedout();
		  }
	  }
	  
	  private void doLoginout(){		  
		  if (currentUser == null){
			  // log in
			  Window.Location.assign("/logingoogle");
		  } else {
			  // log out
			  doLogout();
		  }
		  //Owb.get().showCurrentUserView();
			  
	  }
	  
	  private void doLogout() {
		  new RPCCall<Void>() {
		      @Override
		      protected void callService(AsyncCallback<Void> cb) {
		    	  loginService.logout(cb);
		    	  /*
		    	  try{
		        	loginService.logout(cb);
		        } catch (NoUserException e){
		        	GWT.log("ERROR UserBadgePresenter::doLogout ");
		        	e.printStackTrace();
		        }*/
		      }

		      @Override
		      public void onSuccess(Void result) {
		        // logout event already fired by RPCCall
		      }

		      @Override
		      public void onFailure(Throwable caught) {
		        Window.alert("An error occurred: " + caught.toString());
		      }
		    }.retry(3);
	  }
	  
	  private void userLoggedin(){		  
		  if (currentUser == null){
			  Window.alert("UserBadgePresenter:: Error in userLoggedin, null user");
			  userLoggedout();
			  return;
		  }
		  UserBadgePresenter.this.display.getProfilePictureFrame().setResource(Resources.INSTANCE.sampleProfilePicture());
		  UserBadgePresenter.this.display.getLogoutText().setText("Logout");
		  UserBadgePresenter.this.display.getPreferencesText().setText("Preferences");
		  UserBadgePresenter.this.display.getPreferencesText().setEnabled(true);
		  UserBadgePresenter.this.display.getUserNameLabel().setText(currentUser.getName());
		  UserBadgePresenter.this.display.getUserTypeLabel().setText(currentUser.getUserType());
		  UserBadgePresenter.this.display.getUserEmailLabel().setText(currentUser.getEmailAddress());
		  // change text of loginout link to "logout"		  
	  }
	  private void userLoggedout(){		  
		  UserBadgePresenter.this.display.getProfilePictureFrame().setResource(Resources.INSTANCE.loggedoutPicture());
		  UserBadgePresenter.this.display.getLogoutText().setText("Login");
		  UserBadgePresenter.this.display.getPreferencesText().setText("");
		  UserBadgePresenter.this.display.getPreferencesText().setEnabled(false);
		  UserBadgePresenter.this.display.getUserNameLabel().setText("");
		  UserBadgePresenter.this.display.getUserTypeLabel().setText("");
		  UserBadgePresenter.this.display.getUserEmailLabel().setText("");
		  // change text of loginout link to "login"		  
	  }	  
	  
	  public void go(final HasWidgets container){
		  container.clear();
		  container.add(display.asWidget());
		  bind();
	  }
	  
}
