package com.example.carrentalapp.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.carrentalapp.data.dao.ClientDao;
import com.example.carrentalapp.data.dao.PaiementDao;
import com.example.carrentalapp.data.dao.ReservationDao;
import com.example.carrentalapp.data.dao.UserDao;
import com.example.carrentalapp.data.dao.VoitureDao;
import com.example.carrentalapp.data.entity.Client;
import com.example.carrentalapp.data.entity.Paiement;
import com.example.carrentalapp.data.entity.Reservation;
import com.example.carrentalapp.data.entity.User;
import com.example.carrentalapp.data.entity.Voiture;

@Database(
        entities = {User.class, Voiture.class, Client.class, Reservation.class, Paiement.class},
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    // DAO
    public abstract UserDao userDao();
    public abstract VoitureDao voitureDao();
    public abstract ClientDao clientDao();
    public abstract ReservationDao reservationDao();
    public abstract PaiementDao paiementDao();

    // Singleton
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "car_rental_db"
                    )
                    .allowMainThreadQueries() // simplification (niveau étudiant)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    // 🔥 Initialisation des données (UNE SEULE FOIS)
    private static RoomDatabase.Callback roomCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onCreate(androidx.sqlite.db.SupportSQLiteDatabase db) {
                    super.onCreate(db);

                    new Thread(() -> {
                        AppDatabase database = instance;

                        // Admin par défaut
                        database.userDao().insert(new User(
                                "admin@gmail.com",
                                "1234",
                                "admin"
                        ));

                        // Voitures initiales
                        database.voitureDao().insert(new Voiture("Toyota", "Yaris", 2020, 300, true));
                        database.voitureDao().insert(new Voiture("Dacia", "Logan", 2019, 200, true));
                        database.voitureDao().insert(new Voiture("BMW", "X5", 2022, 800, true));

                        // Client exemple
                        database.clientDao().insert(new Client(
                                "CLIENT",
                                "TEST",
                                "test@example.com",
                                "0600000000"
                        ));

                    }).start();
                }
            };
}