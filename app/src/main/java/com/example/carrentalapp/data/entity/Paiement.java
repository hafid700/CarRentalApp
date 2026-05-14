package com.example.carrentalapp.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "paiements")
public class Paiement {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int reservation_id;
    private double montant;
    private String date_paiement;

    public Paiement(int reservation_id, double montant, String date_paiement) {
        this.reservation_id = reservation_id;
        this.montant = montant;
        this.date_paiement = date_paiement;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getReservation_id() { return reservation_id; }
    public double getMontant() { return montant; }
    public String getDate_paiement() { return date_paiement; }
}