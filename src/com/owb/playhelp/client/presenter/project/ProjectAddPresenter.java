/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.event.project.ProjectNeedAddEvent;
import com.owb.playhelp.client.event.project.ProjectNeedAddEventHandler;
import com.owb.playhelp.client.event.project.ProjectUpdateEvent;
import com.owb.playhelp.client.event.project.ProjectAddCancelledEvent;
import com.owb.playhelp.client.presenter.Presenter;
import com.owb.playhelp.client.presenter.project.ProjectNeedInputPresenter;
import com.owb.playhelp.client.service.project.ProjectServiceAsync;
import com.owb.playhelp.client.helper.MouseClick;
import com.owb.playhelp.client.helper.RPCCall;
import com.owb.playhelp.client.service.LoginServiceAsync;
import com.owb.playhelp.client.service.project.ProjectService;
import com.owb.playhelp.client.view.project.ProjectNeedInputView;
import com.owb.playhelp.shared.UserProfileInfo;
import com.owb.playhelp.shared.ngo.NgoInfo;
import com.owb.playhelp.shared.project.ProjectInfo;
import com.owb.playhelp.shared.ChapterInfo;
import com.owb.playhelp.shared.orphanage.OrphanageInfo;
import com.owb.playhelp.shared.ResourceInfo;

public class ProjectAddPresenter implements Presenter {
	public interface Display {
		Widget asWidget();
		HasClickHandlers getCancelButton();
		HasClickHandlers getSaveButton();
		HasValue<String> getNameField();
		HasValue<String> getChapterField();
		HasValue<String> getNgoField();
		HasValue<String> getOrphanageField();
		HasValue<String> getDescriptionField();
		HasClickHandlers getNeedTable();
		HasClickHandlers getAddNeedButton();
		void setData(List<String[]> needList);
		String getDeleteNeed(ClickEvent event);
		MouseClick getMouseClick(ClickEvent event);
	}

	private final SimpleEventBus eventBus;
	private final Display display;
	private ProjectInfo project;
	private final ProjectServiceAsync projectService;
	private UserProfileInfo currentUser;

	public ProjectAddPresenter(UserProfileInfo currentUser,ProjectServiceAsync projectService,
			SimpleEventBus eventBus, Display display) {
		this.currentUser = currentUser;
		this.projectService = projectService;
		this.eventBus = eventBus;
		this.display = display;
		this.project = new ProjectInfo();
	}

	public ProjectAddPresenter(UserProfileInfo currentUser,ProjectServiceAsync projectService,
			SimpleEventBus eventBus, Display display, final String id) {
		this(currentUser,projectService,eventBus,display);
		
		if (id == null) return;
		
		// Now check if there is a project with id and gather the information to 
		// display it in the area
	}
	public void bind() {
	      this.display.getSaveButton().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	          //doSave();
	        }
	      });

	      this.display.getCancelButton().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	          GWT.log("ProjectAddPresenter: Firing ProjectAddCancelledEvent");
	          eventBus.fireEvent(new ProjectAddCancelledEvent());
	        }
	      });

	      this.display.getAddNeedButton().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	          ProjectNeedInputPresenter projectNeedInputPresenter = new ProjectNeedInputPresenter(currentUser,eventBus,new ProjectNeedInputView());
	        }
	      });
	      
	      eventBus.addHandler(ProjectNeedAddEvent.TYPE, new ProjectNeedAddEventHandler(){
	    	  public void onProjectNeedAdd(ProjectNeedAddEvent event){
	    		  // update project with new need resource and update table
	    		  //addNeed(event.getResource());
	    	  }
	      });
	      
	      if (display.getNeedTable() != null) display.getNeedTable().addClickHandler(new ClickHandler(){
	    	 public void onClick(ClickEvent event){
	    		 String deletedNeed = display.getDeleteNeed(event); 
	    		 if (deletedNeed != null){
	    			 //project.getNeeds().remove(deletedNeed);
	    			 //display.setData(toStringList(project.getNeeds()));
	    		 }
	    	 }
	      });
	}
	
	/*
	private void addNeed(ResourceInfo need){
		if (need.equals("")) return;
		project.addNeed(need);
		display.setData(toStringList(project.getNeeds()));		
	}
	
	  private void doSave() {
		  project = new ProjectInfo();
		  project.setName(display.getNameField().getValue().trim());
		  project.setDescription(display.getDescriptionField().getValue().trim());
		  project.setChapter(new ChapterInfo(display.getChapterField().getValue().trim()));
		  project.setOrphanage(new OrphanageInfo(display.getNameField().getValue().trim()));
		  project.setNgo(new NgoInfo(display.getNameField().getValue().trim()));
	    new RPCCall<ProjectInfo>() {
	      @Override
	      protected void callService(AsyncCallback<ProjectInfo> cb) {
	        projectService.updateProject(currentUser.getUniqueId(), project, cb);
	      }

	      @Override
	      public void onSuccess(ProjectInfo result) {
	        GWT.log("ProjectAddPresenter: Firing ProjectUpdateEvent");
	        eventBus.fireEvent(new ProjectUpdateEvent(result)); 
	      }

	      @Override
	      public void onFailure(Throwable caught) {
	        Window.alert("Error retrieving project...");
	      }
	    }.retry(3);
	  }
	  */
	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		bind();
	}
	
	private List<String[]> toStringList(Set<ResourceInfo> needs){
		List<String[]> list = new ArrayList<String[]>();
		for (ResourceInfo resource : needs){
			list.add(resource.toStringArray());
		}
		return list;
	}
	

}
