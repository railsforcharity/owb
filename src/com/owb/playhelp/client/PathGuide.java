/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.maps.client.geocode.Geocoder;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import com.owb.playhelp.shared.UserProfileInfo;
import com.owb.playhelp.shared.ngo.NgoInfo;
import com.owb.playhelp.client.presenter.Presenter;
import com.owb.playhelp.client.presenter.MainHomePresenter;
import com.owb.playhelp.client.presenter.NewsHomePresenter;
import com.owb.playhelp.client.presenter.FriendsHomePresenter;
import com.owb.playhelp.client.presenter.RadioHomePresenter;
import com.owb.playhelp.client.presenter.ContactHomePresenter;
import com.owb.playhelp.client.presenter.ChapterHomePresenter;
import com.owb.playhelp.client.presenter.FriendHomePresenter;
import com.owb.playhelp.client.presenter.NgoHomePresenter;
import com.owb.playhelp.client.presenter.OrphanageHomePresenter;
import com.owb.playhelp.client.presenter.project.ProjectHomePresenter;
import com.owb.playhelp.client.presenter.project.ProjectMainPresenter;
import com.owb.playhelp.client.presenter.UserPreferenceEditPresenter;
import com.owb.playhelp.client.presenter.ngo.AddNgoPresenter;
import com.owb.playhelp.client.presenter.ngo.ReportAbuseNgoPresenter;
import com.owb.playhelp.client.presenter.ngo.ShowDetailsNgoPresenter;
import com.owb.playhelp.client.presenter.orphanage.AddOrphanagePresenter;
import com.owb.playhelp.client.presenter.orphanage.AddOrphanageStatusPresenter;
import com.owb.playhelp.client.presenter.project.AddProjectPresenter;
import com.owb.playhelp.client.presenter.project.AddProjectStatusPresenter;
import com.owb.playhelp.client.service.project.ProjectService;
import com.owb.playhelp.client.service.project.ProjectServiceAsync;
import com.owb.playhelp.client.service.ContributionService;
import com.owb.playhelp.client.service.ContributionServiceAsync;
import com.owb.playhelp.client.service.LoginService;
import com.owb.playhelp.client.service.LoginServiceAsync;
import com.owb.playhelp.client.service.UserService;
import com.owb.playhelp.client.service.UserServiceAsync;
import com.owb.playhelp.client.service.ngo.NgoService;
import com.owb.playhelp.client.service.ngo.NgoServiceAsync;
import com.owb.playhelp.client.service.orphanage.OrphanageService;
import com.owb.playhelp.client.service.orphanage.OrphanageServiceAsync;
import com.owb.playhelp.client.view.MainHomeView;
import com.owb.playhelp.client.view.NewsHomeView;
import com.owb.playhelp.client.view.FriendsHomeView;
import com.owb.playhelp.client.view.RadioHomeView;
import com.owb.playhelp.client.view.ContactHomeView;
import com.owb.playhelp.client.view.chapter.ChapterHomeView;
import com.owb.playhelp.client.view.friend.FriendHomeView;
import com.owb.playhelp.client.view.ngo.NgoHomeView;
import com.owb.playhelp.client.view.ngo.AddNgoView;
import com.owb.playhelp.client.view.ngo.ReportAbuseNgoView;
import com.owb.playhelp.client.view.ngo.ShowDetailsNgoView;
import com.owb.playhelp.client.view.orphanage.AddOrphanageView;
import com.owb.playhelp.client.view.orphanage.AddOrphanageStatusView;
import com.owb.playhelp.client.view.project.AddProjectView;
import com.owb.playhelp.client.view.project.AddProjectStatusView;
import com.owb.playhelp.client.view.orphanage.OrphanageHomeView;
import com.owb.playhelp.client.view.project.ProjectHomeView;
import com.owb.playhelp.client.view.project.ProjectMainView;
import com.owb.playhelp.client.view.UserPreferenceEditView;
import com.owb.playhelp.client.event.MainViewEvent;
import com.owb.playhelp.client.event.MainViewEventHandler;
import com.owb.playhelp.client.event.LoginEvent;
import com.owb.playhelp.client.event.LoginEventHandler;
import com.owb.playhelp.client.event.LogoutEvent;
import com.owb.playhelp.client.event.LogoutEventHandler;
import com.owb.playhelp.client.event.MainHomeEvent;
import com.owb.playhelp.client.event.MainHomeEventHandler;
import com.owb.playhelp.client.event.NewsHomeEvent;
import com.owb.playhelp.client.event.NewsHomeEventHandler;
import com.owb.playhelp.client.event.FriendsHomeEvent;
import com.owb.playhelp.client.event.FriendsHomeEventHandler;
import com.owb.playhelp.client.event.RadioHomeEvent;
import com.owb.playhelp.client.event.RadioHomeEventHandler;
import com.owb.playhelp.client.event.ContactHomeEvent;
import com.owb.playhelp.client.event.ContactHomeEventHandler;
import com.owb.playhelp.client.event.ChapterHomeEvent;
import com.owb.playhelp.client.event.ChapterHomeEventHandler;
import com.owb.playhelp.client.event.FriendHomeEvent;
import com.owb.playhelp.client.event.FriendHomeEventHandler;
import com.owb.playhelp.client.event.NgoHomeEvent;
import com.owb.playhelp.client.event.NgoHomeEventHandler;
import com.owb.playhelp.client.event.OrphanageHomeEvent;
import com.owb.playhelp.client.event.OrphanageHomeEventHandler;
import com.owb.playhelp.client.event.PreferencesEditEvent;
import com.owb.playhelp.client.event.PreferencesEditEventHandler;
import com.owb.playhelp.client.event.user.UserPreferenceUpdateEvent;
import com.owb.playhelp.client.event.user.UserPreferenceUpdateEventHandler;
import com.owb.playhelp.client.event.user.UserPreferenceEditCancelEvent;
import com.owb.playhelp.client.event.user.UserPreferenceEditCancelEventHandler;
import com.owb.playhelp.client.event.ngo.NgoUpdateEvent;
import com.owb.playhelp.client.event.ngo.ShowPopupAddNgoEvent;
import com.owb.playhelp.client.event.ngo.ShowPopupAddNgoEventHandler;
import com.owb.playhelp.client.event.ngo.ShowPopupDetailsNgoEventHandler;
import com.owb.playhelp.client.event.ngo.ShowPopupReportAbuseNgoEvent;
import com.owb.playhelp.client.event.ngo.ShowPopupReportAbuseNgoEventHandler;
import com.owb.playhelp.client.event.ngo.NgoRemoveEvent;
import com.owb.playhelp.client.event.ngo.NgoRemoveEventHandler;
import com.owb.playhelp.client.event.ngo.ShowPopupDetailsNgoEvent;
import com.owb.playhelp.client.event.ngo.ShowPopupDetailsNgoEventHandler;
import com.owb.playhelp.client.event.orphanage.ShowPopupAddOrphanageEvent;
import com.owb.playhelp.client.event.orphanage.ShowPopupAddOrphanageEventHandler;
import com.owb.playhelp.client.event.orphanage.ShowPopupAddOrphanageStatusEvent;
import com.owb.playhelp.client.event.orphanage.ShowPopupAddOrphanageStatusEventHandler;
import com.owb.playhelp.client.event.project.ShowPopupAddProjectEvent;
import com.owb.playhelp.client.event.project.ShowPopupAddProjectEventHandler;
import com.owb.playhelp.client.helper.RPCCall;


