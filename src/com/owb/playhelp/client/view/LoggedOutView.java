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
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.presenter.LoggedOutPresenter;

public class LoggedOutView extends Composite implements LoggedOutPresenter.Display {

	private static LoggedOutViewUiBinder uiBinder = GWT
			.create(LoggedOutViewUiBinder.class);

	interface LoggedOutViewUiBinder extends UiBinder<Widget, LoggedOutView> {
	}

	public LoggedOutView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Button button;

	public LoggedOutView(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

    @Override
    public HasClickHandlers getButton(){
    	return button;
    }

}
