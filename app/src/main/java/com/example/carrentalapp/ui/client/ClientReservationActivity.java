package com.example.carrentalapp.ui.client;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carrentalapp.R;
import com.example.carrentalapp.data.database.AppDatabase;
import com.example.carrentalapp.data.entity.*;
import com.example.carrentalapp.utils.SessionManager;

import java.util.List;

public class ClientReservationActivity extends AppCompatActivity {

    ListView listView;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_reservations);

        listView = findViewById(R.id.listView);
        db = AppDatabase.getInstance(this);

        loadData();
    }

    private void loadData() {

        SessionManager session = new SessionManager(this);
        int userId = session.getUserId();

        User user = db.userDao().getUserById(userId);
        Client client = db.clientDao().getClientByEmail(user.getEmail());

        List<Reservation> list =
                db.reservationDao().getReservationsByClient(client.getId());

        listView.setAdapter(new ClientReservationAdapter(this, list, db));
    }
}