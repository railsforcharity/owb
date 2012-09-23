/** 
 * Copyright 2011 Miguel Charcos Llorens
 */
package com.owb.playhelp.server;

import com.owb.playhelp.server.utils.cache.*;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.PersistenceManager;

public final class PMFactory {
	@SuppressWarnings("rawtypes")
	private static final java.lang.Class[] classes = 
		new java.lang.Class[] {
		com.owb.playhelp.server.domain.FeedNews.class
	};
	private static final PersistenceManagerFactory pmfInstance =
		JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	private PMFactory(){}
	
	public PersistenceManagerFactory get(){
		return pmfInstance;
	}
	
	@SuppressWarnings("rawtypes")
	public static java.lang.Class[] getClasses(){
		return classes;
	}
	
	public static PersistenceManager getNonTxnPm(){
		PersistenceManager pm = pmfInstance.getPersistenceManager();
		pm.addInstanceLifecycleListener(new CacheMgmtLifecycleListener(),classes);
		return pm;
	}

	public static PersistenceManager getTxnPm(){
		PersistenceManager pm = pmfInstance.getPersistenceManager();
		pm.addInstanceLifecycleListener(new CacheMgmtTxnLifecycleListener(),classes);
		return pm;
	}
	
}
