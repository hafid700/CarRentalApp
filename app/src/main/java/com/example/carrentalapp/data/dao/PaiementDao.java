package com.example.carrentalapp.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.carrentalapp.data.entity.Paiement;

import java.util.List;

@Dao
public interface PaiementDao {

    @Insert
    void insert(Paiement paiement);

    @Query("SELECT * FROM paiements")
    List<Paiement> getAllPaiements();
}