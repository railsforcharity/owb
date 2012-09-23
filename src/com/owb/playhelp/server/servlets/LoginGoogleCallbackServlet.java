/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.server.servlets;

import java.io.IOException;
import java.security.Principal;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.owb.playhelp.server.utils.AuthenticationProvider;
import com.owb.playhelp.server.LoginHelper;
import com.owb.playhelp.server.domain.UserProfile;

@SuppressWarnings("serial") 
public class LoginGoogleCallbackServlet extends HttpServlet {
  private static Logger log = Logger.getLogger(LoginGoogleCallbackServlet.class
      .getName());

  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    Principal googleUser = request.getUserPrincipal();
    if (googleUser != null) {
      // update or create user
      UserProfile userProfile = new UserProfile(googleUser.getName(),  AuthenticationProvider.GOOGLE);
      userProfile.setName(googleUser.getName());
      UserProfile owbapp = new LoginHelper().doLogin(request.getSession(),userProfile);
  
      log.info("User id:" + owbapp.getId().toString() + " " + request.getUserPrincipal().getName());
    }
    response.sendRedirect(LoginHelper.getApplitionURL(request));
  }
}
