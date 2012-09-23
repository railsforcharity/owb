package com.owb.playhelp.client.view.project;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.presenter.project.ProjectMainPresenter;

public class ProjectMainView extends Composite implements ProjectMainPresenter.Display {

	private static ProjectMainViewUiBinder uiBinder = GWT
			.create(ProjectMainViewUiBinder.class);

	interface ProjectMainViewUiBinder extends UiBinder<Widget, ProjectMainView> {
	}

	public ProjectMainView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	HorizontalPanel tabPanel;
	@UiField
	VerticalPanel mainPanel;

	public HorizontalPanel getTabPanel(){
		return tabPanel;
	}
	public VerticalPanel getMainPanel(){
		return mainPanel;
	}
    
	@Override
	public Widget asWidget(){
		return this;
	}

}
