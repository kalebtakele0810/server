package com.aaupush.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryGenerator {
	/*
	 * SessionFactory must only be generated once in the application life cycle
	 */
	private static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void setSessionFactory(SessionFactory sessionFactory) {
		SessionFactoryGenerator.sessionFactory = sessionFactory;
	}

}
