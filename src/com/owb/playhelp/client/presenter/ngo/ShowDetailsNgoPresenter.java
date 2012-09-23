/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter.ngo;

import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Anchor;

import com.google.gwt.maps.client.Maps; 
import com.google.gwt.maps.client.geom.LatLng; 
import com.google.gwt.maps.client.geocode.Geocoder;
import com.google.gwt.maps.client.geocode.LatLngCallback;
import com.google.gwt.user.client.Timer;

import com.owb.playhelp.client.event.ngo.NgoUpdateEvent;
import com.owb.playhelp.client.helper.RPCCall;
import com.owb.playhelp.client.service.LoginServiceAsync;
import com.owb.playhelp.client.service.ngo.NgoServiceAsync;
import com.owb.playhelp.client.view.ListReportView;
import com.owb.playhelp.shared.ChapterInfo;
import com.owb.playhelp.shared.orphanage.OrphanageInfo;
import com.owb.playhelp.shared.UserProfileInfo;
import com.owb.playhelp.shared.ngo.NgoInfo;
import com.owb.playhelp.client.presenter.Presenter;
import com.owb.playhelp.client.helper.MapHelper;

public class ShowDetailsNgoPresenter implements Presenter {
	public interface Display {
		Widget asWidget();
	    void hide();
	    HasClickHandlers getOkBut();
	    Label getNameField();
	    Label getDescField();
	    Label getAddressField();
	    Label getPhoneField();
		Label getEmailField();
		Label getWebField();
		Label getMembersField();
	    Label getMembersReqField();
	    Label getFollowersField();
	    Label getAbuseReportField();
	    ListReportView getAdminReportField();
	}

	private final SimpleEventBus eventBus;
	private final Display display;
	private NgoInfo ngo;

	private UserProfileInfo currentUser;
	private final NgoServiceAsync ngoService;
	

	public ShowDetailsNgoPresenter(UserProfileInfo currentUser, NgoServiceAsync ngoService,
			SimpleEventBus eventBus,Display display) {
		this.currentUser = currentUser;
		this.ngoService = ngoService;
		this.eventBus = eventBus;
		this.display = display;
		this.ngo = null;
	}
	public ShowDetailsNgoPresenter(NgoInfo ngo, UserProfileInfo currentUser, NgoServiceAsync ngoService,
			SimpleEventBus eventBus, Display display) {
		this(currentUser, ngoService, eventBus, display);
		this.ngo = ngo;
	}

	public void bind() {
	    this.display.getOkBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	display.hide();
	        }
	      });
	    
	}

	  @Override
	public void go(final HasWidgets container) {
		bind();
		
		if (ngo == null) return;

		this.display.getNameField().setText(this.ngo.getName());
		this.display.getDescField().setText(this.ngo.getDescription());
		this.display.getAddressField().setText(this.ngo.getAddress());
		this.display.getPhoneField().setText(this.ngo.getPhone());
		this.display.getEmailField().setText(this.ngo.getEmail());
		this.display.getWebField().setText(this.ngo.getWebsite());
		
		// Now add the list of members, followers,...
		this.display.getMembersField().setText(this.getMemberListField(ngo));
		this.display.getMembersReqField().setText(this.getMemberReqListField(ngo));
		this.display.getFollowersField().setText(this.getFollowerListField(ngo));
		this.display.getAbuseReportField().setText(this.getAbuseReportListField(ngo));
		this.display.getAdminReportField().setData(ngo.getAdminReportList());
	}
	
	
	private String getMemberListField(NgoInfo ngo){
		
		List<String> oList = ngo.getMemberList(); 
		if (oList == null) return "No members";
		
		String listField = "";
		
		for (String m:oList){
			listField = listField + "|" + m + "|";
		}
		return listField;
		
	}
	private String getMemberReqListField(NgoInfo ngo){
		
		List<String> oList = ngo.getMemberReqList(); 
		if (oList == null) return "No members";
		
		String listField = "";
		
		for (String m:oList){
			listField = listField + "|" + m + "|";
		}
		return listField;
		
	}
	private String getFollowerListField(NgoInfo ngo){
		
		List<String> oList = ngo.getFollowerList(); 
		if (oList == null) return "No members";
		
		String listField = "";
		
		for (String m:oList){
			listField = listField + "|" + m + "|";
		}
		return listField;
		
	}
	  
	private String getAbuseReportListField(NgoInfo ngo){
		
		List<String> oList = ngo.getAbuseReportList(); 
		if (oList == null) return "No members";
		
		String listField = "";
		
		for (String m:oList){
			listField = listField + "|" + m + "|";
		}
		return listField;
		
	}
	  
	private String getNgoReportListField(NgoInfo ngo){
		
		List<String> oList = ngo.getNgoReportList(); 
		if (oList == null) return "No members";
		
		String listField = "";
		
		for (String m:oList){
			listField = listField + "|" + m + "|";
		}
		return listField;
		
	}
	  
}
