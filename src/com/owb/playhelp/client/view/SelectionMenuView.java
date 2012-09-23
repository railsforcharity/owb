package com.owb.playhelp.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.presenter.SelectionMenuPresenter;

public class SelectionMenuView extends Composite implements SelectionMenuPresenter.Display {

	private static SelectionMenuViewUiBinder uiBinder = GWT
			.create(SelectionMenuViewUiBinder.class);

	interface SelectionMenuViewUiBinder extends
			UiBinder<Widget, SelectionMenuView> {
	}

	@UiField
    Anchor homeLink;
	@UiField
    Anchor friendLink;
	@UiField
    Anchor projectLink;
	@UiField
    Anchor chapterLink;
	@UiField
    Anchor ngoLink;
	@UiField
    Anchor orphanageLink;
	@UiField
    Anchor moreLink;
	
	public SelectionMenuView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public HasClickHandlers getHomeLink() {
	  return homeLink;
	}
	@Override
	public HasClickHandlers getFriendLink() {
	  return friendLink;
	}
	@Override
	public HasClickHandlers getProjectLink() {
	  return projectLink;
	}
	@Override
	public HasClickHandlers getChapterLink() {
	  return chapterLink;
	}
	@Override
	public HasClickHandlers getNgoLink() {
	  return ngoLink;
	}
	@Override
	public HasClickHandlers getOrphanageLink() {
	  return orphanageLink;
	}
	@Override
	public HasClickHandlers getMoreLink() {
	  return moreLink;
	}

}
