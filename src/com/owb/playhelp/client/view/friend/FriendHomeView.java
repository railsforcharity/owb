package com.owb.playhelp.client.view.friend;

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

import com.owb.playhelp.client.presenter.FriendHomePresenter;

public class FriendHomeView extends Composite implements FriendHomePresenter.Display {

	private static FriendHomeViewUiBinder uiBinder = GWT
			.create(FriendHomeViewUiBinder.class);

	interface FriendHomeViewUiBinder extends UiBinder<Widget, FriendHomeView> {
	}

	public FriendHomeView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public Widget asWidget(){
		return this;
	}


}
