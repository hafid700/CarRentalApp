package com.example.carrentalapp.ui.auth;

import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carrentalapp.R;
import com.example.carrentalapp.data.database.AppDatabase;
import com.example.carrentalapp.data.entity.Client;
import com.example.carrentalapp.data.entity.User;

public class RegisterActivity extends AppCompatActivity {

    EditText email, password, nom, prenom, telephone;
    Button registerBtn;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        registerBtn = findViewById(R.id.registerBtn);
        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        telephone = findViewById(R.id.telephone);

        db = AppDatabase.getInstance(this);

        registerBtn.setOnClickListener(v -> register());
    }

    private void register() {
        String mail = email.getText().toString();
        String pass = password.getText().toString();
        String n = nom.getText().toString();
        String p = prenom.getText().toString();
        String tel = telephone.getText().toString();

        if (mail.isEmpty() || pass.isEmpty() || n.isEmpty() || p.isEmpty() || tel.isEmpty()) {
            Toast.makeText(this, "Remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        if (db.userDao().getUserByEmail(mail) != null) {
            Toast.makeText(this, "Email déjà utilisé", Toast.LENGTH_SHORT).show();
            return;
        }

        db.userDao().insert(new User(mail, pass, "client"));

        Toast.makeText(this, "Compte créé", Toast.LENGTH_SHORT).show();
        finish();

        db.clientDao().insert(
                new Client(n, p, mail, tel)
        );
    }
}