package com.example.carrentalapp.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carrentalapp.R;
import com.example.carrentalapp.ui.auth.LoginActivity;
import com.example.carrentalapp.ui.client.ClientListActivity;
import com.example.carrentalapp.ui.reservation.ReservationListActivity;
import com.example.carrentalapp.ui.voiture.VoitureListActivity;
import com.example.carrentalapp.utils.SessionManager;

public class DashboardActivity extends AppCompatActivity {

    LinearLayout cardVoitures, cardClients, cardReservations;
    ImageView btnLogout;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // ✅ Cast correct : LinearLayout (pas Button)
        cardVoitures    = findViewById(R.id.cardVoitures);
        cardClients     = findViewById(R.id.cardClients);
        cardReservations = findViewById(R.id.cardReservations);

        // ✅ Cast correct : ImageView (pas Button)
        btnLogout = findViewById(R.id.btnLogout);

        session = new SessionManager(this);

        cardVoitures.setOnClickListener(v ->
                startActivity(new Intent(this, VoitureListActivity.class))
        );

        cardClients.setOnClickListener(v ->
                startActivity(new Intent(this, ClientListActivity.class))
        );

        cardReservations.setOnClickListener(v ->
                startActivity(new Intent(this, ReservationListActivity.class))
        );

        btnLogout.setOnClickListener(v -> {
            session.logout();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}