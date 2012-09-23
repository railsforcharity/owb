package com.owb.playhelp.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.maps.client.MapWidget; 


import com.owb.playhelp.client.presenter.MainHomePresenter;

public class MainHomeView extends Composite implements MainHomePresenter.Display {

	private static MainHomeViewUiBinder uiBinder = GWT
			.create(MainHomeViewUiBinder.class);

	interface MainHomeViewUiBinder extends UiBinder<Widget, MainHomeView> {
	}
    
	@UiField 
    HorizontalPanel mapPanel; 
	
	public MainHomeView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
    
	public Widget asWidget(){
		return this;
	}
	
	public HorizontalPanel getMapPanel(){
		return mapPanel;
	}
}
