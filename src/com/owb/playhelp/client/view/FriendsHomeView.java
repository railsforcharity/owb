package com.owb.playhelp.client.view;

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

import com.owb.playhelp.client.presenter.FriendsHomePresenter;

public class FriendsHomeView extends Composite implements FriendsHomePresenter.Display {

	private static FriendsHomeViewUiBinder uiBinder = GWT
			.create(FriendsHomeViewUiBinder.class);

	interface FriendsHomeViewUiBinder extends UiBinder<Widget, FriendsHomeView> {
	}

	public FriendsHomeView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public FriendsHomeView(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
