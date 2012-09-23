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
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.presenter.CenterPanelPresenter;


public class CenterPanel extends Composite implements CenterPanelPresenter.Display {

	private static CenterPanelUiBinder uiBinder = GWT
			.create(CenterPanelUiBinder.class);

	interface CenterPanelUiBinder extends UiBinder<Widget, CenterPanel> {
	}

	public CenterPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public Widget asWidget(){
		return this;
	}

	@UiField
	  HorizontalPanel barPanel;
	
	@UiField
	MenuItem ngoItem;
	@UiField
	MenuItem addNgoItem;
	@UiField
	MenuItem orphanageItem;
	@UiField
	MenuItem addOrphanageItem;
	//@UiField
	//MenuItem addProjectField;
	@UiField
	MenuItem friendItem;
	@UiField
	MenuItem avatarItem;
	@UiField
	MenuItem mediaItem;
	@UiField
	MenuItem filterItem;
	@UiField
	MenuItem shareItem;


	public HorizontalPanel getBarPanel() {
		  return barPanel;
		}
	@Override
	public MenuItem getAddNgoItem() {
		  return addNgoItem;
		}
	@Override
	public MenuItem getAddOrphanageItem() {
		  return addOrphanageItem;
		}
	/*public MenuItem getAddProjectField() {
		  return addProjectField;
		}*/
}
