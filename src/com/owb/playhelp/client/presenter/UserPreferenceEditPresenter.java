/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import com.owb.playhelp.client.helper.RPCCall;
import com.owb.playhelp.client.service.LoginServiceAsync;
import com.owb.playhelp.client.service.LoginService;
import com.owb.playhelp.client.service.UserServiceAsync;
import com.owb.playhelp.shared.UserProfileInfo;
import com.owb.playhelp.client.event.user.UserPreferenceUpdateEvent;
import com.owb.playhelp.client.event.user.UserPreferenceEditCancelEvent;

public class UserPreferenceEditPresenter implements Presenter {
	public interface Display {
		Widget asWidget();
		public Image getProfilePictureFrame();
		public HasValue<String> getNameLabel();
		public HasValue<String> getEmailLabel();
		public HasClickHandlers getCancelButton();
		public HasClickHandlers getSaveButton();
		public FileUpload getUploadFile();
		public FormPanel getFormPanel();
	}

	private final SimpleEventBus eventBus;
	private final Display display;

	private UserProfileInfo newUser;
	private UserServiceAsync userService;

	public UserPreferenceEditPresenter(UserProfileInfo currentUser, UserServiceAsync userService, SimpleEventBus eventBus, Display display) {
		this.newUser = currentUser;
		this.userService = userService;
		this.eventBus = eventBus;
		this.display = display;
	}

	public void bind() {
		this.display.getProfilePictureFrame().addClickHandler(new ClickHandler(){
			  public void onClick(ClickEvent event){
				  // browse for picture
			  }
		  });
		this.display.getCancelButton().addClickHandler(new ClickHandler(){
			  public void onClick(ClickEvent event){
				  // cancel operation
				  GWT.log("UserPreferenceEditPresenter:: Firing UserPreferenceEditCancelEvent");
				  eventBus.fireEvent(new UserPreferenceEditCancelEvent());
			  }
		  });
		this.display.getSaveButton().addClickHandler(new ClickHandler(){
			  public void onClick(ClickEvent event){
				  // Upload image
				  // I wonder if I don't have to add other elements here before
				  // submitting so we can find the file with a specific name
				  display.getFormPanel().submit();
				  
				  // Somehow we need to store it in the data base
				  // May be we could create an object that submit the form
				  // and at the same time creates the info in the DB
				  
				  // save data
				  doSave();
			  }
		  });
		this.display.getUploadFile().addAttachHandler(new Handler() {
			public void onAttachOrDetach(AttachEvent event) {
				// Show new picture in the frame.
				// We may want to add a button with an undo to restore the old picture
				// And also something to select an old picture
				//Window.alert("yes: "+display.getUploadFile().getFilename());
				//if(chooser.isAttached()==false && myButton.isEnabled()==true)
				//    myButton.setEnabled(false);
				//else if(chooser.isAttached()==true && myButton.isEnabled()==false)
				//    myButton.setEnabled(true);
				} });
		// Add an event handler to the form.
		/*
		this.display.getFormPanel().addSubmitHandler(new FormPanel.SubmitHandler() {
		    public void onSubmit(FormPanel.SubmitEvent event) {
		    	// we do not need this because the name is created by the server.
		    	// it could be something like user_uniqueId+datestamp
		    // This event is fired just before the form is submitted. We can
			// take this opportunity to perform validation.
			//if (tb.getText().length() == 0) {
			//    Window.alert("The text box must not be empty");
			//    event.cancel();
			//}
		    }
		});*/

		this.display.getFormPanel().addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
		    public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
			// When the form submission is successfully completed, this
			// event is fired. Assuming the service returned a response of type
			// text/html, we can get the result text here (see the FormPanel
			// documentation for further explanation).
			Window.alert(event.getResults());
		    }
		});
	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());

		// Fill the values of the widgets
		if (newUser != null) {
			UserPreferenceEditPresenter.this.display.getNameLabel().setValue(newUser.getName());
			UserPreferenceEditPresenter.this.display.getEmailLabel().setValue(newUser.getEmailAddress());
		}
		
		// Because we're going to add a FileUpload widget, we'll need to set the
	    // form to use the POST method, and multipart MIME encoding.
		UserPreferenceEditPresenter.this.display.getFormPanel().setAction(GWT.getModuleBaseURL()+"upload");
		UserPreferenceEditPresenter.this.display.getFormPanel().setEncoding(FormPanel.ENCODING_MULTIPART);
		UserPreferenceEditPresenter.this.display.getFormPanel().setMethod(FormPanel.METHOD_POST);

		
		bind();
	}
	
	private void doSave() {		
		if (newUser == null){
			Window.alert("Error with loggin...");
			return;
		}
		
		// Copy the new data from the widgets to the new created user before making the call for update
		newUser.setName(display.getNameLabel().getValue().trim());
	    newUser.setEmailAddress(display.getEmailLabel().getValue().trim());

	    new RPCCall<UserProfileInfo>() {
	      @Override
	      protected void callService(AsyncCallback<UserProfileInfo> cb) {
	    	  userService.updateCurrentUserInfo(newUser, cb);
	      }

	      @Override
	      public void onSuccess(UserProfileInfo result) {
	        GWT.log("UserPreferenceEditPresenter: Firing UserPreferenceUpdateEvent");
	        eventBus.fireEvent(new UserPreferenceUpdateEvent(result));
	      }

	      @Override
	      public void onFailure(Throwable caught) {
	        Window.alert("Error retrieving user...");
	      }
	    }.retry(3);
	  }

}
