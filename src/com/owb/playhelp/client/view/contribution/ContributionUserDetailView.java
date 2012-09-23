package com.owb.playhelp.client.view.contribution;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.dom.client.HasClickHandlers;

import com.owb.playhelp.client.presenter.contribution.ContributionUserDetailPresenter;

public class ContributionUserDetailView extends PopupPanel implements ContributionUserDetailPresenter.Display {

	private static ProjectContributeViewUiBinder uiBinder = GWT
			.create(ProjectContributeViewUiBinder.class);

	interface ProjectContributeViewUiBinder extends
			UiBinder<Widget, ContributionUserDetailView> {
	}

	public ContributionUserDetailView() {
		super(true);
		add(uiBinder.createAndBindUi(this));
		//setName("Name");
		//setPopupPosition(location.getLeft(),location.getTop());  ClickPoint location
		show();
	}

	@UiField
	Anchor doneLink;

	  @Override
	  public Widget asWidget() {
	    return this;
	  }

	  @Override 
	  public HasClickHandlers getDoneLink(){
		  return doneLink;
	  }

	  @Override
	  public void hide() {
	    super.hide();
	  }
}
