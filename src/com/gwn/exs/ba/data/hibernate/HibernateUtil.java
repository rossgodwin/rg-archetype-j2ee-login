package com.gwn.exs.ba.data.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
//            return new Configuration().configure("org/gwn/curl/data/hibernate/hibernate.cfg.xml").buildSessionFactory(new StandardServiceRegistryBuilder().build());
        	return new Configuration().configure("com/gwn/exs/ba/data/hibernate/hibernate.cfg.xml").buildSessionFactory();
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static void commit() {
    	getSessionFactory().getCurrentSession().getTransaction().commit();
    	getSessionFactory().getCurrentSession().beginTransaction();
    }
}
