package com.owb.playhelp.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.jdo.JDOCanRetryException;
import javax.jdo.JDOUserException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.owb.playhelp.client.service.orphanage.OrphanageService;
import com.owb.playhelp.server.domain.UserProfile;
import com.owb.playhelp.server.domain.orphanage.Orphanage;
import com.owb.playhelp.shared.exceptions.NoUserException;
import com.owb.playhelp.shared.orphanage.OrphanageInfo;

public class OrphanageServiceImpl extends RemoteServiceServlet implements OrphanageService {

	private static Logger logger = Logger.getLogger(OrphanageServiceImpl.class.getName());
	public final static String CHANNEL_ID = "channel_id";
	private static final int NUM_RETRIES = 5;

	@Override
	public OrphanageInfo updateOrphanage(OrphanageInfo orphanageInfo){

		Orphanage orphanage = Orphanage.findOrCreateOrphanage(new Orphanage(orphanageInfo));
		orphanage.reEdit(orphanageInfo);
		
	    PersistenceManager pm = PMFactory.getTxnPm();
	    UserProfile user = LoginHelper.getLoggedUser(getThreadLocalRequest().getSession(), pm);
	    if (user == null) return null;
	    pm.close();
	    
	    pm = PMFactory.getTxnPm();
		String userUniqueId = user.getUniqueId();
		
	    if (orphanage.getMembers().size() == 0){
	    	orphanage.addMember(userUniqueId);
	    }
		
	    if (!orphanage.isMember(userUniqueId)) return null;
	    
		try {
			for (int i = 0; i < NUM_RETRIES; i++){
				pm.currentTransaction().begin();
				pm.makePersistent(orphanage);
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
		      orphanageInfo = null;
		} finally {
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
				logger.warning("transaction rollback");
				orphanageInfo = null;
			}
			pm.close();
		}
		
		return orphanageInfo;
		
		// Check if name changed. Name is the key for UniqueId. 
		// If it changed we have to verify that the new name does not overlap with 
		// an existing name. I guess, for simplicity now, we should keep the name unchangeable
		// but we could perform these kind of tests in the future if we want something more sophisticated
		
		//Orphanage addedOrphanage = addOrphanage(orphanageInfo);
		
		// do something to store the information
		// probably creating a Orphanage from OrphanageInfo and
		// store it if it does not exist already
		//return Orphanage.toInfo(addedOrphanage);
	}
	

	@Override
	public ArrayList<OrphanageInfo> getOrphanageList(){
		ArrayList<OrphanageInfo> orphanageList = new ArrayList<OrphanageInfo>();
		
		PersistenceManager pm = PMFactory.getNonTxnPm();
		UserProfile user = LoginHelper.getLoggedUser(getThreadLocalRequest().getSession(), pm);
		String userUniqueId = null;
		if (user != null) userUniqueId = user.getUniqueId();
		pm.close();

		pm = PMFactory.getNonTxnPm();
		try{
			String qstring = null;
			Query dq = null;
			qstring = "";
			
			dq = pm.newQuery("select id from " + Orphanage.class.getName());			
			List<Long> foundIdOrphanages;
			foundIdOrphanages = (List<Long>) dq.execute();
			
			Orphanage foundOrphanage = null;
			OrphanageInfo orphanageInfo = null;
			ArrayList<OrphanageInfo> orphanageArray = new ArrayList<OrphanageInfo>();
			for (Long orphanageId: foundIdOrphanages){
				if (orphanageId != null){
					foundOrphanage = pm.getObjectById(Orphanage.class, orphanageId);
					//pm.deletePersistent(pm.getObjectById(Orphanage.class, orphanageId));
					orphanageInfo = Orphanage.toInfo(foundOrphanage,userUniqueId);
					orphanageArray.add(orphanageInfo);	
				}
			}
			 return orphanageArray;
		}// end try
	    catch (Exception e) {
	        e.printStackTrace();
	      }
	    return null;
	}

