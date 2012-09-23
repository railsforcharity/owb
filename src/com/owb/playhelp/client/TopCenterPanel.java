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
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.owb.playhelp.client.presenter.TopCenterPanelPresenter;

public class TopCenterPanel extends Composite implements TopCenterPanelPresenter.Display {

	private static TopCenterPanelUiBinder uiBinder = GWT
			.create(TopCenterPanelUiBinder.class);

	interface TopCenterPanelUiBinder extends UiBinder<Widget, TopCenterPanel> {
	}

	@UiField
	Image emailPanel;
	@UiField
	Image newsPanel;
	@UiField
	Image worldPanel;
	@UiField
	Image friendPanel;
	@UiField
	Image blogPanel;
	@UiField
	Image radioPanel;
	@UiField
	Image owbPanel;
	
	public TopCenterPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public Widget asWidget(){
		return this;
	}

	public Image getEmailPanel() {
	    return emailPanel;
	}
	public Image getNewsPanel() {
	    return newsPanel;
	}
	public Image getWorldPanel() {
	    return worldPanel;
	}
	public Image getFriendPanel() {
	    return friendPanel;
	}
	public Image getBlogPanel() {
	    return blogPanel;
	}
	public Image getRadioPanel() {
	    return radioPanel;
	}
	public Image getOwbPanel() {
	    return owbPanel;
	}
}
