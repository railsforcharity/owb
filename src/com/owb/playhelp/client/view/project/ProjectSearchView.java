package com.owb.playhelp.client.view.project;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HorizontalPanel;

import com.owb.playhelp.client.presenter.project.ProjectSearchPresenter;

public class ProjectSearchView extends Composite implements
		ProjectSearchPresenter.Display {

	private static ProjectSearchViewUiBinder uiBinder = GWT
			.create(ProjectSearchViewUiBinder.class);

	interface ProjectSearchViewUiBinder extends
			UiBinder<Widget, ProjectSearchView> {
	}

	@UiField
	FlexTable projectsTable;
	@UiField
	Label loadingLabel;
	@UiField
	Button addProjectButton;
	
	public ProjectSearchView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public Widget asWidget() {
		return this;
	}
	private void displayProjects(List<HorizontalPanel> projects){
		int i = 0;
		projectsTable.removeAllRows();
		
		if (projects == null || projects.size() == 0){
			loadingLabel.setText("No Projects");
			return;
		}

	    loadingLabel.setVisible(false);		
	    for (final HorizontalPanel project: projects){
	    	projectsTable.setWidget(i,0,project);
	    	i++;	    	
	    }
	}

	@Override
	public HasClickHandlers getList(){
		return projectsTable;
	}
	@Override
	public HasClickHandlers getAddProjectButton(){
		return addProjectButton;
	}
	@Override
	public void setData(List<HorizontalPanel> data){
		displayProjects(data);
	}

}
