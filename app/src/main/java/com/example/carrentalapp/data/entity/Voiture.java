package com.example.carrentalapp.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "voitures")
public class Voiture {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String marque;
    private String modele;
    private int annee;
    private double prix_jour;
    private boolean disponible;

    public Voiture(String marque, String modele, int annee, double prix_jour, boolean disponible) {
        this.marque = marque;
        this.modele = modele;
        this.annee = annee;
        this.prix_jour = prix_jour;
        this.disponible = disponible;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMarque() { return marque; }
    public String getModele() { return modele; }
    public int getAnnee() { return annee; }
    public double getPrix_jour() { return prix_jour; }
    public boolean isDisponible() { return disponible; }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}