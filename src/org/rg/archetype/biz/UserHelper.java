package org.rg.archetype.biz;

import org.rg.archetype.data.hibernate.HibernateUtil;
import org.rg.archetype.data.hibernate.entity.User;
import org.rg.archetype.data.shared.UserRole;

public class UserHelper {

	public static User createUser(String username, String plainPassword, String email) {
		String ePwd = UserPasswordHelper.encryptPassword(plainPassword);
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(ePwd);
		user.setRole(UserRole.USER);
		user.setEmail(email);
		
		HibernateUtil.getSessionFactory().getCurrentSession().save(user);
		
		return user;
	}
}
