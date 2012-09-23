/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client;


import com.owb.playhelp.client.event.LoginEvent;
import com.owb.playhelp.client.presenter.LoggedOutPresenter;
import com.owb.playhelp.client.presenter.UserBadgePresenter;
import com.owb.playhelp.client.presenter.UserKarmaPresenter;
import com.owb.playhelp.client.presenter.SelectionMenuPresenter;
import com.owb.playhelp.client.presenter.MainMenuPresenter;
import com.owb.playhelp.client.presenter.CenterPanelPresenter;
import com.owb.playhelp.client.presenter.TopCenterPanelPresenter;
import com.owb.playhelp.client.presenter.ngo.NgoMapMarkerInfoPresenter;
import com.owb.playhelp.client.view.LoggedOutView;
import com.owb.playhelp.client.view.UserBadgeView;
import com.owb.playhelp.client.view.UserKarmaView;
import com.owb.playhelp.client.view.SelectionMenuView;
import com.owb.playhelp.client.view.MainMenuView;
import com.owb.playhelp.client.view.ngo.NgoMapMarkerInfoView;
import com.owb.playhelp.client.helper.RPCCall;
import com.owb.playhelp.shared.UserProfileInfo;
import com.owb.playhelp.client.HeaderPanel;
import com.owb.playhelp.shared.FieldVerifier;
import com.owb.playhelp.shared.ngo.NgoInfo;
import com.owb.playhelp.client.service.LoginService;
import com.owb.playhelp.client.service.LoginServiceAsync;
import com.owb.playhelp.client.service.UserService;
import com.owb.playhelp.client.service.UserServiceAsync;
import com.owb.playhelp.client.service.ngo.NgoService;
import com.owb.playhelp.client.service.ngo.NgoServiceAsync;
import com.owb.playhelp.client.service.orphanage.OrphanageService;
import com.owb.playhelp.client.service.orphanage.OrphanageServiceAsync;
import com.owb.playhelp.client.service.project.ProjectService;
import com.owb.playhelp.client.service.project.ProjectServiceAsync;
import com.owb.playhelp.client.channel.Channel;
import com.owb.playhelp.client.channel.ChannelFactory;
import com.owb.playhelp.client.channel.SocketListener;
import com.owb.playhelp.client.event.project.ProjectAvailableEvent;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamFactory;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.maps.client.Maps;



