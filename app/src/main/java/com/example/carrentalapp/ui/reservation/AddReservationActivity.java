package com.example.carrentalapp.ui.reservation;

import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carrentalapp.R;
import com.example.carrentalapp.data.database.AppDatabase;
import com.example.carrentalapp.data.entity.Client;
import com.example.carrentalapp.data.entity.Reservation;
import com.example.carrentalapp.data.entity.Voiture;

import java.util.List;

public class AddReservationActivity extends AppCompatActivity {

    Spinner clientSpinner, voitureSpinner;
    EditText dateDebut, dateFin, prix;
    Button saveBtn;

    AppDatabase db;

    List<Client> clients;
    List<Voiture> voitures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reservation);

        clientSpinner = findViewById(R.id.clientSpinner);
        voitureSpinner = findViewById(R.id.voitureSpinner);
        dateDebut = findViewById(R.id.dateDebut);
        dateFin = findViewById(R.id.dateFin);
        prix = findViewById(R.id.prix);
        saveBtn = findViewById(R.id.saveBtn);

        db = AppDatabase.getInstance(this);

        loadData();

        saveBtn.setOnClickListener(v -> save());
    }

    private void loadData() {
        clients = db.clientDao().getAllClients();
        voitures = db.voitureDao().getAllVoitures();

        // 🔥 SANS STREAM (SAFE)
        String[] clientNames = new String[clients.size()];
        for (int i = 0; i < clients.size(); i++) {
            clientNames[i] = clients.get(i).getNom();
        }

        String[] voitureNames = new String[voitures.size()];
        for (int i = 0; i < voitures.size(); i++) {
            voitureNames[i] = voitures.get(i).getMarque();
        }

        ArrayAdapter<String> clientAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                clientNames
        );
        clientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clientSpinner.setAdapter(clientAdapter);

        ArrayAdapter<String> voitureAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                voitureNames
        );
        voitureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        voitureSpinner.setAdapter(voitureAdapter);
    }

    private void save() {

        if (clients.isEmpty() || voitures.isEmpty()) {
            Toast.makeText(this, "Ajouter clients et voitures d'abord", Toast.LENGTH_SHORT).show();
            return;
        }

        String d1 = dateDebut.getText().toString().trim();
        String d2 = dateFin.getText().toString().trim();
        String pr = prix.getText().toString().trim();

        if (d1.isEmpty() || d2.isEmpty() || pr.isEmpty()) {
            Toast.makeText(this, "Remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        double p;

        try {
            p = Double.parseDouble(pr);
        } catch (Exception e) {
            Toast.makeText(this, "Prix invalide", Toast.LENGTH_SHORT).show();
            return;
        }

        int clientId = clients.get(clientSpinner.getSelectedItemPosition()).getId();
        int voitureId = voitures.get(voitureSpinner.getSelectedItemPosition()).getId();

        db.reservationDao().insert(
                new Reservation(clientId, voitureId, d1, d2, p, "en_cours")
        );

        Toast.makeText(this, "Réservation ajoutée", Toast.LENGTH_SHORT).show();

        finish();
    }
}