package com.owb.playhelp.client.view.project;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.PopupPanel;

import com.owb.playhelp.client.presenter.project.ProjectContributeNotLoggedPresenter;

public class ProjectContributeNotLoggedView extends PopupPanel implements ProjectContributeNotLoggedPresenter.Display {

	private static ProjectContributeNotLoggedViewUiBinder uiBinder = GWT
			.create(ProjectContributeNotLoggedViewUiBinder.class);

	interface ProjectContributeNotLoggedViewUiBinder extends
			UiBinder<Widget, ProjectContributeNotLoggedView> {
	}

	public ProjectContributeNotLoggedView() {
		super(true);
		add(uiBinder.createAndBindUi(this));
		show();
	}

	@UiField
	Anchor doneLink;
	
	@Override 
	public HasClickHandlers getDoneLink(){
	  return doneLink;
	}

	
	@Override
	public void hide() {
	  super.hide();
	}
	

}
