package com.example.masbah_quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Score extends AppCompatActivity {

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_score);

    // Get the score from the intent
    Intent intent = getIntent();
    int score = intent.getIntExtra("score", 0);

    // Display the score
    TextView scoreTextView = findViewById(R.id.scoreTextView);
    scoreTextView.setText("Score: " + score);
}
}