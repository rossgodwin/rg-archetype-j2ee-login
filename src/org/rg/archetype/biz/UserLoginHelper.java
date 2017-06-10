package org.rg.archetype.biz;

import javax.security.auth.login.FailedLoginException;

import org.apache.commons.lang3.StringUtils;
import org.rg.archetype.common.UserPrincipal;
import org.rg.archetype.data.hibernate.dao.DAOFactory;
import org.rg.archetype.data.hibernate.entity.User;

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
