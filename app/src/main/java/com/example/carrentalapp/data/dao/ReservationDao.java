package com.example.carrentalapp.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.carrentalapp.data.entity.Reservation;

import java.util.List;

@Dao
public interface ReservationDao {

    @Insert
    void insert(Reservation reservation);

    @Update
    void update(Reservation reservation);

    @Delete
    void delete(Reservation reservation);

    @Query("SELECT * FROM reservations")
    List<Reservation> getAllReservations();

    @Query("SELECT * FROM reservations WHERE id = :id LIMIT 1")
    Reservation getReservationById(int id);

    @Query("SELECT * FROM reservations WHERE client_id = :clientId")
    List<Reservation> getReservationsByClient(int clientId);

    @Query("SELECT * FROM reservations WHERE voiture_id = :id ORDER BY date_fin DESC LIMIT 1")
    Reservation getLastReservationByVoiture(int id);

}