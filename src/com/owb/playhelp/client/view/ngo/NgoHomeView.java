package com.owb.playhelp.client.view.ngo;

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

import com.owb.playhelp.client.presenter.NgoHomePresenter;

public class NgoHomeView extends Composite implements NgoHomePresenter.Display {

	private static NgoHomeViewUiBinder uiBinder = GWT
			.create(NgoHomeViewUiBinder.class);

	interface NgoHomeViewUiBinder extends UiBinder<Widget, NgoHomeView> {
	}

	public NgoHomeView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public Widget asWidget(){
		return this;
	}

}
