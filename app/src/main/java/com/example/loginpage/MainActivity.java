package com.example.loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;


public class MainActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    EditText confirmPasswordEditText;
    Button signupBtn;



    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.Password);
        confirmPasswordEditText = findViewById(R.id.CONFIRMPASSWORD);
        signupBtn = findViewById(R.id.SignUp);

        db = FirebaseFirestore.getInstance();  // Initialize Firestore instance

        signupBtn.setOnClickListener(v -> {
            String mail = emailEditText.getText().toString().trim();
            String pass = passwordEditText.getText().toString().trim();
            String confirmPass = confirmPasswordEditText.getText().toString().trim();

            if (validateInput(mail, pass, confirmPass)) {
                // Create a new User object
                Modeluser user = new Modeluser(mail, pass,confirmPass);

                // Add the user to the Firestore database
                db.collection("User")
                        .add(user)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(MainActivity.this, "Sign-up successful!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                        })
                        .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });
    }

    private boolean validateInput(String mail, String pass, String confirmPass) {
        if (mail.isEmpty()) {
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (pass.isEmpty()) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!pass.equals(confirmPass)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
