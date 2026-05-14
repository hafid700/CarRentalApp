package com.example.carrentalapp.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reservations")
public class Reservation {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int client_id;
    private int voiture_id;

    private String date_debut;
    private String date_fin;

    private double prix_total;

    // ✅ UN SEUL statut
    private String statut; // en_cours, payé, annulee

    public Reservation(int client_id, int voiture_id,
                       String date_debut, String date_fin,
                       double prix_total, String statut) {

        this.client_id = client_id;
        this.voiture_id = voiture_id;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.prix_total = prix_total;
        this.statut = statut;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getClient_id() { return client_id; }
    public int getVoiture_id() { return voiture_id; }

    public String getDate_debut() { return date_debut; }
    public String getDate_fin() { return date_fin; }

    public double getPrix_total() { return prix_total; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}