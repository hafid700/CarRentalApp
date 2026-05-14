package com.example.carrentalapp.ui.voiture;

import android.content.Context;
import android.view.*;
import android.widget.*;

import com.example.carrentalapp.R;
import com.example.carrentalapp.data.entity.Voiture;

import java.util.List;

public class VoitureAdapter extends BaseAdapter {

    private Context context;
    private List<Voiture> list;

    public VoitureAdapter(Context context, List<Voiture> list) {
        this.context = context;
        this.list = list;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_voiture, parent, false);
        }

        Voiture v = list.get(i);

        TextView title = view.findViewById(R.id.title);
        TextView sub = view.findViewById(R.id.subtitle);


        title.setText(v.getMarque() + " " + v.getModele());
        sub.setText("Prix: " + v.getPrix_jour() + " | Disponible: " + v.isDisponible());

        TextView voitureId = view.findViewById(R.id.voitureId);
        voitureId.setText("ID: " + v.getId());

        return view;
    }
}