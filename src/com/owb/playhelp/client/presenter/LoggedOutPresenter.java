/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.event.MainViewEvent;

public class LoggedOutPresenter implements Presenter {
	public interface Display {
		Widget asWidget();
		public HasClickHandlers getButton();
	}

	private final Display display;
	private final SimpleEventBus eventBus;

	public LoggedOutPresenter(SimpleEventBus eventBus, Display display) {
		this.display = display;
		this.eventBus = eventBus;
	}

	public void bind() {
		  this.display.getButton().addClickHandler(new ClickHandler(){
			  public void onClick(ClickEvent event){
				  eventBus.fireEvent(new MainViewEvent());
			  }
		  });
		
	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
		bind();
	}

}