public class PathGuide implements ValueChangeHandler<String>  {
	private final SimpleEventBus thePath;
	private final UserProfileInfo currentUser;
	
	private final NgoServiceAsync ngoService;
	private final OrphanageServiceAsync orphanageService;
	private final ProjectServiceAsync projectService;
	private final UserServiceAsync userService;
	private final LoginServiceAsync loginService;
	Presenter presenter = null;

	private String lastView = "0";
	private Geocoder geocoder;
	
	
	public PathGuide(LoginServiceAsync loginService, UserServiceAsync userService, NgoServiceAsync ngoService, OrphanageServiceAsync orphanageService, 
			ProjectServiceAsync projectService, SimpleEventBus thePath, UserProfileInfo currentUser){
		this.loginService = loginService;
		this.userService = userService;
		this.ngoService = ngoService;
		this.orphanageService = orphanageService;
		this.projectService = projectService;
		this.thePath = thePath;
		this.currentUser = currentUser;
		this.geocoder = null;
		bind();
	}
	
	private void bind(){
		
		//ProjectServiceAsync projectService = GWT.create(ProjectService.class);
		ContributionServiceAsync contributionService = GWT.create(ContributionService.class);
		ProjectGuide projectGuide = new ProjectGuide(projectService, contributionService, thePath, currentUser);
		projectGuide.go();
		
		History.addValueChangeHandler(this);
		thePath.addHandler(PreferencesEditEvent.TYPE, new PreferencesEditEventHandler(){
			public void onEditPreference(PreferencesEditEvent event){
				History.newItem("edituser");
			}
		});
		thePath.addHandler(UserPreferenceUpdateEvent.TYPE, new UserPreferenceUpdateEventHandler(){
			public void onUserPreferenceUpdate(UserPreferenceUpdateEvent event){
				History.newItem(lastView);
				// I should user History.newItem(lastView); to return to the last view
				// before clicking preference link. But I am not sure how to handle this yet
			}
		});
		thePath.addHandler(UserPreferenceEditCancelEvent.TYPE, new UserPreferenceEditCancelEventHandler(){
			public void onUserPreferenceEditCancel(UserPreferenceEditCancelEvent event){
				History.newItem(lastView);
				// I should user History.newItem(lastView); to return to the last view
				// before clicking preference link. But I am not sure how to handle this yet
			}
		});
		thePath.addHandler(NewsHomeEvent.TYPE, new NewsHomeEventHandler(){
			public void onNewsHome(NewsHomeEvent event){
				lastView = "news";
				History.newItem("news");
			}
		});
		thePath.addHandler(MainHomeEvent.TYPE, new MainHomeEventHandler(){
			public void onMainHomeRequest(MainHomeEvent event){
				lastView = "map";
				History.newItem("map");
			}
		});
		thePath.addHandler(FriendsHomeEvent.TYPE, new FriendsHomeEventHandler(){
			public void onFriendsHome(FriendsHomeEvent event){
				lastView = "friends";
				History.newItem("friends");
			}
		});
		thePath.addHandler(RadioHomeEvent.TYPE, new RadioHomeEventHandler(){
			public void onRadioHome(RadioHomeEvent event){
				lastView = "radio";
				History.newItem("radio");
			}
		});
		thePath.addHandler(ContactHomeEvent.TYPE, new ContactHomeEventHandler(){
			public void onContactHome(ContactHomeEvent event){
				lastView = "contactus";
				History.newItem("contactus");
			}
		});
		thePath.addHandler(ChapterHomeEvent.TYPE, new ChapterHomeEventHandler(){
			public void onChapterHomeRequest(ChapterHomeEvent event){
				History.newItem("chphome");
			}
		});
		thePath.addHandler(FriendHomeEvent.TYPE, new FriendHomeEventHandler(){
			public void onFriendHomeRequest(FriendHomeEvent event){
				History.newItem("frdhome");
			}
		});
		thePath.addHandler(NgoHomeEvent.TYPE, new NgoHomeEventHandler(){
			public void onNgoHomeRequest(NgoHomeEvent event){
				History.newItem("ngohome");
			}
		});
		thePath.addHandler(OrphanageHomeEvent.TYPE, new OrphanageHomeEventHandler(){
			public void onOrphanageHomeRequest(OrphanageHomeEvent event){
				History.newItem("orphome");
			}
		});
		thePath.addHandler(ShowPopupDetailsNgoEvent.TYPE, new ShowPopupDetailsNgoEventHandler(){
			public void onShowPopupDetailsNgo(ShowPopupDetailsNgoEvent event){
				ShowDetailsNgoPresenter showDetailsNgoPresenter = new ShowDetailsNgoPresenter(event.getNgo(),currentUser, ngoService, thePath,new ShowDetailsNgoView(event.getClickPoint()));
				showDetailsNgoPresenter.go(Owb.get().getMainPanel());
			}
		});
		thePath.addHandler(ShowPopupReportAbuseNgoEvent.TYPE, new ShowPopupReportAbuseNgoEventHandler(){
			public void onShowPopupReportAbuseNgo(ShowPopupReportAbuseNgoEvent event){
				if (currentUser == null){
					Window.alert("You must log in to report an Organization");
					return;
				}

				ReportAbuseNgoPresenter reportNgoPresenter = new ReportAbuseNgoPresenter(event.getNgo(), currentUser, ngoService,thePath,new ReportAbuseNgoView(event.getClickPoint()));
				reportNgoPresenter.go(Owb.get().getMainPanel());
			}
		});
		thePath.addHandler(ShowPopupAddNgoEvent.TYPE, new ShowPopupAddNgoEventHandler(){
			public void onShowPopupAddNgo(ShowPopupAddNgoEvent event){
				if (currentUser == null){
					Window.alert("You must log in to add or update an Organization");
					return;
				}
				if (event.getNgo() != null){
					if (!event.getNgo().getMember()){
						Window.alert("You can't update an Organization if you are not a member ");
						return;
					}
				}
				AddNgoPresenter addNgoPresenter = new AddNgoPresenter(event.getNgo(), currentUser, ngoService,thePath,geocoder,new AddNgoView(event.getClickPoint()));
		        addNgoPresenter.go(Owb.get().getMainPanel());
			}
		});
		thePath.addHandler(ShowPopupAddOrphanageEvent.TYPE, new ShowPopupAddOrphanageEventHandler(){
			public void onShowPopupAddOrphanage(ShowPopupAddOrphanageEvent event){
				if (currentUser == null){
					Window.alert("You must log in to add or update an Organization");
					return;
				}
				if (event.getOrphanage() != null){
					if (!event.getOrphanage().getMember()){
						Window.alert("You can't update an Orphanage if you are not a member ");
						return;
					}
				}
				AddOrphanagePresenter addOrphanagePresenter = new AddOrphanagePresenter(event.getOrphanage(), currentUser, orphanageService,thePath,geocoder,new AddOrphanageView(event.getClickPoint()));
				addOrphanagePresenter.go(Owb.get().getMainPanel());
			}
		});
		thePath.addHandler(ShowPopupAddOrphanageStatusEvent.TYPE, new ShowPopupAddOrphanageStatusEventHandler(){
			public void onShowPopupAddOrphanageStatus(ShowPopupAddOrphanageStatusEvent event){
				if (currentUser == null){
					Window.alert("You must log in to add or update an Organization");
					return;
				}
				if (event.getOrphanage() != null){
					if (!event.getOrphanage().getMember()){
						Window.alert("You can't update the orphanage status if you are not a member ");
						return;
					}
				}
				AddOrphanageStatusPresenter addOrphanageStatusPresenter = new AddOrphanageStatusPresenter(event.getOrphanage(), orphanageService,thePath,new AddOrphanageStatusView(event.getClickPoint()));
				addOrphanageStatusPresenter.go(Owb.get().getMainPanel());
			}
		});
		thePath.addHandler(ShowPopupAddProjectEvent.TYPE, new ShowPopupAddProjectEventHandler(){
			public void onShowPopupAddProject(ShowPopupAddProjectEvent event){
				if (currentUser == null){
					Window.alert("You must log in to add or update a Project");
					return;
				}
				if (event.getProject() != null){
					if (!event.getProject().getMember()){
						Window.alert("You can't update an Project if you are not a member ");
						return;
					}
				}
				AddProjectPresenter addProjectPresenter = new AddProjectPresenter(event.getProject(), currentUser, projectService,thePath,geocoder,new AddProjectView(event.getClickPoint()));
				addProjectPresenter.go(Owb.get().getMainPanel());
			}
		});
		thePath.addHandler(NgoRemoveEvent.TYPE, new NgoRemoveEventHandler(){
			public void onNgoRemove(NgoRemoveEvent event){
				if (currentUser == null){
					Window.alert("You must log in to remove an Organization");
					return;
				}
				if (event.getNgo() != null){
					if (!event.getNgo().getMember()){
						Window.alert("You can't remove an Organization if you are not a member ");
						return;
					}
				}
				final NgoInfo delNgo = event.getNgo();
				new RPCCall<Void>() {
				      @Override
				      protected void callService(AsyncCallback<Void> cb) {
				    	  ngoService.removeNgo(delNgo, cb);
				      }

				      @Override
				      public void onSuccess(Void result) {
				        GWT.log("PathGuide: Ngo was removed");
				      }

				      @Override
				      public void onFailure(Throwable caught) {
				        Window.alert("Error removing Ngo...");
				      }
				    }.retry(3);
			}
		});
		
		
		/*
		thePath.addHandler(LogoutEvent.TYPE, new LogoutEventHandler(){
			public void onLogout(LogoutEvent event){
				GWT.log("PathGuide: Logout event received");
				History.newItem("loginout");
			}
		});*/
		/*
		thePath.addHandler(MainViewEvent.TYPE, new MainViewEventHandler(){
			public void onMainView(MainViewEvent event){
				GWT.log("PathGuide: MainView event received");
				History.newItem("restart");
			}
		});*/
	}
	
