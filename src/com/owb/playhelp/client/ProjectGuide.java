package com.owb.playhelp.client;

import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.History;
import com.owb.playhelp.client.event.project.ProjectHomeEvent;
import com.owb.playhelp.client.event.project.ProjectHomeEventHandler;
import com.owb.playhelp.client.event.project.ProjectListEvent;
import com.owb.playhelp.client.event.project.ProjectListEventHandler;
import com.owb.playhelp.client.event.project.ProjectSearchEvent;
import com.owb.playhelp.client.event.project.ProjectSearchEventHandler;
import com.owb.playhelp.client.event.project.ProjectAddEvent;
import com.owb.playhelp.client.event.project.ProjectAddEventHandler;
import com.owb.playhelp.client.event.project.ProjectAddCancelledEvent;
import com.owb.playhelp.client.event.project.ProjectAddCancelledEventHandler;
import com.owb.playhelp.client.event.project.ProjectMainEvent;
import com.owb.playhelp.client.event.project.ProjectMainEventHandler;
import com.owb.playhelp.client.event.project.ProjectUpdateEvent;
import com.owb.playhelp.client.event.project.ProjectUpdateEventHandler;
import com.owb.playhelp.client.presenter.Presenter;
import com.owb.playhelp.client.presenter.project.ProjectMainPresenter;
import com.owb.playhelp.client.presenter.project.ProjectTabPresenter;
import com.owb.playhelp.client.presenter.project.ProjectHomePresenter;
import com.owb.playhelp.client.presenter.project.ProjectListPresenter;
import com.owb.playhelp.client.presenter.project.ProjectSearchPresenter;
import com.owb.playhelp.client.presenter.project.ProjectAddPresenter;
import com.owb.playhelp.client.view.project.ProjectMainView;
import com.owb.playhelp.client.view.project.ProjectTabView;
import com.owb.playhelp.client.view.project.ProjectHomeView;
import com.owb.playhelp.client.view.project.ProjectListView;
import com.owb.playhelp.client.view.project.ProjectSearchView;
import com.owb.playhelp.client.view.project.ProjectAddView;
import com.owb.playhelp.shared.UserProfileInfo;
import com.owb.playhelp.client.service.project.ProjectServiceAsync;
import com.owb.playhelp.client.service.project.ProjectService;
import com.owb.playhelp.client.service.ContributionServiceAsync;
import com.owb.playhelp.client.service.ContributionService;

public class ProjectGuide implements ValueChangeHandler<String> {
	private final SimpleEventBus thePath;
	private final UserProfileInfo currentUser;
	
	private ProjectServiceAsync projectService;
	private ContributionServiceAsync contributionService;
	ProjectMainPresenter mainPresenter = null;
	ProjectTabPresenter tabPresenter = null;
	Presenter presenter = null;
	
	private String lastView = "projhome";

	public ProjectGuide(ProjectServiceAsync projectService, ContributionServiceAsync contributionService, SimpleEventBus thePath, UserProfileInfo currentUser){
		this.thePath = thePath;
		this.currentUser = currentUser;
		this.projectService = projectService;
		this.contributionService = contributionService;
		bind();
	}
	
	private void bind(){
		History.addValueChangeHandler(this);
		
		//projectService = GWT.create(ProjectService.class);
		//contributionService = GWT.create(ContributionService.class);

		thePath.addHandler(ProjectHomeEvent.TYPE, new ProjectHomeEventHandler(){
			public void onProjectHomeRequest(ProjectHomeEvent event){
				lastView = "projhome";
				History.newItem("projhome");
			}
		});
		thePath.addHandler(ProjectListEvent.TYPE, new ProjectListEventHandler(){
			public void onProjectList(ProjectListEvent event){
				lastView = "projlist";
				History.newItem("projlist");
			}
		});
		thePath.addHandler(ProjectSearchEvent.TYPE, new ProjectSearchEventHandler(){
			public void onProjectSearch(ProjectSearchEvent event){
				lastView = "projsearch";
				History.newItem("projsearch");
			}
		});
		thePath.addHandler(ProjectUpdateEvent.TYPE, new ProjectUpdateEventHandler(){
			public void onProjectUpdated(ProjectUpdateEvent event){
				lastView = "projsearch";
				History.newItem("projsearch"); 
			}
		});
		thePath.addHandler(ProjectAddEvent.TYPE, new ProjectAddEventHandler(){
			public void onProjectAdd(ProjectAddEvent event){
				lastView = "projadd";
				History.newItem("projadd");
			}
		});
		thePath.addHandler(ProjectAddCancelledEvent.TYPE, new ProjectAddCancelledEventHandler(){
			public void onProjectAddCancelled(ProjectAddCancelledEvent event){
				//lastView = "projadd";
				History.newItem("projsearch");
			}
		});

		thePath.addHandler(ProjectMainEvent.TYPE, new ProjectMainEventHandler(){
			public void onProjectMainRequest(ProjectMainEvent event){
				History.newItem("projmain");				
			}
		});
	}

	  public void onValueChange(ValueChangeEvent<String> event) {
	    String token = event.getValue();

	    if (token != null) {
	      Presenter presenter = null;
	      
	      Set<String> listTokens = new HashSet<String>(Arrays.asList(
	    		  new String[] {"projmain","projhome","projlist","projsearch","projadd"}));
	      // if token is in the list add tab and main window
	      if (listTokens.contains(token)){
			  mainPresenter = new ProjectMainPresenter(currentUser,thePath,new ProjectMainView());
			  tabPresenter = new ProjectTabPresenter(currentUser,thePath,new ProjectTabView());
			  mainPresenter.go(Owb.get().getMainPanel());
			  tabPresenter.go(mainPresenter.getTabPanel()); 	   
			  //Owb.get().getMainTitle().setText("Project View"); 	  
	      }

	      if (token.equals("projmain")) {
				presenter = new ProjectHomePresenter(currentUser,thePath,new ProjectHomeView());
				presenter.go(mainPresenter.getMainPanel());	     
				History.newItem(lastView);
	        return;
	      } 
	      if (token.equals("projhome")) {
				presenter = new ProjectHomePresenter(currentUser,thePath,new ProjectHomeView());
				presenter.go(mainPresenter.getMainPanel());	        
	        return;
	      } 
	      if (token.equals("projlist")) {
				if (currentUser != null){
			    	presenter = new ProjectListPresenter(currentUser, projectService, contributionService, thePath, new ProjectListView());
					presenter.go(mainPresenter.getMainPanel());
				}	        
	        return;
	      } 
	      if (token.equals("projsearch")) {
				presenter = new ProjectSearchPresenter(currentUser, projectService, contributionService,
						thePath, new ProjectSearchView());
				presenter.go(mainPresenter.getMainPanel());	        
	        return;
	      } 
	      if (token.equals("projadd")) {
				presenter = new ProjectAddPresenter(currentUser, projectService, thePath,new ProjectAddView());
				presenter.go(mainPresenter.getMainPanel());	        
	        return;
	      } 
	    }

	  }

		public void go() {
			History.fireCurrentHistoryState();
		}
	
}
