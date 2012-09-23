/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.view.chapter;

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

import com.owb.playhelp.client.presenter.ChapterHomePresenter;

public class ChapterHomeView extends Composite implements ChapterHomePresenter.Display {

	private static ChapterHomeViewUiBinder uiBinder = GWT
			.create(ChapterHomeViewUiBinder.class);

	interface ChapterHomeViewUiBinder extends UiBinder<Widget, ChapterHomeView> {
	}

	public ChapterHomeView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public Widget asWidget(){
		return this;
	}

}
