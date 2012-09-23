package com.owb.playhelp.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.owb.playhelp.client.presenter.UserKarmaPresenter;

public class UserKarmaView extends Composite implements UserKarmaPresenter.Display {

	private static UserKarmaViewUiBinder uiBinder = GWT
			.create(UserKarmaViewUiBinder.class);

	interface UserKarmaViewUiBinder extends UiBinder<Widget, UserKarmaView> {
	}

	public UserKarmaView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	//@UiField
	//Label userKarmaLabel;
	
	public Widget asWidget(){
		return this;
	}
	
	//public HasText getUserKarmaLabel(){
	//	return userKarmaLabel;
	//}

}
