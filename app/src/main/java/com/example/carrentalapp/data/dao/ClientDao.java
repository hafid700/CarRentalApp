package com.example.carrentalapp.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.carrentalapp.data.entity.Client;
import com.example.carrentalapp.data.entity.Reservation;

import java.util.List;

@Dao
public interface ClientDao {

    @Insert
    void insert(Client client);

    @Update
    void update(Client client);

    @Delete
    void delete(Client client);

    @Query("SELECT * FROM clients")
    List<Client> getAllClients();

    @Query("SELECT * FROM clients WHERE id = :id")
    Client getClientById(int id);

    @Query("SELECT * FROM clients WHERE email = :email LIMIT 1")
    Client getClientByEmail(String email);

    @Query("SELECT * FROM reservations WHERE client_id = :clientId")
    List<Reservation> getReservationsByClient(int clientId);


}