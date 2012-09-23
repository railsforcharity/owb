package com.owb.playhelp.client.helper;

import com.google.gwt.maps.client.InfoWindow;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.geocode.Geocoder;
import com.google.gwt.maps.client.geocode.LatLngCallback;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.InfoWindowContent; 
import com.google.gwt.user.client.ui.HasWidgets;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;

import com.owb.playhelp.shared.ngo.NgoInfo;

final public class MapHelper {
	
	public final static String MapKEY = "AIzaSyDVrMIiTG45-TVel7LCWCH1i6Cqab12FgE";
	
	private MapWidget map; // = new MapWidget(); 
	private Geocoder geocoder = null;
	private boolean isApiLoaded = false;
	private final HasWidgets container;
	
	public MapHelper(){
		this.container = null;
		Maps.loadMapsApi(MapKEY, "2", false, new Runnable() {
			   public void run() { 
				   //final MapWidget map = new MapWidget(LatLng.newInstance(0, 0), 2); 
				   map = new MapWidget(LatLng.newInstance(0, 0), 2); 
				   map.setSize("100%", "100%");
				   map.addControl(new LargeMapControl()); 
				   map.setDoubleClickZoom(true);
				   map.setDraggable(true);
				   map.setGoogleBarEnabled(true);
			        geocoder = new Geocoder();
			        isApiLoaded = true;
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

			
		}// end try
	    catch (Exception e) {
	        e.printStackTrace();
	        // Window.alert("Map cannot be loaded!!");
	      }
		
	}
	
	public MapHelper(final HasWidgets cont){
		this.container = cont;
		Maps.loadMapsApi(MapKEY, "2", false, new Runnable() {
			   public void run() { 
				   map = new MapWidget(LatLng.newInstance(0, 0), 2); 				   
				   map.setSize("100%", "100%");
				   map.addControl(new LargeMapControl()); 
				   map.setDoubleClickZoom(true);
				   map.setDraggable(true);
				   map.setGoogleBarEnabled(true);
			        //geocoder = new Geocoder();
			        isApiLoaded = true;
			   }
		    });

		try{
			Timer apiLoadedTimer = new Timer() {
				@Override
				public void run() {
					if (isApiLoaded) {
						container.add(map);
						cancel();
					}
					
				}
			};
			apiLoadedTimer.scheduleRepeating(3000);

			
		}// end try
	    catch (Exception e) {
	        e.printStackTrace();
	        // Window.alert("Map cannot be loaded!!");
	      }
        
		
	}
	
	public MapWidget getMap(){
		return map;
	}
	
	public void setMarker(LatLng point){
		InfoWindow info = map.getInfoWindow();
		Marker marker = new Marker(point);
		map.addOverlay(marker);
        //info.open(marker, new InfoWindowContent(""));
		
		/* An example of things we can do with markers ...
		LatLng latlong = LatLng.newInstance(lat, lon);
		   MarkerOptions markerOptions = MarkerOptions.newInstance();
		   markerOptions.setIcon(Icon.newInstance("/img/ship.png"));
		   markerOptions.setTitle(name);  
		   Marker marker = new Marker(latlong, markerOptions);
		   map.addOverlay(marker);*/

	}
	public void setMarker(NgoInfo ngo){
		InfoWindow info = map.getInfoWindow();
		Marker marker = new Marker(LatLng.newInstance(ngo.getLatitude(),ngo.getLongitude()));
		map.addOverlay(marker);
        info.open(marker, new InfoWindowContent(ngo.getName()));
	}

	public LatLng getPoint(final String address) {
		
		final InfoWindow info = map.getInfoWindow();
		LatLng point = null;
		
		geocoder.getLatLng(address, new LatLngCallback() {
	      public void onFailure() {
	    	  //alert(address + " not found"); 
	      }

	      public void onSuccess(LatLng point) {
	      }
	    });
		
		return point;
	  }
	public LatLng getPoint(NgoInfo ngo) {
		if (ngo == null) return null;
		return getPoint(ngo.getAddress());
	}
	public boolean isApiLoaded(){ 
		return this.isApiLoaded;
	}
	
}
