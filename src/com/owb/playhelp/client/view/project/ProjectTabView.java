package com.owb.playhelp.client.view.project;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.presenter.project.ProjectTabPresenter;

public class ProjectTabView extends Composite implements ProjectTabPresenter.Display {

	private static ProjectTabViewUiBinder uiBinder = GWT
			.create(ProjectTabViewUiBinder.class);

	interface ProjectTabViewUiBinder extends UiBinder<Widget, ProjectTabView> {
	}

	@UiField
	MenuItem homeLink;
	@UiField
	MenuItem listLink;
	@UiField
	MenuItem searchLink;

	public ProjectTabView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
    
	public Widget asWidget(){
		return this;
	}
	
	public MenuItem getHomeLink(){
		return homeLink;
	}
	
	public MenuItem getListLink(){
		return listLink;
	}
	
	public MenuItem getSearchLink(){
		return searchLink;
	}
	

}
