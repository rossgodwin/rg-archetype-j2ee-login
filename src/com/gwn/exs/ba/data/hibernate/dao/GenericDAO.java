package com.gwn.exs.ba.data.hibernate.dao;

import com.gwn.exs.ba.data.shared.ILongId;

/**
 * https://developer.jboss.org/wiki/GenericDataAccessObjects
 *
 * @param <T>
 * @param <ID>
 */
public interface GenericDAO<T, ID extends ILongId> {

	T findById(ID id, boolean readOnly);
	
	T makePersistant(T entity);
}
