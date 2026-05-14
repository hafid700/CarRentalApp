package com.example.carrentalapp.ui.voiture;

import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carrentalapp.R;
import com.example.carrentalapp.data.database.AppDatabase;
import com.example.carrentalapp.data.entity.Voiture;

public class AddEditVoitureActivity extends AppCompatActivity {

    EditText marque, modele, annee, prix;
    CheckBox dispo;
    Button saveBtn, deleteBtn;

    AppDatabase db;
    int voitureId = -1;
    Voiture voiture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_voiture);

        marque = findViewById(R.id.marque);
        modele = findViewById(R.id.modele);
        annee = findViewById(R.id.annee);
        prix = findViewById(R.id.prix);
        dispo = findViewById(R.id.dispo);
        saveBtn = findViewById(R.id.saveBtn);
        deleteBtn = findViewById(R.id.deleteBtn);

        db = AppDatabase.getInstance(this);

        voitureId = getIntent().getIntExtra("id", -1);

        if (voitureId != -1) {
            voiture = db.voitureDao().getVoitureById(voitureId);

            if (voiture != null) {
                fillData();
            } else {
                Toast.makeText(this, "Erreur chargement voiture", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        saveBtn.setOnClickListener(v -> save());
        deleteBtn.setOnClickListener(v -> delete());
    }

    private void fillData() {
        marque.setText(voiture.getMarque());
        modele.setText(voiture.getModele());
        annee.setText(String.valueOf(voiture.getAnnee()));
        prix.setText(String.valueOf(voiture.getPrix_jour()));
        dispo.setChecked(voiture.isDisponible());
    }

    private void save() {
        String m = marque.getText().toString().trim();
        String mo = modele.getText().toString().trim();
        String an = annee.getText().toString().trim();
        String pr = prix.getText().toString().trim();

        // 🔴 Vérification
        if (m.isEmpty() || mo.isEmpty() || an.isEmpty() || pr.isEmpty()) {
            Toast.makeText(this, "Remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        int a;
        double p;

        try {
            a = Integer.parseInt(an);
            p = Double.parseDouble(pr);
        } catch (Exception e) {
            Toast.makeText(this, "Année ou prix invalide", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean d = dispo.isChecked();

        if (voitureId == -1) {
            db.voitureDao().insert(new Voiture(m, mo, a, p, d));
        } else {
            voiture.setDisponible(d);
            voiture = new Voiture(m, mo, a, p, d);
            voiture.setId(voitureId);
            db.voitureDao().update(voiture);
        }

        Toast.makeText(this, "Enregistré", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void delete() {
        if (voiture != null) {
            db.voitureDao().delete(voiture);
            Toast.makeText(this, "Supprimé", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}