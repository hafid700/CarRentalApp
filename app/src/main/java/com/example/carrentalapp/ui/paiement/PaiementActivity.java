package com.example.carrentalapp.ui.paiement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.carrentalapp.R;
import com.example.carrentalapp.data.database.AppDatabase;
import com.example.carrentalapp.data.entity.Paiement;
import com.example.carrentalapp.data.entity.Reservation;

public class PaiementActivity extends AppCompatActivity {

    TextView montantTxt;
    Button payerBtn;
    AppDatabase db;

    int reservationId;
    double montant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paiement);

        montantTxt = findViewById(R.id.montantTxt);
        payerBtn = findViewById(R.id.payerBtn);

        db = AppDatabase.getInstance(this);

        reservationId = getIntent().getIntExtra("reservationId", -1);
        montant = getIntent().getDoubleExtra("montant", 0);

        montantTxt.setText("Montant: " + montant + " DH");

        payerBtn.setOnClickListener(v -> payer());

    }

    private void payer() {

        db.paiementDao().insert(
                new Paiement(reservationId, montant, "2026-01-01")
        );

        Reservation r = db.reservationDao().getReservationById(reservationId);

        if (r != null) {
            r.setStatut("payé");
            db.reservationDao().update(r);
        }

        Toast.makeText(this, "Paiement réussi", Toast.LENGTH_LONG).show();

        finish(); // retour écran précédent
    }
}