package com.example.carrentalapp.ui.client;

import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carrentalapp.R;
import com.example.carrentalapp.data.database.AppDatabase;
import com.example.carrentalapp.data.entity.Client;
import com.example.carrentalapp.data.entity.User;
import com.example.carrentalapp.utils.SessionManager;

public class EditProfileActivity extends AppCompatActivity {

    EditText nom, prenom, email, tel;
    Button saveBtn;

    AppDatabase db;
    Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        email = findViewById(R.id.email);
        tel = findViewById(R.id.tel);
        saveBtn = findViewById(R.id.saveBtn);

        db = AppDatabase.getInstance(this);

        loadData();

        saveBtn.setOnClickListener(v -> updateProfile());
    }

    private void loadData() {

        SessionManager session = new SessionManager(this);
        int userId = session.getUserId();

        User user = db.userDao().getUserById(userId);
        client = db.clientDao().getClientByEmail(user.getEmail());

        nom.setText(client.getNom());
        prenom.setText(client.getPrenom());
        email.setText(client.getEmail());
        tel.setText(client.getTelephone());
    }

    private void updateProfile() {

        String n = nom.getText().toString();
        String p = prenom.getText().toString();
        String e = email.getText().toString();
        String t = tel.getText().toString();

        if (n.isEmpty() || p.isEmpty() || e.isEmpty()) {
            Toast.makeText(this, "Champs obligatoires", Toast.LENGTH_SHORT).show();
            return;
        }

        client.setNom(n);
        client.setPrenom(p);
        client.setEmail(e);
        client.setTelephone(t);

        db.clientDao().update(client);

        Toast.makeText(this, "Profil mis à jour", Toast.LENGTH_SHORT).show();
        finish();
    }
}