package com.cs203.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.cs203.model.*;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Hotel.class)
                    .addAnnotatedClass(Reservation.class)
                    .addAnnotatedClass(Room.class)
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(WorkerHotelMapping.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
