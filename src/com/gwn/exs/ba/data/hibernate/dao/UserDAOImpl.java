package com.gwn.exs.ba.data.hibernate.dao;

import org.hibernate.Query;

import com.gwn.exs.ba.data.dao.UserDAO;
import com.gwn.exs.ba.data.hibernate.entity.User;
import com.gwn.exs.ba.data.shared.ILongId;

public class UserDAOImpl extends GenericHibernateDAO<User, ILongId> implements UserDAO {

	@Override
	public User findByILongId(ILongId id, boolean readOnly) {
		return findById(id.getId(), readOnly);
	}
	
	@Override
	public User findById(Long id, boolean readOnly) {
		Query q = getSession().createQuery("from " + User.class.getSimpleName() + " u where u.id = :id");
		q.setParameter("id", id);
		User r = (User) q.uniqueResult();
		return r;
	}

	@Override
	public User findByUsername(String username) {
		Query q = getSession().createQuery("from " + User.class.getSimpleName() + " u where u.username = :username");
		q.setParameter("username", username);
		User r = (User) q.uniqueResult();
		return r;
	}
}
