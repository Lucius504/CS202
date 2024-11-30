package com.cs203.controller;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.cs203.exceptions.ValidationException;
import com.cs203.model.Hotel;
import com.cs203.model.Reservation;
import com.cs203.model.Room;
import com.cs203.model.User;
import com.cs203.util.HibernateUtil;

public class ReservationController {
    public List<Reservation> getAllReservations() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Reservation", Reservation.class).list();
        }
    }

    public List<Reservation> getAllReservationsByHotel(Long hotelId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Reservation r where r.hotel.id = :hotelId", Reservation.class)
                    .setParameter("hotelId", hotelId).list();
        }
    }

    public List<Reservation> getAllReservationsByRoom(Long roomId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Reservation r where r.room.id = :roomId", Reservation.class)
                    .setParameter("roomId", roomId).list();
        }
    }

    public List<Reservation> getReservationsByCustomer(Long customerId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Reservation where customer.id = :customerId", Reservation.class)
                    .setParameter("customerId", customerId)
                    .list();
        }
    }

    public List<Reservation> getReservationsByHotelAndRoom(Long hotelId, Long roomId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session
                    .createQuery("from Reservation r where r.hotel.id = :hotelId and r.room.id = :roomId",
                            Reservation.class)
                    .setParameter("hotelId", hotelId)
                    .setParameter("roomId", roomId)
                    .list();
        }

    }

    public Reservation getReservationById(Long reservationId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Reservation.class, reservationId);
        }
    }

    public Reservation createReservation(
            User user,
            Room room,
            Hotel hotel,
            Date reservationDate,
            Date checkInDate,
            Date checkOutDate) throws ValidationException {
        if (user == null || room == null || reservationDate == null || checkInDate == null || checkOutDate == null)
            throw new ValidationException();

        return this.createReservation(new Reservation(user, room, hotel, reservationDate, checkInDate, checkOutDate));
    }

    public Reservation createReservation(Reservation reservation) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(reservation);
            transaction.commit();
            return reservation;
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
        return null;
    }

    public Reservation updateReservation(
            User user,
            Room room,
            Hotel hotel,
            Date reservationDate,
            Date checkInDate,
            Date checkOutDate) throws ValidationException {
        if (user == null || room == null || reservationDate == null || checkInDate == null || checkOutDate == null)
            throw new ValidationException();

        return this.updateReservation(new Reservation(user, room, hotel, reservationDate, checkInDate, checkOutDate));
    }

    public Reservation updateReservation(Reservation reservation) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(reservation);
            transaction.commit();
            return reservation;
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
        return null;
    }

    public void cancelReservation(Long reservationId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Reservation reservation = session.get(Reservation.class, reservationId);
            if (reservation != null) {
                session.remove(reservation);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
    }

    public boolean checkRoomAvailability(Long roomId, String startDate, String endDate) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Reservation> reservations = session.createQuery(
                    "from Reservation where room.id = :roomId and (startDate <= :endDate and endDate >= :startDate)",
                    Reservation.class)
                    .setParameter("roomId", roomId)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .list();
            return reservations.isEmpty();
        }
    }
}