	public void go() {
		if ("".equals(History.getToken())) {
		    History.newItem("news");
		  } else {
		    History.fireCurrentHistoryState();
		  }
	}
	
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();
		if (token != null) {
			Presenter presenter = null;
			if (token.equals("edituser")) {
				//Owb.get().getMainTitle().setText("User Preferences");
				presenter = new UserPreferenceEditPresenter(currentUser, userService, thePath, new UserPreferenceEditView());
				presenter.go(Owb.get().getMainPanel());	
	        return;
	      } 
			if (token.equals("orphome")) {
				//Owb.get().getMainTitle().setText("Orphanage View");
				presenter = new OrphanageHomePresenter(currentUser,thePath,new OrphanageHomeView());
				presenter.go(Owb.get().getMainPanel());
	        return;
	      } 
			if (token.equals("ngohome")) {
				//Owb.get().getMainTitle().setText("NGO View");
				presenter = new NgoHomePresenter(currentUser,thePath,new NgoHomeView());
				presenter.go(Owb.get().getMainPanel());
	        return;
	      } 
			if (token.equals("frdhome")) {
				//Owb.get().getMainTitle().setText("Friend View");
				presenter = new FriendHomePresenter(currentUser,thePath,new FriendHomeView());
				presenter.go(Owb.get().getMainPanel());
	        return;
	      } 
			if (token.equals("chphome")) {
				//Owb.get().getMainTitle().setText("Chapter View");
				presenter = new ChapterHomePresenter(currentUser,thePath,new ChapterHomeView());
				presenter.go(Owb.get().getMainPanel());
	        return;
	      } 
			if (token.equals("map")) {
				//Owb.get().getMainTitle().setText("Welcome To Karmagotchi");
				presenter = new MainHomePresenter(currentUser,ngoService,orphanageService,thePath,new MainHomeView());
				presenter.go(Owb.get().getMainPanel());
	        return;
	      } 
			if (token.equals("news")) {
				//Owb.get().getMainTitle().setText("Welcome To Karmagotchi");
				presenter = new NewsHomePresenter(currentUser,thePath,new NewsHomeView());
				presenter.go(Owb.get().getMainPanel());
	        return;
	      } 
			if (token.equals("0")) {
				//Owb.get().getMainTitle().setText("Welcome To Karmagotchi");
				presenter = new NewsHomePresenter(currentUser,thePath,new NewsHomeView());
				presenter.go(Owb.get().getMainPanel());
	        return;
	      } 
			if (token.equals("friends")) {
				//Owb.get().getMainTitle().setText("Welcome To Karmagotchi");
				presenter = new FriendsHomePresenter(currentUser,thePath,new FriendsHomeView());
				presenter.go(Owb.get().getMainPanel());
	        return;
	      } 
			if (token.equals("radio")) {
				//Owb.get().getMainTitle().setText("Welcome To Karmagotchi");
				presenter = new RadioHomePresenter(currentUser,thePath,new RadioHomeView());
				presenter.go(Owb.get().getMainPanel());
	        return;
	      } 
			if (token.equals("contactus")) {
				//Owb.get().getMainTitle().setText("Welcome To Karmagotchi");
				presenter = new ContactHomePresenter(currentUser,thePath,new ContactHomeView());
				presenter.go(Owb.get().getMainPanel());
	        return;
	      } 
			/*
			if (token.equals("loginout")) {
				Owb.get().loggedOutView();
	        return;
	      } 
			if (token.equals("restart")) {
				Owb.get().showCurrentUserView();
	        return;
	      } */
			
		}
		//lastView = token;
	}
}
