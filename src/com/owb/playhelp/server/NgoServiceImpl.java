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
import com.owb.playhelp.client.service.ngo.NgoService;
import com.owb.playhelp.server.domain.ConfirmationBadge;
import com.owb.playhelp.server.domain.UserProfile;
import com.owb.playhelp.server.domain.ngo.Ngo;
import com.owb.playhelp.server.domain.ngo.NgoItem;
import com.owb.playhelp.shared.exceptions.NoUserException;
import com.owb.playhelp.shared.ngo.NgoInfo;
import com.owb.playhelp.shared.ngo.NgoItemInfo;

public class NgoServiceImpl extends RemoteServiceServlet implements NgoService {

	private static Logger logger = Logger.getLogger(NgoServiceImpl.class.getName());
	public final static String CHANNEL_ID = "channel_id";
	private static final int NUM_RETRIES = 5;
  
	@Override
	public NgoInfo requestMemberNgo(NgoInfo ngoInfo){

		Ngo ngo = Ngo.findOrCreateNgo(new Ngo(ngoInfo));
		
	    PersistenceManager pm = PMFactory.getTxnPm();
	    UserProfile user = LoginHelper.getLoggedUser(getThreadLocalRequest().getSession(), pm);
	    
        
	    pm.close();
	    
	    pm = PMFactory.getTxnPm();
		String userUniqueId = user.getUniqueId();
		
	    if (ngo.getMembers().size() == 0){
			ngo.addMember(userUniqueId);
	    }
		
	    if (ngo.isMember(userUniqueId)) return null;
	    
	    ngo.requestMember(userUniqueId);
	    
		try {
			for (int i = 0; i < NUM_RETRIES; i++){
				pm.currentTransaction().begin();
				pm.makePersistent(ngo);
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
		      ngoInfo = null;
		} finally {
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
				logger.warning("transaction rollback");
				ngoInfo = null;
			}
			pm.close();
		}
		
		return ngoInfo;
		
		// Check if name changed. Name is the key for UniqueId. 
		// If it changed we have to verify that the new name does not overlap with 
		// an existing name. I guess, for simplicity now, we should keep the name unchangeable
		// but we could perform these kind of tests in the future if we want something more sophisticated
		
		//Ngo addedNgo = addNgo(ngoInfo);
		
		// do something to store the information
		// probably creating a Ngo from NgoInfo and
		// store it if it does not exist already
		//return Ngo.toInfo(addedNgo);
	}
	
	
	@Override
	public NgoInfo updateNgo(NgoInfo ngoInfo){

		Ngo ngo = Ngo.findOrCreateNgo(new Ngo(ngoInfo));
		if (ngo == null) return ngoInfo;
		ngo.reEdit(ngoInfo);
		
	    PersistenceManager pm = PMFactory.getTxnPm();
	    UserProfile user = LoginHelper.getLoggedUser(getThreadLocalRequest().getSession(), pm);
	    if (user == null) return null;
	    pm.close();
	    
	    pm = PMFactory.getTxnPm();
		String userUniqueId = user.getUniqueId();
		
	    if (ngo.getMembers().size() == 0){
			ngo.addMember(userUniqueId);
	    }
		
	    if (!ngo.isMember(userUniqueId)) return null;
	    
		try {
			for (int i = 0; i < NUM_RETRIES; i++){
				pm.currentTransaction().begin();
				pm.makePersistent(ngo);
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
		      ngoInfo = null;
		} finally {
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
				logger.warning("transaction rollback");
				ngoInfo = null;
			}
			pm.close();
		}
		
		return ngoInfo;
		
		// Check if name changed. Name is the key for UniqueId. 
		// If it changed we have to verify that the new name does not overlap with 
		// an existing name. I guess, for simplicity now, we should keep the name unchangeable
		// but we could perform these kind of tests in the future if we want something more sophisticated
		
		//Ngo addedNgo = addNgo(ngoInfo);
		
		// do something to store the information
		// probably creating a Ngo from NgoInfo and
		// store it if it does not exist already
		//return Ngo.toInfo(addedNgo);
	}
	
    @Override
    public NgoInfo reportAbuseNgo(NgoInfo ngoInfo, String report){

		Ngo ngo = Ngo.findOrCreateNgo(new Ngo(ngoInfo));
		
	    PersistenceManager pm = PMFactory.getTxnPm();
	    UserProfile user = LoginHelper.getLoggedUser(getThreadLocalRequest().getSession(), pm);
	    if (user == null) return null;
	    pm.close();
	    
	    pm = PMFactory.getTxnPm();
		String userUniqueId = user.getUniqueId();
		
		// Now update ConfirmationBadge
		ConfirmationBadge confB = ngo.getConfirmationBadge();
		confB.addAbuse(userUniqueId, report);
	    
		try {
			for (int i = 0; i < NUM_RETRIES; i++){
				pm.currentTransaction().begin();
				pm.makePersistent(confB);
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
		      ngoInfo = null;
		} finally {
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
				logger.warning("transaction rollback");
				ngoInfo = null;
			}
			pm.close();
		}
		
		return ngoInfo;
		
		// Check if name changed. Name is the key for UniqueId. 
		// If it changed we have to verify that the new name does not overlap with 
		// an existing name. I guess, for simplicity now, we should keep the name unchangeable
		// but we could perform these kind of tests in the future if we want something more sophisticated
		
		//Ngo addedNgo = addNgo(ngoInfo);
		
		// do something to store the information
		// probably creating a Ngo from NgoInfo and
		// store it if it does not exist already
		//return Ngo.toInfo(addedNgo);
	}
	
