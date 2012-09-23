/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.server;

import java.util.logging.Logger;

import javax.servlet.http.HttpSession;
import javax.jdo.JDOCanRetryException;
import javax.jdo.PersistenceManager;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.owb.playhelp.server.domain.UserProfile;
import com.owb.playhelp.client.service.UserService;
import com.owb.playhelp.shared.UserProfileInfo;
import com.owb.playhelp.shared.exceptions.NoUserException;

public class UserServiceImpl extends RemoteServiceServlet implements UserService {
	
	public final static String CHANNEL_ID = "channel_id";
	private static Logger logger = Logger.getLogger(UserServiceImpl.class.getName());
    private static final int NUM_RETRIES = 5;
    
    public UserServiceImpl(){
    	
    }
    
	@Override
	public UserProfileInfo updateCurrentUserInfo(UserProfileInfo newUser){
        
		UserProfile user = UserProfile.findUserProfile(new UserProfile(newUser.getUniqueId()));
		user.reEdit(newUser);
		
	    PersistenceManager pm = PMFactory.getTxnPm();
		
		try {
			for (int i = 0; i < NUM_RETRIES; i++){
				pm.currentTransaction().begin();
				pm.makePersistent(user);
				try {
			          logger.fine("starting commit");
			          pm.currentTransaction().commit();
			          logger.fine("commit was successful");
			          break;
			    } catch (JDOCanRetryException e1) {
			          if (i == (NUM_RETRIES - 1)) {
			            throw e1;
			          }
			        }
			} // end for
		}catch (Exception e) {
		      e.printStackTrace();
		      logger.warning(e.getMessage());
		      newUser = null;
		} finally {
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
				logger.warning("transaction rollback");
				newUser = null;
			}
			pm.close();
		}
		
		return newUser;
	}
	
	
}

