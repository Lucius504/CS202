package com.cs203.controller;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.cs203.util.HibernateUtil;
import com.cs203.exceptions.ValidationException;
import com.cs203.model.Hotel;

public class HotelController {
    public List<Hotel> getAllHotels() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Hotel", Hotel.class).list();
        }
    }

    public Hotel getHotelById(Long hotelId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Hotel.class, hotelId);
        }
    }

    public Hotel addHotel(String name, String address, String phone) throws ValidationException {
        if (name.isEmpty() || address.isEmpty() || phone.isEmpty())
            throw new ValidationException();

        return addHotel(new Hotel(name, address, phone));
    }

    public Hotel addHotel(Hotel hotel) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(hotel);
            transaction.commit();
            return hotel;
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
        return null;
    }

    public Hotel updateHotel(String name, String address, String phone) throws ValidationException {
        if (name.isEmpty() || address.isEmpty() || phone.isEmpty())
            throw new ValidationException();

        return updateHotel(new Hotel(name, address, phone));
    }

    public Hotel updateHotel(Hotel hotel) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(hotel);
            transaction.commit();
            return hotel;
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
        return null;
    }

    public void deleteHotel(Long hotelId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Hotel hotel = session.get(Hotel.class, hotelId);
            if (hotel != null) {
                session.remove(hotel);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
    }

}
