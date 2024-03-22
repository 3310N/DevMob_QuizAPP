package com.example.masbah_quizapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // STEP 1: Declaration of variables
     EditText etLogin, etPassword;
     Button bLogin;
     TextView tvRegister;
    private boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // STEP 2: Recuperation des Ids
        etLogin= findViewById(R.id.etMail);
        etPassword= findViewById(R.id.etPassword);
        bLogin= findViewById(R.id.bLogin);
        tvRegister= findViewById(R.id.tvRegister);

        // STEP 3: Creation of the listener
        bLogin.setOnClickListener(view -> {
            // STEP 4: Action to be performed
            if (etLogin.getText().toString().equals("toto") && etPassword.getText().toString().equals("123")){
                startActivity(new Intent(MainActivity.this, Quiz1.class));
            }
            else {
                Toast.makeText(getApplicationContext(),"Login or password incorrect !",Toast.LENGTH_SHORT).show();
            }
        });
        tvRegister.setOnClickListener(view -> {
            // STEP 4: Traitement
            startActivity(new Intent(MainActivity.this, Register.class));
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }
}