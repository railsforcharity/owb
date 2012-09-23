/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.owb.playhelp.shared.UserProfileInfo;

public class LoginEvent extends GwtEvent<LoginEventHandler> {
  public static Type<LoginEventHandler> TYPE = new Type<LoginEventHandler>();
  private final UserProfileInfo userInfo;
  
  public LoginEvent(UserProfileInfo userInfo){
	  this.userInfo = userInfo;
  };
  public UserProfileInfo getUser(){
	  return userInfo;
  }
  
  @Override public Type<LoginEventHandler> getAssociatedType(){return TYPE;}
  @Override protected void dispatch(LoginEventHandler handler){handler.onLogin(this);}
  
}
