package com.example.carrentalapp.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.carrentalapp.data.entity.Voiture;

import java.util.List;

@Dao
public interface VoitureDao {

    @Insert
    void insert(Voiture voiture);

    @Update
    void update(Voiture voiture);

    @Delete
    void delete(Voiture voiture);

    @Query("SELECT * FROM voitures")
    List<Voiture> getAllVoitures();

    @Query("SELECT * FROM voitures WHERE id = :id")
    Voiture getVoitureById(int id);


}