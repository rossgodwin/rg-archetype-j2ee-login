package org.rg.archetype.data.hibernate.dao;

import org.rg.archetype.data.dao.UserDAO;

public class DAOFactory {

	private static DAOFactory instance;
	
	private UserDAO userDAO;
	
	public static DAOFactory getInstance() {
		if (instance == null) {
			instance = new DAOFactory();
		}
		return instance;
	}
	
	public UserDAO getUserDAO() {
		if (userDAO == null) {
			userDAO = new UserDAOImpl();
		}
		return userDAO;
	}
}
