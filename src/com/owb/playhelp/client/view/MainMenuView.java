package com.owb.playhelp.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.presenter.MainMenuPresenter;

public class MainMenuView extends Composite implements MainMenuPresenter.Display {

	private static MainMenuViewUiBinder uiBinder = GWT
			.create(MainMenuViewUiBinder.class);

	interface MainMenuViewUiBinder extends
			UiBinder<Widget, MainMenuView> {
	}

	@UiField
	MenuBar mainMenu;
	@UiField	
    MenuItem homeLink,friendLink,projectLink,chapterLink,ngoLink,orphanageLink;
	@UiField
	MenuItem moreLink;
	
	public MainMenuView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public MenuItem getHomeLink() {
	  return homeLink;
	}
	@Override
	public MenuItem getFriendLink() {
	  return friendLink;
	}
	@Override
	public MenuItem getProjectLink() {
	  return projectLink;
	}
	@Override
	public MenuItem getChapterLink() {
	  return chapterLink;
	}
	@Override
	public MenuItem getNgoLink() {
	  return ngoLink;
	}
	@Override
	public MenuItem getOrphanageLink() {
	  return orphanageLink;
	}
	@Override
	public MenuItem getMoreLink() {
	  return moreLink;
	}
	
	@Override
	public MenuBar getMainMenu(){
		return mainMenu;
	}
}
