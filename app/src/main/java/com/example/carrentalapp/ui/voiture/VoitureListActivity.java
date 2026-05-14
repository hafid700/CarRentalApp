package com.example.carrentalapp.ui.voiture;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carrentalapp.R;
import com.example.carrentalapp.data.database.AppDatabase;
import com.example.carrentalapp.data.entity.Voiture;

import java.util.List;

public class VoitureListActivity extends AppCompatActivity {

    ListView listView;
    Button addBtn;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voiture_list);

        listView = findViewById(R.id.listView);
        addBtn = findViewById(R.id.addBtn);
        db = AppDatabase.getInstance(this);

        loadData();

        addBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, AddEditVoitureActivity.class));
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, AddEditVoitureActivity.class);
            intent.putExtra("id", (int) id);
            startActivity(intent);
        });
    }

    private void loadData() {
        List<Voiture> list = db.voitureDao().getAllVoitures();
        listView.setAdapter(new VoitureAdapter(this, list));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}