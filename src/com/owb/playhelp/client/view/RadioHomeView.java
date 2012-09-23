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
import com.owb.playhelp.client.presenter.RadioHomePresenter;

public class RadioHomeView extends Composite implements RadioHomePresenter.Display {

	private static RadioHomeViewUiBinder uiBinder = GWT
			.create(RadioHomeViewUiBinder.class);

	interface RadioHomeViewUiBinder extends UiBinder<Widget, RadioHomeView> {
	}

	public RadioHomeView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public RadioHomeView(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
