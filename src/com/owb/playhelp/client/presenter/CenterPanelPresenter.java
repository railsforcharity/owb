package com.owb.playhelp.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.owb.playhelp.client.event.ngo.ShowPopupAddNgoEvent;
import com.owb.playhelp.client.event.orphanage.ShowPopupAddOrphanageEvent;
import com.owb.playhelp.client.event.project.ProjectAddEvent;
import com.owb.playhelp.client.presenter.MainHomePresenter.Display;
import com.owb.playhelp.shared.UserProfileInfo;
import com.owb.playhelp.client.helper.ClickPoint;


public class CenterPanelPresenter  implements Presenter {
	public interface Display {
		Widget asWidget();
		HorizontalPanel getBarPanel();
		MenuItem getAddNgoItem();
		MenuItem getAddOrphanageItem();
		//MenuItem getAddProjectField();
	}

	private final SimpleEventBus eventBus;
	private final Display display;

	private UserProfileInfo currentUser;


	public CenterPanelPresenter(UserProfileInfo currentUser,
			SimpleEventBus eventBus, Display display) {
		this.currentUser = currentUser;
		this.eventBus = eventBus;
		this.display = display;
		
		//LatLng cawkerCity = LatLng.newInstance(39.509, -98.434); 
        //this.map = new MapWidget(cawkerCity, 2); 
	}


	public void bind() {
		this.display.getAddNgoItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowPopupAddNgoEvent(new ClickPoint(100,100)));
			}
		});
		this.display.getAddOrphanageItem().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ShowPopupAddOrphanageEvent(new ClickPoint(100,100)));
			}
		});
		/*this.display.getAddProjectField().setCommand(new Command() {
			@Override
			public void execute() {
				eventBus.fireEvent(new ProjectAddEvent());
			}
		});*/
	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		bind();
	}
	
}
