package com.owb.playhelp.client.view.project;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.presenter.project.ProjectHomePresenter;

public class ProjectHomeView extends Composite implements ProjectHomePresenter.Display {

	private static ProjectHomeViewUiBinder uiBinder = GWT
			.create(ProjectHomeViewUiBinder.class);

	interface ProjectHomeViewUiBinder extends UiBinder<Widget, ProjectHomeView> {
	}

	public ProjectHomeView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public Widget asWidget(){
		return this;
	}


}
