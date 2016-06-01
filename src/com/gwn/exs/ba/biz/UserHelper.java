package com.gwn.exs.ba.biz;

import com.gwn.exs.ba.data.hibernate.HibernateUtil;
import com.gwn.exs.ba.data.hibernate.entity.User;
import com.gwn.exs.ba.data.shared.UserRole;

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
