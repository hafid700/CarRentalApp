package com.example.carrentalapp.ui.client;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import com.example.carrentalapp.utils.SessionManager;

import com.example.carrentalapp.R;
import com.example.carrentalapp.data.database.AppDatabase;
import com.example.carrentalapp.data.entity.Voiture;

import java.util.List;

public class ClientHomeActivity extends AppCompatActivity {

    ListView listView;
    AppDatabase db;

    EditText searchInput;
    TextView countLabel;
    List<Voiture> fullList;
    List<Voiture> filteredList;
    ClientVoitureAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SessionManager session = new SessionManager(this);
        int userId = session.getUserId();
        Toast.makeText(this, "Bienvenue 👋  ID: " + userId, Toast.LENGTH_SHORT).show();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home);

        listView    = findViewById(R.id.listView);
        searchInput = findViewById(R.id.searchInput);
        countLabel  = findViewById(R.id.countLabel);
        db          = AppDatabase.getInstance(this);

        loadData();

        // Quick-action cards (LinearLayout, not Button)
        LinearLayout historiqueBtn = findViewById(R.id.historiqueBtn);
        historiqueBtn.setOnClickListener(v ->
                startActivity(new Intent(this, ClientReservationActivity.class))
        );

        LinearLayout profileBtn = findViewById(R.id.profileBtn);
        profileBtn.setOnClickListener(v ->
                startActivity(new Intent(this, ClientProfileActivity.class))
        );
    }

    private void loadData() {

        fullList     = db.voitureDao().getAllVoitures();
        filteredList = new java.util.ArrayList<>(fullList);
        adapter      = new ClientVoitureAdapter(this, filteredList);
        listView.setAdapter(adapter);

        listView.getViewTreeObserver().addOnGlobalLayoutListener(() ->
                setListViewHeightBasedOnChildren(listView)
        );

        updateCount();

        searchInput.addTextChangedListener(new android.text.TextWatcher() {

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().toLowerCase();
                filteredList.clear();
                for (Voiture v : fullList) {
                    String text = (v.getMarque() + " " + v.getModele()).toLowerCase();
                    if (text.contains(query)) {
                        filteredList.add(v);
                    }
                }
                adapter.notifyDataSetChanged();
                updateCount();
            }

            @Override public void afterTextChanged(android.text.Editable s) {}
        });
    }

    private void updateCount() {
        if (countLabel != null) {
            countLabel.setText(filteredList.size() + " voiture" + (filteredList.size() > 1 ? "s" : ""));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        fullList.clear();
        fullList.addAll(db.voitureDao().getAllVoitures());
        filteredList.clear();
        filteredList.addAll(fullList);
        adapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(listView);
        updateCount();
    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        if (listView.getAdapter() == null) return;
        int totalHeight = 0;
        for (int i = 0; i < listView.getAdapter().getCount(); i++) {
            View item = listView.getAdapter().getView(i, null, listView);
            item.measure(
                    View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            );
            totalHeight += item.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listView.getAdapter().getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}