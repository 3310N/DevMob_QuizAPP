package com.example.masbah_quizapp;

import android.util.Log;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Quiz extends AppCompatActivity {

Button Btn1, Btn2, Btn3, Btn4;
private int score = 0;
private String correctAnswer;
private static final String TAG = "MyActivity";

private int totalQuestions = 0;
private List<DocumentSnapshot> questionsList = new ArrayList<>();
private int numQuestionsAnswered = 0;

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_quiz);

	// Retrieve questions from Firestore
	getQuestionsFromFirestore();
}

private void getQuestionsFromFirestore() {
	FirebaseFirestore db = FirebaseFirestore.getInstance();
	db.collection("Quiz")
			.get()
			.addOnCompleteListener(task -> {
				if (task.isSuccessful()) {
					questionsList = task.getResult().getDocuments();
					totalQuestions = questionsList.size(); // Set the total number of questions
					Collections.shuffle(questionsList); // Shuffle the list of documents
					displayNextQuestion();
				} else {
					Log.d(TAG, "Error getting documents: ", task.getException());
				}
			});
}

private void displayNextQuestion() {
	if (questionsList.isEmpty()) {
		// No more questions to display
		// Start ScoreActivity and pass the score
		startActivity(new Intent(Quiz.this, Score.class)
				.putExtra("score", score));
		finish(); // Finish the quiz activity
		return;
	}

	DocumentSnapshot document = questionsList.remove(0);
	String questionText = document.getString("Question");
	List<String> options = (List<String>) document.get("Options");
	String imageUrl = document.getString("ImageUrl");
	correctAnswer = document.getString("correctAnswer");
	displayQuestion(questionText, options, imageUrl);
}

private void displayQuestion(String questionText, List<String> options, String imageUrl) {
	// Find the TextView for the question
	TextView questionTextView = findViewById(R.id.questionTextView);
	questionTextView.setText(questionText);

	ImageView imageView = findViewById(R.id.imageView);
	Glide.with(this).load(imageUrl).into(imageView);

	// Find the Button for each option and set the text
	Btn1 = findViewById(R.id.option1Button);
	Btn1.setText(options.get(0));

	Btn2 = findViewById(R.id.option2Button);
	Btn2.setText(options.get(1));

	Btn3 = findViewById(R.id.option3Button);
	Btn3.setText(options.get(2));

	Btn4 = findViewById(R.id.option4Button);
	Btn4.setText(options.get(3));

	// Set click listeners for the option buttons
	Btn1.setOnClickListener(v -> {
		checkAnswer(options.get(0), correctAnswer);
	});

	Btn2.setOnClickListener(v -> {
		checkAnswer(options.get(1), correctAnswer);
	});

	Btn3.setOnClickListener(v -> {
		checkAnswer(options.get(2), correctAnswer);
	});

	Btn4.setOnClickListener(v -> {
		checkAnswer(options.get(3), correctAnswer);
	});
}

private void checkAnswer(String selectedOption, String correctAnswer) {
	numQuestionsAnswered++;
	if (selectedOption.equals(correctAnswer)) {
		// Answer is correct, add a point to the score
		score++;
		Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
	} else {
		// Answer is incorrect, show a message or provide feedback
		Toast.makeText(this, "Incorrect. Try again!", Toast.LENGTH_SHORT).show();
	}

	// Check if all questions answered
	if (numQuestionsAnswered == totalQuestions) {
		// Start ScoreActivity and pass the email and score
		Intent intent = new Intent(Quiz.this, Score.class);
		intent.putExtra("score", score);
		startActivity(intent);
		finish(); // Finish the quiz activity
	} else {
		// Load the next question
		getQuestionsFromFirestore();
	}
}



private void updateScore() {
	// Find the TextView for the score
	TextView scoreTextView = findViewById(R.id.scoreTextView);
	// Update the scoreTextView with the current score
	scoreTextView.setText("Score: " + score);
}
}
