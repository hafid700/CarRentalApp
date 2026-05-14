package com.example.carrentalapp.ui.client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.carrentalapp.R;
import com.example.carrentalapp.data.database.AppDatabase;
import com.example.carrentalapp.data.entity.Client;
import com.example.carrentalapp.data.entity.Reservation;
import com.example.carrentalapp.data.entity.Voiture;
import com.example.carrentalapp.utils.PdfGenerator;

import java.util.List;

public class ClientReservationAdapter extends BaseAdapter {

    Context context;
    List<Reservation> list;
    AppDatabase db;

    public ClientReservationAdapter(Context c, List<Reservation> l, AppDatabase db) {
        context = c;
        list = l;
        this.db = db;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return list.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {

        if (view == null) {
            view = LayoutInflater.from(context)
                    .inflate(R.layout.item_client_reservation, parent, false);
        }

        Reservation r = list.get(i);

        TextView tv = view.findViewById(R.id.textReservation);
        Button pdfBtn = view.findViewById(R.id.pdfBtn);

        tv.setText(
                "Voiture ID: " + r.getVoiture_id() +
                        " | Total: " + r.getPrix_total() +
                        " | Statut: " + r.getStatut()
        );

        pdfBtn.setOnClickListener(v -> {

            Client client = db.clientDao().getClientById(r.getClient_id());
            Voiture voiture = db.voitureDao().getVoitureById(r.getVoiture_id());

            PdfGenerator.generate(context, r, client, voiture);
        });

        return view;
    }
}