package at.intelligentminds.service.model;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateSupport {

	private static final SessionFactory sessionFactory;
	private static final ServiceRegistry serviceRegistry;

	static {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			Configuration configuration = new Configuration().configure();
			configuration.addAnnotatedClass(User.class);

			StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
			builder.applySettings(configuration.getProperties());
			serviceRegistry = builder.build();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			sessionFactory.openSession();

		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed! " + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public static boolean persist(Object obj) {
		try {
			getSession().saveOrUpdate(obj);
		} catch (HibernateException e) {
			System.out.println("Committing error: " + e);
			return false;
		}
		return true;
	}

	public static void destroy() {
		StandardServiceRegistryBuilder.destroy(serviceRegistry);
	}
}