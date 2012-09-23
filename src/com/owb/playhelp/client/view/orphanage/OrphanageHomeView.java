package com.owb.playhelp.client.view.orphanage;

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

import com.owb.playhelp.client.presenter.OrphanageHomePresenter;

public class OrphanageHomeView extends Composite implements OrphanageHomePresenter.Display {

	private static OrphanageHomeViewUiBinder uiBinder = GWT
			.create(OrphanageHomeViewUiBinder.class);

	interface OrphanageHomeViewUiBinder extends
			UiBinder<Widget, OrphanageHomeView> {
	}

	public OrphanageHomeView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public Widget asWidget(){
		return this;
	}

}
