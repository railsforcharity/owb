/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter.orphanage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
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

import com.owb.playhelp.client.event.orphanage.OrphanageUpdateEvent;
import com.owb.playhelp.client.event.orphanage.ShowPopupAddOrphanageStatusEvent;
import com.owb.playhelp.client.helper.RPCCall;
import com.owb.playhelp.client.helper.ClickPoint;
import com.owb.playhelp.client.service.LoginServiceAsync;
import com.owb.playhelp.client.service.orphanage.OrphanageServiceAsync;
import com.owb.playhelp.shared.ChapterInfo;
import com.owb.playhelp.shared.UserProfileInfo;
import com.owb.playhelp.shared.orphanage.OrphanageInfo;
import com.owb.playhelp.client.presenter.Presenter;
import com.owb.playhelp.client.helper.MapHelper;

public class AddOrphanagePresenter implements Presenter {
	public interface Display {
		Widget asWidget();
	    void hide();
	    HasClickHandlers getSaveBut();
	    HasClickHandlers getCancelBut();
		HasValue<String> getNameField();
		HasValue<String> getDescField();
		HasValue<String> getAddressField();
		HasValue<String> getPhoneField();
		HasValue<String> getEmailField();
		HasValue<String> getWebField();
	}

	private final SimpleEventBus eventBus;
	private final Display display;
	private OrphanageInfo orphanage;

	private UserProfileInfo currentUser;
	private final OrphanageServiceAsync orphanageService;
	
	private String address;
	private double lat,lng;
	private boolean isApiLoaded;
	private final Geocoder geocoder;

	public AddOrphanagePresenter(UserProfileInfo currentUser, OrphanageServiceAsync orphanageService,
			SimpleEventBus eventBus, Geocoder geocoder, Display display) {
		this.currentUser = currentUser;
		this.orphanageService = orphanageService;
		this.eventBus = eventBus;
		this.geocoder = geocoder;
		this.display = display;
		this.orphanage = null;
	}
	public AddOrphanagePresenter(OrphanageInfo orphanage, UserProfileInfo currentUser, OrphanageServiceAsync orphanageService,
			SimpleEventBus eventBus, Geocoder geocoder, Display display) {
		this(currentUser, orphanageService, eventBus, geocoder, display);
		this.orphanage = orphanage;
	}

	public void bind() {
	    this.display.getSaveBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	updateOrphanage();
	        	if (orphanage.getStandard() == null){
	        		display.hide();
	        		
	        		// We activate member so we can edit the status when we call the event
	        		orphanage.activateMember();
	        		
	        		GWT.log("OrphanageAddPresenter: Firing ShowPopupAddOrphanageStatusEvent");
	    	        eventBus.fireEvent(new ShowPopupAddOrphanageStatusEvent(new ClickPoint(100,100),orphanage)); 
	    	        return;
	        	}
	        	doSave();
	        }
	      });
	    this.display.getCancelBut().addClickHandler(new ClickHandler() {
	        public void onClick(ClickEvent event) {
	          display.hide();
	        }
	      });
	    
	}

	  @Override
	public void go(final HasWidgets container) {
		bind();
		
		if (orphanage == null) return;

		this.display.getNameField().setValue(this.orphanage.getName());
		this.display.getDescField().setValue(this.orphanage.getDescription());
		this.display.getAddressField().setValue(this.orphanage.getAddress());
		this.display.getPhoneField().setValue(this.orphanage.getPhone());
		this.display.getEmailField().setValue(this.orphanage.getEmail());
		this.display.getWebField().setValue(this.orphanage.getWebsite());
	}
	  
	  private void updateOrphanage() {
		  
		  double lat = -1.0;
		  double lng = -1.0;
		  
          if (orphanage == null){
			  orphanage = new OrphanageInfo(display.getNameField().getValue().trim(),
					  	display.getDescField().getValue().trim(),
					  	display.getAddressField().getValue().trim(), lat, lng, 
					  	display.getPhoneField().getValue().trim(),
					  	display.getEmailField().getValue().trim(),
					  	display.getWebField().getValue().trim());
          } else {
        	  orphanage.setDescription(display.getDescField().getValue().trim());
        	  orphanage.setAddress(display.getAddressField().getValue().trim(), lat, lng);
        	  orphanage.setPhone(display.getPhoneField().getValue().trim());
        	  orphanage.setEmail(display.getEmailField().getValue().trim());
        	  orphanage.setWebsite(display.getWebField().getValue().trim());
          }
          
	  }
	  
	  private void doSave(){
	    new RPCCall<OrphanageInfo>() {
	      @Override
	      protected void callService(AsyncCallback<OrphanageInfo> cb) {
	    	  orphanageService.updateOrphanage(orphanage, cb);
	      }

	      @Override
	      public void onSuccess(OrphanageInfo result) {
	    	  display.hide();
	        GWT.log("OrphanageAddPresenter: Firing OrphanageUpdateEvent");
	        eventBus.fireEvent(new OrphanageUpdateEvent(result)); 
	      }

	      @Override
	      public void onFailure(Throwable caught) {
	        Window.alert("Error retrieving project...");
	      }
	    }.retry(3);
	  }
	  
	  // Some how dhit does not work
	  private void SolveAddress(){
	        geocoder.getLatLng(address, new LatLngCallback() {
	  	      public void onFailure() {
	  	    	  Window.alert(address + " cannot be resolved"); 
	  	      }

	  	      public void onSuccess(LatLng point) {
	  	    	lat = point.getLatitude();
	  	    	lng = point.getLongitude();
	  	    	isApiLoaded = true;
	  	    	Window.alert("Latitude: "+lat+", Longitude: "+lng);
	  	      }
	  	    });

			try{
				Timer apiLoadedTimer = new Timer() {
					@Override
					public void run() {
						if (isApiLoaded) {
							cancel();
						}
						
					}
				};
				apiLoadedTimer.scheduleRepeating(3000);
				Window.alert("Time out!!");
			}// end try
		    catch (Exception e) {
		        e.printStackTrace();
		        Window.alert("Time out: "+address + " cannot be resolved"); 
		      } 
	  }
	  private void SolveAddress2(){
		  isApiLoaded = false;
			Maps.loadMapsApi(MapHelper.MapKEY, "2", false, new Runnable() {
				   public void run() { 
					   Geocoder geocoder = new Geocoder();
					   //this.lat = map.getPoint(this).getLatitude();
					   //this.lng = map.getPoint(this).getLongitude();
				        //isApiLoaded = true;
				        geocoder.getLatLng(address, new LatLngCallback() {
				  	      public void onFailure() {
				  	    	  Window.alert(address + " cannot be resolved"); 
				  	      }

				  	      public void onSuccess(LatLng point) {
				  	    	lat = point.getLatitude();
				  	    	lng = point.getLongitude();
				  	    	isApiLoaded = true;
				  	    	Window.alert("Latitude: "+lat+", Longitude: "+lng);
				  	      }
				  	    });
				   }
			    });

			try{
				Timer apiLoadedTimer = new Timer() {
					@Override
					public void run() {
						if (isApiLoaded) {
							cancel();
						}
						
					}
				};
				apiLoadedTimer.scheduleRepeating(3000);
				Window.alert("Time out!!");
			}// end try
		    catch (Exception e) {
		        e.printStackTrace();
		        Window.alert("Time out: "+address + " cannot be resolved"); 
		      } 
	  }

}
