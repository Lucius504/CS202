package com.cs203.controller;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.cs203.context.LoggedInUser;
import com.cs203.enums.Roles;
import com.cs203.exceptions.ValidationException;
import com.cs203.model.User;
import com.cs203.util.HibernateUtil;

public class UserController {
    public List<User> getAllUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).list();
        }
    }

    public List<User> getUsersByRole(Roles role) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from User where role = :role", User.class)
                    .setParameter("role", role.toString()).list();
        }
    }

    public List<User> getWorkersByHotel(Long hotelId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT w FROM WorkerHotelMapping m JOIN m.user w WHERE m.hotel.id = :hotelId",
                    User.class).setParameter("hotelId", hotelId).list();
        }
    }

    public User getUserById(Long userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(User.class, userId);
        }
    }

    public User createUser(String username, String password, Roles role) throws ValidationException {
        if (username.isEmpty() || password.isEmpty() || role == null)
            throw new ValidationException();

        return createUser(new User(username, password, role));
    }

    public User createUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
        return null;
    }

    public User updateUser(String username, String password, Roles role) throws ValidationException {
        if (username.isEmpty() || password.isEmpty() || role == null)
            throw new ValidationException();

        return updateUser(new User(username, password, role));
    }

    public User updateUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
        return null;
    }

    public void deleteUser(Long userId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, userId);
            if (user != null) {
                session.remove(user);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
    }

    public boolean login(String username, String password) throws ValidationException {
        if (username.isEmpty() || password.isEmpty())
            throw new ValidationException();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.createQuery("from User where username = :username and password = :password", User.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .uniqueResult();

            if (user != null) {
                LoggedInUser.getInstance().setUser(user);
                return true;
            }

            return false;
        }
    }
}
