package org.rg.archetype.data.hibernate.dao;

import org.hibernate.Session;
import org.rg.archetype.data.dao.GenericDAO;
import org.rg.archetype.data.hibernate.HibernateUtil;
import org.rg.archetype.data.shared.ILongId;

/**
 * https://developer.jboss.org/wiki/GenericDataAccessObjects
 * 
 * @param <T>
 * @param <ID>
 */
public abstract class GenericHibernateDAO<T, ID extends ILongId> implements GenericDAO<T, ID> {

	protected Session getSession() {
		return HibernateUtil.getSessionFactory().getCurrentSession();
	}
}
