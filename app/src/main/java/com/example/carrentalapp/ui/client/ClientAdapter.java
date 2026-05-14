package com.example.carrentalapp.ui.client;

import android.content.Context;
import android.view.*;
import android.widget.*;

import com.example.carrentalapp.R;
import com.example.carrentalapp.data.entity.Client;

import java.util.List;

public class ClientAdapter extends BaseAdapter {

    private Context context;
    private List<Client> list;

    public ClientAdapter(Context context, List<Client> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_client, parent, false);
        }

        Client c = list.get(i);

        TextView name = view.findViewById(R.id.name);
        TextView info = view.findViewById(R.id.info);
        TextView idTxt = view.findViewById(R.id.clientId);
        idTxt.setText("ID: " + c.getId());

        name.setText(c.getNom() + " " + c.getPrenom());
        info.setText(c.getEmail() + " | " + c.getTelephone());

        return view;
    }
}