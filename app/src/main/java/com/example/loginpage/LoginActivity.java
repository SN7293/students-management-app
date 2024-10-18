package com.example.loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    // Declare UI components and Firestore instance
    EditText emailEditText;
    EditText passwordEditText;
    Button loginBtn;
    TextView signUpLink; // Declare sign-up link
    public FirebaseFirestore db;  // Firebase Firestore instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize UI components
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.Password);
        loginBtn = findViewById(R.id.Login);
        signUpLink = findViewById(R.id.sign_up_link); // Initialize sign-up link

        // Set the login button listener
        loginBtn.setOnClickListener(v -> {
            String mail = emailEditText.getText().toString().trim();
            String pass = passwordEditText.getText().toString().trim();

            // Check if email or password fields are empty
            if (mail.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
            } else if (pass.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
            } else {
                // Authenticate the user
                userAuth(mail, pass);
            }
        });

        // Set the sign-up link listener
        signUpLink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class); // Replace with your Sign Up Activity class
            startActivity(intent);
        });
    }

    // Method to authenticate user
    public void userAuth(String mail, String pass) {
        // Ensure the Firestore instance is initialized
        if (db != null) {
            // Query Firestore for the user's email
            db.collection("User")
                    .whereEqualTo("email", mail)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();

                            if (!querySnapshot.isEmpty()) {
                                // If user exists, check password
                                for (QueryDocumentSnapshot document : querySnapshot) {
                                    Modeluser user = document.toObject(Modeluser.class);

                                    if (user.password.equals(pass)) {
                                        // Password matches, login successful
                                        Intent intent = new Intent(LoginActivity.this, ActivityStudent.class);
                                        startActivity(intent);
                                        finish();
                                        Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Incorrect password
                                        Toast.makeText(LoginActivity.this, "Incorrect password!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                // No user found with that email
                                Toast.makeText(LoginActivity.this, "User not found!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Error fetching data
                            Toast.makeText(LoginActivity.this, "Error fetching data: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            // Firebase Firestore is not initialized
            Toast.makeText(LoginActivity.this, "Firestore is not initialized", Toast.LENGTH_SHORT).show();
        }
    }
}
