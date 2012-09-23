/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.presenter.project.ProjectListPresenter;

public class ProjectListView extends Composite implements ProjectListPresenter.Display {

	private static ProjectListViewUiBinder uiBinder = GWT
			.create(ProjectListViewUiBinder.class);

	interface ProjectListViewUiBinder extends UiBinder<Widget, ProjectListView> {
	}

	@UiField
	FlexTable contributionsTable;
	@UiField
	Label loadingLabel;
	
	public ProjectListView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public Widget asWidget(){
		return this;
	}
			
	private void displayContributions(List<HorizontalPanel> contributions){
		int i = 0;
		contributionsTable.removeAllRows();
		
		if (contributions == null || contributions.size() == 0){
			loadingLabel.setText("No Projects");
			return;
		}

	    loadingLabel.setVisible(false);		
	    for (final HorizontalPanel contribution: contributions){
	    	contributionsTable.setWidget(i,0,contribution);
	    	i++;	    	
	    }
	}
	
	@Override
	public HasClickHandlers getList(){
		return contributionsTable;
	}
	@Override
	public void setData(List<HorizontalPanel> data){
		
		displayContributions(data);
	}
}
