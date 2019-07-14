package com.loan.dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author essakijothi_v
 *
 */
@Repository
public class LoanDao {

	private final Logger logger = LogManager.getLogger(this.getClass().getName());
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession() {
		return getSessionFactory().openSession();
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * @param query
	 * @param params
	 * @param isUnique
	 * @param offset
	 * @param count
	 * @return Object
	 * 
	 * Returns resultset of query
	 * 
	 */
	public Object getFromDb(final String query, final Map<String, ?> params, final boolean isUnique, int offset, int count){
		logger.debug("getFromDb :: query = {}, params = {}, isUnique = {}, offset = {}, count = {}", query, params, isUnique, offset, count);
		Session session = getSession();
		Object returnObj = null;
  		logger.trace("Generating SQL :: getFromDb");
		final Query qry = session.createQuery(query);
		try {
			if(offset != 0 && count != 0){
				qry.setFirstResult(offset);
				qry.setMaxResults(count);
			}
			
  			logger.trace("Setting paramaters :: getFromDb");
			applyParameters(qry, params);

			if (isUnique) {
  				logger.trace("Retriving resultset :: getFromDb");
				final Iterator<?> itr = qry.list().iterator();
				if (itr.hasNext()) {
					returnObj = itr.next();
				}
			} else {
  				logger.trace("Retriving resultset list :: getFromDb");
				returnObj = qry.list();
			}
			
		}  catch (HibernateException e) {
  			logger.error("Hibernate exception in getFromDb", e);
		} catch (Exception e) {
  			logger.error("Exception in getFromDb", e);
		}finally{
			if(session!=null){
				session.flush();
				session.close();
  				logger.trace("Session closed :: getFromDb");
			}
		}
		return returnObj;
	}
	
	/**
	 * @param qry
	 * @param params
	 * 
	 * Applying parameters to the query
	 *  
	 */
	private void applyParameters(final Query qry, final Map<?, ?> params){
		try {
  			logger.debug("Applying parameters");
			if (params != null) {
				final Iterator<?> keyItr = params.keySet().iterator();
				while (keyItr.hasNext()) {
					final String paramName = (String) keyItr.next();
					final Object paramValue = params.get(paramName);
					if (paramValue instanceof String) {
						qry.setString(paramName, (String) paramValue);
					} else if (paramValue instanceof Long) {
						qry.setLong(paramName, ((Long) paramValue).longValue());
					} else if (paramValue instanceof Integer) {
						qry.setInteger(paramName, ((Integer) paramValue).intValue());
					} else if (paramValue instanceof Timestamp) {
						qry.setTimestamp(paramName, (Timestamp) paramValue);
					} else if (paramValue instanceof Date) {
						qry.setDate(paramName, (Date) paramValue);
					} else if (paramValue instanceof Boolean) {
						qry.setBoolean(paramName, ((Boolean) paramValue).booleanValue());
					} else if (paramValue instanceof Float) {
						qry.setFloat(paramName, ((Float) paramValue).floatValue());
					} else if (paramValue instanceof ArrayList) {
						qry.setParameterList(paramName, (Collection<?>) paramValue);
					}
				}
			}
		} catch (IllegalArgumentException ile) {
  			logger.error("Illegal argument", ile);
		}
	}
	
}
