package com.gwn.exs.ba.data.hibernate.dao;

import org.hibernate.Query;

import com.gwn.exs.ba.data.hibernate.entity.User;
import com.gwn.exs.ba.data.shared.ILongId;

public class UserDAO extends GenericHibernateDAO<User, ILongId> {

	@Override
	public User findById(ILongId id, boolean readOnly) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User makePersistant(User entity) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public User findByUsername(String username) {
		Query q = getSession().createQuery("from User u where u.username = :username");
		q.setParameter("username", username);
		User user = (User) q.uniqueResult();
		return user;
	}
}
