package com.owb.playhelp.client.view.contribution;

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
import com.owb.playhelp.client.presenter.contribution.ContributionUserInfoPresenter;

public class ContributionUserInfoView extends Composite implements ContributionUserInfoPresenter.Display {

	private static ContributionUserInfoViewUiBinder uiBinder = GWT
			.create(ContributionUserInfoViewUiBinder.class);

	interface ContributionUserInfoViewUiBinder extends UiBinder<Widget, ContributionUserInfoView> {
	}

	@UiField
	HorizontalPanel mainPanel;
	@UiField
	Label nameLabel;
	@UiField
	Label descLabel;
	@UiField
	Label resourceLabel;
	@UiField
	Anchor detailLink;

	public ContributionUserInfoView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	public ContributionUserInfoView(String name, String desc, String resource) {
		initWidget(uiBinder.createAndBindUi(this));
		nameLabel.setText(name);
		descLabel.setText(desc);
		resourceLabel.setText(resource);
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
	public Label getResourceLabel(){
		return resourceLabel;
	}
	public Anchor getDetailLink(){
		return detailLink;
	}

}
