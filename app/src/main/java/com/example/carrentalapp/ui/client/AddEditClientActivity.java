package com.example.carrentalapp.ui.client;

import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carrentalapp.R;
import com.example.carrentalapp.data.database.AppDatabase;
import com.example.carrentalapp.data.entity.Client;

public class AddEditClientActivity extends AppCompatActivity {

    EditText nom, prenom, email, telephone;
    Button saveBtn, deleteBtn;

    AppDatabase db;
    int clientId = -1;
    Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_client);

        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        email = findViewById(R.id.email);
        telephone = findViewById(R.id.telephone);
        saveBtn = findViewById(R.id.saveBtn);
        deleteBtn = findViewById(R.id.deleteBtn);

        db = AppDatabase.getInstance(this);

        clientId = getIntent().getIntExtra("id", -1);

        if (clientId != -1) {
            client = db.clientDao().getAllClients()
                    .stream()
                    .filter(c -> c.getId() == clientId)
                    .findFirst()
                    .orElse(null);

            if (client != null) fillData();
        }

        saveBtn.setOnClickListener(v -> save());
        deleteBtn.setOnClickListener(v -> delete());
    }

    private void fillData() {
        nom.setText(client.getNom());
        prenom.setText(client.getPrenom());
        email.setText(client.getEmail());
        telephone.setText(client.getTelephone());
    }

    private void save() {
        String n = nom.getText().toString().trim();
        String p = prenom.getText().toString().trim();
        String e = email.getText().toString().trim();
        String t = telephone.getText().toString().trim();

        if (n.isEmpty() || p.isEmpty() || e.isEmpty() || t.isEmpty()) {
            Toast.makeText(this, "Remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        if (clientId == -1) {
            db.clientDao().insert(new Client(n, p, e, t));
        } else {
            Client updated = new Client(n, p, e, t);
            updated.setId(clientId);
            db.clientDao().update(updated);
        }

        finish();
    }

    private void delete() {
        if (client != null) {
            db.clientDao().delete(client);
        }
        finish();
    }
}