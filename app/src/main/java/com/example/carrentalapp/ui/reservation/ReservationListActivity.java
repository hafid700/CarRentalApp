package com.example.carrentalapp.ui.reservation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carrentalapp.R;
import com.example.carrentalapp.data.database.AppDatabase;
import com.example.carrentalapp.data.entity.Reservation;

import java.util.List;

public class ReservationListActivity extends AppCompatActivity {

    ListView listView;
    Button addBtn;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_list);

        listView = findViewById(R.id.listView);
        addBtn = findViewById(R.id.addBtn);
        db = AppDatabase.getInstance(this);

        loadData();

        addBtn.setOnClickListener(v ->
                startActivity(new Intent(this, AddReservationActivity.class))
        );
    }

    private void loadData() {

        List<Reservation> list = db.reservationDao().getAllReservations();

        if (list == null) {
            list = new java.util.ArrayList<>();
        }

        listView.setAdapter(new ReservationAdapter(this, list));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}