package com.example.carrentalapp.ui.client;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carrentalapp.R;
import com.example.carrentalapp.data.database.AppDatabase;
import com.example.carrentalapp.data.entity.Client;
import com.example.carrentalapp.data.entity.User;
import com.example.carrentalapp.utils.SessionManager;

public class ClientProfileActivity extends AppCompatActivity {

    TextView clientId, clientNom, clientPrenom, clientEmail, clientTelephone;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile);

        clientId = findViewById(R.id.clientId);
        clientNom = findViewById(R.id.clientNom);
        clientPrenom = findViewById(R.id.clientPrenom);
        clientEmail = findViewById(R.id.clientEmail);
        clientTelephone = findViewById(R.id.clientTelephone);
        db = AppDatabase.getInstance(this);

        loadProfile();

        Button editBtn = findViewById(R.id.editBtn);

        editBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, EditProfileActivity.class));
        });

    }

    private void loadProfile() {

        SessionManager session = new SessionManager(this);
        int userId = session.getUserId();

        User user = db.userDao().getUserById(userId);
        Client client = db.clientDao().getClientByEmail(user.getEmail());

        clientId.setText(String.valueOf(client.getId()));
        clientNom.setText(client.getNom());
        clientPrenom.setText(client.getPrenom());
        clientEmail.setText(client.getEmail());
        clientTelephone.setText(client.getTelephone());
    }
}