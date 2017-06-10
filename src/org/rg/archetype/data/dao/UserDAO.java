package org.rg.archetype.data.dao;

import org.rg.archetype.data.hibernate.entity.User;
import org.rg.archetype.data.shared.ILongId;

public interface UserDAO extends GenericDAO<User, ILongId> {

	public User findByUsername(String username);
}
