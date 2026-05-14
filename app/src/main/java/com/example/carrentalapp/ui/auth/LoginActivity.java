package com.example.carrentalapp.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carrentalapp.R;
import com.example.carrentalapp.data.database.AppDatabase;
import com.example.carrentalapp.data.entity.User;
import com.example.carrentalapp.ui.client.ClientHomeActivity;
import com.example.carrentalapp.ui.dashboard.DashboardActivity;
import com.example.carrentalapp.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button loginBtn;
    TextView goRegister;          // ← TextView, plus Button
    AppDatabase db;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email      = findViewById(R.id.email);
        password   = findViewById(R.id.password);
        loginBtn   = findViewById(R.id.loginBtn);
        goRegister = findViewById(R.id.tvSignUp);   // ← nouvel ID

        db      = AppDatabase.getInstance(this);
        session = new SessionManager(this);

        loginBtn.setOnClickListener(v -> login());

        goRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }

    private void login() {
        String mail = email.getText().toString();
        String pass = password.getText().toString();

        if (mail.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = db.userDao().login(mail, pass);

        if (user != null) {

            SessionManager session = new SessionManager(this);
            session.saveUser(user.getId(), user.getRole());

            String role = user.getRole();

            if (role == null) {
                Toast.makeText(this, "Erreur: rôle utilisateur null", Toast.LENGTH_LONG).show();
                return;
            }

            if (role.equals("admin") || role.equals("employe")) {

                startActivity(new Intent(this, DashboardActivity.class));

            } else if (role.equals("client")) {

                startActivity(new Intent(this, ClientHomeActivity.class));

            } else {

                Toast.makeText(this, "Rôle inconnu: " + role, Toast.LENGTH_SHORT).show();
                return;
            }

            finish();

        } else {
            Toast.makeText(this, "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
        }
    }
}