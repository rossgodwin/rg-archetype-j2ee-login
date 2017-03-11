package com.gwn.exs.ba.data.hibernate.dao;

import org.hibernate.Session;

import com.gwn.exs.ba.data.dao.GenericDAO;
import com.gwn.exs.ba.data.hibernate.HibernateUtil;
import com.gwn.exs.ba.data.shared.ILongId;

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
