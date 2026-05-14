package com.example.carrentalapp.ui.client;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carrentalapp.R;
import com.example.carrentalapp.data.database.AppDatabase;
import com.example.carrentalapp.data.entity.Client;

import java.util.List;

public class ClientListActivity extends AppCompatActivity {

    ListView listView;
    Button addBtn;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);

        listView = findViewById(R.id.listView);
        addBtn = findViewById(R.id.addBtn);
        db = AppDatabase.getInstance(this);

        loadData();

        addBtn.setOnClickListener(v ->
                startActivity(new Intent(this, AddEditClientActivity.class))
        );

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, AddEditClientActivity.class);
            intent.putExtra("id", (int) id);
            startActivity(intent);
        });
    }

    private void loadData() {
        List<Client> list = db.clientDao().getAllClients();
        listView.setAdapter(new ClientAdapter(this, list));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}