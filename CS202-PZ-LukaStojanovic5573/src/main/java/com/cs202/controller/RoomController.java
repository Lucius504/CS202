package com.cs203.controller;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.cs203.util.HibernateUtil;
import com.cs203.exceptions.ValidationException;
import com.cs203.model.Hotel;
import com.cs203.model.Room;

public class RoomController {
    public List<Room> getAllRooms() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Room", Room.class).list();
        }
    }

    public List<Room> getRoomsByHotel(Long hotelId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Room r where r.hotel.id = :hotelId", Room.class)
                    .setParameter("hotelId", hotelId).list();
        }
    }

    public Room getRoomById(Long roomId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Room.class, roomId);
        }
    }

    public Room addRoom(int roomNumber, String roomType, double price, boolean availabilityStatus, Hotel hotel)
            throws ValidationException {
        if (roomNumber <= 0 || roomType.isEmpty() || price <= 0 || hotel == null)
            throw new ValidationException();

        return addRoom(new Room(roomNumber, roomType, price, availabilityStatus, hotel));
    }

    public Room addRoom(Room room) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(room);
            transaction.commit();
            return room;
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
        return null;
    }

    public Room updateRoom(int roomNumber, String roomType, double price, boolean availabilityStatus, Hotel hotel)
            throws ValidationException {
        if (roomNumber <= 0 || roomType.isEmpty() || price <= 0 || hotel == null)
            throw new ValidationException();

        return updateRoom(new Room(roomNumber, roomType, price, availabilityStatus, hotel));
    }

    public Room updateRoom(Room room) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(room);
            transaction.commit();
            return room;
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
        return null;
    }

    public void deleteRoom(Long roomId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Room room = session.get(Room.class, roomId);
            if (room != null) {
                session.remove(room);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
    }
}
