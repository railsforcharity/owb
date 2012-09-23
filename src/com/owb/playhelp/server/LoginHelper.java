package com.owb.playhelp.server;

import java.util.logging.Logger;
import java.util.Date;

import javax.jdo.JDOCanRetryException;
import javax.jdo.JDOException;
import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.jdo.Query;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.owb.playhelp.server.LoginHelper;
import com.owb.playhelp.server.PMFactory;
import com.owb.playhelp.server.PMFactory;
import com.owb.playhelp.server.domain.UserProfile;
import com.owb.playhelp.server.utils.ServletHelper;
import com.owb.playhelp.server.utils.ServletUtils;

public class LoginHelper extends RemoteServiceServlet {
	  private static final long serialVersionUID = 2888983680310646846L;

	  private static Logger logger = Logger.getLogger(LoginHelper.class.getName());
	  private static final int NUM_RETRIES = 5;

	  static public String getApplitionURL(HttpServletRequest request) {

	    if (ServletHelper.isDevelopment(request)) {
	      //return "http://127.0.0.1:8888/Owb.html?gwt.codesvr=127.0.0.1:9997";
	      return "http://127.0.0.1:8888/Owb.html?gwt.codesvr=127.0.0.1:9997";
	    } else {
	      return ServletUtils.getBaseUrl(request);
	    }

	  }
	  
	  static public UserProfile getLoggedUser(HttpSession session, PersistenceManager pm) {
		  boolean isPM = false;
		  
		  if (session == null) return null; // session is not opened
		  
		  String userId = (String) session.getAttribute("userId");
		  if (userId == null) return null; // no user is logged in
		  
		  Long id = Long.parseLong(userId.trim());

		  if (pm == null) {
		    // then create local pm
		    pm = PMFactory.getNonTxnPm();
		    isPM = true;
		  }
		  
		  String query = "select from " + UserProfile.class.getName() + " where id == :userId";
		  Query q = pm.newQuery(query);
		  q.setUnique(true);
		  
		  try{
			  UserProfile user = (UserProfile) q.execute(id);
			  user.setLastActive(new Date());
			  return user;
		  } finally {
			  if (isPM) pm.close();
		  }		  
	  }
	  
	  // Here we check if the user exists in the database and add her/him if not
	  public UserProfile doLogin(HttpSession session, UserProfile userProfile){
		  UserProfile currentUser = UserProfile.findUserProfile(userProfile);
		  UserProfile newUser = null;

		    PersistenceManager pm = PMFactory.getTxnPm();
		    Transaction tx = pm.currentTransaction();
		    
		    try{
		    	for (int i=0; i < NUM_RETRIES; i++){
		            tx = pm.currentTransaction();
		            tx.begin();
		            newUser = (UserProfile) pm.getObjectById(UserProfile.class, currentUser.getId());
		    		newUser.setLastActive(new Date());
		    		newUser.setLastLoginOn(new Date());
		    		try{
		    			tx.commit();
		    			String aux1 = String.valueOf(newUser.getId());
		    			session.setAttribute("userId",String.valueOf(newUser.getId()));
		    			String aux2 = (String) session.getAttribute("userId");
		    			session.setAttribute("loggedin",true);
		    			break;
		    		} catch (JDOCanRetryException e1) {
		    	          if (i == (NUM_RETRIES - 1)) { 
		    	              throw e1;
		    	            }		    			
		    		}
		    	}
		    	
		    } catch (JDOException e) {
		        e.printStackTrace();
		        return null;
		      } finally{
		          if (tx.isActive()) {
		              logger.severe("loginStart transaction rollback.");
		              tx.rollback();
		            }
		            pm.close();
		          }
		  
		  
		  return newUser;
	  }
}
