package com.example.masbah_quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Score extends AppCompatActivity {
    Button bLogout, bTry;
    ProgressBar progressBar;
    TextView tvScore;
    int score;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        tvScore =(TextView) findViewById(R.id.tvScore);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        bLogout=(Button) findViewById(R.id.bLogout);
        bTry=(Button) findViewById(R.id.bTry);
        Intent intent=getIntent();
        score=intent.getIntExtra("score",0) ;
        progressBar.setProgress(100*score/5);
        tvScore.setText(100*score/5+" %");
        //Toast.makeText(getApplicationContext(),score+"",Toast.LENGTH_SHORT).show();
        bLogout.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Merci de votre Participation !", Toast.LENGTH_SHORT).show();
            finish();
        });
        bTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Score.this,Quiz1.class));
            }
        });

    }
}
