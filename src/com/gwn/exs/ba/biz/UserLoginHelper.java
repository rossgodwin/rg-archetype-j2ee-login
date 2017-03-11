package com.gwn.exs.ba.biz;

import javax.security.auth.login.FailedLoginException;

import org.apache.commons.lang3.StringUtils;

import com.gwn.exs.ba.common.UserPrincipal;
import com.gwn.exs.ba.data.hibernate.dao.DAOFactory;
import com.gwn.exs.ba.data.hibernate.entity.User;

public class UserLoginHelper {

	public static boolean validateLogin(String username, String password) throws FailedLoginException {
		User user = DAOFactory.getInstance().getUserDAO().findByUsername(username);
		
		if (user == null) {
			throw new FailedLoginException("Invalid username");
		}
		
		if (StringUtils.isNoneEmpty(password)) {
			if (UserPasswordHelper.checkPassword(password, user.getPassword())) {
				return true;
			} else {
				throw new FailedLoginException("Invalid password");
			}
		}
		
		return false;
	}
	
	public static UserPrincipal getPrincipal(String username) {
		User user = DAOFactory.getInstance().getUserDAO().findByUsername(username);
		return getPrincipal(user);
	}
	
	public static UserPrincipal getPrincipal(User user) {
		UserPrincipal principal = new UserPrincipal(user.getId(), user.getUsername(), user.getRole());
		return principal;
	}
}