	@Override
	public void removeOrphanage(OrphanageInfo orphanageInfo){
		
		if (orphanageInfo.getUniqueId() == null) {
			logger.info("UniqueId is empty");
			return;
		}
		
		PersistenceManager pm = PMFactory.getTxnPm();
		UserProfile user = LoginHelper.getLoggedUser(getThreadLocalRequest().getSession(), pm);
		if (user == null) return;
		String userUniqueId = user.getUniqueId();
		pm.close();
		
		pm = PMFactory.getTxnPm();
	    Transaction tx = null;
	    Orphanage oneResult = null;

	    String uniqueId = orphanageInfo.getUniqueId();

	    Query q = pm.newQuery(Orphanage.class, "uniqueId == :uniqueId");
	    q.setUnique(true);

	    // perform the query and creation under transactional control,
	    // to prevent another process from creating an acct with the same id.
	    try {
	      for (int i = 0; i < NUM_RETRIES; i++) {
	        tx = pm.currentTransaction();
	        tx.begin();
	        oneResult = (Orphanage) q.execute(uniqueId);
	        if (oneResult != null) {
	        	logger.info("Found object with uniqueId: " + uniqueId);
	        	if (oneResult.isMember(userUniqueId)) pm.deletePersistent(oneResult);
	        	else logger.info("UserProfile " + uniqueId + " is not a member");
	        } else {
	        	logger.info("UserProfile " + uniqueId + " does not exist and can't be removed...");
	        }
	        try {
	          tx.commit();
	          break;
	        }
	        catch (JDOCanRetryException e1) {
	          if (i == (NUM_RETRIES - 1)) { 
	            throw e1;
	          }
	        }
	      } // end for
	    } catch (JDOUserException e){
	          logger.info("JDOUserException: Orphanage table is empty"); 	
		        try {
			          tx.commit();
			        }
			        catch (JDOCanRetryException e1) {
			        }
	    } catch (Exception e) {
	      e.printStackTrace();
	    } 
	    finally {
	      if (tx.isActive()) {
	        tx.rollback();
	      }
	      pm.close();
	      q.closeAll();
	    }
	}
	
	private Orphanage addOrphanage(OrphanageInfo orphanageInfo){
		PersistenceManager pm = PMFactory.getTxnPm();
		Orphanage orphanage = null;
		try{
				pm.currentTransaction().begin();
				orphanage = new Orphanage(orphanageInfo);
				pm.makePersistent(orphanage);
				try{
					pm.currentTransaction().commit();
				} catch (JDOCanRetryException e1){
					throw e1;
				}
			
		}catch (Exception e) {
			orphanage = null;
		}finally{
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
				orphanage = null;
			}
			pm.close();
		}
		return orphanage;
	}

	/*
	@Override
	public OrphanageInfo getOrphanage(String id){
		OrphanageInfo fakeOrphanageInfo = new OrphanageInfo();
		return fakeOrphanageInfo;
	}
	
	@Override
	public String deleteOrphanage(String id) throws NoUserException {
		// should delete the orphanage
		return "orphanageDeleted";
	}
	

	public ArrayList<OrphanageItemInfo> getUserOrphanageList(){
		ArrayList<OrphanageItemInfo> orphanageInfoList = new ArrayList<OrphanageItemInfo>();
		PersistenceManager pm = PMFactory.getNonTxnPm();
		try{
		      UserProfile user = LoginHelper.getLoggedUser(getThreadLocalRequest().getSession(), pm);
		      if (user == null)
		        return null;
		      
		      Set<OrphanageItem> orphanages = user.getOrphanages();
		      
		      if (orphanages == null) return null;
		      for (OrphanageItem orphanage:orphanages){
		    	  orphanageInfoList.add(OrphanageItem.toInfo(ngo));
		      }
		}// end try
	    finally {
	        pm.close();
	      }
	    return ngoInfoList;
	}

	*/
		
}