/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Owb implements EntryPoint {
    
	/**
	 * 
	 * @author Miguel Charcos Llorens
	 *
	 */
	
	/*
	 * I define this here to be used on the push channel call but we may want later to 
	 * create a new class similar to Message 
	 */
	public enum Type {
	      NEW_CONTENT_AVAILABLE,
	      TEXT_MESSAGE,
	    }
	
	/**
	 * Define interfaces
	 */
	interface OwbUiBinder extends UiBinder<DockLayoutPanel, Owb> {}
	
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	
	SerializationStreamFactory pushServiceStreamFactory;
	
	/**
	 * Layout the window with three frameworks
	 */
	@UiField LeftPanel actionPanel;
	@UiField HorizontalPanel topCenterPanel;
	@UiField HorizontalPanel barPanel;
	@UiField ScrollPanel centerPanel;
	@UiField VerticalPanel statusPanel;

	RootLayoutPanel rootLayout;
    
	private UserProfileInfo currentUser;
	
	// Definition of presenters
	private UserBadgePresenter userBadgePresenter = null;
	private UserKarmaPresenter userKarmaPresenter = null;
	//private SelectionMenuPresenter selectionMenuPresenter = null;
	private MainMenuPresenter selectionMenuPresenter = null;
	private CenterPanelPresenter centerPanelPresenter = null;
	private TopCenterPanelPresenter topCenterPanelPresenter = null;
	
	/**
	 * Define variables:
	 *  - eventBus creates a bus where to propagate events
	 *  - binder: not sure yet
	 */
	private SimpleEventBus thePath = new SimpleEventBus();
	private static final OwbUiBinder binder = GWT.create(OwbUiBinder.class);
	
	/**
	 * RPC services
	 */
	private LoginServiceAsync loginService = GWT.create(LoginService.class);
	  
	/**
	 * Create a singleton to... 
	 */
	private static Owb singleton;
	  

	/**
	 * Get singleton
	 */
	public static Owb get() {
	  return singleton;
	}
	  
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		singleton = this;
		
		 /*
		    * Asynchronously loads the Maps API.
		    *
		    * The first parameter should be a valid Maps API Key to deploy this
		    * application on a public server, but a blank key will work for an
		    * application served from localhost.
		   */
		   //Maps.loadMapsApi("", "2", false, new Runnable() {
		   //   public void run() {
		   //	   }
		   // });

		
		//doSuperUser();
		showCurrentUserView();
	}


	public void doSuperUser() {
	  new RPCCall<Void>() {
	    @Override protected void callService(AsyncCallback<Void> cb) {
		  // the call returns a generic guest user if no logged in
	      loginService.doSuperUser(cb);
	    }

	    @Override public void onSuccess(Void cb) {
		      Window.alert("Super user successcully created");
	    }

	    @Override public void onFailure(Throwable caught) {
	      Window.alert("Error Creating super user --- " + caught.getMessage());
	    }
	  }.retry(3);
	}
	
	public void loggedOutView(){
		  rootLayout = RootLayoutPanel.get();
		  rootLayout.clear();
		  LoggedOutPresenter loggedOutPresenter = new LoggedOutPresenter(thePath,new LoggedOutView());
		  loggedOutPresenter.go(rootLayout);
	}
	
	public void showCurrentUserView() {
	  new RPCCall<UserProfileInfo>() {
	    @Override protected void callService(AsyncCallback<UserProfileInfo> cb) {
		  // the call returns a generic guest user if no logged in
	      loginService.getCurrentUserInfo(cb);
	    }

	    @Override public void onSuccess(UserProfileInfo userInfo) {
	    	setUser(userInfo);	
	    	downloadCode();
	    }

	    @Override public void onFailure(Throwable caught) {
	      Window.alert("Error: " + caught.getMessage());
	    }
	  }.retry(3);
	}
	
	private void downloadCode(){
		GWT.runAsync(new RunAsyncCallback() {
		      @Override public void onFailure(Throwable reason) {
		        Window.alert("Code download error: " + reason.getMessage());
		      }

		      @Override public void onSuccess() {
		    	  createCurrentUserView();
		        if (currentUser != null) thePath.fireEvent(new LoginEvent(currentUser));
		      }
		    });
	}
	
	private void createCurrentUserView() {
	  //rootLayout = RootLayoutPanel.get();
	  //rootLayout.clear();
	  
	  DockLayoutPanel outer = binder.createAndBindUi(this);
	  rootLayout = RootLayoutPanel.get();
	  rootLayout.clear();
	  rootLayout.add(outer);
	  
	  initiateOWB();
	}
	
	private void initiateOWB() {
	  UserServiceAsync userService = GWT.create(UserService.class);
	  NgoServiceAsync ngoService = GWT.create(NgoService.class);
	  OrphanageServiceAsync orphanageService = GWT.create(OrphanageService.class);
	  ProjectServiceAsync projectService = GWT.create(ProjectService.class);
	  PathGuide guide = new PathGuide(loginService, userService, ngoService, orphanageService, projectService, thePath, currentUser);
	  guide.go();
	  
	  
	  userBadgePresenter = new UserBadgePresenter(loginService, thePath, new UserBadgeView());
	  userBadgePresenter.go(actionPanel.getProfilePanel());
	  
	  userKarmaPresenter = new UserKarmaPresenter(currentUser, thePath, new UserKarmaView());
	  userKarmaPresenter.go(actionPanel.getMenuPanel());

	  //selectionMenuPresenter = new SelectionMenuPresenter(currentUser, thePath, new SelectionMenuView());
	  //selectionMenuPresenter.go(actionPanel.getMenuPanel());
	  //selectionMenuPresenter = new MainMenuPresenter(currentUser, thePath, new MainMenuView());
	  //selectionMenuPresenter.go(actionPanel.getTestPanel());

	  topCenterPanelPresenter = new TopCenterPanelPresenter(thePath, new TopCenterPanel());
	  topCenterPanelPresenter.go(topCenterPanel);
	  
	  centerPanelPresenter = new CenterPanelPresenter(currentUser,thePath, new CenterPanel());
	  centerPanelPresenter.go(barPanel);
	  
	  }
		
	private void startChannel(){
		String channelUser = currentUser.getChannel();
		// Leave routine if no channel returned
		if (channelUser == null) return;
		GWT.log("The channel of the current ("+ channelUser +") user is opening...");
		Channel channel = ChannelFactory.createChannel(channelUser);
		channel.open(new SocketListener(){
			public void onOpen(){
				GWT.log("Channel onOpen()");
			}
			public void onMessage(String info){
				try {
			          SerializationStreamReader reader = pushServiceStreamFactory.createStreamReader(info);
			          Type infoType = (Type) reader.readObject();
			          switch(infoType){
			          case NEW_CONTENT_AVAILABLE:
					      GWT.log("Pushed message received: NEW_CONTENT_AVAILABLE");
					      thePath.fireEvent(new ProjectAvailableEvent());
					      break;

					    case TEXT_MESSAGE:
					      GWT.log("Pushed message received: TEXT_MESSAGE: " + infoType);
					      break;

					    default:
					      Window.alert("Unknown message type: " + infoType);		
			          }
			        } catch (SerializationException e) {
			          throw new RuntimeException("Unable to deserialize " + info, e);
			        }
			}
		});
	}
	
	public SimpleEventBus getThePath(){
		return thePath;
	}
	
	void setUser(UserProfileInfo currentUser){
		this.currentUser = currentUser;
	}
	
    // These functions will return the main panels
	public ScrollPanel getMainPanel() {
		return centerPanel;
	}
	
}
