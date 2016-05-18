package main;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.logging.FileHandler;
import java.util.logging.Level;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;
    static {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.hibernate").setUseParentHandlers(false);

        try {
           sessionFactory = new Configuration().configure()
                    .buildSessionFactory();

        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}