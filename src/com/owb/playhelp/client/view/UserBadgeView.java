/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.presenter.UserBadgePresenter;

public class UserBadgeView extends Composite implements UserBadgePresenter.Display {

	@UiField
	Image profilePicture;
	@UiField
	Label userNameLabel;
	@UiField
	Label userEmailLabel;
	@UiField
	Label userTypeLabel;
	@UiField
	VerticalPanel karmaPanel;
	@UiField
	Anchor preferences;
	@UiField
	Anchor loginout;
	
	private static UserBadgeViewUiBinder uiBinder = GWT
			.create(UserBadgeViewUiBinder.class);

	interface UserBadgeViewUiBinder extends UiBinder<Widget, UserBadgeView> {
	}

	public UserBadgeView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public HasClickHandlers getPreferencesLink() {
	  return preferences;
	}
	@Override
	public Anchor getPreferencesText() {
	  return preferences;
	}
	@Override
	public HasClickHandlers getLogoutLink() {
	  return loginout;
	}
	@Override
	public Anchor getLogoutText() {
	  return loginout;
	}
	@Override
	public HasText getUserNameLabel() {
	  return userNameLabel;
	}
	@Override
	public HasText getUserEmailLabel() {
	  return userEmailLabel;
	}
	@Override
	public HasText getUserTypeLabel() {
	  return userTypeLabel;
	}
	@Override
	public Image getProfilePictureFrame() {
	  return profilePicture;
	}
	@Override
	public VerticalPanel getKarmaPanel(){
		return karmaPanel;
	}
	
}
