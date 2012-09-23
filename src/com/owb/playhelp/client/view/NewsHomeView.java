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

import com.owb.playhelp.client.presenter.NewsHomePresenter;

public class NewsHomeView extends Composite implements NewsHomePresenter.Display {

	private static NewsHomeViewUiBinder uiBinder = GWT
			.create(NewsHomeViewUiBinder.class);

	interface NewsHomeViewUiBinder extends UiBinder<Widget, NewsHomeView> {
	}

	public NewsHomeView() {
		initWidget(uiBinder.createAndBindUi(this));
	}



}
