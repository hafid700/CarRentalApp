package com.example.carrentalapp.ui.reservation;

import android.content.Context;
import android.view.*;
import android.widget.*;

import com.example.carrentalapp.R;
import com.example.carrentalapp.data.database.AppDatabase;
import com.example.carrentalapp.data.entity.*;

import java.util.List;

public class ReservationAdapter extends BaseAdapter {

    Context context;
    List<Reservation> list;
    AppDatabase db;

    public ReservationAdapter(Context context, List<Reservation> list) {
        this.context = context;
        this.list = list;
        this.db = AppDatabase.getInstance(context);
    }

    @Override
    public int getCount() { return list.size(); }

    @Override
    public Object getItem(int i) { return list.get(i); }

    @Override
    public long getItemId(int i) { return list.get(i).getId(); }


    @Override
    public View getView(int i, View view, ViewGroup parent) {

        if (view == null) {
            view = LayoutInflater.from(context)
                    .inflate(R.layout.item_reservation, parent, false);
        }

        Reservation r = list.get(i);

        TextView title = view.findViewById(R.id.title);
        TextView sub = view.findViewById(R.id.sub);

        Client c = db.clientDao().getClientById(r.getClient_id());
        Voiture v = db.voitureDao().getVoitureById(r.getVoiture_id());

        String clientName = (c != null) ? c.getNom() : "Client inconnu";
        String voitureName = (v != null) ? v.getMarque() : "Voiture supprimée";

        title.setText(clientName + " - " + voitureName);

        sub.setText(
                "Du " + r.getDate_debut() +
                        " au " + r.getDate_fin() +
                        " | " + r.getPrix_total() + " DH"
        );

        return view;
    }
}