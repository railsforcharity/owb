package com.owb.playhelp.client.view.ngo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.owb.playhelp.client.helper.ClickPoint;
import com.owb.playhelp.client.view.ListReportView;
import com.owb.playhelp.client.presenter.ngo.ShowDetailsNgoPresenter;
import com.google.gwt.user.client.ui.PopupPanel;

public class ShowDetailsNgoView extends PopupPanel implements ShowDetailsNgoPresenter.Display {

	  @UiTemplate("ShowDetailsNgoView.ui.xml")
	  interface Binder extends UiBinder<Widget, ShowDetailsNgoView> {
	  }

	  private static final Binder binder = GWT.create(Binder.class);
	  
	public ShowDetailsNgoView() {
	    super(true);
	    add(binder.createAndBindUi(this));
		//initWidget(uiBinder.createAndBindUi(this));
	}
	
	public ShowDetailsNgoView(ClickPoint location) {
	    super(true);
	    add(binder.createAndBindUi(this));
	    setPopupPosition(location.getLeft(), location.getTop());
	    show();
	}
	
	@UiField Label nameField,descField,addressField,phoneField,emailField,webField;
	@UiField Label membersField,membersReqField,followersField,abuseReportField;
	@UiField ListReportView adminReportField;
	@UiField
	Anchor okBut;

	  @Override
	  public Widget asWidget() {
	    return this;
	  }

	  @Override
	  public void hide() {
	    super.hide();
	  }

	  public void go(HasWidgets container) {
		
	    
	  }

	  @Override
	  public HasClickHandlers getOkBut(){
		  return okBut;
	  }

	  @Override
	  public Label getNameField(){
		  return nameField;
	  }
	  @Override
	  public Label getDescField(){
		  return descField;
	  }
	  @Override
	  public Label getAddressField(){
		  return addressField;
	  }
	  @Override
	  public Label getPhoneField(){
		  return phoneField;
	  }
	  @Override
	  public Label getEmailField(){
		  return emailField;
	  }
	  @Override
	  public Label getWebField(){
		  return webField;
	  }
	  
	  @Override
	  public Label getMembersField(){
		  return membersField;
	  }
	  @Override
	  public Label getMembersReqField(){
		  return membersReqField;
	  }
	  @Override
	  public Label getFollowersField(){
		  return followersField;
	  }
	  @Override
	  public Label getAbuseReportField(){
		  return abuseReportField;
	  }
	  @Override
	  public ListReportView getAdminReportField(){
		  return adminReportField;
	  }


}
