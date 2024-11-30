package com.cs203.model;

import jakarta.persistence.*;

@Entity
@Table(name = "hotel_workers")
public class WorkerHotelMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "worker_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "hotel_id", referencedColumnName = "id")
    private Hotel hotel;

    public WorkerHotelMapping() {
    }

    public WorkerHotelMapping(User user, Hotel hotel) {
        this.user = user;
        this.hotel = hotel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getWorker() {
        return user;
    }

    public void setWorker(User user) {
        this.user = user;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}