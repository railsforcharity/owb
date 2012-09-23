package com.owb.playhelp.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class LeftPanel extends Composite {

	private static LeftPanelUiBinder uiBinder = GWT
			.create(LeftPanelUiBinder.class);

	interface LeftPanelUiBinder extends UiBinder<Widget, LeftPanel> {
	}

	public LeftPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	  VerticalPanel profilePanel;
	@UiField
	  VerticalPanel testPanel;
	@UiField
	  VerticalPanel menuPanel;

	public VerticalPanel getProfilePanel() {
	  return profilePanel;
	}

	public VerticalPanel getTestPanel() {
	  return testPanel;
	}
	
	public VerticalPanel getMenuPanel() {
		  return menuPanel;
		}

}