    @Override
    public NgoInfo confirmNgo(NgoInfo ngoInfo){

		Ngo ngo = Ngo.findOrCreateNgo(new Ngo(ngoInfo));
		
	    PersistenceManager pm = PMFactory.getTxnPm();
	    UserProfile user = LoginHelper.getLoggedUser(getThreadLocalRequest().getSession(), pm);
		//ngo.addConfirmation(user);
	    if (user == null) return null;
	    pm.close();
	    
	    pm = PMFactory.getTxnPm();
		//String userUniqueId = user.getUniqueId();
		
		// Now update ConfirmationBadge
		ConfirmationBadge confB = ngo.getConfirmationBadge();
		confB.addConfirmation(user);
				
		try {
			for (int i = 0; i < NUM_RETRIES; i++){
				pm.currentTransaction().begin();
				pm.makePersistent(confB);
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
		      ngoInfo = null;
		} finally {
			if (pm.currentTransaction().isActive()){
				pm.currentTransaction().rollback();
				logger.warning("transaction rollback");
				ngoInfo = null;
			}
			pm.close();
		}
			    
        // Update the ngoInfo that will be returned
        if (ngoInfo != null) ngoInfo = Ngo.toInfo(ngo,user.getUniqueId());
		return ngoInfo;
		
		// Check if name changed. Name is the key for UniqueId. 
		// If it changed we have to verify that the new name does not overlap with 
		// an existing name. I guess, for simplicity now, we should keep the name unchangeable
		// but we could perform these kind of tests in the future if we want something more sophisticated
		
		//Ngo addedNgo = addNgo(ngoInfo);
		
		// do something to store the information
		// probably creating a Ngo from NgoInfo and
		// store it if it does not exist already
		//return Ngo.toInfo(addedNgo);
	}
    
	@Override
	public ArrayList<NgoInfo> getNgoList(){
		ArrayList<NgoInfo> ngoList = new ArrayList<NgoInfo>();
		
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
			
			dq = pm.newQuery("select id from " + Ngo.class.getName());			
			List<Long> foundIdNgos;
			foundIdNgos = (List<Long>) dq.execute();
			
			Ngo foundNgo = null;
			NgoInfo ngoInfo = null;
			ArrayList<NgoInfo> ngoArray = new ArrayList<NgoInfo>();
			for (Long ngoId: foundIdNgos){
				if (ngoId != null){
					foundNgo = pm.getObjectById(Ngo.class, ngoId);
					//pm.deletePersistent(pm.getObjectById(Ngo.class, ngoId));
					ngoInfo = Ngo.toInfo(foundNgo,userUniqueId);
					if (user != null){
						if (ngoInfo.getConfirmed() || user.isAdmin() || foundNgo.isMember(userUniqueId)){
							ngoArray.add(ngoInfo);
						}
					} else {
						if (ngoInfo.getConfirmed()){
							ngoArray.add(ngoInfo);
						}
					}
											
				}
			}
			 return ngoArray;
		}// end try
	    catch (Exception e) {
	        e.printStackTrace();
	      }
	    return null;
	}

	@Override
	public void removeNgo(NgoInfo ngoInfo){
		
		if (ngoInfo.getUniqueId() == null) {
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
	    Ngo oneResult = null;

	    String uniqueId = ngoInfo.getUniqueId();

	    Query q = pm.newQuery(Ngo.class, "uniqueId == :uniqueId");
	    q.setUnique(true);

	    // perform the query and creation under transactional control,
	    // to prevent another process from creating an acct with the same id.
	    try {
	      for (int i = 0; i < NUM_RETRIES; i++) {
	        tx = pm.currentTransaction();
	        tx.begin();
	        oneResult = (Ngo) q.execute(uniqueId);
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
	          logger.info("JDOUserException: Ngo table is empty"); 	
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
	
	private Ngo addNgo(NgoInfo ngoInfo){
		PersistenceManager pm = PMFactory.getTxnPm();
		Ngo ngo = null;
		try{
				pm.currentTransaction().begin();
				ngo = new Ngo(ngoInfo);
				pm.makePersistent(ngo);
				try{
					pm.currentTransaction().commit();
				} catch (JDOCanRetryException e1){
					throw e1;
				}
			
		}catch (Exception e) {
			ngo = null;
		}finally{
			if (pm.currentTransaction().isActive()) {
				pm.currentTransaction().rollback();
				ngo = null;
			}
			pm.close();
		}
		return ngo;
	}

	/*
	@Override
	public NgoInfo getNgo(String id){
		NgoInfo fakeNgoInfo = new NgoInfo();
		return fakeNgoInfo;
	}
	
	@Override
	public String deleteNgo(String id) throws NoUserException {
		// should delete the ngo
		return "ngoDeleted";
	}
	

	public ArrayList<NgoItemInfo> getUserNgoList(){
		ArrayList<NgoItemInfo> ngoInfoList = new ArrayList<NgoItemInfo>();
		PersistenceManager pm = PMFactory.getNonTxnPm();
		try{
		      UserProfile user = LoginHelper.getLoggedUser(getThreadLocalRequest().getSession(), pm);
		      if (user == null)
		        return null;
		      
		      Set<NgoItem> ngos = user.getNgos();
		      
		      if (ngos == null) return null;
		      for (NgoItem ngo:ngos){
		    	  ngoInfoList.add(NgoItem.toInfo(ngo));
		      }
		}// end try
	    finally {
	        pm.close();
	      }
	    return ngoInfoList;
	}

	*/
		
}
