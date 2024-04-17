package com.example.masbah_quizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.widget.Button;
import android.widget.TextView;
import android.Manifest;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePage extends AppCompatActivity {

private static final int PERMISSIONS_REQUEST_CODE = 100;
private final String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};

@SuppressLint("SetTextI18n")
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_home_page);

	FirebaseAuth mAuth = FirebaseAuth.getInstance();
	TextView welcomeTextView = findViewById(R.id.welcomeTextView);
	Button startQuizButton = findViewById(R.id.startQuizButton);

	startQuizButton.setOnClickListener(v -> {
		// Request permissions if not already granted
		if (!checkPermissions()) {
			requestPermissions();
		} else {
			// All permissions granted, start the quiz activity
			startActivity(new Intent(HomePage.this, Quiz.class));
		}
	});

	FirebaseUser currentUser = mAuth.getCurrentUser();
	if (currentUser != null) {
		String userEmail = currentUser.getEmail();
		String welcomeText = "Welcome, " + userEmail + "!";

		SpannableString spannableString = new SpannableString(welcomeText);
		assert userEmail != null;
		spannableString.setSpan(new StyleSpan(Typeface.BOLD), 8, 8 + userEmail.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		welcomeTextView.setText(spannableString);
	}

}

private boolean checkPermissions() {
	for (String permission : permissions) {
		if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
			return false;
		}
	}
	return true;
}

private void requestPermissions() {
	ActivityCompat.requestPermissions(this, permissions, PERMISSIONS_REQUEST_CODE);
}

@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
	super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	if (requestCode == PERMISSIONS_REQUEST_CODE) {
		if (checkPermissions()) {
			// All permissions granted, start the quiz activity
			startActivity(new Intent(HomePage.this, Quiz.class));
		} else {
			System.out.println("Permissions denied");
			// Permissions denied, show a message or disable functionality
		}
	}
}
}