package com.owb.playhelp.client.view.ngo;

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
import com.owb.playhelp.client.presenter.ngo.NgoMapMarkerInfoPresenter;

public class NgoMapMarkerInfoView extends Composite implements NgoMapMarkerInfoPresenter.Display {

	private static NgoMapMarkerInfoViewUiBinder uiBinder = GWT
			.create(NgoMapMarkerInfoViewUiBinder.class);

	interface NgoMapMarkerInfoViewUiBinder extends
			UiBinder<Widget, NgoMapMarkerInfoView> {
	}

	public NgoMapMarkerInfoView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Label warningMsg, ngoName, ngoDescription, ngoAddress, ngoPhone, ngoEmail;
	@UiField
	HTMLPanel mainPanel;
	@UiField
	Anchor editBut, removeBut, reportBut, joinBut, followBut, confirmBut, fulldescBut;

	  @Override
	public Widget asWidget(){
		return this;
	}

	  @Override
	public HTMLPanel getMainPanel(){
		return mainPanel;
	}
	  @Override
	public Label getNgoName(){
		return ngoName;
	}
	  @Override
	public Label getWarningMsg(){
		return warningMsg;
	}
	  
	  @Override
	public Label getNgoDescription(){
		return ngoDescription;
	}
	  @Override
	public Label getNgoAddress(){
		return ngoAddress;
	}
	  @Override
	public Label getNgoPhone(){
		return ngoPhone;
	}
	  @Override
	public Label getNgoEmail(){
		return ngoEmail;
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
		public Anchor getReportBut(){
		  return reportBut;
		}
		@Override
		public Anchor getJoinBut(){
		  return joinBut;
		}
		@Override
		public Anchor getFollowBut(){
		  return followBut;
		}
		@Override
		public Anchor getConfirmBut(){
		  return confirmBut;
		}
		@Override
		public Anchor getFulldescBut(){
		  return fulldescBut;
		}


}
