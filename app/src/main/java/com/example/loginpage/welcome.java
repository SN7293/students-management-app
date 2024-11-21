package com.example.loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class welcome extends AppCompatActivity {

    private Button signInButton, signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_actvity);


        signInButton = findViewById(R.id.signInButton);
        signUpButton = findViewById(R.id.signUpButton);


        signInButton.setOnClickListener(v -> {
            Intent intent = new Intent(welcome.this, LoginActivity.class);
            startActivity(intent);
        });


        signUpButton.setOnClickListener(v -> {
            Intent intent = new Intent(welcome.this, MainActivity.class);
            startActivity(intent);
        });
    }

}

