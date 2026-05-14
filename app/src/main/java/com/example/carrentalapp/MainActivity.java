package com.example.carrentalapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carrentalapp.ui.auth.LoginActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Redirection directe vers Login
        startActivity(new Intent(this, LoginActivity.class));
        finish();


    }

}