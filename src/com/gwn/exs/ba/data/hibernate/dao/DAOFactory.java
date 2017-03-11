package com.gwn.exs.ba.data.hibernate.dao;

import com.gwn.exs.ba.data.dao.UserDAO;

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
