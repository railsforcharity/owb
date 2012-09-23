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

import com.owb.playhelp.client.presenter.project.ProjectNeedMoneyPresenter;

public class ProjectNeedMoneyView extends Composite implements ProjectNeedMoneyPresenter.Display {

	private static ProjectNeedMoneyViewUiBinder uiBinder = GWT
			.create(ProjectNeedMoneyViewUiBinder.class);

	interface ProjectNeedMoneyViewUiBinder extends
			UiBinder<Widget, ProjectNeedMoneyView> {
	}

	public ProjectNeedMoneyView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public Widget asWidget(){
		return this;
	}

	

}
