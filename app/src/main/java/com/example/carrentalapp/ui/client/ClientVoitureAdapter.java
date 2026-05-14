package com.example.carrentalapp.ui.client;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.view.*;
import android.widget.*;

import com.example.carrentalapp.R;
import com.example.carrentalapp.data.database.AppDatabase;
import com.example.carrentalapp.data.entity.*;
import com.example.carrentalapp.ui.paiement.PaiementActivity;
import com.example.carrentalapp.utils.SessionManager;

import java.util.Calendar;
import java.util.List;

public class ClientVoitureAdapter extends BaseAdapter {

    Context context;
    List<Voiture> list;
    AppDatabase db;

    public ClientVoitureAdapter(Context context, List<Voiture> list) {
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
                    .inflate(R.layout.item_client_voiture, parent, false);
        }

        Voiture v = list.get(i);

        TextView title      = view.findViewById(R.id.title);
        TextView priceLabel = view.findViewById(R.id.priceLabel);
        TextView dateLabel  = view.findViewById(R.id.dateLabel);
        TextView statusBadge = view.findViewById(R.id.statusBadge);
        Button btn          = view.findViewById(R.id.reserveBtn);

        // Nom du véhicule
        title.setText(v.getMarque() + " " + v.getModele());

        // Prix par jour
        priceLabel.setText(v.getPrix_jour() + " DH / jour");

        if (v.isDisponible()) {
            // ✅ Disponible
            statusBadge.setText("✓ Dispo");
            statusBadge.setTextColor(android.graphics.Color.parseColor("#1DB954"));
            statusBadge.setBackgroundResource(R.drawable.bg_badge_green);

            dateLabel.setText("Disponible maintenant");

            btn.setEnabled(true);
            btn.setText("Réserver");
            btn.setBackgroundResource(R.drawable.bg_btn_green);
            btn.setTextColor(android.graphics.Color.BLACK);
            btn.setOnClickListener(vw -> reserve(v));

        } else {
            // 🔴 Indisponible
            statusBadge.setText("✗ Indispo");
            statusBadge.setTextColor(android.graphics.Color.parseColor("#FF4444"));
            statusBadge.setBackgroundResource(R.drawable.bg_badge_red);

            Reservation r = db.reservationDao().getLastReservationByVoiture(v.getId());
            if (r != null) {
                dateLabel.setText("Disponible le " + r.getDate_fin());
            } else {
                dateLabel.setText("Non disponible");
            }

            btn.setEnabled(false);
            btn.setText("Indisponible");
            btn.setBackgroundResource(R.drawable.bg_card_dark);
            btn.setTextColor(android.graphics.Color.parseColor("#555555"));
            btn.setOnClickListener(null);
        }

        return view;
    }

    private void reserve(Voiture voiture) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_reservation_dialog, null);

        Button dateDebutBtn = view.findViewById(R.id.dateDebutBtn);
        Button dateFinBtn   = view.findViewById(R.id.dateFinBtn);
        TextView resultTxt  = view.findViewById(R.id.resultTxt);

        Calendar debut = Calendar.getInstance();
        Calendar fin   = Calendar.getInstance();
        final int[] jours = {0};

        dateDebutBtn.setOnClickListener(v -> {
            DatePickerDialog dialog = new DatePickerDialog(context,
                    (view1, y, m, d) -> {
                        debut.set(y, m, d);
                        dateDebutBtn.setText(d + "/" + (m + 1) + "/" + y);
                    },
                    debut.get(Calendar.YEAR),
                    debut.get(Calendar.MONTH),
                    debut.get(Calendar.DAY_OF_MONTH));
            dialog.getDatePicker().setMinDate(System.currentTimeMillis());
            dialog.show();
        });

        dateFinBtn.setOnClickListener(v -> {
            DatePickerDialog dialog = new DatePickerDialog(context,
                    (view12, y, m, d) -> {
                        fin.set(y, m, d);
                        dateFinBtn.setText(d + "/" + (m + 1) + "/" + y);

                        if (fin.before(debut)) {
                            Toast.makeText(context, "Date fin invalide", Toast.LENGTH_SHORT).show();
                            jours[0] = 0;
                            resultTxt.setText("Dates invalides ❌");
                            return;
                        }

                        long diff = fin.getTimeInMillis() - debut.getTimeInMillis();
                        jours[0] = (int) (diff / (1000 * 60 * 60 * 24));
                        double total = voiture.getPrix_jour() * jours[0];
                        resultTxt.setText("Jours: " + jours[0] + " | Total: " + total + " DH");
                    },
                    fin.get(Calendar.YEAR),
                    fin.get(Calendar.MONTH),
                    fin.get(Calendar.DAY_OF_MONTH));
            dialog.getDatePicker().setMinDate(System.currentTimeMillis());
            dialog.show();
        });

        new AlertDialog.Builder(context)
                .setTitle("🚗 Réserver " + voiture.getMarque() + " " + voiture.getModele())
                .setView(view)
                .setPositiveButton("Confirmer", (dialog, which) -> {

                    if (jours[0] <= 0) {
                        Toast.makeText(context, "Veuillez choisir des dates valides", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Calendar today = Calendar.getInstance();
                    if (debut.before(today)) {
                        Toast.makeText(context, "Date début invalide", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    double total = voiture.getPrix_jour() * jours[0];

                    SessionManager session = new SessionManager(context);
                    int userId = session.getUserId();

                    User user     = db.userDao().getUserById(userId);
                    Client client = db.clientDao().getClientByEmail(user.getEmail());
                    int clientId  = client.getId();

                    Reservation reservation = new Reservation(
                            clientId,
                            voiture.getId(),
                            dateDebutBtn.getText().toString(),
                            dateFinBtn.getText().toString(),
                            total,
                            "en_cours"
                    );

                    db.reservationDao().insert(reservation);

                    voiture.setDisponible(false);
                    db.voitureDao().update(voiture);

                    Toast.makeText(context, "✅ Réservation confirmée !", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(context, PaiementActivity.class);
                    intent.putExtra("montant", total);
                    context.startActivity(intent);

                    list.remove(voiture);
                    notifyDataSetChanged();
                })
                .setNegativeButton("Annuler", null)
                .show();
    }
}