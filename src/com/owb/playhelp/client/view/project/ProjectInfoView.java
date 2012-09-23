package com.owb.playhelp.client.view.project;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.owb.playhelp.client.presenter.project.ProjectInfoPresenter;

public class ProjectInfoView extends Composite implements ProjectInfoPresenter.Display {

	private static ProjectInfoViewUiBinder uiBinder = GWT
			.create(ProjectInfoViewUiBinder.class);

	interface ProjectInfoViewUiBinder extends UiBinder<Widget, ProjectInfoView> {
	}

	@UiField
	HorizontalPanel mainPanel;
	@UiField
	Label nameLabel;
	@UiField
	Label descLabel;
	@UiField
	Anchor contributeLink;

	public ProjectInfoView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	public ProjectInfoView(String name, String desc) {
		initWidget(uiBinder.createAndBindUi(this));
		nameLabel.setText(name);
		descLabel.setText(desc);
	}

	public HorizontalPanel getContainer(){
		return mainPanel;
	}
	public Label getNameLabel(){
		return nameLabel;
	}
	public Label getDescriptionLabel(){
		return descLabel;
	}
	public Anchor getContributeLink(){
		return contributeLink;
	}

}
