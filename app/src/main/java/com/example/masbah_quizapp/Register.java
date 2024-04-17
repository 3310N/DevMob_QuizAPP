package com.example.masbah_quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
TextInputEditText editTextEmail, editTextPassword;
Button signUp;
TextView signIn;
FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_register);

    editTextEmail = findViewById(R.id.email);
    editTextPassword = findViewById(R.id.password);
    signIn = findViewById(R.id.sign_in);
    signUp = findViewById(R.id.sign_up);

    signUp.setOnClickListener(v -> {
        Intent intent = new Intent(Register.this, MainActivity.class);
        startActivity(intent);
        finish();
    });

    signUp.setOnClickListener(v -> {

        String email, password;
        email = String.valueOf(editTextEmail.getText());
        password = String.valueOf(editTextPassword.getText());
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(Register.this, "Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(Register.this, "Enter Password", Toast.LENGTH_SHORT).show();
        }

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(Register.this, "User Registered Successfully ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Register.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(Register.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                });

    });


}
}