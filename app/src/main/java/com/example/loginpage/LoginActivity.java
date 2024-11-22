package com.example.loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginBtn;
    private TextView signUpLink;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.Password);
        loginBtn = findViewById(R.id.Login);
        signUpLink = findViewById(R.id.sign_up_link);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        loginBtn.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            authViewModel.loginUser(email, password);
        });

        signUpLink.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, MainActivity.class)));

        observeViewModel();
    }

    private void observeViewModel() {
        authViewModel.getSuccessMessage().observe(this, success -> {
            Toast.makeText(this, success, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, ActivityStudent.class);
            startActivity(intent);
            finish();
        });

        authViewModel.getErrorMessage().observe(this, error ->
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        );
    }
}
