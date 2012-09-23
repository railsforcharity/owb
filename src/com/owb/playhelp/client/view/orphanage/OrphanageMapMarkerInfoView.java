package com.owb.playhelp.client.view.orphanage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.owb.playhelp.client.presenter.orphanage.OrphanageMapMarkerInfoPresenter;

public class OrphanageMapMarkerInfoView extends Composite implements OrphanageMapMarkerInfoPresenter.Display {

	private static OrphanageMapMarkerInfoViewUiBinder uiBinder = GWT
			.create(OrphanageMapMarkerInfoViewUiBinder.class);

	interface OrphanageMapMarkerInfoViewUiBinder extends
			UiBinder<Widget, OrphanageMapMarkerInfoView> {
	}

	public OrphanageMapMarkerInfoView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Label orphanageName, orphanageDescription, orphanageAddress, orphanagePhone, orphanageEmail;
	@UiField
	HTMLPanel mainPanel;
	@UiField
	Anchor editBut, removeBut, reportBut, followBut, fulldescBut,addprojBut;

	  @Override
	public Widget asWidget(){
		return this;
	}

	  @Override
	public HTMLPanel getMainPanel(){
		return mainPanel;
	}
	  @Override
	public Label getOrphanageName(){
		return orphanageName;
	}
	  @Override
	public Label getOrphanageDescription(){
		return orphanageDescription;
	}
	  @Override
	public Label getOrphanageAddress(){
		return orphanageAddress;
	}
	  @Override
	public Label getOrphanagePhone(){
		return orphanagePhone;
	}
	  @Override
	public Label getOrphanageEmail(){
		return orphanageEmail;
	}

		@Override
		public Anchor getEditBut(){
		  return editBut;
		}
		@Override
		public Anchor getRemoveBut(){
		  return removeBut;
		}
		@Override
		public HasClickHandlers getReportBut(){
		  return reportBut;
		}
		@Override
		public HasClickHandlers getFollowBut(){
		  return followBut;
		}
		@Override
		public HasClickHandlers getFulldescBut(){
		  return fulldescBut;
		}
		@Override
		public HasClickHandlers getAddprojBut(){
		  return addprojBut;
		}


}
