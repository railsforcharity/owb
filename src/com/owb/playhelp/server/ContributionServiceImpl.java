/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.server;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import java.util.logging.Logger;
import javax.jdo.PersistenceManager;
import javax.jdo.JDOCanRetryException;
import javax.jdo.Query;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.owb.playhelp.server.domain.UserProfile;
import com.owb.playhelp.server.domain.Contribution;
import com.owb.playhelp.client.service.ContributionService;
import com.owb.playhelp.shared.ContributionInfo;
import com.owb.playhelp.shared.UserProfileInfo;
import com.owb.playhelp.shared.exceptions.NoUserException;

@SuppressWarnings("serial")
public class ContributionServiceImpl extends RemoteServiceServlet implements ContributionService {

	private static Logger logger = Logger.getLogger(ContributionServiceImpl.class.getName());
	public final static String CHANNEL_ID = "channel_id";
	private static final int NUM_RETRIES = 5;
	
	@Override
	public ContributionInfo addContribution(ContributionInfo contributionInfo){
		PersistenceManager pm = PMFactory.getTxnPm();
		Contribution contribution = null;
		try{
				pm.currentTransaction().begin();
				contribution = new Contribution(contributionInfo);
				pm.makePersistent(contribution);
				try{
					pm.currentTransaction().commit();
				} catch (JDOCanRetryException e1){
					throw e1;
				}
			
		}catch (Exception e) {
			contribution = null;
		}finally{
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
				contribution = null;
			}
			pm.close();
		}
		return contribution.toInfo();
	}
	
	@Override 
	public ArrayList<ContributionInfo> getUserContribution(){
		
		PersistenceManager pm = PMFactory.getTxnPm();
		ArrayList<ContributionInfo> contributions = new ArrayList<ContributionInfo>();
		try{
			UserProfile user = LoginHelper.getLoggedUser(getThreadLocalRequest().getSession(), pm);
		    if (user == null) return null;
		    Query dq = pm.newQuery("select id from "+Contribution.class.getName()+" where contributorId == :id");
		    List<Long> foundIdContributions;
		    foundIdContributions = (List<Long>) dq.execute(user.getUniqueId());
		    Contribution foundContribution = null;
		    for (Long contribId: foundIdContributions){
		    	if (contribId != null){
		    		foundContribution = pm.getObjectById(Contribution.class,contribId);
		    		contributions.add(foundContribution.toInfo());
		    	}// endif
		    } // endfor
		    return contributions;
		} catch (Exception e){
			e.printStackTrace();
		}finally{
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
			}
			pm.close();
		}
		
		return null;
	}
	
}