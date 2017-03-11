package com.gwn.exs.ba.data.dao;

import com.gwn.exs.ba.data.hibernate.entity.User;
import com.gwn.exs.ba.data.shared.ILongId;

public interface UserDAO extends GenericDAO<User, ILongId> {

	public User findByUsername(String username);
}
